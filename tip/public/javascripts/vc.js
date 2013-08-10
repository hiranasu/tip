$(function() {
	var socket = new WebSocket("ws://localhost:9001/echowebsocketmessage");
    socket.onmessage = function(event) {
    	// 受信処理
    }
    
    var mapOptions = {
        zoom: 16,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: false
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    var geoErr = function(flag){
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
    }, function(){
            geoErr(1);
        });
    } else {
        geoErr(2);
    }
    $('#map_content').on('pageshow', function(){
        google.maps.event.trigger(map, 'resize');
        map.setCenter(Latlng); 
    });
});