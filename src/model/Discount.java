package model;

public class Discount {

    private final double percentage;

    public Discount(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "percentage=" + percentage +
                '}';
    }
}