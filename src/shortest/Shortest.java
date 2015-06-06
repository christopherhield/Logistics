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

        long start = System.currentTimeMillis();
        PathFinder.findBestPath("Seattle, WA", "Miami, FL", nodes);
        System.out.println(System.currentTimeMillis() - start + " ms");

        GregorianCalendar gc = new GregorianCalendar(2016, 0, 1);
        
        Schedule n = nodes.get("Seattle, WA");
        
        System.out.println(n.determineDaysForLoad(gc, 38));
        n.dumpSchedule();
  
        System.out.println(n.bookDaysForLoad(gc, 138));
        n.dumpSchedule();
        
    }

    
    private static void makeNodes() {


        Node n = new Node("Seattle, WA", 0, 25, 95);
        nodes.put(n.getName(), n);
        n = new Node("San Francisco, CA", 0, 10, 330);
        nodes.put(n.getName(), n);
        n = new Node("Los Angeles, CA", 0, 80, 500);
        nodes.put(n.getName(), n);
        n = new Node("Phoenix, AZ", 0, 300, 615);
        nodes.put(n.getName(), n);
        n = new Node("Denver, CO", 0, 360, 330);
        nodes.put(n.getName(), n);
        n = new Node("Santa Fe, NM", 0, 440, 500);
        nodes.put(n.getName(), n);
        n = new Node("Fargo, ND", 0, 520, 100);
        nodes.put(n.getName(), n);
        n = new Node("Austin, TX", 0, 610, 690);
        nodes.put(n.getName(), n);
        n = new Node("St. Louis, MO", 0, 720, 400);
        nodes.put(n.getName(), n);
        n = new Node("Chicago, IL", 0, 760, 180);
        nodes.put(n.getName(), n);
        n = new Node("New Orleans, LA", 0, 850, 690);
        nodes.put(n.getName(), n);
        n = new Node("Nashville, TN", 0, 900, 475);
        nodes.put(n.getName(), n);
        n = new Node("Detroit, MI", 0, 980, 175);
        nodes.put(n.getName(), n);
        n = new Node("Boston, MA", 0, 1160, 150);
        nodes.put(n.getName(), n);
        n = new Node("New York City, NY", 0, 1125, 250);
        nodes.put(n.getName(), n);
        n = new Node("Norfolk, VA", 0, 1115, 410);
        nodes.put(n.getName(), n);
        n = new Node("Atlanta, GA", 0, 1120, 600);
        nodes.put(n.getName(), n);
        n = new Node("Miami, FL", 0, 1190, 780);
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
