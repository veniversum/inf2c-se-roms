/**
 * 
 */
package roms;

import java.util.List;

/**
 * An abstract device supporting both event input and event output.
 * 
 * All input/output device classes should inherit from this class
 * and call the constructor to initialise the instanceName.
 * 
 * This class inherits the implementation of AbstractInputDevice 
 * and merges in a copy of the implementation of AbstractOutputDevice.  
 * It is needed because Java does not support multiple inheritance.
 * 
 * @author pbj
 *
 */
public abstract class AbstractIODevice 
        extends AbstractInputDevice {

    public AbstractIODevice(String instance) {
        super(instance);
    }

    /**
     * ALL BELOW COPIED FROM AbstractOutputDevice
     */
    
    private EventCollector collector;
    
        
    public void setCollector(EventCollector c) {
        collector = c;
    }
    
    public EventCollector getCollector() {
        return collector;
    }
       
    public void sendMessage(String messageName, List<String> argList) {
        String deviceClass = getClass().getSimpleName();
        String deviceInstance = getInstanceName();
    
        collector.collectEvent(
            new Event(
                Clock.getInstance().getDateAndTime(), 
                deviceClass,
                deviceInstance,
                messageName,
                argList));

    }

}
