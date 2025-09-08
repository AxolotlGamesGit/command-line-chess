public class GameState { 
  public enum PieceType { 
    EMPTY, 
    PAWN, 
    KNIGHT, 
    BISHOP, 
    ROOK, 
    QUEEN, 
    KING 
  } 
  public enum Player { 
    NONE, 
    WHITE, 
    BLACK 
  } 
  public final PieceType[][] STARTING_PIECES = {{ PieceType.ROOK,  PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING,  PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK }, 
                                                { PieceType.PAWN,  PieceType.PAWN,   PieceType.PAWN,   PieceType.PAWN,  PieceType.PAWN,  PieceType.PAWN,   PieceType.PAWN,   PieceType.PAWN }, 
                                                { PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY, PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY }, 
                                                { PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY, PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY }, 
                                                { PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY, PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY }, 
                                                { PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY, PieceType.EMPTY, PieceType.EMPTY,  PieceType.EMPTY,  PieceType.EMPTY }, 
                                                { PieceType.PAWN,  PieceType.PAWN,   PieceType.PAWN,   PieceType.PAWN,  PieceType.PAWN,  PieceType.PAWN,   PieceType.PAWN,   PieceType.PAWN }, 
                                                { PieceType.ROOK,  PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING,  PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK }}; 
  public final Player[][] STARTING_PLAYERS = {{Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK}, 
                                              {Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK}, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE}, 
                                              {Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE}}; 

  private PieceType[][] pieces; 
  private Player[][] pieceOwners; 
  private Player turn; 
  public GameState() { 
    pieces = STARTING_PIECES; 
    pieceOwners = STARTING_PLAYERS; 
    turn = Player.WHITE; 
  } 
  /* Turns the board state into a string like the following: 
  * R N B Q K B N R 
  * P P P P P P P P 
  * 
  * 
  * 
  * 
  * p p p p p p p p 
  * r n b q k b k r 
  */ 
  public String toString() { 
    String result = ""; 
    for (int i = 0; i < 8; i++) { 
      for (int j = 0; j < 8; j++) { 
        if (pieceOwners[i][j] == Player.BLACK) { 
          switch(pieces[i][j]) { 
            case EMPTY: 
              result += " "; 
              break; 
            case PAWN: 
              result += "P"; 
              break; 
            case BISHOP: 
              result += "B"; 
              break; 
            case KNIGHT: 
              result += "N"; 
              break; 
            case ROOK: 
              result += "R"; 
              break; 
            case QUEEN: 
              result += "Q"; 
              break; 
            case KING: 
              result += "K"; 
              break; 
          } 
        } 
        else if (pieceOwners[i][j] == Player.WHITE) { 
          switch(pieces[i][j]) { 
            case EMPTY: 
              result += " "; 
              break; 
            case PAWN: 
              result += "P"; 
              break; 
            case BISHOP: 
              result += "B"; 
              break; 
            case KNIGHT: 
              result += "N"; 
              break; 
            case ROOK: 
              result += "R"; 
              break; 
            case QUEEN: 
              result += "Q"; 
              break; 
            case KING: 
              result += "K"; 
              break; 
          }
        } 
        else { 
          result += " "; 
        } 
        result += " "; 
      } 
      result += "\n"; 
    } 
    switch(turn) { 
      case WHITE: 
        result += "White to move"; 
        break; 
      case BLACK: 
        result += "White to move"; 
        break; 
      case NONE: 
        result += "Game over"; 
        break; 
    } 
    return result; 
  } 
}