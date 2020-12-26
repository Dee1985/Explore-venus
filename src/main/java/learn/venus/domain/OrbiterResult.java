package learn.venus.domain;

import learn.venus.models.Orbiter;

import java.util.ArrayList;
import java.util.List;

public class OrbiterResult {
    private ArrayList<String> messages = new ArrayList<>();
    private Orbiter payload;

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public Orbiter getPayload() {
        return payload;
    }

    public void setPayload(Orbiter payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages) ;
    }
}
