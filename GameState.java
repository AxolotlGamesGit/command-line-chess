import java.util.ArrayList;

public class GameState { 
  public final Piece EMPTY = new Piece(Piece.PieceType.EMPTY,   Piece.Colour.NONE);
  public final Piece.PieceType PAWN = Piece.PieceType.PAWN;
  public final Piece.PieceType BISHOP = Piece.PieceType.BISHOP;
  public final Piece.PieceType KNIGHT = Piece.PieceType.KNIGHT;
  public final Piece.PieceType ROOK = Piece.PieceType.ROOK;
  public final Piece.PieceType QUEEN = Piece.PieceType.QUEEN;
  public final Piece.PieceType KING = Piece.PieceType.KING;
  public final Piece.Colour WHITE = Piece.Colour.WHITE;
  public final Piece.Colour BLACK = Piece.Colour.BLACK;

  private Piece[][] pieces; 
  private Piece.Colour turn = Piece.Colour.WHITE;
  private boolean whiteShortCastling = true;
  private boolean whiteLongCastling = true;
  private boolean blackShortCastling = true;
  private boolean blackLongCastling = true;
  private ArrayList<Piece> capturedPieces = new ArrayList<Piece>();
  private ArrayList<Move> moves = new ArrayList<Move>();

  public GameState() {
    pieces = new Piece[8][8];
    // Empty
    for (int i = 2; i < 6; i++) {
      for (int j = 0; j < 8; j++) {
        pieces[i][j] = EMPTY;
      }
    }
    // Pawns
    for (int i = 0; i < 8; i++) {
      pieces[1][i] = new Piece(PAWN, WHITE);
      pieces[6][i] = new Piece(PAWN, BLACK);
    }
    // Kings
    pieces[0][4] = new Piece(KING, WHITE);
    pieces[7][4] = new Piece(KING, BLACK);
    // Queens
    pieces[0][3] = new Piece(QUEEN, WHITE);
    pieces[7][3] = new Piece(QUEEN, BLACK);
    // Bishops
    pieces[0][2] = new Piece(BISHOP, WHITE);
    pieces[7][2] = new Piece(BISHOP, BLACK);
    pieces[0][5] = new Piece(BISHOP, WHITE);
    pieces[7][5] = new Piece(BISHOP, BLACK);
    // Knights
    pieces[0][1] = new Piece(KNIGHT, WHITE);
    pieces[7][1] = new Piece(KNIGHT, BLACK);
    pieces[0][6] = new Piece(KNIGHT, WHITE);
    pieces[7][6] = new Piece(KNIGHT, BLACK);
    // Rooks
    pieces[0][0] = new Piece(ROOK, WHITE);
    pieces[7][0] = new Piece(ROOK, BLACK);
    pieces[0][7] = new Piece(ROOK, WHITE);
    pieces[7][7] = new Piece(ROOK, BLACK);
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
        if (pieces[i][j].colour == BLACK) {
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

  private static int backRank(Piece.Colour colour) {
    return (int) (3.5 - 3.5 * (float) upDirection(colour));
  }

  private boolean inCheck() {
    int kingRank = 0;
    int kingFile = 0;
    Piece king = new Piece(KING, turn);
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

  private Move.MoveType getMoveType(Move move) {
    if (move.startRank<0 || move.startRank>7 || move.startFile<0 || move.startFile>7 || move.endRank<0 || move.endRank>7 || move.endFile<0 || move.endFile>7) {
      return Move.MoveType.NONE;
    }
    switch (pieces[move.startRank][move.startFile].piece) {
      case BISHOP:
      case KNIGHT:
      case ROOK:
      case QUEEN:
        return Move.MoveType.NORMAL;
      case KING:
        if (Math.abs(move.startFile - move.endFile) > 1) {
          return Move.MoveType.CASTLE;
        }
        else {
          return Move.MoveType.NORMAL;
        }
      case PAWN:
        if (move.endRank == 0  ||  move.endRank == 7) {
          return Move.MoveType.PROMOTION;
        }
        else if (move.startFile - move.endFile != 0  &&  pieces[move.endRank][move.endFile] == EMPTY) {
          return Move.MoveType.EN_PASSANT;
        }
        else {
          return Move.MoveType.NORMAL;
        }
      default:
        return Move.MoveType.NORMAL;
    }
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
    switch (move.moveType) {
      case NONE:
        return false;
      case EN_PASSANT:
        // No pawn behind you
        if (pieces[move.endRank - upDirection(turn)][move.endFile].piece != PAWN) {
          return false;
        }
        // No previous moves
        if (moves.size() < 1) {
          return false;
        }
        // Last move did not start on the opponents pawn rank
        if (moves.get(moves.size()-1).startRank != backRank(oppositePieceColour(turn))-upDirection(turn)) {
          return false;
        }
        // Last move did not end on the correct square
        if (moves.get(moves.size()-1).endRank != move.endRank-upDirection(turn)  ||  moves.get(moves.size()-1).endFile != move.endFile) {
          return false;
        }
        return true;
      case PROMOTION:
        System.out.println("0");
        // Not on the correct back rank
        if (move.endRank != backRank(oppositePieceColour(turn))) {
          System.out.println("1");
          return false;
        }
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
            if (move.startRank != backRank(turn)+upDirection(turn)) {
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
            System.out.println("2");
            return false;
          }
          // No direct capture
          if (pieces[move.endRank][move.endFile] == EMPTY) {
            System.out.println("3");
            return false;
          }
        }
        return true;
      case CASTLE:
        // Something in the way
        for (int i = 1; i < Math.abs(move.startFile-move.endFile); i++) {
          int rank = move.startRank + i * (int) Math.signum(move.endRank-move.startRank);
          int file = move.startFile + i * (int) Math.signum(move.endFile-move.startFile);
          if (pieces[rank][file] != EMPTY) {
            return false;
          }
        }
        // Short castle
        if (move.endFile == 6) {
          if (turn == WHITE) {
            return whiteShortCastling;
          }
          else {
            return blackShortCastling;
          }
        }
        // Long castle
        else if (move.endFile == 2) {
          if (turn == WHITE) {
            return whiteLongCastling;
          }
          else {
            return blackLongCastling;
          }
        }
      case NORMAL:
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
                if (move.startRank != backRank(turn)+upDirection(turn)) {
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
                return false;
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
    }
    
    return false;
  }
  
  private ArrayList<Move> getPsuedoLegalMoves(int startRank, int startFile) {
    ArrayList<Move> moves = new ArrayList<Move>();
    Piece piece = pieces[startRank][startFile];
    int endRank = 0;
    int endFile = 0;
    if (piece.colour != turn) {
      return moves;
    }
    // Possible moves
    switch(pieces[startRank][startFile].piece) {
      case PAWN:
        endRank = startRank+upDirection(turn);
        endFile = startFile;
        moves.add(new Move(startRank, startFile, endRank, endFile));
        endRank = startRank+2*upDirection(turn);
        endFile = startFile;
        moves.add(new Move(startRank, startFile, endRank, endFile));
        endRank = startRank+upDirection(turn);
        endFile = startFile-1;
        moves.add(new Move(startRank, startFile, endRank, endFile));
        endRank = startRank+upDirection(turn);
        endFile = startFile+1;
        moves.add(new Move(startRank, startFile, endRank, endFile));
        break;
      case BISHOP:
        for (int i = 1; i < 8; i++) {
          if (startFile+i < 8  &&  startRank+i < 8  &&  pieces[startRank+i][startFile+i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank+i, startFile+i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile+i < 8  &&  startRank-i >= 0  &&  pieces[startRank-i][startFile+i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank-i, startFile+i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile-i >= 0  &&  startRank-i >= 0  &&  pieces[startRank-i][startFile-i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank-i, startFile-i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile-i >= 0  &&  startRank+i < 8  &&  pieces[startRank+i][startFile-i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank+i, startFile-i));
          }
          else {
            break;
          }
        }
        break;
      case KNIGHT:
        for (int i = -2; i < 3; i++) {
          if (i == 0) {
            i++;
          }
          moves.add(new Move(startRank, startFile, startRank+i, startFile+3-Math.abs(i)));
          moves.add(new Move(startRank, startFile, startRank+i, startFile-3+Math.abs(i)));
        }
        break;
      case ROOK:
        for (int i = 1; i < 8; i++) {
          if (startFile+i < 8  &&  pieces[startRank][startFile+i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank, startFile+i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile-i >= 0  &&  pieces[startRank][startFile-i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank, startFile-i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startRank+i < 8  &&  pieces[startRank+i][startFile] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank+i, startFile));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startRank-i >= 0  &&  pieces[startRank-i][startFile] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank-i, startFile));
          }
          else {
            break;
          }
        }
        break;
      case QUEEN:
        // Horizontal
        for (int i = 1; i < 8; i++) {
          if (startFile+i < 8  &&  startRank+i < 8  &&  pieces[startRank+i][startFile+i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank+i, startFile+i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile+i < 8  &&  startRank-i >= 0  &&  pieces[startRank-i][startFile+i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank-i, startFile+i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile-i >= 0  &&  startRank-i >= 0  &&  pieces[startRank-i][startFile-i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank-i, startFile-i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile-i >= 0  &&  startRank+i < 8  &&  pieces[startRank+i][startFile-i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank+i, startFile-i));
          }
          else {
            break;
          }
        }
        // Diagonal
        for (int i = 1; i < 8; i++) {
          if (startFile+i < 8  &&  pieces[startRank][startFile+i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank, startFile+i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startFile-i >= 0  &&  pieces[startRank][startFile-i] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank, startFile-i));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startRank+i < 8  &&  pieces[startRank+i][startFile] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank+i, startFile));
          }
          else {
            break;
          }
        }
        for (int i = 1; i < 8; i++) {
          if (startRank-i >= 0  &&  pieces[startRank-i][startFile] == EMPTY) {
            moves.add(new Move(startRank, startFile, startRank-i, startFile));
          }
          else {
            break;
          }
        }
        break;
      case KING:
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if (i == 0  &&  j == 0) {
              continue;
            }
            moves.add(new Move(startRank, startFile, i, j));
          }
        }
        break;
      default:
        break;
    }
    // Delete illegal moves
    for (int i = 0; i < moves.size(); i++) {
      if (i < 0) {
        i = 0;
      }
      moves.get(i).moveType = getMoveType(moves.get(i));
      if (isPsuedoLegalMove(moves.get(i))  ==  false) {
        moves.remove(i);
        i--;
      }
    }
    return moves;
  }
  
  public void move(Move move) throws Exception {
    if (move.moveType == Move.MoveType.NONE) {
      move.moveType = getMoveType(move);
    }
    if (move.moveType == Move.MoveType.PROMOTION  &&  move.promotionPiece == Piece.PieceType.EMPTY) {
      move.promotionPiece = QUEEN;
    }
    if (isPsuedoLegalMove(move) == false) {
      throw new Exception("Illegal move");
    }
    Piece capturedPiece = EMPTY;
    switch (move.moveType) {
      case NORMAL:
        // Move
        capturedPiece = pieces[move.endRank][move.endFile];
        pieces[move.endRank][move.endFile] = pieces[move.startRank][move.startFile];
        pieces[move.startRank][move.startFile] = EMPTY;
        
        // Update castling availability for king moves
        if (pieces[move.startRank][move.startFile].piece == KING) {
          if (turn == WHITE) {
            whiteShortCastling = false;
            whiteLongCastling = false;
          }
          else {
            blackShortCastling = false;
            blackLongCastling = false;
          }
        }
        // Update castling availability for queen side rook moves
        if (move.startRank == backRank(turn)  &&  move.startFile == 0  &&  pieces[move.startRank][move.startFile].piece == ROOK) {
          if (turn == WHITE) {
            whiteLongCastling = false;
          }
          else {
            blackLongCastling = false;
          }
        }
        // Update castling availability for king side rook moves
        if (move.startRank == backRank(turn)  &&  move.startFile == 0  &&  pieces[move.startRank][move.startFile].piece == ROOK) {
          if (turn == WHITE) {
            whiteShortCastling = false;
          }
          else {
            blackShortCastling = false;
          }
        }
        break;
      case EN_PASSANT:
        capturedPiece = pieces[move.endRank-upDirection(turn)][move.endFile];
        pieces[move.endRank-upDirection(turn)][move.endFile] = EMPTY;
        pieces[move.endRank][move.endFile] = pieces[move.startRank][move.startFile];
        pieces[move.startRank][move.startFile] = EMPTY;
        break;
      case PROMOTION:
        capturedPiece = pieces[move.endRank][move.endFile];
        pieces[move.endRank][move.endFile] = new Piece(move.promotionPiece, turn);
        pieces[move.startRank][move.startFile] = EMPTY;
        break;
      case CASTLE:
        // Move king
        capturedPiece = EMPTY;
        pieces[move.endRank][move.endFile] = pieces[move.startRank][move.startFile];
        pieces[move.startRank][move.startFile] = EMPTY;
        // Move king side rook
        if (move.endFile == 6) {
          pieces[move.startRank][5] = new Piece(KNIGHT, turn);
          pieces[move.startRank][7] = EMPTY;
        }
        // Move queen side rook
        else {
          pieces[move.startRank][3] = new Piece(KNIGHT, turn);
          pieces[move.startRank][0] = EMPTY;
        }
        // Update castling availability
        if (turn == WHITE) {
          whiteShortCastling = false;
          whiteLongCastling = false;
        }
        else {
          blackShortCastling = false;
          blackLongCastling = false;
        }
        break;
    }

    if (inCheck()) {
      pieces[move.startRank][move.startFile] = pieces[move.endRank][move.endFile];
      pieces[move.endRank][move.endFile] = capturedPiece;

      throw new Exception("Illegal move - you are in check");
    }

    turn = oppositePieceColour(turn);;
    moves.add(move);
    capturedPieces.add(capturedPiece);
  }
}