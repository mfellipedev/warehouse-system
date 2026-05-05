package warehouse.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TransactionLog(LocalDateTime date, TransactionType type, String sku,String product, String id, int quantity) {

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String format = "| %-19s | %-22s | %-10s | %-15s |%-8s | %-5d";
        return String.format(format, date.format(fmt), type, sku,product, id, quantity);
    }
}
