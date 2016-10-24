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
  * Member variables: ArrayList\<GameMatch> matches, boolean hasUnpairedPlayer, Socket player1, Socket player2, ServerSocket     serverSoct, ClientHandler handler, String firPlayer, String secPlayer
  * Assign default values:
    * matches = new ArrayList\<GameMatch>();
    * hasUnpairedPlayer=false;
    * player1= null;
    * player2= null;
    * firstPlayer= "";
    * secondPlayer= "";
  * Prints "Waiting for client connections on port 7654."
  * Create ServerSocket(7654)
  * Listening stage starting here will repeat forever until terminated by user
    * Listen for client connections on that socket
    * Assign the new client socket to player2
    * Check if the socket already exist in the matches list. (use hasSocket(Socket soc) within a loop)
      * if it is already in the list, start new thread of ClientHandler passing in Socket player2 and the matches list
    * Else if hasUnpairedPlayer is false
      * Check if the message starts with "HELLO "
      * If false, continue (go to next loop)
      * Send "100" back to player2
      * Get username for firstPlayer
      * print firstPlayer+" has connected."
      * player1=player2
      * hasUnpairedPlayer=true
    * Else
      * Check if the message starts with "HELLO "
      * If false, continue (go to next loop)
      * Get username for secondPlayer
      * Send "200 "+firstPlayer to player2
      * Send "200 "+secondPlayer to player1
      * print firstPlayer+" was paired with "+secondPlayer+"."
      * Create a new NimGame called game
      * Send game.toString() to player1
      * Send game.toString() to player2
      * Create a new GameMatch called match with player1, player2, and game
      * Add match into matches list
      * start new thread of ClientHandler passing in Socket player1 and the matches list
      * run the thread
    
* ClientHandler
  * Member variables: Socket sender, Socket receiver, NimBoard board, GameMatch match, ArrayList\<GameMatch> matches
  * Constructor: ClientHandler(Socket connectionSock, ArrayList\<GameMatch> matches)
    * sender=connectionSock
    * this.matches=matches
    * loop through matches to get a match that has Socket sender
    * receiver=match.getOtherSocket()
    * board= match.getBoard()
  * run()
    * create DataOutputStream clientOutput1 for Socket sender
    * create DataOutputStream clientOutput2 for Socket receiver
    * try
      * print "Getting a move from "+sender+"."
      * create BufferedReader clientInput from Socket sender
      * takes in String input from clientInput
      * print input
      * convert input into int row
      * takes in String input from clientInput again
      * print input
      * convert input into int count
      * int result = board.makeChange(row,count)
      * if result is 2
        * print "Invalid move was made."
        * convert result into a string and send it to clientOutput1
        * also send board.toString() to clientOutput1
      * if result is 0 or 1
        * print "Updated the board according to the move."
        * convert result into a string and send it to both clientOutput1 and clientOutput2
        * also send board.toString() to both players
      * if result is 1
        * print sender+" had won the game."
        * remove match from the matches list
    * catch (Exception e)
       * print "Error: "+e.toString();
       * send "2" to clientOutput1
       * send board.toString() to clientOutput1
    * print "Closing the socket "+sender+"."
    * close the socket
      
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
      * close the connection
      * ask user if he wants to play again
        * if yes, make new connection and repeat from sending "HELLO [username]" step
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
