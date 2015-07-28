/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author hieldc
 */
public class Node implements Site {

    private HashMap<String, Integer> links = new HashMap<>();
    private final String name;
    private final int xLoc, yLoc;
    private final Schedule schedule = new Schedule5DayImpl();


    public Node(String id, int x, int y) {
        name = id;
        xLoc = x;
        yLoc = y;
    }


    public String getName() {
        return name;
    }

    @Override
    public void addLink(String tgt, int d) {
        links.put(tgt, d);
    }

    @Override
    public HashSet<String> getNeighbors() {
        return new HashSet(links.keySet());
    }

    @Override
    public int getLinkDistance(String s) {
        return links.get(s);
    }

    @Override
    public void dumpSchedule(Calendar start, Calendar end) {
        schedule.dumpSchedule(start, end);
    }

    @Override
    public int getAvailable(Calendar c) {
        return schedule.getAvailable(c);
    }

    @Override
    public int determineDaysForLoad(Calendar c, int count) {
        return schedule.determineDaysForLoad(c, count);
    }

    @Override
    public int bookDaysForLoad(Calendar c, int count) {
        return schedule.bookDaysForLoad(c, count);
    }

}
