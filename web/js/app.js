if ('serviceWorker' in navigator) {
    console.log('CLIENT: service worker registration in progress.');
    navigator.serviceWorker.register('service-worker.js').then(function() {
        console.log('CLIENT: service worker registration complete.');
    }).catch(function(ex) {
        console.log('CLIENT: service worker registration failure.' + ex);
    });
} else {
    console.log('CLIENT: service worker is not supported.');
}

$("#switchChords").click(function(){
    console.log("HELLEO")
    $(".accompaniment").toggleClass("hidden");
});

$("#enlarge").click(function(){
    let currentSize = $(".flex-container div").css('font-size').replace("px", "");
    let newSize = parseInt(currentSize, 10) + 6;
    $(".flex-container div").css('font-size', newSize + "px");
});

$("#reduce").click(function(){
    let currentSize = $(".flex-container div").css('font-size').replace("px", "");
    let newSize = parseInt(currentSize, 10) - 6;
    $(".flex-container div").css('font-size', newSize + "px");
});

// function pageScroll() {
//     window.scrollBy(0,1);
//     scrolldelay = setTimeout(pageScroll,50);
// }

function getUrlParameter(key) {
    var query = window.location.search.substring(1);
    var pairs = query.split('&');

    for (var i = 0; i < pairs.length; i++) {
        var pair = pairs[i].split('=');
        if(pair[0] == key) {
            if(pair[1].length > 0)
                return pair[1];
        }
    }
    return undefined;
};

var key = 'referer';
var referer = getUrlParameter(key);

// if (referer) alert(referer);
// document.location.replace(referer);

$("#back").click(function(){
    document.location.replace(referer);
});


var isAutoscrollInterrupted = true;
var isAutoScrollEnabled = false;

function doScroll(){
    if(!isAutoscrollInterrupted) {
        window.scrollBy(0, 1);
        scrolldelay = setTimeout(doScroll, 50);
    }
}

function scrollOff(){
    console.log ("no Scroll!");
    window.scroll(0,0);
}

function switchScroll(){
    if(isAutoScrollEnabled){
        if(isAutoscrollInterrupted){
            isAutoscrollInterrupted = false;
            doScroll();
        }else{
            isAutoscrollInterrupted = true;
        }
    }
}
function enableDisableScroll(){
    var autoScrollButton = document.getElementById('autoscroll');
    if(isAutoScrollEnabled){
        isAutoScrollEnabled = false;
        autoScrollButton.innerText="Set autoscroll ON";
    }else{
        isAutoScrollEnabled = true;
        autoScrollButton.innerText="Set autoscroll OFF";
    }
}
function prepare(){
    var autoscroll = document.getElementById('autoscroll');
    autoscroll.addEventListener('click', enableDisableScroll);
    $(".flex-container").click(function() {
        console.log("User click detected!")
        switchScroll()
    });
}


// pageScroll();