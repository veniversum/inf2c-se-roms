/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Task-level model of the ticket printer at the pass.
 * 
 * @author pbj
 *
 */
public class TicketPrinter extends AbstractOutputDevice {

    
    /**
     * 
     * @param instanceName  
     */
    public TicketPrinter(String instanceName) {
        super(instanceName);   
    }
    
    public void printTicket(Ticket ticket) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        String[] preludeArgs1 = 
            {"tuples","3",
             "Table:"
            };
        String[] preludeArgs2 = 
            {                     "_",
             "ID", "Description", "Count"};
        messageArgs.addAll(Arrays.asList(preludeArgs1));
        messageArgs.add(ticket.getTableID());
        messageArgs.addAll(Arrays.asList(preludeArgs2));
        messageArgs.addAll(ticket.toStrings());
        sendMessage("takeTicket", messageArgs);
      
    }


   
     
}
