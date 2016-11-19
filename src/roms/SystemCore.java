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
    private KitchenController kitchenController;
    private Rack rack;
    private PassLight passLight;

    public KitchenController getKitchenController() {
        return kitchenController;
    }

    public void setKitchenController(KitchenController kitchenController) {
        this.kitchenController = kitchenController;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

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
