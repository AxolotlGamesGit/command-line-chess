import java.util.Map;
import static java.util.Map.entry;
import java.util.ArrayList;

public class GameState { 
  public final Piece EMPTY = new Piece(Piece.PieceType.EMPTY,   Piece.Colour.NONE);
  public final Piece WHITE_PAWN = new Piece(Piece.PieceType.PAWN, Piece.Colour.WHITE);
  public final Piece WHITE_BISHOP = new Piece(Piece.PieceType.BISHOP, Piece.Colour.WHITE);
  public final Piece WHITE_KNIGHT = new Piece(Piece.PieceType.KNIGHT, Piece.Colour.WHITE);
  public final Piece WHITE_ROOK = new Piece(Piece.PieceType.ROOK, Piece.Colour.WHITE);
  public final Piece WHITE_QUEEN = new Piece(Piece.PieceType.QUEEN, Piece.Colour.WHITE);
  public final Piece WHITE_KING = new Piece(Piece.PieceType.KING, Piece.Colour.WHITE);
  public final Piece BLACK_PAWN = new Piece(Piece.PieceType.PAWN, Piece.Colour.BLACK);
  public final Piece BLACK_BISHOP = new Piece(Piece.PieceType.BISHOP, Piece.Colour.BLACK);
  public final Piece BLACK_KNIGHT = new Piece(Piece.PieceType.KNIGHT, Piece.Colour.BLACK);
  public final Piece BLACK_ROOK = new Piece(Piece.PieceType.ROOK, Piece.Colour.BLACK);
  public final Piece BLACK_QUEEN = new Piece(Piece.PieceType.QUEEN, Piece.Colour.BLACK);
  public final Piece BLACK_KING = new Piece(Piece.PieceType.KING, Piece.Colour.BLACK);
  public final Piece[][] STARTING_PIECES = { { WHITE_ROOK,  WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING,  WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK }, 
                                             { WHITE_PAWN,  WHITE_PAWN,   WHITE_PAWN,   WHITE_PAWN,  WHITE_PAWN,  WHITE_PAWN,   WHITE_PAWN,   WHITE_PAWN }, 
                                             { EMPTY,       EMPTY,        EMPTY,        EMPTY,       EMPTY,       EMPTY,        EMPTY,        EMPTY      }, 
                                             { EMPTY,       EMPTY,        EMPTY,        EMPTY,       EMPTY,       EMPTY,        EMPTY,        EMPTY      }, 
                                             { EMPTY,       EMPTY,        EMPTY,        EMPTY,       EMPTY,       EMPTY,        EMPTY,        EMPTY      }, 
                                             { EMPTY,       EMPTY,        EMPTY,        EMPTY,       EMPTY,       EMPTY,        EMPTY,        EMPTY      }, 
                                             { BLACK_PAWN,  BLACK_PAWN,   BLACK_PAWN,   BLACK_PAWN,  BLACK_PAWN,  BLACK_PAWN,   BLACK_PAWN,   BLACK_PAWN }, 
                                             { BLACK_ROOK,  BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING,  BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK }}; 

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
  final Map<Character, Piece.PieceType> PIECES = Map.ofEntries(
    entry('P', Piece.PieceType.PAWN),
    entry('N', Piece.PieceType.KNIGHT),
    entry('B', Piece.PieceType.BISHOP),
    entry('R', Piece.PieceType.ROOK),
    entry('Q', Piece.PieceType.QUEEN),
    entry('K', Piece.PieceType.KING)
  );

  private Piece[][] pieces; 
  private Piece.Colour turn;
  private boolean whiteShortCastling = true;
  private boolean whiteLongCastling = true;
  private boolean blackShortCastling = true;
  private boolean blackLongCastling = true;
  private ArrayList<Piece> capturedPieces = new ArrayList<Piece>();
  private ArrayList<Move> moves = new ArrayList<Move>();
    
  public GameState() { 
    pieces = STARTING_PIECES; 
    turn = Piece.Colour.WHITE; 
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
    String result = "\n"; 
    for (int i = 7; i >= 0; i--) { 
      for (int j = 0; j < 8; j++) {
        if (pieces[i][j].colour == Piece.Colour.BLACK) {
          switch(pieces[i][j].piece) { 
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
        else {
          switch(pieces[i][j].piece) {
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
        result += "White to move \n"; 
        break; 
      case BLACK: 
        result += "Black to move \n"; 
        break; 
      case NONE: 
        result += "Game over \n"; 
        break; 
    }
    if (inCheck()) {
      result += "You are in check!";
    }
    return result; 
  } 

  private static int upDirection(Piece.Colour colour) {
    switch(colour) {
      case WHITE:
        return 1;
      case BLACK:
        return -1;
      case NONE:
        return 0;
    }

    return 0;
  }

  private static Piece.Colour oppositePieceColour(Piece.Colour colour) {
    switch (colour) {
      case WHITE:
        return Piece.Colour.BLACK;
      case BLACK:
        return Piece.Colour.WHITE;
      case NONE:
        return Piece.Colour.NONE;
    }
    return Piece.Colour.NONE;
  }

  private boolean inCheck() {
    int kingRank = 0;
    int kingFile = 0;
    Piece king = new Piece(Piece.PieceType.KING, turn);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (pieces[i][j] == king) {
          kingRank = i;
          kingFile = j;
        }
      }
    }

    turn = oppositePieceColour(turn);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (pieces[i][j] != EMPTY  &&  isPsuedoLegalMove(new Move(i, j, kingRank, kingFile))) {
          return true;
        }
      }
    }
    turn = oppositePieceColour(turn);

    return false;
  }

  private boolean isPsuedoLegalMove(Move move) {
    if (move.startRank<0 || move.startRank>7 || move.startFile<0 || move.startFile>7 || move.endRank<0 || move.endRank>7 || move.endFile<0 || move.endFile>7) {
      return false;
    }
    if (pieces[move.startRank][move.startFile] == EMPTY) {
      return false;
    }
    if (pieces[move.startRank][move.startFile].colour != turn) {
      return false;
    }
    if (move.startFile == move.endFile  &&  move.startRank == move.endRank) {
      return false;
    }
    if (pieces[move.endRank][move.endFile].colour == turn) {
      return false;
    }
    switch (pieces[move.startRank][move.startFile].piece) {
      case PAWN:
        // Non taking move
        if (move.startFile == move.endFile) {
          // Moved up 1
          if (move.endRank-move.startRank == upDirection(turn)) {
            // Is already occupied
            if (pieces[move.endRank][move.endFile] != EMPTY) {
              return false;
            }
          }
          // Moved up 2
          else if (move.endRank-move.startRank == 2*upDirection(turn)) {
            // Checks if the two spaces in front are empty
            if (pieces[move.endRank][move.endFile] != EMPTY  ||  pieces[move.startRank+upDirection(turn)][move.endFile] != EMPTY) {
              return false;
            }
            // Wrong starting rank
            if (move.startRank != (int) (3.5f-2.5f*(float)upDirection(turn))) {
              return false;
            }
          }
          // Moved more than 2 spaces.
          else {
            return false;
          }
        }
        // Taking move
        else {
          // Invalid diagonal
          if (Math.abs(move.startFile-move.endFile) != 1  ||  move.endRank-move.startRank != upDirection(turn)) {
            return false;
          }
          // No direct capture
          if (pieces[move.endRank][move.endFile] == EMPTY) {
            // No pawn behind you
            if (pieces[move.endRank - upDirection(turn)][move.endFile].piece != Piece.PieceType.PAWN) {
              return false;
            }
            // Last move did not start on the opponents pawn rank
            if (moves.get(moves.size()-1).startRank != (int) (3.5f-2.5f*(float)upDirection(oppositePieceColour(turn)))) {
              return false;
            }
            // Last move did not end on the correct square
            if (moves.get(moves.size()-1).endRank != move.endRank-upDirection(turn)  ||  moves.get(moves.size()-1).endFile != move.endFile) {
              return false;
            }
            // Capture the pawn we are taking
            pieces[move.endRank - upDirection(turn)][move.endFile] = EMPTY;
          }
        }
        return true;
      case BISHOP:
        // Invalid diagonal
        if (Math.abs(move.endRank-move.startRank) != Math.abs(move.endFile-move.startFile)) {
          return false;
        }
        // Checks if all the squares it passes are blank
        for (int i = 1; i < Math.abs(move.startRank-move.endRank); i++) {
          int rank = move.startRank + (int) Math.copySign(i, move.endRank-move.startRank);
          int file = move.startFile + (int) Math.copySign(i, move.endFile-move.startFile);
          if (pieces[rank][file] != EMPTY) {
            return false;
          }
        }
        return true;
      case KNIGHT:
        // Check if the end square is the right distance away, no need to do sqrt
        if ((Math.pow((move.endRank-move.startRank), 2) + Math.pow((move.endFile-move.startFile), 2))  !=  (double) 5) {
          return false;
        }
        return true;
      case ROOK:
        // Make sure it moves in a straight line.
        if (Math.abs(move.startRank-move.endRank) != 0  &&  Math.abs(move.startFile-move.endFile) != 0) {
          return false;
        }
        // Check to make sure nothing is in the way.
        for (int i = 1; i < Math.abs(move.startRank-move.endRank) + Math.abs(move.startFile-move.endFile); i++) {
          int rank = move.startRank + i * (int) Math.signum(move.endRank-move.startRank);
          int file = move.startFile + i * (int) Math.signum(move.endFile-move.startFile);
          if (pieces[rank][file] != EMPTY) {
            return false;
          }
        }
        return true;
      case QUEEN:
        // Invalid diagonal and invalid straight move
        if (Math.abs(move.startRank-move.endRank) != 0  &&  Math.abs(move.startFile-move.endFile) != 0
            &&  Math.abs(move.endRank-move.startRank) != Math.abs(move.endFile-move.startFile)) {
          return false;
        }
        // Check to see if anything is in the way
        for (int i = 1; i < Math.max(Math.abs(move.startRank-move.endRank), Math.abs(move.startFile-move.endFile)); i++) {
          int rank = move.startRank + i * (int) Math.signum(move.endRank-move.startRank);
          int file = move.startFile + i * (int) Math.signum(move.endFile-move.startFile);
          if (pieces[rank][file] != EMPTY) {
            return false;
          }
        }
        return true;
      case KING:
        // Moves farther than one space
        if (Math.max(Math.abs(move.startRank-move.endRank), Math.abs(move.startFile-move.endFile)) > 1) {
          return false;
        }
        return true;
      default:
        break;
    }
    
    return false;
  }

  private void move(Move move) throws Exception {
    if (isPsuedoLegalMove(move) == false) {
      throw new Exception("Illegal move");
    }

    Piece capturedPiece = pieces[move.endRank][move.endFile];
    pieces[move.endRank][move.endFile] = pieces[move.startRank][move.startFile];
    pieces[move.startRank][move.startFile] = EMPTY;

    if (inCheck()) {
      pieces[move.startRank][move.startFile] = pieces[move.endRank][move.endFile];
      pieces[move.endRank][move.endFile] = capturedPiece;

      throw new Exception("Illegal move - you are in check");
    }

    turn = oppositePieceColour(turn);;
    moves.add(move);
    capturedPieces.add(capturedPiece);
  }

  public void parseMove(String moveString) throws Exception {
    Move move = new Move();
    if (moveString.length() < 2) {
      throw new Exception("Move not even long enough");
    }
    if(moveString.length() == 6  &&  moveString.charAt(2) == '-'  &&  moveString.charAt(3) == '>') {
      if (FILES.containsKey(moveString.charAt(0))  &&  RANKS.containsKey(moveString.charAt(1))) {
        move.startFile = FILES.get(moveString.charAt(0));
        move.startRank = RANKS.get(moveString.charAt(1));
      }
      else {
        throw new Exception("Invalid starting square");
      }

      if (FILES.containsKey(moveString.charAt(4))  &&  RANKS.containsKey(moveString.charAt(5))) {
        move.endFile = FILES.get(moveString.charAt(4));
        move.endRank = RANKS.get(moveString.charAt(5));
      }
      else {
        throw new Exception("Invalid ending square");
      }
    }
    else if(PIECES.containsKey(moveString.charAt(0))) {
      // Algebraic chess notation
    }
    else if (moveString == "0-0"  ||  moveString == "O-O") {
      // King side
    }
    else if (moveString == "0-0-0"  ||  moveString == "O-O-O") {
      // Queen side
    }

    move(move);
  }
}