/**
 * 
 */
package roms;

import java.util.List;

/**
 * An abstract device supporting event input, but not event output.
 * 
 * All input-only device classes should inherit from this class
 * and call the constructor to initialise the instanceName.
 * 
 * @author pbj
 *
 */
public abstract class AbstractInputDevice extends AbstractDevice {
    private EventDistributor distributor;
    
    public AbstractInputDevice(String instance) {
        super(instance);
    }
        
    public EventDistributor getDistributor() {
        return distributor;
    }

    public void setDistributor(EventDistributor distributor) {
        this.distributor = distributor;
    }
    
    public void addDistributorLinks(EventDistributor distributor) {
        setDistributor(distributor);
        distributor.addInputCapableDevice(this);
    }

    /**
     * Dummy method for receiving trigger input events from distributor
     * 
     * @param e
     */
    public void receiveEvent(Event e) {
        String errorMessage = 
                this.getClass().getSimpleName() + " " + e.getDeviceInstance() 
                + " received unrecognised event: " + LS
                + e;
        
        logger.warning(errorMessage);
        
        throw new RuntimeException(errorMessage);
        
    }
    /**
     * Generic method for pulling a non-trigger input event from the end of 
     * distributor event queue.
     * 
     * It checks the event on the event queue has a class and instance names
     * that match those of the interface device invoking this method, and checks 
     * event's message name matches that provided in the messageName argument.
     * 
     * If the match is good, the event is removed from the queue, and this method
     * return's the message arguments carried by the event.
     * 
     * @param messageName
     * @return Message argument strings
     */
    public List<String> fetchMatchingMessage(String messageName) {
        
        String deviceClass = getClass().getSimpleName();
        String deviceInstance = getInstanceName();
   
        return distributor.fetchMatchingEvent(
                    deviceClass, 
                    deviceInstance, 
                    messageName);
    }
}
