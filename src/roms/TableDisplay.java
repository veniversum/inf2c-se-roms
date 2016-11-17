/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Task-level model of a touch-screen display at a table.
 * 
 * @author pbj
 *
 */
public class TableDisplay extends AbstractIODevice {

    
    /**
     * 
     * @param instanceName  
     */
    public TableDisplay(String instanceName) {
        super(instanceName);   
    }
     
    
    /** 
     *    Select device action based on input event message
     *    
     *    @param e
     */
    @Override
    public void receiveEvent(Event e) {
 
         if (e.getMessageName().equals("startOrder") 
                && e.getMessageArgs().size() == 0) {
            
            startOrder();
            
        } else if (e.getMessageName().equals("showMenu") 
                && e.getMessageArgs().size() == 0) {
            
            showMenu();
            
        } else if (e.getMessageName().equals("showTicket") 
                && e.getMessageArgs().size() == 0) {
            
            showTicket();

        } else if (e.getMessageName().equals("addMenuItem") 
                && e.getMessageArgs().size() == 1) {
            
            String menuID = e.getMessageArg(0);
            addMenuItem(menuID);
            
        } else if (e.getMessageName().equals("removeMenuItem") 
                && e.getMessageArgs().size() == 1) {
            
            String menuID = e.getMessageArg(0);
            removeMenuItem(menuID);
            
        } else if (e.getMessageName().equals("submitOrder") 
                && e.getMessageArgs().size() == 0) {
            
            submitOrder();

        } else if (e.getMessageName().equals("payBill") 
                && e.getMessageArgs().size() == 0) {
            
            payBill();

        } else {
            super.receiveEvent(e);
        } 
    }
 
    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     *  
     * Add private field(s) for associations to classes that TableDisplay 
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

    public void startOrder() {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }
    public void showMenu() {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }
    public void showTicket() {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }
    public void addMenuItem(String menuID) {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }
    public void removeMenuItem(String menuID) {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }
    public void submitOrder() {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }
    public void payBill() {
        logger.fine(getInstanceName());
        // TO BE COMPLETED
    }

    /*
     * Put all class modifications above.
     **********************************************************************
     * END MODIFICATION AREA
     *********************************************************************
     */
    
    /*
     *  
     * SUPPORT FOR OUTPUT MESSAGES
     */

    public void displayMenu(Menu menu) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs = 
            {"tuples","3",
             "ID", "Description", "Price"};
        messageArgs.addAll(Arrays.asList(preludeArgs));
        messageArgs.addAll(menu.toStrings());
        sendMessage("viewMenu", messageArgs);
      
    }
    
    public void displayTicket(Ticket ticket) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs = 
            {"tuples","3",
             "ID", "Description", "Count"};
        messageArgs.addAll(Arrays.asList(preludeArgs));
        messageArgs.addAll(ticket.toStrings());
        sendMessage("viewTicket", messageArgs);
      
    }
    
    public void displayBill(Money total) {
        List<String> messageArgs = new ArrayList<String>();
        messageArgs.add("Total:");
        messageArgs.add(total.toString());
        sendMessage("approveBill", messageArgs);
               
    }

     
}
