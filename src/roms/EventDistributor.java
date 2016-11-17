/**
 * 
 */
package roms;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author pbj
 *
 */
public class EventDistributor {
    private static final String LS = System.getProperty("line.separator");
    public static final Logger logger = Logger.getLogger("roms");
    
    private Deque<Event> inputEventQueue;
    
    private Map<String,AbstractInputDevice> deviceMap;
    
    public EventDistributor() {
        inputEventQueue = new LinkedList<Event>();
        deviceMap = new HashMap<String,AbstractInputDevice>();
    }
    
    /**
     * Setup up map entry, so given deviceClass and device instance
     * information, the object for the device can be found quickly.
     * 
     * Also initialise the reverse link back from the device to this 
     * distributor
     * 
     * @param d
     */
    public void addInputCapableDevice(AbstractInputDevice d) {
        String deviceClass = d.getClass().getSimpleName();
        String deviceInstance = d.getInstanceName();
        deviceMap.put(deviceClass + "." + deviceInstance, d);
    }
    
    /**
     * Add event e to the inputEventQueue.
     * 
     * Invoked by test code.
     * 
     * @param e
     */
     public void enqueue(Event e) {
        inputEventQueue.addLast(e);
     }
    
     /**
      * Send trigger event to input-capable devices.
      * 
      * Before sending an event, the system clock is updated to the event's
      * timestamp time.
      */
     private void sendEvent(Event e) {
         String deviceClass = e.getDeviceClass();
         String deviceInstance = e.getDeviceInstance();
            
         Clock.getInstance().setDateAndTime(e.getDate());
         
         String key = deviceClass + "." + deviceInstance;
         AbstractInputDevice d = 
                 deviceMap.get(key);
         if (d != null) {
             d.receiveEvent(e);  
         } else {
             String errorMessage = 
                     "Unrecognised input-capable device.  Class: " + deviceClass
                     + " instance: " + deviceInstance;
             logger.warning(errorMessage);
             throw new RuntimeException(errorMessage);
        }    
     }
     
    /**
     * Send trigger events in queue to input-capable devices.
     * 
     * In between
     * sending triggering events, input-capable devices might pull one or more
     * non-triggering events from the queue using getEventMessageArgs below.
     * 
     */
    public void sendEvents() {
        while (! inputEventQueue.isEmpty()) {
            Event e = inputEventQueue.removeFirst();
            sendEvent(e);
        }
    }
    
    /**
     * Fetch the event at the front of the message queue and return the 
     * arguments of the message it contains.  
     * 
     * Method is called by input-capable devices processing a request for input
     * call from somewhere else in the system.
     * 
     * Method checks that request comes from device named by front event and 
     * is for the message named in the front event.
     * 
     * @param deviceClass
     * @param deviceInstance
     * @param messageName
     * @return
     */
    public List<String> fetchMatchingEvent(
            String deviceClass, 
            String deviceInstance, 
            String messageName) {
        
        if (!inputEventQueue.isEmpty()) {
            Event e = inputEventQueue.removeFirst();
            
            if (e.getDeviceClass().equals(deviceClass)
                && e.getDeviceInstance().equals(deviceInstance)
                && e.getMessageName().equals(messageName) ) {
                
                Clock.getInstance().setDateAndTime(e.getDate());
                
                return e.getMessageArgs();
                      
            } else {
                String errorMessage = 
                    "Event request does not match queued event" + LS 
                    + "  Request for: " 
                    + deviceClass + ", " + deviceInstance + ", " + messageName + LS
                    +"  At queue front: " + e.getDeviceClass() + ", " 
                    + e.getDeviceInstance() + ", " + e.getMessageName() + LS;
                       
                logger.warning(errorMessage);
                throw new RuntimeException(errorMessage);

            }         
        } else {
            throw new RuntimeException(
                    "Request for event from empty queue");
        }
       
    }
}
