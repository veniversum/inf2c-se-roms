/**
 *
 */
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

    private Ticket() {
        orderItemMap = new TreeMap<>();
    }

    public Ticket(String tableID) {
        this();
        this.tableID = tableID;
    }

    public void setSystemCore(SystemCore systemCore) {
        this.systemCore = systemCore;
    }

    public void addMenuItem(String menuID) {
        MenuItem menuItem = systemCore.getMenuProvider().getDefaultMenu().getMenuItem(menuID);
        orderItemMap.compute(menuID, (k, v) -> v == null ? new OrderItem(menuItem) : v.changeQuantity(1));
    }

    public void removeMenuItem(String menuID) {
        orderItemMap.computeIfPresent(menuID, (k, v) -> v.getQuantity() > 1 ? v.changeQuantity(-1) : null);
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
     * @return
     */
    public List<String> toStrings() {
        return orderItemMap.entrySet().stream().map(e -> Arrays.asList(e.getKey(), e.getValue().getDescription(), String.valueOf(e.getValue().getQuantity())))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }


}
