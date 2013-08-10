$(function() {
    var mapOptions = {
        zoom: 16,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: false
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    console.log("A");
    var geoErr = function(flag){
        console.log("B" + flag);
        var cont = "Error: The Geolocation service failed.";
        var opt = {
            map: map,
            position: new google.maps.LatLng(35.69167,139.700211),
            content: cont
        };
        var info = new google.maps.InfoWindow(opt);
        map.setCenter(opt.position);
    }
    
    if(navigator.geolocation) {
        console.log("C");
        navigator.geolocation.getCurrentPosition(function(position) {
        var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        map.setCenter(latlng);
        var marker = new google.maps.Marker({
            position:latlng,
            map: map
        });
        var marker = new google.maps.Marker({
            position:latlng,
            map: map
        });
        console.log("D");
    }, function(){
            geoErr(1);
        });
    } else {
        console.log("E");
        geoErr(2);
    }
    console.log("F");
    $('#map_content').on('pageshow', function(){
        google.maps.event.trigger(map, 'resize');
        map.setCenter(Latlng); 
    });
    console.log("G");
});