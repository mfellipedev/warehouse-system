package org.example;

import warehouse.cli.CLI;
import warehouse.domain.Warehouse;
import warehouse.service.InventoryManager;
import warehouse.service.WarehouseQuery;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        CLI.welcomeMessage();

            int quantityStreets = read(scanner, "Informe a quantidade de ruas no armazém: (Somente valores entre 1 e 26)", 1, 26);
            int quantityLevels = read(scanner, "Informe a quantidade de níveis: (Somente valores entre 1 e 3)", 1, 3);
            int quantityColumns = read(scanner, "Informe a quantidade de colunas: (Somente valores entre 1 e 10)", 1, 10);


            Warehouse warehouse = new Warehouse(quantityStreets, quantityLevels, quantityColumns);
            InventoryManager inventoryManager = new InventoryManager(warehouse);
            WarehouseQuery warehouseQuery = new WarehouseQuery(inventoryManager);
            CLI cli = new CLI(inventoryManager, warehouseQuery);

            cli.start();



    }

    public static int read(Scanner scanner, String message, int min, int max) {
        while (true) {
            try {
                System.out.println(message);
                int value = Integer.valueOf(scanner.nextLine());

                if (value < min || value > max) {
                    throw new IllegalArgumentException("Verifique se os valores atendem as regras!");
                }

                return value;

            } catch (IllegalArgumentException e) {
                if (e instanceof NumberFormatException) {
                    System.out.println("Somente números validos são permitidos!");
                } else {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
