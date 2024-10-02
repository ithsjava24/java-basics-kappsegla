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
        Locale.setDefault(Locale.of("sv", "SE"));
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
                case "5" -> visualize();
                case "e", "E" -> running = false;
            }
        }
    }

    private void visualize() {
        int min = minPrice();
        int max = maxPrice();
        int minLength = String.valueOf(min).length();
        int maxLength = String.valueOf(max).length();

        for (int row = 0; row < 6; row++) {
            int levelValue = (int) (max - ((max - min) / 5.0) * row);
            //Print left axis
            switch (row) {
                case 0 -> System.out.print(max + "|");
                case 5 -> System.out.print(repeatSpace(maxLength - minLength) + min + "|");
                default -> System.out.print(repeatSpace(maxLength) + "|");
            }
            printColumns(levelValue);
            System.out.print("\n");
        }
        printBottomAxis(maxLength);
    }

    private void printColumns(int levelValue) {
        for (int column = 0; column < 24; column++) {
            if (prices[column].price() >= levelValue)
                System.out.print("  x");
            else
                System.out.print("   ");
        }
    }

    private static String repeatSpace(int maxLength) {
        return " ".repeat(maxLength);
    }

    private static void printBottomAxis(int length) {
        System.out.print(repeatSpace(length) +
                         "|------------------------------------------------------------------------\n");
        System.out.print(repeatSpace(length) +
                         "| 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23\n");
    }

    private int maxPrice() {
        return Arrays.stream(prices)
                .mapToInt(TimeAndPrice::price)
                .max().orElse(0);
    }

    private int minPrice() {
        return Arrays.stream(prices)
                .mapToInt(TimeAndPrice::price)
                .min().orElse(0);
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
                5. Visualisering
                e. Avsluta
                """);
    }
}

record TimeAndPrice(String intervall, int price) {
}
