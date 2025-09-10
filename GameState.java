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
  public String toString(boolean showRank, boolean showFile) { 
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
      if (showRank) {
        result += i+1;
      }
      result += "\n"; 
    }
    if (showFile) {
      result += "a b c d e f g h \n";
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

  private static int upDirection(Player player) {
    switch(player) {
      case WHITE:
        return 1;
      case BLACK:
        return -1;
      case NONE:
        return 0;
    }

    return 0;
  }

  private void move(int startFile, int startRank, int endFile, int endRank) throws Exception {
    if (startRank<0 || startRank>7 || startFile<0 || startFile>7 || endRank<0 || endRank>7 || endFile<0 || endFile>7) {
      throw new Exception("Args not in bounds");
    }
    if (pieces[startRank][startFile] == PieceType.EMPTY) {
      throw new Exception("No piece found");
    }
    if (pieceOwners[startRank][startFile] != turn) {
      throw new Exception("Can't move that piece");
    }
    if (startFile == endFile  &&  startRank == endRank) {
      throw new Exception("Must move a piece");
    }
    if (pieceOwners[endRank][endFile] == turn) {
      throw new Exception("Cannot take you own piece");
    }
    boolean inCheck = false;
    if (inCheck) {
      // check to see if the move puts you out of check
      // if so, continue
      // if not, throw illegal move exception
    }
    switch (pieces[startRank][startFile]) {
      case PAWN:
        // Non taking move
        if (startFile == endFile) {
          // Moved up 1
          if (endRank-startRank == upDirection(turn)) {
            // Is already occupied
            if (pieces[endRank][endFile] != PieceType.EMPTY) {
              throw new Exception("Illegal move");
            }
          }
          // Moved up 2
          else if (endRank-startRank == 2*upDirection(turn)) {
            // Checks if the two spaces in front are empty
            if (pieces[endRank][endFile] != PieceType.EMPTY  ||  pieces[startRank+upDirection(turn)][endFile] != PieceType.EMPTY) {
              throw new Exception("Illegal move");
            }
            // Wrong starting rank
            if (startRank != (int) (3.5f-2.5f*(float)upDirection(turn))) {
              throw new Exception("Illegal move");
            }
          }
        }
        // Taking move
        else {
          // Invalid diagonal
          if (Math.abs(startFile-endFile) != 1  ||  endRank-startRank != upDirection(turn)) {
            throw new Exception("Illegal move");
          }
          // No capture
          if (pieces[endRank][endFile] == PieceType.EMPTY) {
            boolean isEnPassant = false; // Change this later
            if (isEnPassant) {
              break;
            }
            throw new Exception("Illegal move");
          }
        }
        break;
      case BISHOP:
        // Invalid diagonal
        if (Math.abs(endRank-startRank) != Math.abs(endFile-startFile)) {
          throw new Exception("Illegal move");
        }
        // Checks if all the squares it passes are blank
        for (int i = 1; i < Math.abs(startRank-endRank); i++) {
          int rank = startRank + (int) Math.copySign(i, endRank-startRank);
          int file = startFile + (int) Math.copySign(i, endFile-startFile);
          if (pieces[rank][file] != PieceType.EMPTY) {
            throw new Exception("Illegal move");
          }
        }
        break;
      case KNIGHT:
        // Check if the end square is the right distance away, no need to do sqrt
        if ((Math.pow((endRank-startRank), 2) + Math.pow((endFile-startFile), 2))  !=  (double) 5) {
          throw new Exception("Illegal move");
        }
        break;
      case ROOK:
        // Make sure it moves in a straight line.
        if (Math.abs(startRank-endRank) != 0  &&  Math.abs(startFile-endFile) != 0) {
          throw new Exception("Illegal move");
        }
        // Check to make sure nothing is in the way.
        for (int i = 1; i < Math.abs(startRank-endRank) + Math.abs(startFile-endFile); i++) {
          int rank = startRank + i * (int) Math.signum(endRank-startRank);
          int file = startFile + i * (int) Math.signum(endFile-startFile);
          if (pieces[rank][file] != PieceType.EMPTY) {
            throw new Exception("Illegal move");
          }
        }
        break;
      case QUEEN:
        // Invalid diagonal and invalid straight move
        if (Math.abs(startRank-endRank) != 0  &&  Math.abs(startFile-endFile) != 0
            &&  Math.abs(endRank-startRank) != Math.abs(endFile-startFile)) {
          throw new Exception("Invalid move");
        }
        // Check to see if anything is in the way
        for (int i = 1; i < Math.max(Math.abs(startRank-endRank), Math.abs(startFile-endFile)); i++) {
          int rank = startRank + i * (int) Math.signum(endRank-startRank);
          int file = startFile + i * (int) Math.signum(endFile-startFile);
          if (pieces[rank][file] != PieceType.EMPTY) {
            throw new Exception("Illegal move");
          }
        }
        break;
      case KING:
        // Moves farther than one space
        if (Math.max(Math.abs(startRank-endRank), Math.abs(startFile-endFile)) > 1) {
          throw new Exception("Illegal move");
        }
        break;
      default:
        break;
    }

    PieceType capturedPiece = pieces[endRank][endFile];
    pieces[endRank][endFile] = pieces[startRank][startFile];
    pieceOwners[endRank][endFile] = turn;
    pieces[startRank][startFile] = PieceType.EMPTY;
    pieceOwners[startRank][startFile] = Player.NONE;

    inCheck = false;
    if (inCheck) {
      pieces[startRank][startFile] = pieces[endRank][endFile];
      pieceOwners[startRank][startFile] = turn;
      pieces[endRank][endFile] = capturedPiece;
      if (capturedPiece != PieceType.EMPTY  &&  turn == Player.WHITE) {
        pieceOwners[startRank][startFile] = Player.BLACK;
      }
      if (capturedPiece != PieceType.EMPTY  &&  turn == Player.BLACK) {
        pieceOwners[startRank][startFile] = Player.WHITE;
      }
      throw new Exception("Illegal move");
    }

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