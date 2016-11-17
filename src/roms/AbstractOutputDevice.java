/**
 * 
 */
package roms;

import java.util.List;

/**
 * An abstract device supporting event output, but not event input.
 * 
 * All output-only device classes should inherit from this class and should
 * themselves have a constructor which takes an instance name as argument.
 * 
 * The code here is copied in the AbstractIODevice class, as the
 * AbstractIODevice cannot inherit from this class as well as the 
 * AbstractInputDevice.
 *  
 * @author pbj
 *
 */
public abstract class AbstractOutputDevice extends AbstractDevice {
     
    public AbstractOutputDevice(String instanceName) {
        super(instanceName);
    }
    
    /**
     * ALL BELOW COPIED TO AbstractIODevice
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
