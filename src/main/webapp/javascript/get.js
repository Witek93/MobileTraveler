var app = angular.module('ServicesApp', []);

app.controller('ServicesController', function ServicesController($scope, $http) {

    $scope.getData = function (long, lati, tag) {
        $http.get('/traveler?longitude='+long+"&latitude="+lati+"&tag="+tag)
            .success(function (data) {
                $scope.services = data;
            });
    }

    $scope.services = {};

});