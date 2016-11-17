/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;

/**
 * Device for accessing a bank card payment processing server over the internet.
 * 
 * Device is modeled at an abstract level with a single output operation for 
 * sending card details and payment amount, and a single input operation for
 * receiving an authorisation code.

 * @author pbj
 *
 */
public class BankClient extends AbstractIODevice {

  
    
    /**
     * 
     * @param instanceName  
     */
    public BankClient(String instanceName) {
        super(instanceName);   
    }
    
     /**
     * Generate an output event sending card details and payment amount 
     */
    public void makePayment(String cardDetails, Money paymentAmount){
        
        logger.fine(getInstanceName());

        List<String> argList = new ArrayList<String>();
        argList.add(cardDetails);
        argList.add(paymentAmount.toString());
        
        sendMessage("makePayment", argList);
    }
    
    
    /**
     * Fetch a non-triggering input event modelling receiving some payment
     * authorisation code from the bank server. 
     * 
     * @return an authorisation code for the payment
     */
    public String waitForAuthorisation() {
         
        logger.fine(getInstanceName());

        
        List<String> messageArgs = 
                fetchMatchingMessage("acceptAuthorisationCode");
        
        return messageArgs.get(0);
    }
  
    
    
    
    /**
     * A single method combining the above operations of requesting and 
     * checking a card.
     * 
     * @return an authorisation code for the card read
     */
    public String authorisePayment(String cardDetails, Money paymentAmount) {
        makePayment(cardDetails, paymentAmount);
        return waitForAuthorisation();
    }

}
