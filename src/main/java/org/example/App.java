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
        while (running) {
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

    private void input() {
        for (int i = 0; i < 24; i++) {
            prices[i] = Integer.parseInt(scanner.nextLine());
        }
    }

    private void minMaxAverage() {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int minHour = 0;
        int maxHour = 0;
        for (int i = 0; i < prices.length; i++) {
            sum += prices[i];
            if (prices[i] < min) {
                min = prices[i];
                minHour = i;
            }
            if (prices[i] > max) {
                max = prices[i];
                maxHour = i;
            }
        }
        double average = (double) sum / prices.length;

        System.out.printf("Lägsta pris: %s, %d öre/kWh\n", intervall(minHour), min);
        System.out.printf("Högsta pris: %s, %d öre/kWh\n", intervall(maxHour), max);
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);


//        Lägsta pris: 02-03, 1 öre/kWh
//        Högsta pris: 00-01, 100 öre/kWh
//        Medelpris: 13,38 öre/kWh


    }

    private String intervall(int minHour) {
        return String.format("%02d-%02d", minHour, minHour + 1);
    }

    private void sort() {

    }

    private void bestChargeTime() {
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

record TimeAndPrice(String intervall, int price){}
