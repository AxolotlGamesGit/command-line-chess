public class Piece {
  public enum PieceType { 
    EMPTY, 
    PAWN, 
    KNIGHT, 
    BISHOP, 
    ROOK, 
    QUEEN, 
    KING 
  } 
  public enum Colour { 
    NONE, 
    WHITE, 
    BLACK
  }
  public PieceType piece;
  public Colour colour;

  public Piece(PieceType pieceType, Colour colourInput) {
    piece = pieceType;
    colour = colourInput;
  }
}
