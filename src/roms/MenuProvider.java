package roms;

public class MenuProvider {
    private Menu defaultMenu;

    public MenuProvider(Menu defaultMenu) {
        this.defaultMenu = defaultMenu;
    }

    public Menu getDefaultMenu() {
        return defaultMenu;
    }

    public void setDefaultMenu(Menu defaultMenu) {
        this.defaultMenu = defaultMenu;
    }
}
