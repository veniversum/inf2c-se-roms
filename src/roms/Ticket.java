/**
 *
 */
package roms;

import roms.TableTicketCoordinator.TicketOperationException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pbj
 */
public class Ticket {
    private SystemCore systemCore;
    private String tableID;
    private Map<String, OrderItem> orderItemMap;
    private int ticketNumber;
    private Date submittedTime;

    private Ticket() {
        orderItemMap = new TreeMap<>();
    }

    public Ticket(String tableID) {
        this();
        this.tableID = tableID;
    }

    /**
     * Returns the auto-incremented sequential number for the ticket.
     *
     * @return Ticket number
     */
    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setSystemCore(SystemCore systemCore) {
        this.systemCore = systemCore;
    }

    public Date getSubmittedTime() {
        return submittedTime;
    }

    public void setSubmittedTime(Date submittedTime) {
        this.submittedTime = submittedTime;
    }

    /**
     * Adds menu item to order. Should only be called by coordinator class.
     *
     * @param menuId Menu item identifier
     * @throws TicketOperationException If no menu has not been initialized or menu item does not exist
     */
    public void addMenuItem(String menuId) throws TicketOperationException {
        Menu menu = systemCore.getMenuProvider().getDefaultMenu();
        if (menu == null) throw new TicketOperationException("Default menu does not exist");
        MenuItem menuItem = systemCore.getMenuProvider().getDefaultMenu().getMenuItem(menuId);
        if (menuItem == null) throw new TicketOperationException("Default menu does not contain menu item");
        orderItemMap.compute(menuId, (k, v) -> v == null ? new OrderItem(menuItem) : v.changeQuantity(1));
    }

    /**
     * Removes menu item from order. Should only be called by coordinator class.
     *
     * @param menuId Menu item identifier
     */
    public void removeMenuItem(String menuId) {
        orderItemMap.computeIfPresent(menuId, (k, v) -> v.getQuantity() > 1 ? v.changeQuantity(-1) : null);
    }

    public String getTableID() {
        return tableID;
    }

    /**
     * Format ticket as list of strings, with, per ticket item, 3 strings for
     * respectively:
     * - MenuID
     * - Description
     * - Count
     * <p>
     * Items are expected to be ordered by MenuID.
     * <p>
     * An example list is:
     * <p>
     * "D1", "Wine",        "1",
     * "D2", "Soft drink",  "3",
     * "M1", "Fish",        "2",
     * "M2", "Veg chili",   "2"
     * <p>
     * These lists of strings are used by TableDisplay and TicketPrinter
     * to produce formatted ticket output messages.
     *
     * @return Above formatted list
     */
    public List<String> toStrings() {
        return orderItemMap.entrySet()
                .stream()
                .map(e -> Arrays.asList(e.getKey()
                        , e.getValue().getDescription()
                        , String.valueOf(e.getValue().getQuantity())))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    protected Set<Map.Entry<String, OrderItem>> getOrderEntrySet() {
        return orderItemMap.entrySet();
    }

    public Money getPayableAmount() {
        return orderItemMap.entrySet()
                .stream()
                .map(e -> e.getValue().getPrice().multiply(e.getValue().getQuantity())).reduce(new Money(), Money::add);
    }
}
