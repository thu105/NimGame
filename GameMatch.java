import java.net.Socket;

class GameMatch(Socket, Socket, NimGame) {
  public final Socket first;
  public final Socket second;
  public final NimGame game;

  public GameMatch(final Socket first, final Socket second, NigGame game) {
    this.first = first;
    this.second = second;
    this.game = game;
  }
  public boolean hasSocket(Socket soc) {
    return ((first==soc)||(second==soc));
  }
}
