import java.io.*;
import java.util.ArrayList;

public class CommandParser {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while(true) {
      String input = reader.readLine();
      String[] tokens = GetTokens(input);
      switch (tokens[0]) {
        case "new":
          System.out.println("Starting new game");
          break;
        case "move":
          System.out.println("Processing move: " + tokens[1]);
          break;
        case "save":
          switch(tokens[1]) {
            case "":
            case " ":
            case "-pgn":
              System.out.println("Saving to pgn");
              break;
            case "-fen":
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
            case "-pgn":
              System.out.println("Loading from pgn");
              break;
            case "-fen":
              System.out.println("Loading from fen");
              break;
            default:
              System.out.println("Invalid token: " + tokens[1]);
          }
          break;
        default:
          System.out.println("Invalid token: " + tokens[0]);
          break;
      }
    }
  }

  private static String[] GetTokens(String input) {
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

    return (String[]) output.toArray();
  }
}
