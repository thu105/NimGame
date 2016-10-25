import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class NimServer{
  public static void main(String[] args){
    //initiate vars
    Socket player1=null, player2=null;
    boolean hasUnpairedPlayer=false;
    ClientHandler handler=null;
    String firstPlayer="", secondPlayer="", input="";
    ServerSocket serverSock=null;
    BufferedReader clientInput1=null, clientInput2=null;
    BufferedWriter clientOutput1=null,clientOutput2=null;
    
    try{
      System.out.println("Waiting for client connections on port 7654.");
      serverSock = new ServerSocket(7654);
      while(true){
        //connect player 1
        player1=serverSock.accept();
        clientInput1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
        clientOutput1= new BufferedWriter(new OutputStreamWriter(player1.getOutputStream()));

        input=clientInput1.readLine();
        if(input.startsWith("HELLO ")){
            clientOutput1.write("100\n");
            clientOutput1.flush();
            firstPlayer=input.substring(6);
            System.out.println(firstPlayer+" is connected and waiting for opponent.");
        //connect player 2
            player2=serverSock.accept();
            clientInput2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
            clientOutput2= new BufferedWriter(new OutputStreamWriter(player2.getOutputStream()));

            input=clientInput2.readLine();
            secondPlayer=input.substring(6);

            clientOutput1.write("200 "+secondPlayer+"\n");
            clientOutput1.flush();
            clientOutput2.write("200 "+firstPlayer+"\n");
            clientOutput2.flush();
          
            //pair clients for game
            System.out.println(firstPlayer+" is paired with "+secondPlayer+".");
            NimBoard board = new NimBoard();

            clientOutput1.write(board.toString());
            clientOutput1.flush();
            clientOutput2.write(board.toString());
            clientOutput2.flush();

            handler = new ClientHandler(player1,player2,board);
            Thread theThread=new Thread(handler);
            theThread.start();
        }
      }
    }
    //exception
    catch(IOException e){
      System.out.println(e.getMessage());
    }
  }
} // NimServer
