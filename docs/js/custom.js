// window.onUsersnapLoad = function(api) {
//     api.init();
// }
// var script = document.createElement('script');
// script.defer = 1;
// script.src = 'https://widget.usersnap.com/global/load/632b7a0e-ba7b-4613-ab8a-a34d8edf1097?onload=onUsersnapLoad';
// document.getElementsByTagName('head')[0].appendChild(script);

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

document.addEventListener("DOMContentLoaded", function(){
    var headerNavBody = document.querySelector('.md-header__nav');
    var navWrapper = document.querySelector('nav.md-tabs');
    var navBody = document.querySelector('nav.md-tabs ul.md-tabs__list');
    var navClone = navBody.cloneNode(true);

    navWrapper.remove();
    headerNavBody.appendChild(navClone);
});
