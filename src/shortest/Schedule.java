/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest;

import java.util.Calendar;

/**
 *
 * @author hieldc
 */
public interface Schedule {

    final int MAX_PER_DAY = 8;

    void dumpSchedule(Calendar start, Calendar end);

    int getAvailable(Calendar c);

    int determineDaysForLoad(Calendar c, int count);

    int bookDaysForLoad(Calendar c, int count);

    //void updateAvailable(Calendar c, int count);

}
