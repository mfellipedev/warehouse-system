package warehouse.cli;

import warehouse.service.InventoryManager;
import warehouse.service.WarehouseQuery;

public class CLI {
    private final InventoryManager inventoryManager;
    private final WarehouseQuery warehouseQuery;

    public CLI(InventoryManager inventoryManager, WarehouseQuery warehouseQuery) {
        this.inventoryManager = inventoryManager;
        this.warehouseQuery = warehouseQuery;
    }

    public void welcomeMessage(){
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     WAREHOUSE SYSTEM         ║");
        System.out.println("║        Bem vindo!            ║");
        System.out.println("║     Aguarde um momento       ║");
        System.out.println("╚══════════════════════════════╝");

        try {
            Thread.sleep(3000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    public void menu() {

    }
}
