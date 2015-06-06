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
    private final int delay;
    private final int xLoc, yLoc;
    private final Schedule schedule = new Schedule5DayImpl();


    public Node(String id, int d, int x, int y) {
        name = id;
        delay = d;
        xLoc = x;
        yLoc = y;
    }


    public String getName() {
        return name;
    }

    public void addLink(String tgt, int d) {
        links.put(tgt, d);
    }

    public HashSet<String> getNeighbors(String s) {
        return new HashSet(links.keySet());
    }

    public int getDelay() {
        return delay;
    }

    public int getLinkTime(String s) {
        return links.get(s);
    }

    @Override
    public void dumpSchedule() {
        schedule.dumpSchedule();
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
    public void updateAvailable(Calendar c, int count) {
        schedule.updateAvailable(c, count);
    }

    @Override
    public int bookDaysForLoad(Calendar c, int count) {
        return schedule.bookDaysForLoad(c, count);
    }

}
