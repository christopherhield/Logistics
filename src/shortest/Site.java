/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest;

import java.util.HashSet;

/**
 *
 * @author hieldc
 */
public interface Site extends Schedule {

    HashSet<String> getNeighbors(String s);

    int getDelay();

    int getLinkTime(String s);
    
    void addLink(String tgt, int d);
}
