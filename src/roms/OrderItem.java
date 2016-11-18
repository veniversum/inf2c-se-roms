package roms;

public class OrderItem extends MenuItem {
    private int quantity;
    private MenuItem menuItem;

    public OrderItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        quantity = 1;
    }

    @Override
    public String getDescription() {
        return menuItem.getDescription();
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem changeQuantity(int delta) {
        this.quantity += delta;
        return this;
    }

    @Override
    public Money getPrice() {
        return menuItem.getPrice();
    }
}
