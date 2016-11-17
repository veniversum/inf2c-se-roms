/**
 * 
 */
package roms;

import java.util.List;

/**
 * Device for reading details of contactless smart cards.
 * 
 * @author pbj
 *
 */
public class CardReader extends AbstractInputDevice {

  
    
    /**
     * 
     * @param instanceName  
     */
    public CardReader(String instanceName) {
        super(instanceName);   
    }
    
    
    /**
     * Fetch a non-triggering input event modelling receiving some payment
     * authorisation code from the bank server. 
     * 
     * @return an authorisation code for the payment
     */
    public String waitForCardDetails() {
         
        logger.fine(getInstanceName());

        List<String> messageArgs = 
                fetchMatchingMessage("acceptCardDetails");
        
        return messageArgs.get(0);
    }
}
