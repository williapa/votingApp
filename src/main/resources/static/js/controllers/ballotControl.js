angular.module('voting')
	.controller('ballotControl', function electionControl($http, $scope, $routeParams, $filter, $location) {
			
		$scope.questions = [];
		$scope.ballotId = $routeParams.id;
		$scope.answers = {};
		$scope.error = "";

		var buildVote = function(qid, cid, r) {

			var vote = new Object();

			var vwt = JSON.parse(localStorage.getItem("vwt"));

			vote['voterId'] = vwt.id;
			vote['ballotId'] = parseInt($routeParams.id);
			vote['questionId'] = qid;
			vote['candidateId'] = cid;
			vote['regionId'] = vwt.regionId;
			vote['rank'] = parseInt(r);
			vote['electionId'] = 1;

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

			console.log("answers: ", ans);

			//the keys for the answer object are the question Ids. 

			var questionIds = Object.keys(ans);

			for(var i = 0; i < questionIds.length; i++) {

				var response = ans[questionIds[i]];

				console.log("response: ", response);

				if(response.type === "yes or no") {

					var v = buildVote(questionIds[i], response.id, "0");

					votes.push(v);

				} else if(response.type === "pick two") {

					var keys = Object.keys(response);

					for(var j = 0; j < keys.length; j++){

						console.log("pick two: ");
						console.log("key: ", keys[j]);

						var v = null;

						if(keys[j] !== "type") {

							v = buildVote(questionIds[i], keys[j], "0");

						}

						if(keys[j] === "-1") {

							var candidates = ans[questionIds[i]].candidates;	

							for(var a = 0; a < candidates.length; a++) {

								if( candidates[a].id === keys[j]) {

									v["writein"] = candidates[a].body;

								}

							} 

						}

						if(keys[j] !== "type") votes.push(v);

					}

				} else if(response.type === "instant runoff") {

					var keys = Object.keys(response);

					//the keys are spots 1, 2, 3, and 4 

					for(var k = 0; k < keys.length; k++) {

						if(keys[k] !== "type") {

							var vote = response[keys[k]];

							console.log("response: ", response);

							console.log("troublesome vote: ", vote);

							var v = null;

							if(keys[k] == "-1") {

								var candidates = ans[questionIds[i]].candidates;

								v = buildVote(questionIds[i], keys[k], "4");	

								for(var s = 0; s < candidates.length; s++) {

									if( candidates[s].id === keys[k]) {

										v["writein"] = candidates[s].body;

									}
								}

							} 


						} else {

							v = buildVote(questionIds[i], keys[k], keys[k]);

						}

						votes.push(v);

					}

				}

			}

			//now our votes array is a list of every vote we need to cast for this ballot
			//let's assume our ng-change functions will enforce our logic of separate ranks
			//for each runoff candidate and only two votes cast for the pick two. 
			console.log("we are sending them the votes: ", votes);

			$http({
				url:'api/vote',
				method: 'POST',
				data: votes,
			}).then(function(response) {

				if(response.data) {
					console.log("votes that got cast: ", response.data);
					//$location.path("/results"); 
				} else {
					console.log("votes not cast.");

				}
			}, function(error) {
				console.log("votes not cast: ");
				console.log(error);
			});

		};



		$scope.checkbox = function(candidate, qid) {

/*			var candidates = [];

			for(var i = 0; i < $scope.questions.length; i++) {

				if ($scope.questions[i].q.id === qid) {

					candidates = $scope.questions[i].candidates;

				}

			}
			var candidateIndex = 0;

			console.log("answers for the checkbox: ", $scope.answers[qid]);

			for(var j = 0; j < candidates.length; j++) {

				if( candidates[j].body === candidate.body) {
					candidateIndex = j;
				}

			}

			var checked = 0;

			var keys = Object.keys($scope.answers[qid]);

			for(var k = 0; keys.length; k++) {
				var checkedBox = $scope.answers[qid][keys[k]];
				console.log("im a big prince fan", checkedBox);

				if($scope.answers[qid][keys[k]].checked) checked++;

				if(checked > 2) {
					$scope.alert = "too many checked boxes.";
					$scope.answers[qid][keys[k]].checked = 0;
				}

			}*/

		};

		$scope.updateRunoff = function(r, x, id) {
/*
			var candidates = [];
			var questionIndex = 0;

			for(var i = 0; i < $scope.questions.length; i++) {

				console.log("question id: ", $scope.questions[i].q.id);

				if( $scope.questions[i].q.id === id) {
					candidates = $scope.questions[i].candidates;
					questionIndex = i;
				}
			}

			candidates = $scope.questions[questionIndex].candidates;

			for(var j = 0; j < candidates.length; j++) {

				if(candidates.body === x) {
					$scope.answers[questionIndex][candidates[j]] = {rank: r};
				} else if ($scope.answers[questionIndex][candidates[j]].rank === r) {
					$scope.answers[questionIndex][candidates[j]] = {rank: "0"};
				}

			}

			console.log("scope.answers ", $scope.answers);*/

		};

		$scope.logout = function () {
			localStorage.removeItem("vwt");
		};

		$scope.goHome = function () {
			$location.path("/");
		}

	});