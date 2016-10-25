/**
* Coded by: Elia Eiroa, Hein Thu
* ClientHandler.java
*
* This class handles one Nim game between two clients
* The server host the game and two clients will take turn making moves
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

	ClientHandler(Socket sender, Socket receiver, NimBoard board)
	{
		this.sender = sender;
		this.receiver=receiver;
		this.board=board;
	}

	public void run(){
		Socket temp=null;
		String input="";
		int row=0, count=0, result=0;
		BufferedWriter outputToSender=null, outputToReceiver=null;
		BufferedReader clientInput=null;

		try{
			while(true){
				outputToSender = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
				outputToReceiver = new BufferedWriter(new OutputStreamWriter(receiver.getOutputStream()));
				clientInput = new BufferedReader(new InputStreamReader(sender.getInputStream()));

				System.out.println("Connection made with " + sender);

					input = clientInput.readLine();
					System.out.println("Received: " + input);
					try{
						row=Integer.parseInt(input);
					}
					catch(NumberFormatException e){
						row=0;
					}
					input = clientInput.readLine();
					System.out.println("Received: " + input);
					try{
						count=Integer.parseInt(input);
					}
					catch(NumberFormatException e){
						count=0;
					}

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


					outputToReceiver.close();
					outputToSender.close();
					clientInput.close();
					sender.close();
					receiver.close();
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
		}
		System.out.println("Ending a game between "+sender+" and "+receiver);
	}
} // ClientHandler for NimServer.java
