package ch.zhaw.soe.swen1.le09.lightcontrol;

import java.util.ArrayList;
import java.util.List;

/**
 * Main light control class. 
 *
 */
public class LightControl {
    private List<LampFX> lamps;

    public LightControl() {
        lamps = new ArrayList<LampFX>();
    }
    
    public void addLamp(LampFX lamp) {
        lamps.add(lamp);
    }
    
    public void setLampsColor(RGBColorPercentage color) {
        lamps.forEach(lamp -> lamp.lampColorProperty().set(color)); 
    }
    
}
