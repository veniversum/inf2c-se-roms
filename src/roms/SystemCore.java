/**
 * 
 */
package roms;

/**
 * @author pbj
 *
 * Demonstration core system class.
 * 
 */
public class SystemCore {
 
    private PassLight passLight;
    
    public void setPassLight(PassLight pl) {
        passLight = pl;
    }
    

    public void cancelReadyUp() {
        passLight.switchOff();
    }
    
 
}
