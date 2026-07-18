package model;

public class Payment {

    private final boolean success;
    private final String transactionId;

    public Payment(boolean success,
                   String transactionId) {

        this.success = success;
        this.transactionId = transactionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "success=" + success +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}