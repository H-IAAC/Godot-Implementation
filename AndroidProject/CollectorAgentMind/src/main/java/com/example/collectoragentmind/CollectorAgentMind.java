package com.example.collectoragentmind;

import android.util.ArraySet;

import androidx.annotation.NonNull;

import com.example.collectoragentmind.util.Apple;
import com.example.collectoragentmind.util.Vector2;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import br.unicamp.cst.core.entities.MemoryObject;


public class CollectorAgentMind extends GodotPlugin {

    private static CollectorAgentMind singleton = null;

    private MemoryObject visionMO;
    private MemoryObject positionMO;
    private MemoryObject touchMO;
    private MemoryObject knownApplesMO;

    private AgentMind agentMind;

    public CollectorAgentMind(Godot godot) {
        super(godot);
        singleton = this;
    }

    public static CollectorAgentMind getInstance() {
        return singleton;
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "CollectorAgentMind";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("start", "stop", "updatePosition", "clearApplesInVision", "addAppleInVision", "clearApplesInReach", "addAppleInReach", "removeAppleFromKnownApples");
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("print_value", String.class));
        signals.add(new SignalInfo("update_value", String.class, String.class));

        signals.add(new SignalInfo("request_apples_in_reach"));
        signals.add(new SignalInfo("request_agent_position"));
        signals.add(new SignalInfo("request_apples_in_sight"));

        signals.add(new SignalInfo("eat_apple", String.class));
        signals.add(new SignalInfo("go_to", String.class, String.class));
        signals.add(new SignalInfo("forage"));

        return signals;
    }

    public void start() {
        if (agentMind != null) {
            agentMind.shutDown();
        }

        agentMind = new AgentMind();

        visionMO = agentMind.getVisionMO();
        positionMO = agentMind.getPositionMO();
        touchMO = agentMind.getTouchMO();
        knownApplesMO = agentMind.getKnownApplesMO();
    }

    public void stop() {
        if (agentMind != null) {
            agentMind.shutDown();
            agentMind = null;

            visionMO = null;
            positionMO = null;
            touchMO = null;
            knownApplesMO = null;
        }
    }

    /*
        COMMANDS
    */
    public void print(String value) {
        emitSignal("print_value", value);
    }

    public void updateValue(String newValue, String valueCode) {
        emitSignal("update_value", newValue, valueCode);
    }

    public void eatApple(String apple_name) {
        emitSignal("eat_apple", apple_name);
    }

    public void goTo(double x, double y) {
        emitSignal("go_to", String.valueOf(x), String.valueOf(y));
    }

    public void forage() {
        emitSignal("forage");
    }

    /*
        DATA REQUESTS / UPDATES
    */

    public void emitRequest(String requestName) {
        emitSignal(requestName);
    }

    public void updatePosition(float x, float y) {
        positionMO.setI(new Vector2((double) x, (double) y));
        updateValue(positionMO.getI().toString(), "POSITION");
    }

    public void clearApplesInVision() {
        visionMO.setI(new ArrayList<Apple>());
        updateValue("No Apples!", "VISION");
    }

    public void addAppleInVision(String name, float x, float y) {
        ArrayList<Apple> inView = (ArrayList<Apple>) visionMO.getI();

        inView.add(new Apple(name, new Vector2((double) x, (double) y)));
        updateValue(inView.toString(), "VISION");

        visionMO.setI(inView);
    }

    public void clearApplesInReach() {
        touchMO.setI(new ArrayList<Apple>());
        updateValue("No Apples!", "TOUCH");
    }

    public void addAppleInReach(String name, float x, float y) {
        ArrayList<Apple> inTouch = (ArrayList<Apple>) touchMO.getI();

        inTouch.add(new Apple(name, new Vector2((double) x, (double) y)));
        updateValue(inTouch.toString(), "TOUCH");

        touchMO.setI(inTouch);
    }

    private int getApplePositionByName(ArrayList<Apple> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void removeAppleFromKnownApples(String name) {
        ArrayList<Apple> knownApples = (ArrayList<Apple>) knownApplesMO.getI();

        int pos = getApplePositionByName(knownApples, name);
        if (pos != -1) {
            knownApples.remove(pos);
        }

        knownApplesMO.setI(knownApples);
    }

}
