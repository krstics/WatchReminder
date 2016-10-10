package com.krstics.watchreminder.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Items")
public class Item {

    @Element(name = "Time", required = true)
    private String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
