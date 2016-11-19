package roms;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class KitchenController {
    private List<KitchenDisplay> kitchenDisplays = new LinkedList<>();
    private TicketPrinter ticketPrinter;
    private SystemCore systemCore;

    public KitchenController(SystemCore systemCore) {
        this.systemCore = systemCore;
    }

    public void setTicketPrinter(TicketPrinter ticketPrinter) {
        this.ticketPrinter = ticketPrinter;
    }

    public void addKitchenDisplay(KitchenDisplay kitchenDisplay) {
        kitchenDisplays.add(kitchenDisplay);
        kitchenDisplay.setKitchenController(this);
    }

    public void removeKitchenDisplay(KitchenDisplay kitchenDisplay) {
        kitchenDisplays.remove(kitchenDisplay);
        kitchenDisplay.setKitchenController(null);
    }

    public void displayRackToAll(Rack rack) {
        kitchenDisplays.forEach(kd -> kd.displayRack(rack));
    }

    public void itemReady(int ticketNumber, String menuID) throws Ticket.TicketOperationException {
        Ticket ticket = systemCore.getRack().getTicketByNumber(ticketNumber);
        if (ticket == null) throw new Ticket.TicketOperationException("Ticket is not found on rack!");
        ticket.fulfillMenuItem(menuID);
        Set<Ticket.Status> statuses = ticket.getTicketStatus();
        if (statuses.contains(Ticket.Status.FIRST_ITEM_FULFILLED)) ticketPrinter.printTicket(ticket);
        if (statuses.contains(Ticket.Status.ALL_FULFILLED)) systemCore.displayReadyUp();
    }
}
