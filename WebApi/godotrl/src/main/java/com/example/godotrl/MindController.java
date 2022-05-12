package com.example.godotrl;

import com.example.godotrl.containers.AcceptContainer;
import com.example.godotrl.cst.AgentMind;
import com.example.godotrl.util.Vector2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MindController {

    private final String EMPTY_SENSOR_INFO = "{" +
            "   \"innersense\": {\"x\": \"0.0\", \"y\": \"0.0\"}," +
            "   \"vision\": []" +
            "}";

    private AgentMind agentMind = null;
    private long id = 0;

    @GetMapping("/initialize")
    public AcceptContainer initialize() {
        id += 1;

        if (agentMind != null) {
            agentMind.shutDown();
        }

        agentMind = new AgentMind();
        agentMind.initialize();

        return new AcceptContainer(id, "Mind initialized");
    }

    @GetMapping("/updatesensors")
    public AcceptContainer updateSensors(@RequestParam(value="info", defaultValue=EMPTY_SENSOR_INFO) String info) {
        id += 1;

        JSONObject parsedInfo = new JSONObject(info);

        // Update inner sense
        JSONObject innerSenseInfo = parsedInfo.getJSONObject("innersense");
        agentMind.updateInnerSense(new Vector2(innerSenseInfo.getDouble("x"), innerSenseInfo.getDouble("y")));

        // Update vision
        JSONArray carsJSONArray = parsedInfo.getJSONArray("vision");
        ArrayList<Vector2> carsArray = new ArrayList<Vector2>();
        for (int i = 0; i < carsJSONArray.length(); i++) {
            JSONObject pos = carsJSONArray.getJSONObject(i);
            carsArray.add(new Vector2(pos.getDouble("x"), pos.getDouble("y")));
        }
        agentMind.updateVision(carsArray);

        return new AcceptContainer(id, "InnerSense: " + new Vector2(innerSenseInfo.getDouble("x"), innerSenseInfo.getDouble("y")) + " - Vision: " + carsArray);
    }

    @GetMapping("/getmotordata")
    public AcceptContainer getMotorData() {
        id += 1;

        return new AcceptContainer(id, String.valueOf(agentMind.getActionData()));
    }
}
