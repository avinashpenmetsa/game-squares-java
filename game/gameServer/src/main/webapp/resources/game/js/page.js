Game = (function() {

	"use strict";

	var colour;
	var player;
	var room;
	var data;
	var blockingTime;
	var blocked;

	return {
		init : function() {
			colour = colourCode;
			player = playerName;
			room = roomName;
			blocked = 'false';
			Game.getPage();
			Game.pageEvents();
			Game.updatePoll();
		},

		getPage : function() {
			$.ajax({
				type : 'GET',
				url : '/game/play/' + room,
				success : function(result) {
					data = result;
					blockingTime = data.blockingTime;
					Game.render();
				}
			});
		},

		render : function() {
			var i = 0;
			var grid = document.createElement('table');
			grid.id = 'board';
			grid.className = 'grid';
			for (var r = 0; r < data.square.ycoordinate; ++r) {
				var tr = grid.appendChild(document.createElement('tr'));
				for (var c = 0; c < data.square.xcoordinate; ++c) {
					var cell = tr.appendChild(document.createElement('td'));
					cell.id = "unselected";
					++i;
				}
			}
			if (data.creator != player) {
				document.getElementById("start").innerHTML = "Not Started";
			}
			document.body.appendChild(grid);
		},

		startGame : function() {
			Game.json = {};
			Game.json.player = playerName;
			$.ajax({
				type : 'POST',
				url : '/game/play/' + room + "/start",
				data : JSON.stringify(Game.json),
				contentType : 'application/json',
				success : function(result) {
					if (result.gameStatus == 'IN_PROGRESS') {
						document.getElementById("start").innerHTML = "Started";
						$('#start').unbind('click');
					} else {
						alert('No. of players less than min players');
					}
				}
			});
		},

		pageEvents : function() {

			$('#start').bind({
				click : function() {
					Game.startGame();
				},

				mouseover : function() {
					$(this).css("color", "green");
				},

				mouseout : function() {
					$(this).css("color", "black");
				}
			});

			$(document).on('mouseover', '#unselected', function() {
				if (blocked == 'false') {
					$(this).css("background-color", colour);
				}
			});

			$(document).on('mouseout', '#unselected', function() {
				$(this).css("background-color", "white");
			});

			$(document)
					.on(
							'click',
							'#unselected',
							function() {
								if (blocked == 'false') {
									var cell = this;
									var col = this.cellIndex;
									var row = this.parentNode.rowIndex;
									Game.json = {};
									Game.json.player = playerName;
									Game.json.xcoordinate = col.toString();
									Game.json.ycoordinate = row.toString();
									$
											.ajax({
												type : 'POST',
												url : '/game/play/' + room
														+ "/acquire",
												contentType : 'application/json',
												data : JSON
														.stringify(Game.json),
												success : function(result) {
													if (result != "") {
														cell.id = 'selected';
														$(cell)
																.css(
																		"background-color",
																		result.colour);
														var playerScore = $('.playerTable #'
																+ result.playerName
																+ '.score');
														var score = parseInt(
																playerScore
																		.text(),
																10);
														playerScore[0].textContent = score + 1;
														if (result.gameStatus == 'ENDED') {
															Game.getWinners();
														} else {
															blocked = 'true';
															setTimeout(
																	"Game.unblock();",
																	blockingTime);
														}
													}
												}
											});
								}
							});
		},

		getWinners : function() {
			Game.json = {};
			Game.json.player = playerName;
			$.ajax({
				type : 'POST',
				url : '/game/play/' + room + "/winners",
				contentType : 'application/json',
				data : JSON.stringify(Game.json),
				success : function(result) {
					var winnerDiv = document.createElement('div');
					winnerDiv.className = 'winnerDiv';
					var players = document.createElement('table');
					players.className = 'players';
					$.each(result.players, function(index, player) {
						var playerRow = players.appendChild(document
								.createElement('tr'));
						var nameCell = playerRow.appendChild(document
								.createElement('td'));
						nameCell.id = player;
						nameCell.className = 'name';
						nameCell.innerHTML = player.toUpperCase();
					});
					var title = document.createElement('h1');
					title.innerHTML = 'WINNER:';
					winnerDiv.appendChild(title);
					winnerDiv.appendChild(players);
					document.body.appendChild(winnerDiv);
				}
			});

		},

		updatePoll : function() {
			if (blocked == 'false') {
				Game.json = {};
				Game.json.player = playerName;
				$
						.ajax({
							type : 'POST',
							url : '/game/play/' + room + "/update",
							contentType : 'application/json',
							data : JSON.stringify(Game.json),
							success : function(result) {
								if (result.gameStatus == 'NOT_STARTED') {
									$('.playersDiv').remove();
									var playersDiv = document
											.createElement('div');
									playersDiv.className = 'playersDiv';
									var playerTable = document
											.createElement('table');
									playerTable.className = 'playerTable';
									$.each(result.players, function(index,
											player) {
										var playerRow = playerTable
												.appendChild(document
														.createElement('tr'));
										var nameCell = playerRow
												.appendChild(document
														.createElement('td'));
										nameCell.id = player.playerName;
										nameCell.className = 'name';
										nameCell.style.color = player.colour;
										nameCell.innerHTML = player.playerName
												.toUpperCase();
										var scoreCell = playerRow
												.appendChild(document
														.createElement('td'));
										scoreCell.id = player.playerName;
										scoreCell.className = 'score';
										scoreCell.style.color = player.colour;
										scoreCell.innerHTML = '0';
									});
									playersDiv.appendChild(playerTable);
									document.body.appendChild(playersDiv);
								} else if (result.gameStatus == 'IN_PROGRESS'
										|| result.gameStatus == 'ENDED') {
									document.getElementById("start").innerHTML = "Started";
									$('#start').unbind('click');
									var table = document
											.getElementById('board');
									var cell = table.rows[result.square.ycoordinate].cells[result.square.xcoordinate];
									if (cell.id == 'unselected') {
										cell.id = 'selected';
										$(cell).css("background-color",
												result.colour);
										var playerScore = $('.playerTable #'
												+ result.playerName + '.score');
										var score = parseInt(
												playerScore.text(), 10);
										playerScore[0].textContent = score + 1;
										if (result.gameStatus == 'ENDED') {
											Game.getWinners();
										} else {
											blocked = 'true';
											setTimeout("Game.unblock();",
													blockingTime);
										}
									}
								}
							}
						});
			}
			setTimeout("Game.updatePoll()", 200);
		},

		unblock : function() {
			blocked = 'false';
		},

		eof : 0
	};
})();

$(document).ready(function() {
	Game.init();
});
