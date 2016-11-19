package roms;

import java.util.LinkedList;
import java.util.List;

public class KitchenController {
    private List<KitchenDisplay> kitchenDisplays = new LinkedList<>();

    public void addKitchenDisplay(KitchenDisplay kitchenDisplay) {
        kitchenDisplays.add(kitchenDisplay);
    }

    public void removeKitchenDisplay(KitchenDisplay kitchenDisplay) {
        kitchenDisplays.remove(kitchenDisplay);
    }

    public void displayRackToAll(Rack rack) {
        kitchenDisplays.forEach(kd -> kd.displayRack(rack));
    }
}
