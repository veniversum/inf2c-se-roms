package roms;

import java.util.*;
import java.util.stream.Collectors;

import static roms.LoggerUtil.logger;

/**
 * The Order rack
 *
 * @author pbj
 */
public class Rack {
    private Map<Integer, Ticket> ticketQueue = new TreeMap<>();
    private int ticketNumberCounter = 0;

    /**
     * Generates a new unique, auto-incrementing ticket number
     *
     * @return next ticket number
     */
    public int getNextTicketNumber() {
        logger.fine("generated ticket number " + ticketNumberCounter);
        return ++ticketNumberCounter;
    }

    public void submitOrder(Ticket ticket) {
        logger.fine("submitting order #" + ticket.getTicketNumber() + " for " + ticket.getTableID());
        ticketQueue.put(ticket.getTicketNumber(), ticket);
    }

    public Ticket getTicketByNumber(int number) {
        logger.fine("retrieving ticket #" + number);
        return ticketQueue.get(number);
    }

    /**
     * Format rack contents as list of strings, with, per order item in each
     * order, 6 strings for respectively:
     * <p>
     * - Time - elapsed time in minutes since order submitted.
     * - Ticket number
     * - MenuID identifying an order item on the ticket
     * - Description of Menu item with MenuID
     * - Count, the number of MenuID items ordered
     * - Ready number, the number of MenuID items actually ready
     * <p>
     * All 6 tuples for order items in a given order have the same time and
     * number.  This format with the time and ticket number repeated is not
     * the most elegant, but is chosen for simplicity.
     * <p>
     * The rack presents the contents of each order ticket with one or more
     * incomplete item orders.  Tickets are placed in order of urgency, the most
     * urgent first.  Specifically,
     * Order items are expected to be ordered
     * - First by Ticket number, in increasing order
     * (Each submitted ticket gets a unique number, and numbers are allocated
     * in ascending order, so lower numbered tickets are always more urgent
     * than higher numbered tickets.)
     * - second, by MenuID.
     * <p>
     * An example list is:
     * <p>
     * "15", "1", "D1", "Wine",       "1", "0",
     * "15", "1", "D3", "Tap water",  "2", "2",
     * "15", "1", "M1", "Fish",       "3", "0",
     * "9",  "2", "D4", "Coffee",     "2", "2",
     * "9",  "2", "P2", "Cake",       "2", "1"
     * <p>
     * This list of strings is used by the KitchenDisplay to show the
     * contents of the rack.
     *
     * @return
     */
    public List<String> toStrings() {
        return ticketQueue.values().stream().map(this::formatTicket).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<String> formatTicket(Ticket ticket) {
        String timeSince = String.valueOf(Clock.minutesBetween(ticket.getSubmittedTime(), Clock.getInstance().getDateAndTime()));
        String ticketNumber = String.valueOf(ticket.getTicketNumber());
        return ticket.getOrderEntrySet().stream().map(e -> Arrays.asList(
                timeSince,
                ticketNumber,
                String.valueOf(e.getKey()),
                String.valueOf(e.getValue().getDescription()),
                String.valueOf(e.getValue().getQuantity()),
                String.valueOf(e.getValue().getFulfilledQuantity()))).flatMap(Collection::stream).collect(Collectors.toList());
    }

}
