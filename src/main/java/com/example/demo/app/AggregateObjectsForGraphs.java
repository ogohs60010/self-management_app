package com.example.demo.app;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AggregateObjectsForGraphs {
    String NumName;
    List<Integer> IntList;
    List<String> DateList;

    public AggregateObjectsForGraphs() {
        this.IntList = new ArrayList<>();
        this.DateList = new ArrayList<>();
    }

    public void addIntList(Integer value) {
        this.IntList.add(value);
    }

    public void addDateList(String dateValue) {
        this.DateList.add(dateValue);
    }
}

