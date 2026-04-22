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

        System.out.println("Informe a quantidade de ruas no armazém: (Somente valores entre 1 e 26)");
        int quantityStreets = Integer.parseInt(scanner.nextLine());
        System.out.println("Informe a quantidade de níveis: (Somente valores entre 1 e 3)");
        int quantityLevels = Integer.parseInt(scanner.nextLine());
        System.out.println("Informe a quantidade de colunas: (Somente valores entre 1 e 10)");
        int quantityColumns = Integer.parseInt(scanner.nextLine());


        try {
            Warehouse warehouse = new Warehouse(quantityStreets, quantityLevels, quantityColumns);
            InventoryManager inventoryManager = new InventoryManager(warehouse);
            WarehouseQuery warehouseQuery = new WarehouseQuery(inventoryManager);
            CLI cli = new CLI(inventoryManager, warehouseQuery);

            cli.menu();
            cli.start();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


    }
}
