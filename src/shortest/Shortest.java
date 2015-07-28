/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author hieldc
 */
public class Shortest {

    static HashMap<String, Site> nodes = new HashMap<>();
    static HashSet<String> seen = new HashSet<>();
    static HashMap<String, Integer> pairs = new HashMap<>();

    public static void main(String[] args) {

        makeNodes();
        dumpNodes();

        doExample("Seattle, WA", "Miami, FL");
        doExample("Los Angeles, CA", "Boston, MA");
        doExample("New Orleans, LA", "Chicago, IL");

        System.out.println("----------------------");

        doBookExample("Chicago, IL", 38);
        System.out.println("---");
        doBookExample("Norfolk, VA", 64);
        System.out.println("---");
        doBookExample("Chicago, IL", 25);
        System.out.println("---");
    }

    private static void doExample(String s1, String s2) {
        long start = System.currentTimeMillis();
        ArrayList<String> path = PathFinder.findBestPath(s1, s2, nodes);
        int distance = PathFinder.getPathDistance(path);
        System.out.println("\nShortest path from " + s1 + " to " + s2 + ": " + path + " = " + String.format("%,d", distance)
                + " mi (" + (System.currentTimeMillis() - start) + " ms)");
    }

    private static void doBookExample(String site, int count) {

        GregorianCalendar gc = new GregorianCalendar(2016, 0, 1);
        Schedule n = nodes.get(site);
        System.out.println("\nDays to load " + count + " items at " + site + " from " + gc.getTime() + ": " + n.determineDaysForLoad(gc, count));
        System.out.println("Schedule BEFORE booking time at " + site + ":");
        n.dumpSchedule(new GregorianCalendar(2016, 0, 1), new GregorianCalendar(2016, 0, 15));

        
        n.bookDaysForLoad(gc, count);
        System.out.println("Schedule AFTER booking time at " + site + ":");
        n.dumpSchedule(new GregorianCalendar(2016, 0, 1), new GregorianCalendar(2016, 0, 15));
    }

    private static void dumpNodes() {
        System.out.println("Node summary:");

        int i = 1;
        for (String s : nodes.keySet()) {
            System.out.print("   " + i++ + ") " + s + ": ");

            for (String n : nodes.get(s).getNeighbors()) {
                System.out.print(n + " (" + nodes.get(s).getLinkDistance(n) + "); ");
            }
            System.out.println();
        }
    }

    private static void makeNodes() {

        Node n = new Node("Seattle, WA", 25, 95);
        nodes.put(n.getName(), n);
        n = new Node("San Francisco, CA", 10, 330);
        nodes.put(n.getName(), n);
        n = new Node("Los Angeles, CA", 80, 500);
        nodes.put(n.getName(), n);
        n = new Node("Phoenix, AZ", 300, 615);
        nodes.put(n.getName(), n);
        n = new Node("Denver, CO", 360, 330);
        nodes.put(n.getName(), n);
        n = new Node("Santa Fe, NM", 440, 500);
        nodes.put(n.getName(), n);
        n = new Node("Fargo, ND", 520, 100);
        nodes.put(n.getName(), n);
        n = new Node("Austin, TX", 610, 690);
        nodes.put(n.getName(), n);
        n = new Node("St. Louis, MO", 720, 400);
        nodes.put(n.getName(), n);
        n = new Node("Chicago, IL", 760, 180);
        nodes.put(n.getName(), n);
        n = new Node("New Orleans, LA", 850, 690);
        nodes.put(n.getName(), n);
        n = new Node("Nashville, TN", 900, 475);
        nodes.put(n.getName(), n);
        n = new Node("Detroit, MI", 980, 175);
        nodes.put(n.getName(), n);
        n = new Node("Boston, MA", 1160, 150);
        nodes.put(n.getName(), n);
        n = new Node("New York City, NY", 1125, 250);
        nodes.put(n.getName(), n);
        n = new Node("Norfolk, VA", 1115, 410);
        nodes.put(n.getName(), n);
        n = new Node("Atlanta, GA", 1120, 600);
        nodes.put(n.getName(), n);
        n = new Node("Miami, FL", 1190, 780);
        nodes.put(n.getName(), n);

        nodes.get("Seattle, WA").addLink("Fargo, ND", 1426);
        nodes.get("Seattle, WA").addLink("San Francisco, CA", 808);

        nodes.get("San Francisco, CA").addLink("Seattle, WA", 808);
        nodes.get("San Francisco, CA").addLink("Denver, CO", 1249);
        nodes.get("San Francisco, CA").addLink("Los Angeles, CA", 382);

        nodes.get("Los Angeles, CA").addLink("San Francisco, CA", 382);
        nodes.get("Los Angeles, CA").addLink("Phoenix, AZ", 372);

        nodes.get("Phoenix, AZ").addLink("Los Angeles, CA", 372);
        nodes.get("Phoenix, AZ").addLink("Santa Fe, NM", 480);

        nodes.get("Denver, CO").addLink("San Francisco, CA", 1249);
        nodes.get("Denver, CO").addLink("Santa Fe, NM", 392);
        nodes.get("Denver, CO").addLink("Fargo, ND", 889);
        nodes.get("Denver, CO").addLink("St. Louis, MO", 851);

        nodes.get("Santa Fe, NM").addLink("Phoenix, AZ", 480);
        nodes.get("Santa Fe, NM").addLink("Denver, CO", 392);
        nodes.get("Santa Fe, NM").addLink("St. Louis, MO", 1032);
        nodes.get("Santa Fe, NM").addLink("Austin, TX", 688);

        nodes.get("Fargo, ND").addLink("Seattle, WA", 1426);
        nodes.get("Fargo, ND").addLink("Denver, CO", 889);
        nodes.get("Fargo, ND").addLink("Chicago, IL", 643);

        nodes.get("Austin, TX").addLink("Santa Fe, NM", 688);
        nodes.get("Austin, TX").addLink("St. Louis, MO", 825);
        nodes.get("Austin, TX").addLink("New Orleans, LA", 512);

        nodes.get("St. Louis, MO").addLink("Chicago, IL", 297);
        nodes.get("St. Louis, MO").addLink("Nashville, TN", 309);
        nodes.get("St. Louis, MO").addLink("Austin, TX", 825);
        nodes.get("St. Louis, MO").addLink("Santa Fe, NM", 1032);
        nodes.get("St. Louis, MO").addLink("Denver, CO", 851);

        nodes.get("Chicago, IL").addLink("Fargo, ND", 643);
        nodes.get("Chicago, IL").addLink("St. Louis, MO", 297);
        nodes.get("Chicago, IL").addLink("New York City, NY", 790);
        nodes.get("Chicago, IL").addLink("Detroit, MI", 282);

        nodes.get("New Orleans, LA").addLink("Austin, TX", 512);
        nodes.get("New Orleans, LA").addLink("Nashville, TN", 532);
        nodes.get("New Orleans, LA").addLink("Atlanta, GA", 469);
        nodes.get("New Orleans, LA").addLink("Miami, FL", 864);

        nodes.get("Nashville, TN").addLink("St. Louis, MO", 309);
        nodes.get("Nashville, TN").addLink("New Orleans, LA", 532);
        nodes.get("Nashville, TN").addLink("Norfolk, VA", 706);

        nodes.get("Detroit, MI").addLink("Chicago, IL", 282);
        nodes.get("Detroit, MI").addLink("New York City, NY", 614);
        nodes.get("Detroit, MI").addLink("Boston, MA", 719);

        nodes.get("Boston, MA").addLink("Detroit, MI", 719);
        nodes.get("Boston, MA").addLink("New York City, NY", 215);

        nodes.get("New York City, NY").addLink("Boston, MA", 215);
        nodes.get("New York City, NY").addLink("Detroit, MI", 614);
        nodes.get("New York City, NY").addLink("Chicago, IL", 790);
        nodes.get("New York City, NY").addLink("Norfolk, VA", 363);

        nodes.get("Norfolk, VA").addLink("New York City, NY", 363);
        nodes.get("Norfolk, VA").addLink("Nashville, TN", 706);
        nodes.get("Norfolk, VA").addLink("Atlanta, GA", 566);

        nodes.get("Atlanta, GA").addLink("Norfolk, VA", 566);
        nodes.get("Atlanta, GA").addLink("New Orleans, LA", 469);
        nodes.get("Atlanta, GA").addLink("Miami, FL", 663);

        nodes.get("Miami, FL").addLink("Atlanta, GA", 663);
        nodes.get("Miami, FL").addLink("New Orleans, LA", 864);

    }

}
