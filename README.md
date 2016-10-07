# NimGame
This repo contains programs to implement a multi-threaded TCP game called Nim 


* Roles:
 * Hein Thu: NimClient, ClientListener, NimBoard
 * Eila Eiroa: NimServer, ClientHandler
* NimClient.java handles the Nim board from the client's side and inputs of the client
* ClientListener.java recieves responses from the server (the other player) and changes the Nim board
* NimServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java recieves messages from a client and relays it to the other clients.
* NimBoard.java handles the Nim game and its rules


* NIM GAME RULES
  * The Nim board will contain three heaps of three, four, and five virtual coins respectively.
  * There are two players and each will take turn removing ANY amount of coins from a single heap.
  * The person that makes the last move (removes the last coin) wins this round and he/she will start the next round.
  * The winner will be the best of five rounds.

* NIM SERVER
  * The server will be hosting multiple Nim games at once.
  * The server will take in a username from a client and pair up every two clients that connects to it.
  * There will be one NimBoard for every two clients
  * The server will take in input from the clients, changes the board and sends back the updated NimBoard output to both clients

* NIM CLIENT
  * The client will first ask user to input a username to identify himself/herself
  * The client will connects to the server and waits for it to be paired with another client
  * Will relay inputs from the user to server and prints the updated NimBoard for every move made
  * Once five rounds are over, the user can play again or quit.
  * The user can surrender mid-game.
