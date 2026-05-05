package warehouse.service;

import warehouse.domain.TransactionLog;
import warehouse.domain.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private List<TransactionLog> transactionLogs;

    public LogManager() {
        this.transactionLogs = new ArrayList<>();
    }

    public void registerLog(LocalDateTime date, TransactionType type,Inventory inventory, int quantity) {
        String product = inventory.getProduct().getName();
        String sku = inventory.getProduct().getSku();
        String id = inventory.getAddress().getId();
        transactionLogs.add(new TransactionLog(date, type, sku, product, id, quantity));
    }

    public List<TransactionLog> getTransactionLogs() {
        return List.copyOf(transactionLogs);
    }

}
