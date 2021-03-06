package roms;

import static roms.LoggerUtil.logger;

public class OrderItem extends MenuItem {
    private int quantity;
    private int fulfilledQuantity;
    private MenuItem menuItem;

    public OrderItem(MenuItem menuItem) {
        logger.fine("init");
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
        logger.fine(String.valueOf(delta));
        this.quantity += delta;
        return this;
    }

    public int getFulfilledQuantity() {
        return fulfilledQuantity;
    }

    /**
     * Increases fulfill quantity

     *
     * @return whether order is fulfilled
     */
    public boolean fulfill() {
        logger.fine("");
        if (fulfilledQuantity >= quantity) return true;
        fulfilledQuantity++;
        return false;
    }

    @Override
    public Money getPrice() {
        return menuItem.getPrice();
    }
}
