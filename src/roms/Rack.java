/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Order rack
 * 
 * @author pbj
 *
 */
public class Rack {
       
    /**
     * Format rack contents as list of strings, with, per order item in each
     * order, 6 strings for respectively:

     * - Time - elapsed time in minutes since order submitted.
     * - Ticket number 
     * - MenuID identifying an order item on the ticket
     * - Description of Menu item with MenuID
     * - Count, the number of MenuID items ordered 
     * - Ready number, the number of MenuID items actually ready
     * 
     * All 6 tuples for order items in a given order have the same time and
     * number.  This format with the time and ticket number repeated is not
     * the most elegant, but is chosen for simplicity. 
     *  
     * The rack presents the contents of each order ticket with one or more
     * incomplete item orders.  Tickets are placed in order of urgency, the most
     * urgent first.  Specifically,  
     * Order items are expected to be ordered 
     *  - First by Ticket number, in increasing order
     *    (Each submitted ticket gets a unique number, and numbers are allocated
     *     in ascending order, so lower numbered tickets are always more urgent
     *     than higher numbered tickets.)
     *  - second, by MenuID.
     * 
     * An example list is:
     * 
     * "15", "1", "D1", "Wine",       "1", "0", 
     * "15", "1", "D3", "Tap water",  "2", "2",
     * "15", "1", "M1", "Fish",       "3", "0",
     * "9",  "2", "D4", "Coffee",     "2", "2",
     * "9",  "2", "P2", "Cake",       "2", "1" 
     * 
     * This list of strings is used by the KitchenDisplay to show the 
     * contents of the rack.
     * 
     * @return
     */
    public List<String> toStrings() {
         
        // Dummy implementation. 
        String[] stringArray = 
            {"15", "1", "D1", "Wine",       "1", "0", 
             "15", "1", "D3", "Tap water",  "2", "2",
             "15", "1", "M1", "Fish",       "3", "0",
             "9", "2", "D4", "Coffee",     "2", "2",
             "9", "2", "P2", "Cake",       "2", "1" 
            };
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
}
