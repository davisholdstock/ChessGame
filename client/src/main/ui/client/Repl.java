package ui.client;

import ui.client.websocket.NotificationHandler;
import webSocketMessages.Notification;

import java.util.Scanner;

import static chess.EscapeSequences.SET_TEXT_COLOR_RED;

public class Repl implements NotificationHandler {
    private final ChessClient client;

    public Repl(String severurl) {
        client = new ChessClient(severurl, this);
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

    @Override
    public void notify(Notification notification) {
        System.out.print(SET_TEXT_COLOR_RED + notification.message());
        printPrompt();
    }
}
