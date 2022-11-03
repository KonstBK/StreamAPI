import java.time.LocalDate;

public class Product {
    private final int id;
    private final String name;
    private double price;
    private final ProductType category;
    private final boolean discount;
    private final LocalDate createdAt;

    public Product(int id, String name, double price, ProductType category, boolean discount, LocalDate dateOfCreation) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.discount = discount;
        this.createdAt = dateOfCreation;
    }

    public void applyDiscoutn(double percent) {
        if (this.discount) {
            this.price = price * percent;
        }
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getType() {
        return category;
    }

    @Override
    public String toString() {
        return "\nProduct{" +
                "id='" + id +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", discount=" + discount +
                ", dateOfCreation=" + createdAt +
                "}";
    }
}
