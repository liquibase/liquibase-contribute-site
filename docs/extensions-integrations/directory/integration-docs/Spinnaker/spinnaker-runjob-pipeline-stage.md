---
title: RunJob Pipeline Stage
---

# Using the Run Job Pipeline Stage with Spinnaker

Spinnaker can execute Liquibase using the official [Liquibase Docker image](https://hub.docker.com/r/liquibase/liquibase). The following diagram shows the Run Job pipeline stage executing a docker run to perform database updates:

![Docker RunJob Pipeline Stage](/images/extensions-integrations/docker.png)

## Setting up Spinnaker and the Run Job pipeline stage

### Step 1: Install Spinnaker

Make sure that you installed Spinnaker. You can use the [Spinnaker Quick Start](https://github.com/armory/minnaker) provided by [Armory](https://www.armory.io/).

### Step 2: Configure Run Job pipeline stage

Configure the Run Job pipeline stage by [setting up a docker registry](https://spinnaker.io/setup/install/providers/docker-registry/) so that Spinnaker can access the images to run.

### Step 3: Configure the manifest

Use the following manifest source text (YAML):

```
apiVersion: batch/v1
kind: Job
metadata:
 name: liquibase-runner
spec:
 backoffLimit: 0
 template:
   spec:
     containers:
        - args:
        - '--classpath=/workspace/example/changelogs'
        - '--changelog-file=samplechangelog.h2.sql'
        - '--username=liqubase'
        - '--password=password'
        - '--url=jdbc:sqlserver://<IP OR HOSTNAME>:1433;database=<database name>'
        - update
        image: 'index.docker.io/liquibase/liquibase:4.1'
        name: liquibase
        volumeMounts:
          - mountPath: /workspace
            name: workspace
        initContainers:
          - command:
              - git
              - clone
              - '--single-branch'
              - '--branch'
              - $(BRANCH)
              - $(REPO)
              - /workspace
            env:
              - name: BRANCH
                value: main
              - name: REPO
                value: 'https://github.com/liquibase/liquibase-github-action-example'
            image: alpine/git
            name: git
            volumeMounts:
              - mountPath: /workspace
                name: workspace
        restartPolicy: Never
        volumes:
          - emptyDir: {}
            name: workspace
```

The following parameters are hard coded for reference:

*   BRANCH: main
*   REPO: [https://github.com/liquibase/liquibase-github-action-example](https://github.com/liquibase/liquibase-github-action-example)

!!! note
    If you use an Oracle database, use the sample JDBC URL for Oracle: `--url=jdbc:oracle:thin:@<IP OR HOSTNAME>:<PORT>/<SERVICE NAME OR SID>`. The format for the JDBC URL for MSSQL Server is the following: `--url=jdbc:sqlserver://<IP OR HOSTNAME>:>:<PORT>;database=<database name>`.

Once you have created the job, you will see the following in Spinnaker:

![Spinnaker Workflow](/images/extensions-integrations/spinnaker_workflow.png)

### Step 4: Run Liquibase

Select **Start Manual Execution** in the upper-right corner of the pipeline. Now, you will see a Liquibase `update` in the Spinnaker console:

![Spinnaker Console](/images/extensions-integrations/spinnaker_console.png)

Pipeline as code (JSON)
-----------------------

As all Spinnaker jobs are based on JSON, see JSON for the job you used if you want to manage your Spinnaker pipeline as a code:

```
{
 "keepWaitingPipelines": false,
 "lastModifiedBy": "admin",
 "limitConcurrent": true,
 "spelEvaluator": "v4",
 "stages": [
   {
	 "account": "spinnaker",
	 "alias": "runJob",
	 "application": "liquibasejenkins",
	 "cloudProvider": "kubernetes",
	 "credentials": "spinnaker",
	 "manifest": {
	   "apiVersion": "batch/v1",
	   "kind": "Job",
	   "metadata": {
		 "name": "liquibase-runner"
	   },
		"spec": {
		  "backoffLimit": 0,
		  "template": {
		    "spec": {
		      "containers": [
		        {
		          "args": [
		            "--classpath=/workspace/example/changelogs",
					"--changelog-file=samplechangelog.h2.sql",
					"--username=liqubase",
					"--password=password",
					"--url=jdbc:h2:file:./H2_project/h2tutorial",
					"update"
				  ],
				  "image": "index.docker.io/liquibase/liquibase:4.1",
				  "name": "liquibase",
				  "volumeMounts": [
		            {
		              "mountPath": "/workspace",
					  "name": "workspace"
		            }
				  ]
		        }
		      ],
		      "initContainers": [
		        {
		          "command": [
					"git",
					"clone",
					"--single-branch",
					"--branch",
					"$(BRANCH)",
					"$(REPO)",
					"/workspace"
				  ],
			      "env": [
		            {
		               "name": "BRANCH",
		               "value": "main"
		            },
		            {
		               "name": "REPO",
		               "value": "https://github.com/liquibase/liquibase-github-action-example"
		            }
		          ],
				  "image": "alpine/git",
		          "name": "git",
		          "volumeMounts": [
		            {
		              "mountPath": "/workspace",
		              "name": "workspace"
		            }
		          ]
		        }
		      ],
		      "restartPolicy": "Never",
		      "volumes": [
		        {
				  "emptyDir": {},
				  "name": "workspace"
				}
			  ]
		    }
		  }
		}
	  },
	  "name": "Run Job (Manifest)",
	  "refId": "1",
	  "requisiteStageRefIds": [],
	  "source": "text",
	  "type": "runJobManifest"
	 }
   ],
   "triggers": [],
   "updateTs": "1603738482000"
}
```

## Related links

*   [Extending Spinnaker with Kubernetes and Containers](https://blog.spinnaker.io/extending-spinnaker-with-kubernetes-and-containers-5d16ec810d81)