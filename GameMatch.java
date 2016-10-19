import java.net.Socket;

class GameMatch(Socket, Socket, NimGame) {
  public final Socket first;
  public final Socket second;
  public final NimBoard game;

  public GameMatch(final Socket first, final Socket second, NigBoard game) {
    this.first = first;
    this.second = second;
    this.game = game;
  }
  public boolean hasSocket(Socket soc) {\\check if the given socket is contained in this match
    return ((first==soc)||(second==soc));
  }
  public Socket getOtherSocket(Socket soc){\\get the socket of other player, return null if the given socket is not in the GameMatch
    if(soc==first)
      return second;
    else if(soc==second)
      return first;
    else
      return Null;
}
