package roms;

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
    private Status status;

    private Ticket() {
        orderItemMap = new TreeMap<>();
        status = Status.NONE_FULFILLED;
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

    public void fulfillMenuItem(String menuId) {
        if (!orderItemMap.containsKey(menuId))
            throw new TicketOperationException("Ticket does not contain menu item to be fulfilled");
        orderItemMap.get(menuId).fulfill();
    }

    /**
     * Lazily updates ticket status. May return multiple statuses if ticket contains only 1 order item.
     *
     * @return List of ticket statuses
     */
    public Set<Status> getTicketStatus() {
        Set<Status> statuses = EnumSet.noneOf(Status.class);
        int fulfilledCount = orderItemMap.values().stream().mapToInt(OrderItem::getFulfilledQuantity).sum();
        int totalCount = orderItemMap.values().stream().mapToInt(OrderItem::getQuantity).sum();

        if (fulfilledCount == 0) return EnumSet.of(Status.NONE_FULFILLED);
        if (status == Status.NONE_FULFILLED && fulfilledCount == 1) {
            status = Status.FIRST_ITEM_FULFILLED;
            statuses.add(Status.FIRST_ITEM_FULFILLED);
        }
        if (status == Status.FIRST_ITEM_FULFILLED && fulfilledCount == totalCount) {
            status = Status.ALL_FULFILLED;
            statuses.add(Status.ALL_FULFILLED);
        }
        return statuses;
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

    public enum Status {NONE_FULFILLED, FIRST_ITEM_FULFILLED, ALL_FULFILLED}

    public static class TicketOperationException extends RuntimeException {
        public TicketOperationException(String message) {
            super(message);
        }
    }
}
