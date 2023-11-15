package ui;

import java.util.Scanner;

public class clientMain {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

//        new Repl(serverUrl).run();
        String option = "logged_out";

        while (!option.toLowerCase().equals("quit")) {
            System.out.printf("\n[" + option.toUpperCase() + "]%n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var numbers = line.split(" ");

            switch (numbers[0]) {
                case "login" -> {
                    option = "logged_in";
                }
                case "help" -> {
                    switch (option.toLowerCase()) {
                        case "logged_out" -> {
                            System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
                            System.out.println("login <USERNAME> <PASSWORD>");
                            System.out.println("quit - playing chess");
                            System.out.println("help - with possible commands");
                        }
                        default -> {
                        }
                    }
                }
                case "quit" -> {
                    option = "quit";
                }
                default -> {
                    option = "logged_out";
                }
            }
        }
    }
}
