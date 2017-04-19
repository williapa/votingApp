angular.module('voting')
	.controller('homeControl', function homeControl($http, $scope, $routeParams, $filter, $location) {

		$scope.signUpForm = false;

		$scope.switch = function() {

			if($scope.signUpForm) {
			  $scope.signUpForm = false;
			}
			else {
			  $scope.signUpForm = true;
			}

		};

		$scope.login = function() {
		
			var voter = {};
			
			voter["name"] = $scope.loginName;
			voter["password"] = $scope.loginPassword;
			
			$http({
				url: '/api/voter/login',
				method: 'POST',
				data: voter,
			}).then(function(response) {
				if(response.data) {
					console.log("success: ", response.data);
					localStorage.setItem("vwt", response.data);
					$location.path("/elections");
				} else {
					console.log("name/password don't match.");
					$scope.error = "name and password don't match.";
				}
			}, function(error) {
				console.log("there was an error: ", error);
				$scope.error = error;
			});
			
		};

		$scope.register = function() {

			var voter = {};                                                                                     
			voter["name"] = $scope.signupName;
			voter["password"] = $scope.signupPassword;
			voter["regionId"] = $scope.signupRegionId;

			$http({
				url:'/api/voter',
				method:'POST',
				data: voter,
			}).then(function(response) {
				if(response.data) {
					console.log("success: ", response.data);
					localStorage.setItem("vwt", response.data);
					$location.path("/elections");
				} else {
					$scope.error = "Name is already taken. ";
				}
			},function(error) {
				console.log("there was an error: ", error);
				$scope.error = error;
			});

		};

		$scope.logout = function () {
			localStorage.removeItem("vwt");
		};

		$scope.goHome = function () {
			$location.path("/");
		};

	});