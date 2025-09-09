import java.util.Map;
import static java.util.Map.entry;    

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
  public final Player[][] STARTING_PLAYERS = {{Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE}, 
                                              {Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE}, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE,  Player.NONE }, 
                                              {Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK}, 
                                              {Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK}};
  final Map<Character, Integer> FILES = Map.ofEntries(
    entry('a', 0),
    entry('b', 1),
    entry('c', 2),
    entry('d', 3),
    entry('e', 4),
    entry('f', 5),
    entry('g', 6),
    entry('h', 7)
  );
  final Map<Character, Integer> RANKS = Map.ofEntries(
    entry('1', 0),
    entry('2', 1),
    entry('3', 2),
    entry('4', 3),
    entry('5', 4),
    entry('6', 5),
    entry('7', 6),
    entry('8', 7)
  );
  final Map<Character, PieceType> PIECES = Map.ofEntries(
    entry('P', PieceType.PAWN),
    entry('N', PieceType.KNIGHT),
    entry('B', PieceType.BISHOP),
    entry('R', PieceType.ROOK),
    entry('Q', PieceType.QUEEN),
    entry('K', PieceType.KING)
  );

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
    for (int i = 7; i >= 0; i--) { 
      for (int j = 0; j < 8; j++) { 
        if (pieceOwners[i][j] == Player.BLACK) { 
          switch(pieces[i][j]) { 
            case EMPTY: 
              result += " "; 
              break; 
            case PAWN: 
              result += "p"; 
              break; 
            case BISHOP: 
              result += "b"; 
              break; 
            case KNIGHT: 
              result += "n"; 
              break; 
            case ROOK: 
              result += "r"; 
              break; 
            case QUEEN: 
              result += "q"; 
              break; 
            case KING: 
              result += "k"; 
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
        result += "Black to move"; 
        break; 
      case NONE: 
        result += "Game over"; 
        break; 
    } 
    return result; 
  } 

  public void move(int startFile, int startRank, int endFile, int endRank) throws Exception {
    if (startRank<0 || startRank>7 || startFile<0 || startFile>7 || endRank<0 || endRank>7 || endFile<0 || endFile>7) {
      throw new Exception("Args not in bounds");
    }
    if (pieces[startRank][startFile] == PieceType.EMPTY) {
      throw new Exception("No piece found");
    }
    if (pieceOwners[startRank][startFile] != turn) {
      throw new Exception("Can't move that piece");
    }

    pieces[endRank][endFile] = pieces[startRank][startFile];
    pieceOwners[endRank][endFile] = turn;
    pieces[startRank][startFile] = PieceType.EMPTY;
    pieceOwners[startRank][startFile] = Player.NONE;

    if (turn == Player.WHITE) {
      turn = Player.BLACK;
    }
    else {
      turn = Player.WHITE;
    }
  }

  public void parseMove(String move) throws Exception {
    int startFile = 0;
    int startRank = 0;
    int endFile = 0;
    int endRank = 0;
    if (move.length() < 2) {
      throw new Exception("Move not even long enough");
    }
    if(move.length() == 6  &&  move.charAt(2) == '-'  &&  move.charAt(3) == '>') {
      if (FILES.containsKey(move.charAt(0))  &&  RANKS.containsKey(move.charAt(1))) {
        startFile = FILES.get(move.charAt(0));
        startRank = RANKS.get(move.charAt(1));
      }
      else {
        throw new Exception("Invalid starting square");
      }

      if (FILES.containsKey(move.charAt(4))  &&  RANKS.containsKey(move.charAt(5))) {
        endFile = FILES.get(move.charAt(4));
        endRank = RANKS.get(move.charAt(5));
      }
      else {
        throw new Exception("Invalid ending square");
      }
    }
    else if(PIECES.containsKey(move.charAt(0))) {
      // Algebraic chess notation
    }
    else if (move == "0-0"  ||  move == "O-O") {
      // King side
    }
    else if (move == "0-0-0"  ||  move == "O-O-O") {
      // Queen side
    }

    move(startFile, startRank, endFile, endRank);
  }
}