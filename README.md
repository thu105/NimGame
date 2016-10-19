# NimGame
This repo contains programs to implement a multi-threaded TCP game called Nim 


* Roles:
 * Hein Thu: NimClient, NimBoard, GameMatch (ClientListener is not needed because it is a turn-based game and the client only play one game at once)
 * Eila Eiroa: NimServer, ClientHandler
* NimClient.java handles the inputs of the client and prints out the board
* NimServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java take a message from a player, make a move on the NimBoard, and send the resulting board to both players
* NimBoard.java handles the Nim game and its rules
* GameMatch.java groups up two clients and one nim game so that server can manage each game easier


* NIM GAME RULES
  * The Nim board will contain three heaps of three, four, and five virtual coins respectively.
  * There are two players and each will take turn removing ANY amount of coins from a single heap.
  * The person that makes the last move (removes the last coin) wins the game.
  
* NIM SERVER
  * NimServer
  * connects players together
  * picks one of them at random to start
  * starts game
  * it works connecting two clients to form one game at a time
  * need to write code for multiple players at once, but I wasn't able to figure it out
  *
  * psudocode:
  * Member variables: ArrayList\<GameMatch> matches, boolean hasUnpairedPlayer, Socket player1, Socket player2, ServerSocket     serverSoct, ClientHandler handler
  * Assign default values:
    * matches = new ArrayList\<GameMatch>();
    * hasUnpairedPlayer=false;
    * player1= null;
    * player2= null;
  * Prints "Waiting for client connections on port 7654."
  * Create ServerSocket(7654)
  * Listening stage starting here will repeat forever until terminated by user
    * Listen for client connections on that socket
    * Assign the new client socket to player2
    * Check if the socket already exist in the matches list. (use hasSocket(Socket soc) within a loop)
      * if it is already in the list, start new thread of ClientHandler passing in player2, and matches
    * Else if hasUnpairedPlayer is false
      * player1=player2
      * hasUnpairedPlayer=true
    * Else
      * Create a new NimGame called game
      * Create a new GameMatch called match with player1, player2, and game
      * Add match into matches list
      * start new thread of ClientHandler passing in player1, and matches
    

* NIM CLIENT
  * Member variables: String rules, String myUsername, String opUsername, String serverReply, String userInput
  * Asks user for server address (user can input nothing if the server is localhost)
  * Establish a connection to the server.
  * Prints out the rules of the game
  * Asks the user to input a username to identify himself/herself
  * Connects to the server by sending "HELLO [username]" and listen from server
  * If server replies "100", print "Waiting for opponent...", wait for "200"
  * If server replies "200 [opponent username]", print "You have been matched with [opponent username]."
  * Take another reply and print it
  * If "100" was ever received (the client is the first to connect), make the first move
  * One move will have these steps:
    * Prompt for row number
    * Send row number to server
    * Prompt for number of coins to remove
    * Send count to server
    * Listen from server
    * If server reply "0"
      * take second reply
      * print it
    * If server reply "1"
      * take second reply
      * print it
      * print "Congragulation! You won the game."
      * ask user if he wants to play again
        * if yes, make new connection and repeat the entire move
        * if no, end the client program
    * If server reply "2"
      * take second reply
      * print it
      * print "The move you made is invalid."
      * repeat the entire move
    * Print "Waiting for [opponent username]'s move..."
    * Listen from server
    * If server reply "0"
      * take second reply
      * print it
      * repeat the entire move
    * If server reply "1"
      * take second reply
      * print it
      * print "Sorry, you lost the game..."
      * ask user if he wants to play again
        * if yes, make new connection and repeat the entire move
        * if no, end the client program
* List of features implemented:
  * GameMatch.java is implemented. (More documentations will be added later)
  * NimBoard.java is implemented.
* List of unfinished features:
  * NimClient.java is in the process
  * Will implement an AI for clients to play against if there is enough time.
  * I'm not sure what the ClientHandler has to do that it doesn't already do 
