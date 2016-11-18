/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * @author pbj
 *
 */
public class Menu {
    private TreeMap<String, MenuItem> menuItemTreeMap;

    public Menu() {
        menuItemTreeMap = new TreeMap<>();
    }

    public void removeMenuItem(String id) {
        assert menuItemTreeMap.containsKey(id) : "Trying to remove item menu not already in menu";
        /*
        THIS IS BAD BAD BAD BAD PRACTICE! Should NOT be used in ANY kind of production systems.
        The above assert is only included because Section 3.4 explicitly mentions so. We will thus
        avoid assertions as much as possible in other areas of the code.

        Asserts should only be used for debugging, as it results in a performance hit. There is also no
        way to ensure that the java program is run with assertions enabled (actually there is but why
        would you even do that!?)

        boolean isAssertEnabled = false;
        assert isAssertEnabled = true; // force assert to have side effect
        if (!isAssertEnabled) System.exit(1);

        Asserts indicate a fatal error in program flow and the program should be terminated. It throws an
        AssertionError, and in most cases errors indicate fatal execution flaws, and the system should crash
        to prevent further undesired effect.

        In the case of trying to remove an item not already in the menu, do we REALLY need to terminate the
        program execution? Sure it might be a 'precondition' but will the expected end state of the system be
        different whether the precondition is met? In either cases the resulting menu will not have the menu
        in it. Imagine if all of the Java collection classes halts the program when an invalid item is attempted
        to be removed.

        Instead if desired, exceptions should be thrown, and these should be a subclassed exception type and not
        the raw exception. It will allow much more flexibility and granularity in handling exceptions. Often,
        exceptions are handled by multiple parts of the program, each rethrowing a new exception to the next
        handler. In fact, in this case it might be best to at most just log the exception.
         */
        menuItemTreeMap.remove(id);
    }

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
