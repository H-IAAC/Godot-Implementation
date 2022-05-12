package com.example.cstgodottest;

import com.example.cstgodottest.container.TestContainer;
import com.example.cstgodottest.cst.AgentMind;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private AgentMind agentMind = null;
    private long id = 0;

    @GetMapping("/initialize")
    public TestContainer initialize(@RequestParam(value="name", defaultValue="Test") String name) {
        id += 1;

        if (agentMind != null) {
            agentMind.shutDown();
        }

        agentMind = new AgentMind();
        agentMind.initialize(name);

        return new TestContainer(id, name);
    }

    @GetMapping("/check")
    public TestContainer check() {
        id += 1;

        if (agentMind != null) {
            return new TestContainer(id, agentMind.getValue());
        }

        return new TestContainer(id, "No mind detected");
    }
}
