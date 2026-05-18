package warehouse.cli;

import warehouse.domain.Product;
import warehouse.domain.TransactionLog;
import warehouse.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    private final InventoryManager inventoryManager;
    private final WarehouseQuery warehouseQuery;
    private final Scanner scanner;
    private LogQuery logQuery;
    private DataStorage dataStorage;

    public CLI(InventoryManager inventoryManager, WarehouseQuery warehouseQuery) {
        this.inventoryManager = inventoryManager;
        this.warehouseQuery = warehouseQuery;
        this.scanner = new Scanner(System.in);
        this.logQuery = new LogQuery(inventoryManager.returnLogManager());
        this.dataStorage = new DataStorage();
    }

    public static void welcomeMessage() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║     WAREHOUSE SYSTEM         ║");
        System.out.println("║        Bem vindo!            ║");
        System.out.println("║     Aguarde um momento       ║");
        System.out.println("╚══════════════════════════════╝");

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
        System.out.println("║6. Histórico De Transação         ║");
        System.out.println("║7. DashBoard Do Armazém         ║");
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
                    logs();
                    continue;
                case 7:
                    dashboard();
                    continue;

            }
        }
    }

    private boolean validOption(String option) {
        try {
            int selectedOption = Integer.parseInt(option);
            if (selectedOption > 7 || selectedOption < 0) {
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
            Product product = new Product(name, sku);
            inventoryManager.store(id, product, quantity);
            inventoryManager.addProduct(product.sku(), product);
            System.out.println("Armazenamento realizado com sucesso!");
            salvarEstado();
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
                salvarEstado();
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
            salvarEstado();
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
        Map<String, String> addresses = warehouseQuery.completeAddressesQuery();
        addresses.forEach((id, status) -> {
            System.out.printf("║ Endereço: %-12s ║ Status: %-55s ║%n", id, status);
        });
    }

    public void logs() {
        System.out.println("1. Exibir Histórico Geral");
        System.out.println("2. Exibir Histórico por Produto");

        String option = scanner.nextLine();

        if (option.equals("1")) {
            List<TransactionLog> listLogs = logQuery.getTransactionLogs();
            if (listLogs.isEmpty()) {
                System.out.println("Nenhum registro encontrado!");
            } else {
                imprimirCabecalho();
                listLogs.forEach(System.out::println);
                imprimirRodape();
            }
        } else if (option.equals("2")) {
            System.out.println("Qual produto deseja exibir?");
            String product = scanner.nextLine();
            List<TransactionLog> listLogs = logQuery.getLogsByProduct(product);
            if (listLogs.isEmpty()) {
                System.out.println("Nenhum registro encontrado!");
            } else {
                imprimirCabecalho();
                listLogs.forEach(System.out::println);
                imprimirRodape();
            }

        } else {
            System.out.println("Informe uma opção valida!");
        }
    }

    private void imprimirCabecalho() {
        String headerFormat = "| %-19s | %-22s | %-10s | %-15s |%-8s | %-5s";
        int larguraTotal = 95;

        System.out.println("\n" + "=".repeat(larguraTotal));
        System.out.printf(headerFormat + "%n", "DATA/HORA", "TIPO", "SKU", "PRODUTO", "LOCAL", "QTD");
        System.out.println("-".repeat(larguraTotal));
    }

    private void imprimirRodape() {
        System.out.println("=".repeat(95));
    }

    private void dashboard() {
        System.out.println("\n========================================");
        System.out.println("             DASHBOARD (WMS)             ");
        System.out.println("========================================");
        System.out.println();
        System.out.println(inventoryManager.getDashboard());
        System.out.println("========================================\n");
    }

    private void salvarEstado(){
        try {
            dataStorage.save(this.inventoryManager);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o estado!");
            e.printStackTrace();
        }
    }
}
