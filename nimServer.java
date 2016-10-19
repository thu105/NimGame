/**
 *
 * Basic server program to start a Nim game
 * Allows two clients to connect to the server and start a game
 * Selects a client at random to go first
 *
**/

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class nimServer {

  /* Maintain list of all client sockets for broadcast
  private ArrayList<Socket> socketList;

  public MTServer()
  {
    socketList = new ArrayList<Socket>();
  }
*/
    public static void main(String[] args) {
        try {
            System.out.println("Waiting for client connections on port 7654.");
            ServerSocket connectionSock = new ServerSocket(7654);

            GameMatch NimGame = new GameMatch();

            // The first player to connect is the player one
            NimClient playerOne = game.new NimClient(connectionSock.accept(), "You the first player to connect");

            // The second player to connect is player two
            NimClient playerTwo = game.new NimClient(connectionSock.accept(), "You are the second player to connect");

            // Tell each who their opponent is
            playerOne.setOpponent(playerTwo);
            playerTwo.setOpponent(playerOne);

            // Randomly selects who goes first
            Random r = new Random();
            int first = r.nextInt(2); //picks either 0 or 1
            if (first == 0) { //if result is 0 then player one goes first
                NimGame.turn = playerOne;
            } else { // if not player two goes first
                NimGame.turn = playerTwo;
            }

            // starts the game
            playerOne.start();
            playerTwo.start();
        } finally {
            connectionSock.close(); //close socket
        }
    }
}
