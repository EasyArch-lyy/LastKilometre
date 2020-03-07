package pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.HashMap;

public class Point implements Serializable {
    private String name;
    private int kind;
    private HashMap<Double,Double>position;
    private String ScopeName;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public HashMap<Double, Double> getPosition() {
        return position;
    }

    public void setPosition(HashMap<Double, Double> position) {
        this.position = position;
    }

    public String getScopeName() {
        return ScopeName;
    }

    public void setScopeName(String scopeName) {
        ScopeName = scopeName;
    }

}
