package roms;

import java.util.HashMap;
import java.util.Map;

public class TableTicketCoordinator {
    private Map<String, Ticket> tableTicketMap = new HashMap<>();
    private SystemCore systemCore;

    public void setSystemCore(SystemCore systemCore) {
        this.systemCore = systemCore;
    }

    /**
     * Creates a new ticket for table and replaces existing ticket.
     *
     * @param tableId Table identifier
     * @return new Ticket object
     */
    public Ticket createTicket(String tableId) {
        Ticket ticket = new Ticket(tableId);
        ticket.setSystemCore(systemCore);
        tableTicketMap.put(tableId, ticket);
        return ticket;
    }

    /**
     * Submits ticket for table to order rack.
     *
     * @param tableId Table identifier
     * @throws TicketOperationException Exception thrown when table has no ticket or ticket has already been submitted
     */
    public void submitTicket(String tableId) throws TicketOperationException {
        if (!tableTicketMap.containsKey(tableId))
            throw new TicketOperationException("Table does not have an active order!");
        Ticket ticket = tableTicketMap.get(tableId);
        if (ticket.getSubmittedTime() != null) throw new TicketOperationException("Ticket has already been submitted!");
        if (systemCore.getRack() == null) throw new TicketOperationException("No order rack to submit to!");
        ticket.setSubmittedTime(Clock.getInstance().getDateAndTime());
        ticket.setTicketNumber(systemCore.getRack().getNextTicketNumber());
        systemCore.getRack().submitOrder(tableTicketMap.get(tableId));
    }

    /**
     * Add menu item for to table's ticket
     *
     * @param tableId Table identifier
     * @param menuId  Menu item identifier
     * @throws TicketOperationException Exception thrown when table has no ticket
     */
    public void addMenuItem(String tableId, String menuId) throws TicketOperationException {
        if (!tableTicketMap.containsKey(tableId)) {
            throw new TicketOperationException("Table does not have an active order!");
        }
        tableTicketMap.get(tableId).addMenuItem(menuId);
    }

    /**
     * Add menu item for to table's ticket
     *
     * @param tableId Table identifier
     * @param menuId  Menu item identifier
     * @throws TicketOperationException Exception thrown when table has no ticket
     */
    public void removeMenuItem(String tableId, String menuId) throws TicketOperationException {
        if (!tableTicketMap.containsKey(tableId)) {
            throw new TicketOperationException("Table does not have an active order!");
        }
        tableTicketMap.get(tableId).removeMenuItem(menuId);
    }

    /**
     * Return the Ticket instance for table if exists, if not returns null.
     *
     * @param tableId Table identifier
     * @return Ticket instance associated with table if exists
     */
    public Ticket getTicket(String tableId) {
        return tableTicketMap.get(tableId);
    }

    public static class TicketOperationException extends RuntimeException {
        public TicketOperationException(String message) {
            super(message);
        }
    }
}
