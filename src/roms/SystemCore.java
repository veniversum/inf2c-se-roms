/**
 *
 */
package roms;

import static roms.LoggerUtil.logger;

/**
 * A system core that connects critical central components of the system.
 * Should only be accessed by controller classes!!
 */
public class SystemCore {
    private TableTicketCoordinator tableTicketCoordinator;
    private MenuProvider menuProvider;
    private KitchenController kitchenController;
    private BankClient bankClient;
    private Rack rack;

    public KitchenController getKitchenController() {
        return kitchenController;
    }

    public void setKitchenController(KitchenController kitchenController) {
        logger.fine("");
        this.kitchenController = kitchenController;
    }

    public Rack getRack() {
        logger.fine("");
        return rack;
    }

    public void setRack(Rack rack) {
        logger.fine("");
        this.rack = rack;
    }

    public TableTicketCoordinator getTableTicketCoordinator() {
        logger.fine("");
        return tableTicketCoordinator;
    }

    public void setTableTicketCoordinator(TableTicketCoordinator tableTicketCoordinator) {
        logger.fine("");
        this.tableTicketCoordinator = tableTicketCoordinator;
    }

    public MenuProvider getMenuProvider() {
        logger.fine("");
        return menuProvider;
    }

    public void setMenuProvider(MenuProvider menuProvider) {
        logger.fine("");
        this.menuProvider = menuProvider;
    }

    public BankClient getBankClient() {
        logger.fine("");
        return bankClient;
    }

    public void setBankClient(BankClient bankClient) {
        logger.fine("");
        this.bankClient = bankClient;
    }
}
