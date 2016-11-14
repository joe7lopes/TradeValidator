app.controller('TradeController', function ($scope, $http) {

    $scope.singleTrade;
    $scope.singleTradeErrors;
    $scope.isSingleTradeSuccess=false;

    function init() {
        $http.get('/api/trade')
            .then(function (response) {
                $scope.singleTrade = JSON.stringify(response.data);
            });
    }

    function hideSuccess(){
        $scope.isSingleTradeSuccess=false;
    }

    $scope.validateTrade = function () {
        $http.post('/api/trade', $scope.singleTrade)
            .error(function (errors) {
                    var errorMessage = "";
                    errors.forEach(function (data) {
                        errorMessage += data.description + ',';
                    });
                    $scope.singleTradeErrors = errorMessage.substring(0, errorMessage.length - 1);
                }
            )
            .success(function () {
                console.log("success");
                $scope.singleTradeErrors="";
                $scope.isSingleTradeSuccess=true;
            })
        ;
    };

    init();

});

