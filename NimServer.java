import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class NimServer{
  public static void main(String[] args){
    Socket player1=null, player2=null;
    boolean hasUnpairedPlayer=false, alreadyConnected=false;
    ClientHandler handler;
    String firstPlayer="", secondPlayer="", input="";
    ServerSocket serverSock;
    BufferedReader clientInput1, clientInput2;
    BufferedWriter clientOutput1,clientOutput2;
    ArrayList<GameMatch> matches= new ArrayList<GameMatch>();
    try{
      System.out.println("Waiting for client connections on port 7654.");
      serverSock = new ServerSocket(7654);
      while(true){
        player1=serverSock.accept();
        for(GameMatch gm: matches){
          if(gm.hasSocket(player1)){
            alreadyConnected=true;
            break;
          }
        }
        if(alreadyConnected){
          System.out.println(player1+" already exist in the matches list.");
          handler = new ClientHandler(player1,matches);
          Thread theThread=new Thread(handler);
          theThread.start();
          continue;
        }

        clientInput1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
        clientOutput1= new BufferedWriter(new OutputStreamWriter(player1.getOutputStream()));
        input=clientInput1.readLine();

        if(input.startsWith("HELLO ")){
            clientOutput1.write("100\n");
            clientOutput1.flush();
            firstPlayer=input.substring(6);
            System.out.println(firstPlayer+" is connected and waiting for opponent.");

            player2=serverSock.accept();
            clientInput2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            clientOutput2= new BufferedWriter(new OutputStreamWriter(player2.getOutputStream()));
            input=clientInput2.readLine();
            secondPlayer=input.substring(6);
            clientOutput1.write("200 "+secondPlayer+"\n");
            clientOutput1.flush();
            clientOutput2.write("200 "+firstPlayer+"\n");
            clientOutput2.flush();
            System.out.println(firstPlayer+" is paired with "+secondPlayer+".");
            NimBoard board = new NimBoard();
            clientOutput1.write(board.toString());
            clientOutput1.flush();
            clientOutput2.write(board.toString());
            clientOutput2.flush();
            GameMatch match = new GameMatch(player1,player2,board);
            matches.add(match);
            handler = new ClientHandler(player1,matches);
            Thread theThread=new Thread(handler);
            theThread.start();
        }
      }
    }
    catch(IOException e){
      System.out.println(e.getMessage());
    }
  }
} // MTServer
