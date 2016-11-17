/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author pbj
 *
 */
public class Ticket {
 
    private String tableID;
    public String getTableID() { 
        return tableID; 
    }
    
    /**
     * Format ticket as list of strings, with, per ticket item, 3 strings for 
     * respectively:
     * - MenuID
     * - Description
     * - Count
     * 
     * Items are expected to be ordered by MenuID.
     * 
     * An example list is:
     * 
     * "D1", "Wine",        "1",
     * "D2", "Soft drink",  "3",
     * "M1", "Fish",        "2",
     * "M2", "Veg chili",   "2"
     * 
     * These lists of strings are used by TableDisplay and TicketPrinter
     * to produce formatted ticket output messages.
     * 
     * @return
     */
    public List<String> toStrings() {
 
        // Dummy implementation. 
        String[] stringArray = 
            {"D1", "Wine",        "1",
             "D2", "Soft drink",  "3",
             "M1", "Fish",        "2",
             "M2", "Veg chili",   "2"
            };
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
    
}
