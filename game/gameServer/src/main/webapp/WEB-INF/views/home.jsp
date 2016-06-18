<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<title>Game</title>
</head>
<body>

	<div id="createGame" align="center">
		<h1>Create Game</h1>
		<form:form method="POST" action="createGame" commandName="createGameDto">
			<table>
				<tr>
					<td><form:label path="playerName">Player Name</form:label></td>
					<td><form:input path="playerName" /></td>
				</tr>
				<tr>
					<td><form:label path="roomName">Game Room Name</form:label></td>
					<td><form:input path="roomName" /></td>
				</tr>
				<tr>
					<td><form:label path="blockingTime">Blocking Time(in ms)</form:label>
					</td>
					<td><form:input path="blockingTime" /></td>
				</tr>
				<tr>
					<td><form:label path="minPlayers">Min Players</form:label></td>
					<td><form:input path="minPlayers" /></td>
				</tr>
				<tr>
					<td><form:label path="maxPlayers">Max Players</form:label></td>
					<td><form:input path="maxPlayers" /></td>
				</tr>
				<tr>
					<td><form:label path="boardSize">Board Size</form:label></td>
					<td><form:input path="boardSize" value="5x5" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Create" /></td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="joinGame" align="center">
		<h1>Join Game</h1>
		<form:form method="POST" action="joinGame" commandName="joinGameDto">
			<table>
				<tr>
					<td><form:label path="playerName">Player Name</form:label></td>
					<td><form:input path="playerName" /></td>
				</tr>
				<tr>
					<td><form:label path="roomName">Game Room Name</form:label></td>
					<td><form:input path="roomName" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Join" /></td>
				</tr>
			</table>
		</form:form>
	</div>

</body>
</html>