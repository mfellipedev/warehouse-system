package warehouse.service;

import warehouse.domain.TransactionLog;

import java.util.List;

public class LogQuery {
    private LogManager logManager;

    public LogQuery(LogManager logManager) {
        this.logManager = logManager;
    }

    public List<TransactionLog> getTransactionLogs() {
        return logManager.getTransactionLogs();
    }

    public List<TransactionLog> getLogsByProduct(String product) {
        return logManager.getTransactionLogs().stream()
                .filter(log -> log.product().equalsIgnoreCase(product))
                .toList();
    }

}
