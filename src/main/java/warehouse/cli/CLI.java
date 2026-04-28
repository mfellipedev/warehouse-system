package warehouse.cli;

import warehouse.domain.Product;
import warehouse.service.InventoryManager;
import warehouse.service.WarehouseQuery;

import java.util.Map;
import java.util.Scanner;

public class CLI {
    private final InventoryManager inventoryManager;
    private final WarehouseQuery warehouseQuery;
    private final Scanner scanner;

    public CLI(InventoryManager inventoryManager, WarehouseQuery warehouseQuery) {
        this.inventoryManager = inventoryManager;
        this.warehouseQuery = warehouseQuery;
        this.scanner = new Scanner(System.in);
    }

    public static void welcomeMessage() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     WAREHOUSE SYSTEM         ║");
        System.out.println("║        Bem vindo!            ║");
        System.out.println("║     Aguarde um momento       ║");
        System.out.println("╚══════════════════════════════╝");

        try {
            Thread.sleep(3000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void menu() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         WAREHOUSE SYSTEM         ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║1. Armazenar                      ║");
        System.out.println("║2. Transferir                     ║");
        System.out.println("║3. Remover Saldo                  ║");
        System.out.println("║4. Buscar Produto                 ║");
        System.out.println("║5. Relatório De Endereços         ║");
        System.out.println("║6. Relatório De Ruas              ║");
        System.out.println("║0. Sair                           ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    public void start() {
        while (true) {
            menu();
            System.out.println("Escolha uma opção: ");
            String option = scanner.nextLine();
            if (!validOption(option)) {
                continue;
            }

            switch (Integer.parseInt(option)) {
                case 0:
                    System.out.println("Obrigado por utilizar o Warehouse System!");
                    System.exit(0);
                case 1:
                    armazenar();
                    continue;
                case 2:
                    transferir();
                    continue;
                case 3:
                    removerSaldo();
                    continue;
                case 4:
                    buscarProduto();
                    continue;
                case 5:
                    relatorioEnderecos();
                    continue;
                case 6:
                    relatorioRuas();
                    continue;
            }
        }
    }

    private boolean validOption(String option) {
        try {
            int selectedOption = Integer.parseInt(option);
            if (selectedOption > 6 || selectedOption < 0) {
                System.out.println("Opção Invalida! \nEncerrando o programa");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void armazenar() {
        System.out.println("ID: (Informe o ID do endereço)");
        String id = scanner.nextLine();
        System.out.println("Informe um nome para o produto: ");
        String name = scanner.nextLine();
        System.out.println("SKU: (Limite de 10 caracteres");
        String sku = scanner.nextLine();
        System.out.println("Quantidade: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        try {
            inventoryManager.store(id, new Product(name, sku), quantity);
            System.out.println("Armazenamento realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void transferir() {
        System.out.println("Informe o ID do endereço de origem: ");
        String origin = scanner.nextLine();
        System.out.println("Informe o ID do endereço de destino: ");
        String dest = scanner.nextLine();
        System.out.println("Quantidade: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        try {
            boolean transfer = inventoryManager.transfer(origin, dest, quantity);
            if (transfer) {
                System.out.println("Transferência realizada com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerSaldo() {
        System.out.println("Informe o ID do endereço: ");
        String id = scanner.nextLine();
        System.out.println("Informe o SKU: ");
        String sku = scanner.nextLine();
        System.out.println("Quantidade a ser removida: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.println("Saldo Restante: " + inventoryManager.remove(id, sku, quantity) + "\n" +
            "Saldo Removido: " + quantity);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarProduto() {
        System.out.println("SKU Buscado: ");
        String sku = scanner.nextLine();

        try {
            warehouseQuery.queryProductInStock(sku).stream().forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void relatorioEnderecos() {
        System.out.println("Todos os endereços serão exibidos!");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════╣");
        Map<String,String> addresses = warehouseQuery.completeAdressesQuery();
        addresses.forEach((id,status) -> {
            System.out.printf("║ Endereço: %-12s ║ Status: %-55s ║%n", id, status);
        });
    }

    public void relatorioRuas(){
        System.out.println("Ruas Disponíveis: ");
        warehouseQuery.streetsQuery().stream()
                .forEach(System.out::println);
    }
}
