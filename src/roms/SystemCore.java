/**
 *
 */
package roms;

/**
 * @author pbj
 *         <p>
 *         Demonstration core system class.
 */
public class SystemCore {
    private TableTicketCoordinator tableTicketCoordinator;
    private MenuProvider menuProvider;
    private PassLight passLight;

    public TableTicketCoordinator getTableTicketCoordinator() {
        return tableTicketCoordinator;
    }

    public void setTableTicketCoordinator(TableTicketCoordinator tableTicketCoordinator) {
        this.tableTicketCoordinator = tableTicketCoordinator;
    }

    public void setPassLight(PassLight pl) {
        passLight = pl;
    }


    public void cancelReadyUp() {
        passLight.switchOff();
    }

    public MenuProvider getMenuProvider() {
        return menuProvider;
    }

    public void setMenuProvider(MenuProvider menuProvider) {
        this.menuProvider = menuProvider;
    }
}
