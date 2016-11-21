package roms;

import static roms.LoggerUtil.logger;

public class MenuItem {
    private String description;
    private Money price;

    protected MenuItem() {
    }

    public MenuItem(String description, Money price) {
        logger.fine("init");
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "description='" + description + '\'' +
                ", price=" + price +
                '}';
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
