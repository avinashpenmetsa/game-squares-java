# game-squares-java

## Game Details
* On the home page players should be able to start a game or join current games
* When joining a game, ask for player name
* Each player is assigned a random color, when he/she joins the game
* A board of configurable dimension will be shown to all the players
* A game automatically starts when at least 2 players join it
* Each player can hover over the board and squares will light up with their assigned color
* Player can select the square by clicking it
* A player can acquire the square by clicking it
* Once a square is acquired it gets filled with the player's color
* An acquired square cannot be taken by any other player and its color will not change on hovering
* Once a square is selected by a player, all players are blocked for x seconds to do anything
* After x seconds, board becomes available again for all the players
* Game ends when all squares are colored and players with maximum squared colored wins.

## Build
Configure the build.properties file in the build folder.
Build the project with build.xml file in the build folder using apache ant.
eg. cmd command : ant - buildfile build.xml

## Deploy 
Deploy the project to apache tomcat. (no extra configurations)

## Project Architecture Overview
The project is based on Spring MVC model
* The state of the games is stored in a singleton manager class.
* The service class contains the logic of the game and updates the data in the manager class.
* The controller classes maps requests and sends the view to the client. It uses the API provided by service class.
* Ajax calls are used to update the data on the client page. Server continuously sends updates to the client.

## Screenshots
![alt tag](https://raw.githubusercontent.com/avinashpenmetsa/game-squares-java/master/screenshot-1.JPG)
![alt tag](https://raw.githubusercontent.com/avinashpenmetsa/game-squares-java/master/screenshot-2.JPG)
![alt tag](https://raw.githubusercontent.com/avinashpenmetsa/game-squares-java/master/screenshot-3.JPG)
