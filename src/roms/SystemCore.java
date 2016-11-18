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
    private MenuProvider menuProvider;
 
    private PassLight passLight;
    
    public void setPassLight(PassLight pl) {
        passLight = pl;
    }
    

    public void cancelReadyUp() {
        passLight.switchOff();
    }

    public MenuProvider getMenuProvider() {
        return menuProvider;
    }

    public void setMenuProvider(MenuProvider menuProvider) {
        this.menuProvider = menuProvider;
    }
}
