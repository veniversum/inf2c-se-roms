/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;

/**
 * Order ready light for pass between kitchen and dining area.
 * 
 * @author pbj
 *
 */
public class PassLight extends AbstractOutputDevice {
    
    public PassLight(String instanceName) {
        super(instanceName);
    }
    
    public void switchOn() {
        logger.fine(getInstanceName());
        List<String> args = new ArrayList<String>();

        sendMessage("viewSwitchedOn", args);
    }
    
    public void switchOff() {
        logger.fine(getInstanceName());
        List<String> args = new ArrayList<String>();

        sendMessage("viewSwitchedOff", args);
    }
}
