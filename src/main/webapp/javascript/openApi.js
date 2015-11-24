OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
    defaultHandlerOptions: {
        'single': true,
        'double': false,
        'pixelTolerance': 0,
        'stopSingle': false,
        'stopDouble': false
    },

    initialize: function(options) {
        this.handlerOptions = OpenLayers.Util.extend(
            {}, this.defaultHandlerOptions
        );
        OpenLayers.Control.prototype.initialize.apply(
            this, arguments
        );
        this.handler = new OpenLayers.Handler.Click(
            this, {
                'click': this.trigger
            }, this.handlerOptions
        );
    },

    trigger: function(e) {
        var lonlat = map.getLonLatFromViewPortPx(e.xy)
        markers.addMarker(new OpenLayers.Marker(lonlat));

        lonlat.transform(
          new OpenLayers.Projection("EPSG:900913"),
          new OpenLayers.Projection("EPSG:4326")
        );

        var coordinates = {longitude: lonlat.lon, latitude: lonlat.lat};
        arrayOfCoordinates.push(coordinates);
    }
});

var arrayOfCoordinates = [];
var map;
var markers;
function init(){
    map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());

    var lonLat = new OpenLayers.LonLat(19.907652, 50.068847)
          .transform(
            new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
            map.getProjectionObject() // to Spherical Mercator Projection
          );

    var zoom=16;

    markers = new OpenLayers.Layer.Markers( "Markers" );
    map.addLayer(markers);

    map.setCenter (lonLat, zoom);
    var click = new OpenLayers.Control.Click();
    map.addControl(click);
    click.activate();
}

function sendData(){
    $.ajax({
        url: "/traveler2",
        type: 'POST',
        data: arrayOfCoordinates,
        contentType: 'application/json',
        data: JSON.stringify(arrayOfCoordinates, null, '\t'),
        success: function() {
            alert('the data was successfully sent to the server');
        }
    });

    arrayOfCoordinates = [];
    map.removeLayer(markers);
}