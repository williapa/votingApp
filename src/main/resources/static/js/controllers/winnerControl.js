angular.module('voting')
	.controller('winnerControl', function winnerControl($http, $scope, $routeParams, $filter, $location) {

		$scope.questionId = $routeParams.qid;
		$scope.candidates = [];
		$scope.username = "";
		$scope.loggedin = false;
		$scope.totalvotes = 0;
		$scope.winningpercentage = 0;

		var doCalculations = function() {

			var c = $scope.candidates;
			var max = 0;
			var total = 0;
			var ind = -1;

			for(var i = 0; i < c.length; i++) {

				total = total + c[i].id;

				if(c[i].id > max) {
					ind = i;
					max = c[i].id;
				}

			}

			max/total;
			$scope.winner = c[ind].body;
			$scope.totalvotes = c[ind].id;
			$scope.winningpercentage = parseFloat(max / total * 100) + "%";

		};

		var vwt = localStorage.getItem("vwt");

		if(vwt !== null) {

			$scope.loggedin = true;

			$scope.username = JSON.parse(vwt).name;
		}

		if(vwt !== null) {

			$scope.loggedin = true;

			$scope.username = JSON.parse(vwt).name;
		}
		$http({
			url: '/api/question/'+ $routeParams.qid,
			method: 'GET',
		}).then(function(response) {
			if(response.data){
				$scope.question = response.data.body;
			} else {
				$scope.question = "ERROR";
			}
		}, function(error) {
			console.log("error: ", error);
		});

		$http({
			url: '/api/vote/question/winner/'+$routeParams.qid,
			method: 'GET',
		}).then(function(response) {
			if(response.data) {
				$scope.candidates = response.data;

				doCalculations();
			} else {
				console.log("error!!!");
			}
		}, function(error) {
			console.log("error");
			console.log(error);
		});


		$scope.logout = function () {

			while(vwt !== null) {

				localStorage.removeItem("vwt");

				vwt = localStorage.getItem("vwt");

			}

			$location.path("/");

		};

});