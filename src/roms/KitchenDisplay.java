/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Task-level model of the touch-screen display in the kitchen.
 * 
 * @author pbj
 *
 */
public class KitchenDisplay extends AbstractIODevice {

    
    /**
     * 
     * @param instanceName  
     */
    public KitchenDisplay(String instanceName) {
        super(instanceName);   
    }
    
    
    /** 
     *    Select device action based on input event message
     *    
     *    @param e
     */
    @Override
    public void receiveEvent(Event e) {
 
         if (e.getMessageName().equals("itemReady") 
                && e.getMessageArgs().size() == 2) {
            String ticketNumberString = e.getMessageArg(0);
            String menuID = e.getMessageArg(1);
             
            itemReady(Integer.parseInt(ticketNumberString), menuID);

        } else {
            super.receiveEvent(e);
        } 
    }
    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     *  
     * Add private field(s) for associations to classes that KitchenDisplay
     * objects need to send messages to. 
     * 
     * Add public setters for these fields.
     * 
     * Complete the methods for handling trigger input events.
     * 
     */

    /*
     * FIELD(S) AND SETTER(S) FOR MESSAGE DESTINATIONS
     */
        
    /*
     * SUPPORT FOR TRIGGER INPUT MESSAGES
     */

    public void itemReady(int ticketNumber, String menuID) {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }

    /*
     * Put all class modifications above.
     **********************************************************************
     * END MODIFICATION AREA
     *********************************************************************
     */

    /** 
     * SUPPORT FOR displayRack OUTPUT MESSAGE
     */

    public void displayRack(Rack rack) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs = 
            {"tuples","6",
             "Time","Ticket#", "MenuID", "Description", "#Ordered", "#Ready"};
        messageArgs.addAll(Arrays.asList(preludeArgs));
        messageArgs.addAll(rack.toStrings());
        sendMessage("viewRack", messageArgs);
      
    }
    /** 
     * SUPPORT FOR displayTicket OUTPUT MESSAGE
     */

    public void displayTicket(Ticket ticket) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs = 
            {"tuples","3",
             "MenuID", "Description", "Count"};
        messageArgs.addAll(Arrays.asList(preludeArgs));
        messageArgs.addAll(ticket.toStrings());
        sendMessage("viewTicket", messageArgs);
      
    }

     
}
