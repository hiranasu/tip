$(function() {
    /* Google Map の表示 */
    // 中心地点: ミッドタウン
    var latlng = new google.maps.LatLng(35.665721,139.731006);
     
    var mapOptions = {
        zoom: 16,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControl: false
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
 
    // Marker Image
    greenMarker = new google.maps.MarkerImage(
        'assets/images/mark_green.png',
        new google.maps.Size(42,60),  // size
        new google.maps.Point(0,0),   // origin
        new google.maps.Point(21,60), // anchor
        new google.maps.Size(42,60)   // Scale
    );
    yellowMarker = new google.maps.MarkerImage(
        'assets/images/mark_yellow.png',
        new google.maps.Size(42,60),  // size
        new google.maps.Point(0,0),   // origin
        new google.maps.Point(21,60), // anchor
        new google.maps.Size(42,60)   // Scale
    );
    redMarker = new google.maps.MarkerImage(
        'assets/images/mark_red.png',
        new google.maps.Size(42,60),  // size
        new google.maps.Point(0,0),   // origin
        new google.maps.Point(21,60), // anchor
        new google.maps.Size(42,60)   // Scale
    );
    grayMarker = new google.maps.MarkerImage(
        'assets/images/mark_gray.png',
        new google.maps.Size(42,60),  // size
        new google.maps.Point(0,0),   // origin
        new google.maps.Point(21,60), // anchor
        new google.maps.Size(42,60)   // Scale
    );
    markerArray = [greenMarker, yellowMarker, redMarker, grayMarker];
 
    // Pin
    var pin = new Array();
    var pinData = new Array();
 
    // WebSocket
    //var host = "ws://localhost:9000/ws";
    var host = "ws://www9057ue.sakura.ne.jp:9000/ws";
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
    var socket = new WS(host);
     
    // 受信処理
    socket.onmessage = function(messageEvent) {
        var data = JSON.parse(messageEvent.data);
 
        var i = 0;
        for (i = 0; i < data.rooms.length; i = i + 1) {
 
            var object = data.rooms[i];
 
            if (pin[object.roomid]) {
                pin[object.roomid].setMap(null);
            }
            pin[object.roomid] = new google.maps.Marker({
                position: new google.maps.LatLng(object.roomlat, object.roomlon),
                map: map,
                icon: markerArray[object.roomstat]
            });
            // PinData 格納
             
 
/*
        google.maps.event.addListener(pin[object.roomid], 'click', function() {
            $.mobile.changePage($("#advancedSrch"), {data-transition= "slideup"});
        });
*/
 
            // TextChange
            numOfEmp = document.getElementById("numOfEmpSeats");
            numOfEmp.innerHTML = object.emptynum;
            // ColorChange
            if (object.roomstat == 0) {
                numOfEmp.className = 'empty';
            }
            else if (object.roomstat == 1) {
                numOfEmp.className = 'lastone';
            }
            else if (object.roomstat == 2) {
                numOfEmp.className = 'occupied';
            }
            else {
                numOfEmp.className = 'closed';
            }
            // TextChange
            numOfTotal = document.getElementById("numOfTotalSeats");
            numOfTotal.innerHTML = object.totalnum;
        }
    };
 
 
 
    //-------------------------------------------------------
    // 適当に散らばせておくピン
    //-------------------------------------------------------
 
    // ガーナ大使館
    var marker1 = new google.maps.Marker({
        position: new google.maps.LatLng(35.661686,139.72543),
        map: map,
        icon: greenMarker
    });
 
    // ホテル六本木
    var marker2 = new google.maps.Marker({
        position: new google.maps.LatLng(35.662627,139.727962),
        map: map,
        icon: yellowMarker
    });
 
    // 乃木坂駅
    var marker3 = new google.maps.Marker({
        position: new google.maps.LatLng(35.666846,139.726396),
        map: map,
        icon: redMarker
    });
 
    // 浅川歯科医院
    var marker4 = new google.maps.Marker({
        position: new google.maps.LatLng(35.667997,139.72573),
        map: map,
        icon: grayMarker
    });
 
    // シリア大使館
    var marker5 = new google.maps.Marker({
        position: new google.maps.LatLng(35.667439,139.733713),
        map: map,
        icon: greenMarker
    });
 
    // サウジアラビア王国大使館
    var marker6 = new google.maps.Marker({
        position: new google.maps.LatLng(35.663307,139.739292),
        map: map,
        icon: yellowMarker
    });
 
    // アーバンスタイル六本木三河台
    var marker7 = new google.maps.Marker({
        position: new google.maps.LatLng(35.664721,139.733552),
        map: map,
        icon: redMarker
    });
 
    // 区立中之町幼稚園
    var marker8 = new google.maps.Marker({
        position: new google.maps.LatLng(35.668539,139.730194),
        map: map,
        icon: grayMarker
    });
 
    // 小林荘
    var marker9 = new google.maps.Marker({
        position: new google.maps.LatLng(35.663902,139.729861),
        map: map,
        icon: greenMarker
    });
 
    // ステリーナ教会・ファボ
    var marker10 = new google.maps.Marker({
        position: new google.maps.LatLng(35.666566,139.729051),
        map: map,
        icon: yellowMarker
    });
 
    // 東京ミッドタウン・イースト
    var marker11 = new google.maps.Marker({
        position: new google.maps.LatLng(35.665272,139.731434),
        map: map,
        icon: redMarker
    });
 
 
 
    $('#map_content').on('pageshow', function(){
        google.maps.event.trigger(map, 'resize');
        map.setCenter(Latlng); 
    });
});