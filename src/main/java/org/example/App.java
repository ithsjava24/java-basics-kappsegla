package org.example;

import java.util.Locale;

public class App {

    public static void main(String[] args) {
        String t = """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta
                """;

        System.out.print(t);
        float f = 2.3f;
        String.format("%.2f", f).replace('.', ',');
        System.out.printf(Locale.of("sv", "SE"), "%.2f", f);
        System.out.println("Hello There!");
    }
}
