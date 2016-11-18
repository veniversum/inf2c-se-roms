package roms;

public class MenuItem {
    private String description;
    private Money price;

    protected MenuItem() {
    }

    public MenuItem(String description, Money price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
