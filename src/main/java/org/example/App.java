package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static final int WINDOW_SIZE = 4;
    Scanner scanner = new Scanner(System.in);
    TimeAndPrice[] prices = new TimeAndPrice[24];

    public static void main(String[] args) {
        Locale.setDefault(Locale.of("sv","SE"));
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
            prices[i] = new TimeAndPrice(intervall(i), Integer.parseInt(scanner.nextLine()));
        }
    }

    private void minMaxAverage() {
        int sum = 0;
        TimeAndPrice min = prices[0];
        TimeAndPrice max = prices[0];
        for (TimeAndPrice price : prices) {
            sum += price.price();
            if (price.price() < min.price()) {
                min = price;
            }
            if (price.price() > max.price()) {
                max = price;
            }
        }
        double average = (double) sum / prices.length;

        System.out.printf("Lägsta pris: %s, %d öre/kWh\n", min.intervall(), min.price());
        System.out.printf("Högsta pris: %s, %d öre/kWh\n", max.intervall(), max.price());
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);
    }

    private String intervall(int minHour) {
        return String.format("%02d-%02d", minHour, minHour + 1);
    }

    private void sort() {
        var temp = Arrays.copyOf(prices, prices.length);
        Arrays.sort(temp, Comparator.comparingInt(TimeAndPrice::price).reversed());
        for (var tAndP : temp) {
            System.out.print(tAndP.intervall() + " " + tAndP.price() + " öre\n");
        }
    }

    private void bestChargeTime() {
        double minAverage = Double.MAX_VALUE;
        TimeAndPrice startTime = prices[0];

        double sum = 0;
        for (int i = 0; i < WINDOW_SIZE; i++) {
            sum += prices[i].price();
        }
        for (int i = 0; i < prices.length - WINDOW_SIZE; i++) {
            if (sum / WINDOW_SIZE < minAverage) {
                minAverage = sum / WINDOW_SIZE;
                startTime = prices[i];
            }
            sum = sum - prices[i].price() + prices[i + WINDOW_SIZE].price();
        }
        System.out.printf("Påbörja laddning klockan %s\n", startTime.intervall().substring(0, 2));
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", minAverage);

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

record TimeAndPrice(String intervall, int price) {
}
