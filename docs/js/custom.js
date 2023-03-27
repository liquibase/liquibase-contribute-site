window.onUsersnapLoad = function(api) {
    api.init();
}
var script = document.createElement('script');
script.defer = 1;
script.src = 'https://widget.usersnap.com/global/load/632b7a0e-ba7b-4613-ab8a-a34d8edf1097?onload=onUsersnapLoad';
document.getElementsByTagName('head')[0].appendChild(script);


document.addEventListener("DOMContentLoaded", function(){
    var headerNavBody = document.querySelector('.md-header__nav');
    var navWrapper = document.querySelector('nav.md-tabs');
    var navBody = document.querySelector('nav.md-tabs ul.md-tabs__list');
    var navClone = navBody.cloneNode(true);

    navWrapper.remove();
    headerNavBody.appendChild(navClone);
});