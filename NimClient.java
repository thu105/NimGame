/**
* NimClient.java
*
* This program will connect to the NimServer so that the user can play a multiplayer Mim game
* It will not be a multithreaded program because Nim is a turn-based game so no two action needs to simultaneously run
*/

import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class NimClient{
  public static void main(String[] args){
    Scanner keyboard;
    String userInput, serverReply="", myUsername, opUsername, serverAddress;
    boolean isFirst;
    int port;
    Socket connectionSock;
    BufferedWriter serverOutput;
    BufferedReader serverInput;

    keyboard = new Scanner(System.in);
    System.out.print("Please enter the address of the Nim server (press enter if it is a localhost): ");
    serverAddress= keyboard.nextLine();
    if(serverAddress.equals(""))
      serverAddress="localhost";
    port=7654;
    System.out.println("Connecting to server on port " + port);
    try{
      while(true){
        connectionSock=new Socket(serverAddress, port);
        serverOutput= new BufferedWriter(new OutputStreamWriter(connectionSock.getOutputStream()));
        serverInput= new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
        System.out.println("Connection made.");

        System.out.println("Welcome to Nim Game!!");
        System.out.println("You will be matched with another player to play a Nim game. Here are the rules:");
        System.out.println("    - There are three heaps of three, four, and five coins respectively.");
        System.out.println("    - If it is your turn, you can remove ANY amount of coins from a SINGLE heap.");
        System.out.println("    - The person that removes the last coin wins.");
        System.out.println("    - The frist player to connect to the server will get the first turn.");
        System.out.println("--------------------------------------------------------------------------------------");

        System.out.print("Please enter a name you wished to be addressed as: ");
        myUsername= keyboard.nextLine();
        userInput="HELLO "+myUsername+"\n";
        serverOutput.write(userInput);
        serverOutput.flush();
        serverReply=serverInput.readLine();
        isFirst=false;

        if(serverReply.equals("100")){
          isFirst=true;
          System.out.println("Waiting for opponent...");
          serverReply=serverInput.readLine();
        }
        opUsername=serverReply.substring(4);
        System.out.println("You have been matched with "+opUsername+".");
        serverReply=serverInput.readLine();
        serverReply+="\n";
        serverReply+=serverInput.readLine();
        serverReply+="\n";
        serverReply+=serverInput.readLine();
        System.out.println("----------------------------");
        System.out.println(serverReply);
        System.out.println("----------------------------");


        if(!isFirst){
          System.out.println("Waiting for "+opUsername+"\'s move...");
          serverReply=serverInput.readLine();//listening for status code
          serverReply=serverInput.readLine();//getting the board
          serverReply+="\n";
          serverReply+=serverInput.readLine();
          serverReply+="\n";
          serverReply+=serverInput.readLine();
          System.out.println("----------------------------");
          System.out.println(serverReply);
          System.out.println("----------------------------");
        }
        
        while(true){
          System.out.print("Please enter the row number you wished to change: ");
          userInput= keyboard.nextLine();
          serverOutput.write(userInput+"\n");
          serverOutput.flush();
          System.out.print("Please enter the number of coins to remove: ");
          userInput= keyboard.nextLine();
          serverOutput.write(userInput+"\n");
          serverOutput.flush();

          serverReply=serverInput.readLine();//listening for status code
          if(serverReply.equals("0")){
            serverReply=serverInput.readLine();//getting the board
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            System.out.println("----------------------------");
            System.out.println(serverReply);
            System.out.println("----------------------------");
          }
          else if(serverReply.equals("1")){
            serverReply=serverInput.readLine();//getting the board
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            System.out.println("----------------------------");
            System.out.println(serverReply);
            System.out.println("----------------------------");
            System.out.println("Congratulation! You won the game.");
            break;
          }
          else{
            serverReply=serverInput.readLine();//getting the board
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            System.out.println("----------------------------");
            System.out.println(serverReply);
            System.out.println("----------------------------");
            System.out.println("The move you made is invalid.");
            continue;
          }

          System.out.println("Waiting for "+opUsername+"\'s move...");
          serverReply=serverInput.readLine();//listening for status code
          if(serverReply.equals("0")){
            serverReply=serverInput.readLine();//getting the board
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            System.out.println("----------------------------");
            System.out.println(serverReply);
            System.out.println("----------------------------");
          }
          else{
            serverReply=serverInput.readLine();//getting the board
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            serverReply+="\n";
            serverReply+=serverInput.readLine();
            System.out.println("----------------------------");
            System.out.println(serverReply);
            System.out.println("----------------------------");
            System.out.println("Sorry, you lost the game.");
            break;
          }
        }
        connectionSock.close();
        serverInput.close();
        serverOutput.close();
        System.out.print("Would you like to play again? (y/n): ");
        userInput= keyboard.nextLine();
        if(userInput.equals("n"))
          break;
      }
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
