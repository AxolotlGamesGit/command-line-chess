public class Move {
  public enum MoveType {
    NONE,
    NORMAL,
    CASTLE,
    EN_PASSANT,
    PROMOTION
  }
  int startRank;
  int startFile;
  int endRank;
  int endFile;
  MoveType moveType;
  Piece.PieceType promotionPiece;

  public Move() {
    startRank = 0;
    startFile = 0;
    endRank = 0;
    endFile = 0;
    moveType = MoveType.NONE;
    promotionPiece = Piece.PieceType.EMPTY;
  }

  public Move(int sRank, int sFile, int eRank, int eFile) {
    startRank = sRank;
    startFile = sFile;
    endRank = eRank;
    endFile = eFile;
    moveType = MoveType.NONE;
    promotionPiece = Piece.PieceType.EMPTY;
  }
  
  public Move(int sRank, int sFile, int eRank, int eFile, MoveType mType) {
    startRank = sRank;
    startFile = sFile;
    endRank = eRank;
    endFile = eFile;
    moveType = mType;
    promotionPiece = Piece.PieceType.EMPTY;
  }
  
  public Move(int sRank, int sFile, int eRank, int eFile, Piece.PieceType pPiece) {
    startRank = sRank;
    startFile = sFile;
    endRank = eRank;
    endFile = eFile;
    moveType = MoveType.PROMOTION;
    promotionPiece = pPiece;
  }
}
