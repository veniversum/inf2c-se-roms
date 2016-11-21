package roms;

import java.util.HashMap;
import java.util.Map;

import static roms.LoggerUtil.logger;

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
        logger.fine("init : " + tableId);
        Ticket ticket = new Ticket(tableId);
        tableTicketMap.put(tableId, ticket);
        return ticket;
    }

    /**
     * Submits ticket for table to order rack.
     *
     * @param tableId Table identifier
     * @throws Ticket.TicketOperationException Exception thrown when table has no ticket or ticket has already been submitted
     */
    public void submitTicket(String tableId) throws Ticket.TicketOperationException {
        if (!tableTicketMap.containsKey(tableId))
            throw new Ticket.TicketOperationException("Table does not have an active order!");
        Ticket ticket = tableTicketMap.get(tableId);
        if (ticket.getSubmittedTime() != null) throw new Ticket.TicketOperationException("Ticket has already been submitted!");
        if (systemCore.getRack() == null) throw new Ticket.TicketOperationException("No order rack to submit to!");
        ticket.setSubmittedTime(Clock.getInstance().getDateAndTime());
        ticket.setTicketNumber(systemCore.getRack().getNextTicketNumber());
        logger.fine("submitting ticket #" + ticket.getTicketNumber());
        systemCore.getRack().submitOrder(tableTicketMap.get(tableId));
    }

    /**
     * Add menu item for to table's ticket
     *
     * @param tableId Table identifier
     * @param menuId  Menu item identifier
     * @throws Ticket.TicketOperationException Exception thrown when table has no ticket
     */
    public void addMenuItem(String tableId, String menuId) throws Ticket.TicketOperationException {
        if (!tableTicketMap.containsKey(tableId)) {
            throw new Ticket.TicketOperationException("Table does not have an active order!");
        }
        Menu menu = systemCore.getMenuProvider().getMenu();
        if (menu == null) throw new Ticket.TicketOperationException("Default menu does not exist");
        MenuItem menuItem = systemCore.getMenuProvider().getMenu().getMenuItem(menuId);
        if (menuItem == null) throw new Ticket.TicketOperationException("Default menu does not contain menu item");
        logger.fine("adding menu item " + menuId + " to table " + tableId);
        tableTicketMap.get(tableId).addMenuItem(menuId, menuItem);
    }

    /**
     * Add menu item for to table's ticket
     *
     * @param tableId Table identifier
     * @param menuId  Menu item identifier
     * @throws Ticket.TicketOperationException Exception thrown when table has no ticket
     */
    public void removeMenuItem(String tableId, String menuId) throws Ticket.TicketOperationException {
        if (!tableTicketMap.containsKey(tableId)) {
            throw new Ticket.TicketOperationException("Table does not have an active order!");
        }
        logger.fine("removing menu item " + menuId + " from table " + tableId);
        tableTicketMap.get(tableId).removeMenuItem(menuId);
    }

    /**
     * Return the Ticket instance for table if exists, if not returns null.
     *
     * @param tableId Table identifier
     * @return Ticket instance associated with table if exists
     */
    public Ticket getTicket(String tableId) {
        logger.fine(tableId);
        return tableTicketMap.get(tableId);
    }

}
