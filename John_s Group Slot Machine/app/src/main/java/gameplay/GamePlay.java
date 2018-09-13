package gameplay;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bluetooth.BTBridge;
import bluetooth.BTData;
import bluetooth.BTType;
import bluetooth.BridgeDelegate;
import slotmachine.johnnywaity.com.slotmachine.GameActivity;

public class GamePlay implements BridgeDelegate {

    private float lastPayout = 0;

    private boolean isMultiplayer = false;
    private BTBridge bridge = null;

    private GameActivity activity;

    private int opponentRoll = 0;
    private float opponentScore = 0;

    private int rollCount = 0;

    public GamePlay(){
        if(BTBridge.getSharedInstance() != null){
            isMultiplayer = true;
            bridge = BTBridge.getSharedInstance();
            bridge.setDelegate(this);
        }
    }

    public ArrayList<SlotIcons> roll(){ //payout is the player's bet
        rollCount++;
        ArrayList<SlotIcons> slotIcons = new ArrayList<>();
        for (int i=0;i<3;i++){
            int chance = 0;
            chance = (int)(Math.random()*30)+1;

            if (chance>=1 && chance<=3){ //small jackpot, larger payout
                slotIcons.add(SlotIcons.Cherry);
            }
            else if ((chance>3) && (chance<=8)){ //smaller chance, moderate payout
                slotIcons.add(SlotIcons.Bell);
            }
            else if (chance>8 && chance<15){ //larger chance, small payout
                slotIcons.add(SlotIcons.Grape);
            }
            else if (chance>=15 && chance<=20){ //larger chance, small payout
                slotIcons.add(SlotIcons.Bar);
            }
            else if (chance>20 && chance<29){ //no extra payout, largest chance
                slotIcons.add(SlotIcons.Orange);
            }
            else if (chance>=29 && chance<=30){ //very small chance, much larger payout
                slotIcons.add(SlotIcons.Seven);
            }
        }
        calculatePayout(slotIcons);
        return slotIcons;

    }

    private void calculatePayout(ArrayList<SlotIcons> results){
        float score = 0;
        for(int i = 0; i < 3; i++){
            score += SlotIcons.iconToPayout(results.get(i));
        }
        Map<SlotIcons, Integer> duplicates = new HashMap<>();
        for(SlotIcons s : results){
            int currentValue = 0;
            if(duplicates.containsKey(s)){
                currentValue += 1;
            }
            currentValue ++;
            duplicates.put(s, currentValue);
        }
        boolean addedMultiplier = false;
        for(SlotIcons s : duplicates.keySet()){
            if(duplicates.get(s) == 2){
                score *= 1;
                addedMultiplier = true;
            }
            else if(duplicates.get(s) == 3){
                score *= 5;
                addedMultiplier = true;
            }
        }
        if(!addedMultiplier){
            score *= 0;
        }

        score *= 100;
        score = Math.round(score);
        score /= 100;
        lastPayout += score;



    }

    public float getLastPayout() {
        if(isMultiplayer){
            BTData d = new BTData(BTType.Score);
            d.setiData(lastPayout);
            bridge.write(d);

            BTData d1 = new BTData(BTType.Roll);
            d1.setRollData(rollCount);
            bridge.write(d1);

            activity.updatePlayerRolls(rollCount + "/3");
            checkForVictory();
        }

        return lastPayout;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setActivity(GameActivity activity) {
        this.activity = activity;
    }

    public int getRollCount() {
        return rollCount;
    }

    private void checkForVictory(){
        if(rollCount == 3 && opponentRoll == 3){
            if(lastPayout > opponentScore){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getBaseContext(), "You Win", Toast.LENGTH_LONG);
                    }
                });
            }else if(opponentScore > lastPayout){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getBaseContext(), "You Lose", Toast.LENGTH_LONG);
                    }
                });
            }else{
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getBaseContext(), "You Tie", Toast.LENGTH_LONG);
                    }
                });
            }

        }
    }

    @Override
    public void onMessage(BTData d) {
        if(d.getType() == BTType.Score){
            activity.updateOpponentScore("Opponent Score: " + d.getiData());
            opponentScore = d.getiData();
        }else if(d.getType() == BTType.Roll){
            activity.updateOpponentRolls(d.getRollData() + "/3");
            opponentRoll = d.getRollData();
        }
        checkForVictory();
    }
}
