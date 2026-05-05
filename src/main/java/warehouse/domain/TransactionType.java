package warehouse.domain;

public enum TransactionType {
    ARMAZENAMENTO("ENTRADA"),
    SAIDA_TRANSFERENCIA("SAIDA"),
    ENTRADA_TRANSFERENCIA("ENTRADA"),
    REMOCAO("SAIDA"),
    AJUSTE("AJUSTE");

    private final String status;

    TransactionType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
