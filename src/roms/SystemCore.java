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
        logger.finer("");
        this.kitchenController = kitchenController;
    }

    public Rack getRack() {
        logger.finer("");
        return rack;
    }

    public void setRack(Rack rack) {
        logger.finer("");
        this.rack = rack;
    }

    public TableTicketCoordinator getTableTicketCoordinator() {
        logger.finer("");
        return tableTicketCoordinator;
    }

    public void setTableTicketCoordinator(TableTicketCoordinator tableTicketCoordinator) {
        logger.finer("");
        this.tableTicketCoordinator = tableTicketCoordinator;
    }

    public MenuProvider getMenuProvider() {
        logger.finer("");
        return menuProvider;
    }

    public void setMenuProvider(MenuProvider menuProvider) {
        logger.finer("");
        this.menuProvider = menuProvider;
    }

    public BankClient getBankClient() {
        logger.finer("");
        return bankClient;
    }

    public void setBankClient(BankClient bankClient) {
        logger.finer("");
        this.bankClient = bankClient;
    }
}
