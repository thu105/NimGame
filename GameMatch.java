import java.net.Socket;

public class GameMatch{
  private final Socket first;
  private final Socket second;
  private NimBoard game;

  public GameMatch(final Socket first, final Socket second, NimBoard game) {
    this.first = first;
    this.second = second;
    this.game = game;
  }
  public boolean hasSocket(Socket soc) {
    return ((first==soc)||(second==soc));
  }
  public Socket getOtherSocket(Socket soc) {
    if(soc==first)
      return second;
    else if(soc==second)
      return first;
    else
      return null;
  }
  public NimBoard getBoard(){
    return game;
  }
}
