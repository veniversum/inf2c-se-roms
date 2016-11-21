package roms;

/**
 * Menu provider class. Provides extensibility and follows open/closed principle.
 *
 * Current implementation has a static default menu, but can be extended to support
 * menu based on day of week or period of day (breakfast, lunch, dinner).
 */
public class MenuProvider {
    private Menu defaultMenu;

    public MenuProvider(Menu defaultMenu) {
        LoggerUtil.logger.fine("init");
        setMenu(defaultMenu);
    }

    public Menu getMenu() {
        LoggerUtil.logger.fine("");
        return defaultMenu;
    }

    public void setMenu(Menu defaultMenu) {
        this.defaultMenu = defaultMenu;
    }
}
