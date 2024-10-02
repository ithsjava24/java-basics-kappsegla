package org.example;

import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    int[] prices = new int[24];

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        boolean running = true;
        while( running ) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> input();
                case "2" -> minMaxAverage();
                case "3" -> sort();
                case "4" -> bestChargeTime();
                case "e", "E" -> running = false;
            }
        }
    }

    private void bestChargeTime() {
    }

    private void sort() {
        
    }

    private void minMaxAverage() {
        
    }

    private void input() {
        for (int i = 0; i < 24; i++) {
            prices[i] = Integer.parseInt(scanner.nextLine());
        }
    }

    private void printMenu() {
        System.out.print("""
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """);
    }


}
