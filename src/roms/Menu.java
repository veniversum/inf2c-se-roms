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
public class Menu {
    /**
     * Format menu as list of strings, with, per menu item, 3 strings for 
     * respectively:
     * - MenuID
     * - Description
     * - Price
     * 
     * Items are expected to be ordered by MenuID.
     * 
     * An example list is:
     * 
     * "D1", "Wine",        "2.50",
     * "D2", "Soft drink",  "1.50",
     * "M1", "Fish",        "7.95",
     * "M2", "Veg chili",   "6.70"
     * 
     * These lists of strings are used by TableDisplay and TicketPrinter
     * to produce formatted ticket output messages.
     * 
     * @return
     */
    public List<String> toStrings() {
 
        // Dummy implementation. 
        String[] stringArray = 
            {"D1", "Wine",        "2.50",
             "D2", "Soft drink",  "1.50",
             "M1", "Fish",        "7.95",
             "M2", "Veg chili",   "6.70"
            };
        List<String> ss = new ArrayList<String>();
        ss.addAll(Arrays.asList(stringArray));
        return ss;
    }
    
}
