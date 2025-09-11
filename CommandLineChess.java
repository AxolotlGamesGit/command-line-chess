import java.io.*;
import java.util.ArrayList;

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
          System.out.println("Processing move: " + tokens[1]);
          try {
            state.parseMove(tokens[1]);
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
}