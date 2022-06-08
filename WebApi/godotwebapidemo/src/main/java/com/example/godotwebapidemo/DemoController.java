package com.example.godotwebapidemo;

import com.example.godotwebapidemo.containers.AcceptContainer;
import com.example.godotwebapidemo.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {

    private final String EMPTY_SENSOR_INFO = "{" +
            "   \"innersense\": {\"x\": \"0.0\", \"y\": \"0.0\"}," +
            "   \"vision\": []" +
            "}";

    private long id = 0;

    private Vector2 pos;
    private List<Vector2> carsInSight;
    private Updater updater;

    @GetMapping("/initialize")
    public AcceptContainer initialize() {
        id += 1;

        updater = new Updater();

        return new AcceptContainer(id, "Initialized demo API", RequestType.INFO);
    }

    @PostMapping ("/updatesensors")
    public AcceptContainer updateSensors(@RequestBody SensorData info) {
        id += 1;

        if (updater.updateSensor()) {
            // Get inner sense data
            pos = info.innersense;

            carsInSight = info.vision;

            return new AcceptContainer(id, "InnerSense: " + pos + " - Vision: " + carsInSight, RequestType.SENSOR);
        }

        return new AcceptContainer(id, "Awaiting motor request", RequestType.INFO);
    }

    @GetMapping("/getmotordata")
    public AcceptContainer getMotorData() {
        id += 1;

        if (updater.updateMotor()) {
            if (!carsInSight.contains(pos.add(new Vector2(0, -1))) && !carsInSight.contains(pos.add(new Vector2(-1, -1)))) {
                return new AcceptContainer(id, Action.UP.toString(), RequestType.MOTOR);
            } else if (!carsInSight.contains(pos.add(new Vector2(-1, 0))) && !carsInSight.contains(pos.add(new Vector2(-2, 0))) && pos.getX() > 0) {
                return new AcceptContainer(id, Action.LEFT.toString(), RequestType.MOTOR);
            } else if (!carsInSight.contains(pos.add(new Vector2(1, 0))) && !carsInSight.contains(pos.add(new Vector2(-2, 0))) && !carsInSight.contains(pos.add(new Vector2(-1, 0)))) {
                return new AcceptContainer(id, Action.RIGHT.toString(), RequestType.MOTOR);
            }
            return new AcceptContainer(id, Action.DOWN.toString(), RequestType.MOTOR);
        }

        return new AcceptContainer(id, "Awaiting sensor request", RequestType.INFO);
    }

    @GetMapping("/logwin")
    public AcceptContainer logWin() {
        id += 1;

        return new AcceptContainer(id, "Win logged", RequestType.WIN);
    }

    @GetMapping("/loglose")
    public AcceptContainer logDefeat() {
        id += 1;

        return new AcceptContainer(id, "Defeat logged", RequestType.LOSE);
    }
}
