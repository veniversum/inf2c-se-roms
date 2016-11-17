/**
 * 
 */
package roms;

import java.util.logging.Logger;

/**
 * An abstract device class, defining fields and methods needed by input, output
 * and input-output device classes.
 * 
 * All other device classes should inherit from this class
 * and call the constructor to initialise the instanceName.
 * 
 * @author pbj
 *
 */
public abstract class AbstractDevice {
    protected static final String LS = System.getProperty("line.separator");
    protected static final Logger logger = Logger.getLogger("roms");
    
    private String instanceName;
    
    public AbstractDevice(String instance) {
        instanceName = instance;
    }
    
    public String getInstanceName() {
        return instanceName;
    }
     

}
