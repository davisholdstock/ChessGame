package ui.client;

import java.util.Scanner;

public class Repl {
    private final ChessClient client;

    public Repl(String severurl) {
        client = new ChessClient(severurl);
    }

    public void run() {
        System.out.println("Welcome to Chess 2.0. Sign in to start.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n[" + client.getState() + "]>>> ");
    }
}
