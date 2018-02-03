package net.bndy.wf.modules.cms.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoType {
    Page(10),
    Article(20),
    File(30);

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return this.name();
    }

    BoType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static BoType fromObject(Map<String,Object> data) {
        for (BoType boType: BoType.values()) {
            if (Integer.toString(boType.getValue()).equals(data.get("value").toString())) {
                return boType;
            }
        }
        return null;
    }
}
