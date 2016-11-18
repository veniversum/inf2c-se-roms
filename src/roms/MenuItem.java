package roms;

public class MenuItem {
    String description;
    String price;

    public MenuItem(String description, String price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
