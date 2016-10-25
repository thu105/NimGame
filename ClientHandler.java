/**
 * Coded by: Elia Eiroa, Hein Thu
 * ClientHandler.java
 *
 * This class handles one Nim game between two clients
 * The server host the game and two clients will take turn making moves
 * Each game run on seperate thread but all threads has a list of GameMatch to keep track of all the games
 */
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
	private Socket sender = null, receiver=null;
	private NimBoard board;
	private GameMatch match;
	private ArrayList<GameMatch> matches;

	ClientHandler(Socket sender, ArrayList<GameMatch> matches)
	{
		this.sender = sender;
		this.matches = matches;	// Keep reference to master list of games
		for(GameMatch gm: matches){
			if(gm.hasSocket(sender)){
				match=gm;
				break;
			}
		}
		receiver=match.getOtherSocket(sender);
		board=match.getBoard();
	}

	public void run(){
		Socket temp;
		String input;
		int row, count, result=0;
		BufferedWriter outputToSender, outputToReceiver;
		BufferedReader clientInput;

		try{
			while(true){
				outputToSender = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
				outputToReceiver = new BufferedWriter(new OutputStreamWriter(receiver.getOutputStream()));
				clientInput = new BufferedReader(new InputStreamReader(sender.getInputStream()));
				System.out.println("Connection made with socket " + sender);
				input = clientInput.readLine();
				System.out.println("Received: " + input);
				row=Integer.parseInt(input);
				input = clientInput.readLine();
				System.out.println("Received: " + input);
				count=Integer.parseInt(input);

				result= board.makeChange(row,count);
				if(result==2){
					System.out.println("Invalid move was made.");
					outputToSender.write("2\n");
					outputToSender.flush();
					outputToSender.write(board.toString());
					outputToSender.flush();
					continue;
				}
				else if(result==1){
					System.out.println(sender+" has won the game.");
					outputToSender.write("1\n");
					outputToSender.flush();
					outputToSender.write(board.toString());
					outputToSender.flush();
					outputToReceiver.write("1\n");
					outputToReceiver.flush();
					outputToReceiver.write(board.toString());
					outputToReceiver.flush();
					sender.close();
					receiver.close();
					matches.remove(match);
					break;
				}
				else{
					System.out.println("Updated the board according to the changes.");
					outputToSender.write("0\n");
					outputToSender.flush();
					outputToSender.write(board.toString());
					outputToSender.flush();
					outputToReceiver.write("0\n");
					outputToReceiver.flush();
					outputToReceiver.write(board.toString());
					outputToReceiver.flush();
					System.out.println("Switching between sender and reciever...");
					temp=sender;
					sender=receiver;
					receiver=temp;
				}
			}
		}
		catch (Exception e){
			System.out.println("Error: " + e.toString());
			// Remove from arraylist
			matches.remove(match);
		}
		System.out.println("Ending a game between "+sender+" and "+receiver);
	}
} // ClientHandler for NimServer.java
