/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;

/**
 * Task-level model of a touch-screen display at a table.
 * 
 * @author pbj
 *
 */
public class ReceiptPrinter extends AbstractOutputDevice {

    
    /**
     * 
     * @param instanceName  
     */
    public ReceiptPrinter(String instanceName) {
        super(instanceName);   
    }
        
    /** 
     * SUPPORT FOR printReceipt OUTPUT MESSAGE
     */

    public void printReceipt(Money total, String authCode) {
        logger.fine(getInstanceName());
      
        List<String> messageArgs = new ArrayList<String>();
        messageArgs.add("Total:");
        messageArgs.add(total.toString());
        messageArgs.add("AuthCode:");
        messageArgs.add(authCode);
        sendMessage("takeReceipt", messageArgs);
      
    }

     
}
