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

function pageScroll() {
    window.scrollBy(0,1);
    scrolldelay = setTimeout(pageScroll,50);
}

// pageScroll();