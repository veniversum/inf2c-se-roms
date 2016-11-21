package roms;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static roms.LoggerUtil.logger;

public class KitchenController {
    private List<KitchenDisplay> kitchenDisplays = new LinkedList<>();
    private TicketPrinter ticketPrinter;
    private SystemCore systemCore;

    public KitchenController(SystemCore systemCore) {
        logger.fine("init");
        this.systemCore = systemCore;
    }

    public void setTicketPrinter(TicketPrinter ticketPrinter) {
        logger.fine(ticketPrinter.getInstanceName());
        this.ticketPrinter = ticketPrinter;
    }

    public void addKitchenDisplay(KitchenDisplay kitchenDisplay) {
        logger.fine(kitchenDisplay.getInstanceName());
        kitchenDisplays.add(kitchenDisplay);
        kitchenDisplay.setKitchenController(this);
    }

    public void removeKitchenDisplay(KitchenDisplay kitchenDisplay) {
        logger.fine(kitchenDisplay.getInstanceName());
        kitchenDisplays.remove(kitchenDisplay);
        kitchenDisplay.setKitchenController(null);
    }

    public void displayRackToAll(Rack rack) {
        logger.fine("displaying rack info to all kitchen displays");
        kitchenDisplays.forEach(kd -> kd.displayRack(rack));
    }

    public void itemReady(int ticketNumber, String menuID) throws Ticket.TicketOperationException {
        logger.fine("indicate item " + menuID + " ready for ticket #" + ticketNumber);
        Ticket ticket = systemCore.getRack().getTicketByNumber(ticketNumber);
        if (ticket == null) throw new Ticket.TicketOperationException("Ticket is not found on rack!");
        ticket.fulfillMenuItem(menuID);
        logger.fine("fulfilling item " + menuID + " for ticket #" + ticketNumber);
        Set<Ticket.Status> statuses = ticket.getTicketStatus();
        if (statuses.contains(Ticket.Status.FIRST_ITEM_FULFILLED)) ticketPrinter.printTicket(ticket);
        if (statuses.contains(Ticket.Status.ALL_FULFILLED)) systemCore.displayReadyUp();
    }
}
