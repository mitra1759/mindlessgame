package bluetooth;

import java.io.Serializable;

public class BTData implements Serializable {

    private BTType type;
    private float iData;

    private int rollData;

    public BTData(BTType type){
        this.type = type;
    }

    public float getiData() {
        return iData;
    }

    public void setiData(float iData) {
        this.iData = iData;
    }

    public BTType getType() {
        return type;
    }


    public int getRollData() {
        return rollData;
    }

    public void setRollData(int rollData) {
        this.rollData = rollData;
    }
}
