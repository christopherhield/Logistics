/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class PathFinder {

    private static final HashSet<String> seen = new HashSet<>();
    private static final HashMap<String, Integer> pairs = new HashMap<>();
    private static HashMap<String, Site> nodes = null;
    private static int lowTime = Integer.MAX_VALUE;
    private static ArrayList<String> lowPath = null;
    private static int MPH = 50;

    private static void mapAllPairs(HashMap<String, Site> listIn, String init) {
        nodes = listIn;
        mapPairs(init);
    }

    private static void mapPairs(String init) {

        seen.add(init);
        for (String n : nodes.get(init).getNeighbors(init)) {
            //System.out.println(init + "->" + n + " = " + (nodes.get(init).getDelay() + nodes.get(init).getLinkTime(n) + nodes.get(n).getDelay()));

            pairs.put(init + "->" + n, (nodes.get(init).getDelay() + nodes.get(init).getLinkTime(n) + nodes.get(n).getDelay()));
            if (!seen.contains(n)) {
                mapPairs(n);
            }
        }
    }

    public static void findBestPath(String start, String end, HashMap<String, Site> listIn) {

        if (pairs.isEmpty()) {
            mapAllPairs(listIn, start);
            seen.clear();
        }

        ArrayList<String> initLoc = new ArrayList<>();
        initLoc.add(start);

        findPaths(start, end, initLoc);
        System.out.println("\n" + lowPath + " " + lowTime);

    }

    private static void findPaths(String start, String end, ArrayList<String> path) {

        if (start.equals(end)) {
            processPath(path);
            return;
        }
        HashSet<String> fromHere = new HashSet<>();

        for (String s : pairs.keySet()) {
            String first = s.substring(0, s.indexOf("-"));
            String second = s.substring(s.indexOf(">") + 1);

            if (first.equals(start) && !seen.contains(second)) {
                fromHere.add(s);
            }
        }

        for (String s : fromHere) {
            String second = s.substring(s.indexOf(">") + 1);
            if (path.contains(second)) {
                //System.out.println(" Been here: " + second + " (" + path + ")");
                continue;
            }
            ArrayList<String> newPath = new ArrayList<>(path);
            newPath.add(second);
            findPaths(second, end, newPath);
        }

    }

    private static void processPath(ArrayList<String> path) {
        int time = 0;
        time += sumDelays(path);
        time += sumLinks(path);
        if (time < lowTime) {
            lowTime = time;
            lowPath = new ArrayList<>(path);
        }
        //System.out.println(path + " " + time);
    }

    private static int sumDelays(ArrayList<String> path) {
        int time = 0;
        for (String s : path) {
            time += nodes.get(s).getDelay();
        }
        return time;
    }

    private static int sumLinks(ArrayList<String> path) {
        int time = 0;
        String start = path.get(0);

        for (int i = 1; i < path.size(); i++) {
            String pair = start + "->" + path.get(i);
            time += pairs.get(pair)/MPH;
            start = path.get(i);
        }
        return time;
    }
}
