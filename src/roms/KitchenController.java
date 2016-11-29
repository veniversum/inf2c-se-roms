package roms;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static roms.LoggerUtil.logger;

/**
 * Kitchen controller class. Mediator class for kitchen displays, ticket printer, pass light and button.
 * This implementation allows for extension on requirements, allowing multiple displays in the kitchen.
 */
public class KitchenController {
    private List<KitchenDisplay> kitchenDisplays = new LinkedList<>();
    private TicketPrinter ticketPrinter;
    private SystemCore systemCore;
    private PassLight passLight;

    /**
     * Constructor for kitchen controller. Must be initialized with a system core.
     *
     * @param systemCore system core
     */
    public KitchenController(SystemCore systemCore) {
        logger.finer("init");
        this.systemCore = systemCore;
    }

    /**
     * Sets ticket printer
     *
     * @param ticketPrinter
     */
    public void setTicketPrinter(TicketPrinter ticketPrinter) {
        logger.finer(ticketPrinter.getInstanceName());
        this.ticketPrinter = ticketPrinter;
    }

    /**
     * Adds kitchen display
     *
     * @param kitchenDisplay
     */
    public void addKitchenDisplay(KitchenDisplay kitchenDisplay) {
        logger.finer(kitchenDisplay.getInstanceName());
        kitchenDisplays.add(kitchenDisplay);
        kitchenDisplay.setKitchenController(this);
    }

    /**
     * Removes kitchen display
     *
     * @param kitchenDisplay
     */
    public void removeKitchenDisplay(KitchenDisplay kitchenDisplay) {
        logger.finer(kitchenDisplay.getInstanceName());
        kitchenDisplays.remove(kitchenDisplay);
        kitchenDisplay.setKitchenController(null);
    }

    /**
     * Sets pass light
     *
     * @param pl
     */
    public void setPassLight(PassLight pl) {
        logger.finer("");
        passLight = pl;
    }

    /**
     * Cancel ready up light
     */
    public void cancelReadyUp() {
        logger.fine("");
        passLight.switchOff();
    }

    /**
     * Turns on ready up light
     */
    public void displayReadyUp() {
        logger.fine("");
        passLight.switchOn();
    }

    /**
     * Display rack to all connected kitchen displays
     *
     * @param rack
     */
    public void displayRackToAll(Rack rack) {
        logger.fine("displaying rack info to all kitchen displays");
        kitchenDisplays.forEach(kd -> kd.displayRack(rack));
    }

    /**
     * Indicated a order item to be ready.
     * Print ticket if first item in ticket, turns order ready light on if last item in ticket.
     *
     * @param ticketNumber ticket number of ticket
     * @param menuID       menu item ID for item to be marked ready
     * @throws Ticket.TicketOperationException
     */
    public void itemReady(int ticketNumber, String menuID) throws Ticket.TicketOperationException {
        logger.fine("indicate item " + menuID + " ready for ticket #" + ticketNumber);
        Ticket ticket = systemCore.getRack().getTicketByNumber(ticketNumber);
        if (ticket == null) throw new Ticket.TicketOperationException("Ticket is not found on rack!");
        ticket.fulfillMenuItem(menuID);
        logger.fine("fulfilling item " + menuID + " for ticket #" + ticketNumber);
        Set<Ticket.Status> statuses = ticket.getTicketStatus();
        if (statuses.contains(Ticket.Status.FIRST_ITEM_FULFILLED)) ticketPrinter.printTicket(ticket);
        if (statuses.contains(Ticket.Status.ALL_FULFILLED)) {
            displayReadyUp();
            systemCore.getRack().removeOrder(ticketNumber);
        }
    }
}
