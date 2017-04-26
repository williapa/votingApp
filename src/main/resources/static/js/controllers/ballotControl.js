angular.module('voting')
	.controller('ballotControl', function electionControl($http, $scope, $routeParams, $filter, $location) {
			
		$scope.questions = [];
		$scope.ballotId = $routeParams.id;
		$scope.answers = {};
		$scope.error = "";
		var vwt = JSON.parse(localStorage.getItem("vwt"));
		$http.defaults.headers.common['Auth-Token'] = vwt.password;
		$scope.username = "";
		$scope.loggedin = false;

		if(vwt !== null && vwt.name.length > 0) {

			$scope.loggedin = true;

			$scope.username = vwt.name;
		}

		var buildVote = function(qid, cid, r) {

			var vote = new Object();

			vote['voterId'] = parseInt(vwt.id);
			vote['ballotId'] = parseInt($routeParams.id);
			vote['questionId'] = parseInt(qid);
			vote['candidateId'] = parseInt(cid);
			vote['regionId'] = parseInt(vwt.regionId);
			vote['rank'] = parseInt(r);
			vote['electionId'] = 1;

			console.log("vote we just built: ", vote);

			if(isNaN(cid)) {
				return null;
			}

			return vote;
		}
		//build questionData
		$http({
			url: 'api/ballotquestion/forballot/'+$routeParams.id,
			method: 'GET',
		}).then(function(response) {

			if(response.data){

				console.log("questions for ballot id "+ $scope.ballotId  + ": ", response.data);

				$scope.questions = response.data;

				for(var i = 0; i < $scope.questions.length; i++) {

					var q = $scope.questions[i];

					if(q.q.type === "instant runoff" || q.q.type === "pick two") {

						$scope.questions[i].candidates.push({id: -1, body: 'WRITE-IN', questionId: q.q.id});

					}

					var c = $scope.questions[i].candidates;

					$scope.answers[q.q.id] = {type: q.q.type};

					for(var j = 0; j < c.length; c++) {
						var candi = c[j];
						$scope.questions[i]
					}

				}

			} else {

				console.log("no questions for this ballot yet. ");

			}

		}, function(error){
			console.log("there was a server error?", error);
		});

		$scope.vote = function() {

			var ans = $scope.answers;
			var qs = $scope.questions;
			var votes = [];

			var v = {};

			//the keys for the answer object are the question Ids. 

			var questionIds = Object.keys(ans);

			for(var i = 0; i < questionIds.length; i++) {

				var response = ans[questionIds[i]];

				if(response.type === "yes or no") {

					v = buildVote(questionIds[i], response.id, "0");

					console.log("yes or no vote to push: ", v);

					if(v !== null) votes.push(v);

				} else if(response.type === "pick two") {

					var keys = Object.keys(response);

					for(var j = 0; j < keys.length; j++){

						if(keys[j] !== "type") {

							v = buildVote(questionIds[i], keys[j], "0");

						}

						if(keys[j] === "-1") {

							var candidates = null;

							for(var n = 0; n < $scope.questions.length; n++) {

								if(parseInt($scope.questions[n].q.id) === parseInt(questionIds[i])) {

									candidates = $scope.questions[n].candidates;

								}

							}

							for(var s = 0; s < candidates.length; s++) {

								if(parseInt(candidates[s].id) === parseInt(keys[j])) {

									v["writein"] = candidates[s].body;

								}
							}

						}

						console.log("pick two vote to push: ", v);

						if(keys[j] !== "type" && v !== null) votes.push(v);

					}

				} else if(response.type === "instant runoff") {

					var keys = Object.keys(response);

					//the keys are spots 1, 2, 3, and 4 
					for(var r = 0; r < keys.length; r++) {

						if(keys[r] == "type") {
							 keys.splice(r, 1);
						}

					}

					for(var k = 0; k < keys.length; k++) {

						if(keys[k] !== "type") {

							var vote = response[keys[k]];

							if(keys[k] == "-1") {

								var candidates = null;

								for(var n = 0; n < $scope.questions.length; n++) {

									if(parseInt($scope.questions[n].q.id) === parseInt(questionIds[i])) {

										candidates = $scope.questions[n].candidates;

									}

								}

								v = buildVote(vote.questionId, vote.id, (k+1));	

								v["writein"] = vote.body;

								/*for(var s = 0; s < candidates.length; s++) {

									if(parseInt(candidates[s].id) === -1) {

										v["writein"] = candidates[s].body;

									}

								}*/

							} 

							else {

								v = buildVote(questionIds[i], vote.id, (k+1));

								if(vote.id === -1) {
									v["writein"] = vote.body;
								}

							}

							console.log("instant runoff vote to push: ", v);

							if(v !== null) votes.push(v);

						}

					}

				}

			}

			//now our votes array is a list of every vote we need to cast for this ballot
			//let's assume our ng-change functions will enforce our logic of separate ranks
			//for each runoff candidate and only two votes cast for the pick two. 
			console.log("we are sending them the votes: ", votes);
			//update header


			$http({
				url:'api/vote',
				method: 'POST',
				data: votes,
			}).then(function(response) {

				if(response.data) {
					console.log("votes that got cast: ", response.data);
					$location.path("/results/1"); 
				} else {
					console.log("votes not cast.");

				}
			}, function(error) {
				console.log("votes not cast: ");
				console.log(error);
			});

		};

		$scope.checkbox = function(candidate, qid) {

		};

		$scope.updateRunoff = function(r, x, id) {

		};

		$scope.logout = function () {

			var vwt = localStorage.getItem("vwt");

			while(vwt !== null) {

				localStorage.removeItem("vwt");

				vwt = localStorage.getItem("vwt");

			}

			$location.path("/");
			
		};

		$scope.goHome = function () {
			$location.path("/");
		}

	});