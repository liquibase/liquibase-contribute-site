// Usersnap Feedback button (right side of page)
window.onUsersnapLoad = function(api) {
   api.init();
}
var script = document.createElement('script');
script.defer = 1;
script.src = 'https://widget.usersnap.com/global/load/632b7a0e-ba7b-4613-ab8a-a34d8edf1097?onload=onUsersnapLoad';
document.getElementsByTagName('head')[0].appendChild(script);

// Usersnap Sentiment widget (bottom of page)
document.addEventListener("DOMContentLoaded", function() {
    var contentWrapper = document.querySelector('article.md-content__inner');
    var rateWidgetContainer = document.createElement("div")
    rateWidgetContainer.id = "rateWidgetContainer";
    contentWrapper.appendChild(rateWidgetContainer);


    var rateScript = document.createElement('script');
    rateScript.defer = 1;
    rateScript.src = 'https://widget.usersnap.com/embed/load/a47dad89-4a65-4340-85ff-7e9cfc75d2d6?onload=onUsersnapCXLoad';
    document.getElementsByTagName('body')[0].appendChild(rateScript);

    window.onUsersnapCXLoad = function(api) {
        api.init({ mountNode: document.getElementById('rateWidgetContainer') });
    }
});

// This DOMContentLoaded listener moves the Home/Answer/Write/Advocate/Code/Extensions&Integrations nav from 
// where it goes by default in the mkdocs theme to where we wanted it in order to fit our liquibase.com layout style. 
// Varun came up with using JS to move it around rather than CSS or changing the theme somehow.
document.addEventListener("DOMContentLoaded", function(){
    var headerNavBody = document.querySelector('.md-header__nav');
    var navWrapper = document.querySelector('nav.md-tabs');
    var navBody = document.querySelector('nav.md-tabs ul.md-tabs__list');
    var navClone = navBody.cloneNode(true);

    navWrapper.remove();
    headerNavBody.appendChild(navClone);
});

// CommonRoom Integration
(function() {
  if (typeof window === 'undefined') return;
  if (typeof window.signals !== 'undefined') return;
  var script = document.createElement('script');
  script.src = 'https://cdn.cr-relay.com/v1/site/e1a23843-37bf-4fbc-aabd-3d76832c0198/signals.js';
  script.async = true;
  window.signals = Object.assign(
    [],
    ['page', 'identify', 'form'].reduce(function (acc, method){
      acc[method] = function () {
        signals.push([method, arguments]);
        return signals;
      };
     return acc;
    }, {})
  );
  document.head.appendChild(script);
})();
