package warehouse.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record TransactionLog(LocalDateTime date, TransactionType type, String sku,String product, String id, int quantity) {
    @JsonCreator
    public TransactionLog(
            @JsonProperty("date") LocalDateTime date,
            @JsonProperty("type") TransactionType type,
            @JsonProperty("sku") String sku,
            @JsonProperty("product") String product,
            @JsonProperty("id") String id,
            @JsonProperty("quantity") int quantity
    ) {
        this.date = date;
        this.type = type;
        this.sku = sku;
        this.product = product;
        this.id = id;
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String format = "| %-19s | %-22s | %-10s | %-15s |%-8s | %-5d";
        return String.format(format, date.format(fmt), type, sku,product, id, quantity);
    }
}
