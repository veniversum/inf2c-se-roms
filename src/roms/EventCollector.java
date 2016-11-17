/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pbj
 *
 */
public class EventCollector {
   
    private List<Event> collectedEvents;
    
    public EventCollector () {
        collectedEvents = new ArrayList<Event>();
    }
    
    public void collectEvent(Event e) {
        collectedEvents.add(e);    
    }   
    
    public List<Event> fetchEvents() {
        List<Event> result = collectedEvents;
        collectedEvents = new ArrayList<Event>();
        return result;
    }

}
