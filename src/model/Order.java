package model;

public class Order {

    private final String orderId;
    private final String customerId;
    private final String productId;

    private Customer customer;
    private Product product;
    private Discount discount;
    private Payment payment;

    private double finalPrice;

    public Order(String orderId,
                 String customerId,
                 String productId) {

        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {

        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customer=" + customer +
                ", product=" + product +
                ", discount=" + discount +
                ", payment=" + payment +
                ", finalPrice=" + finalPrice +
                '}';
    }
}