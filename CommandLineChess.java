import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;

public class CommandLineChess {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    GameState state = new GameState();
    boolean showRank = true;
    boolean showFile = true;
    boolean isRunning = true;

    while(isRunning) {
      String input = reader.readLine();
      String[] tokens = getTokens(input);
      switch (tokens[0]) {
        case "display":
          System.out.println(state.toString(showRank, showFile));
          break;
        case "new":
          state = new GameState();
          System.out.println(state.toString(showRank, showFile));
          break;
        case "quit":
          isRunning = false;
          break;
        case "move":
          if (tokens.length < 2) {
            System.out.println("Please provide a move");
            break;
          }
          try {
            Move move = parseMove(tokens[1]);
            state.move(move);
            System.out.println(state.toString(showRank, showFile));
          } 
          catch (Exception e) {
            System.out.println("Invalid move: " + e);
          }
          break;
        case "save":
          switch(tokens[1]) {
            case "":
            case " ":
            case "--pgn":
              System.out.println("Saving to pgn");
              break;
            case "--fen":
              System.out.println("Saving to fen");
              break;
            default:
              System.out.println("Invalid token: " + tokens[1]);
          }
          break;
        case "load":
          switch(tokens[1]) {
            case "":
            case " ":
            case "--pgn":
              System.out.println("Loading from pgn");
              break;
            case "--fen":
              System.out.println("Loading from fen");
              break;
            default:
              System.out.println("Invalid token: " + tokens[1]);
          }
          break;
        case "settings":
          if (tokens.length < 2) {
            System.out.println("Show rank: " + showRank);
            System.out.println("Show file: " + showFile);
            break;
          }
          for (int i = 1; i < tokens.length; i++) {
            switch (tokens[i]) {
              case "--showrank":
                if (tokens.length > i+1) {
                  switch (tokens[i+1]) {
                    case "true":
                    case "True":
                      showRank = true;
                      i++;
                      break;
                    case "false":
                    case "False":
                      showRank = false;
                      i++;
                      break;
                    default:
                      System.out.println("Show rank: " + showRank);
                      break;
                  }
                  break;
                }
                System.out.println("Show rank: " + showRank);
                break;
              case "--showfile":
                if (tokens.length > i+1) {
                  switch (tokens[i+1]) {
                    case "true":
                    case "True":
                      showFile = true;
                      i++;
                      break;
                    case "false":
                    case "False":
                      showFile = false;
                      i++;
                      break;
                    default:
                      System.out.println("Show file: " + showFile);
                      break;
                  }
                  break;
                }
                System.out.println("Show file: " + showFile);
                break;
              default:
                break;
            }
          }
          break;
        default:
          System.out.println("Invalid token: " + tokens[0]);
          break;
      }
    }
  }

  private static String[] getTokens(String input) {
    ArrayList<String> output = new ArrayList<String>();
    int j = 0;
    while(j < input.length()) {
      while(j < input.length()  &&  input.charAt(j) == ' ') {
        j++;
      }

      String current = "";
      while(j < input.length()  &&  input.charAt(j) != ' ') {
        current += input.charAt(j);
        j++;
      }

      output.add(current);
    }

    String[] outputArray = new String[output.size()];
    for (int i = 0; i < output.size(); i++) {
      outputArray[i] = output.get(i);
    }
    return outputArray;
  }
  
  public static Move parseMove(String moveString) throws Exception {
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
    
    Move move = new Move();
    if (moveString.length() < 2) {
      throw new Exception("Move not even long enough");
    }
    // Custom notation (a1->h8, ect)
    if ((moveString.length() == 6  ||  moveString.length() == 9)  &&  moveString.charAt(2) == '-'  &&  moveString.charAt(3) == '>') {
      // Start square
      if (FILES.containsKey(moveString.charAt(0))  &&  RANKS.containsKey(moveString.charAt(1))) {
        move.startFile = FILES.get(moveString.charAt(0));
        move.startRank = RANKS.get(moveString.charAt(1));
      }
      else {
        throw new Exception("Invalid starting square");
      }
        
      // End square
      if (FILES.containsKey(moveString.charAt(4))  &&  RANKS.containsKey(moveString.charAt(5))) {
        move.endFile = FILES.get(moveString.charAt(4));
        move.endRank = RANKS.get(moveString.charAt(5));
      }
      else {
        throw new Exception("Invalid ending square");
      }
      
      // Pawn promotion
      if (moveString.length() == 9  &&  moveString.charAt(6) == '-'  &&  moveString.charAt(7) == '>') {
        if (PIECES.containsKey(moveString.charAt(8))) {
          move.moveType = Move.MoveType.PROMOTION;
          move.promotionPiece = PIECES.get(moveString.charAt(8));
        }
      }
    }
    else if (moveString == "0-0"  ||  moveString == "O-O") {
      // King side
    }
    else if (moveString == "0-0-0"  ||  moveString == "O-O-O") {
      // Queen side
    }

    return move;
  }
}