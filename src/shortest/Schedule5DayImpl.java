/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shortest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author hieldc
 */
public class Schedule5DayImpl implements Schedule {

    private final HashMap<Calendar, Integer> calendar = new HashMap<>();

    private static final HashSet<Calendar> holidays = new HashSet<>();

    static {
        holidays.add(new GregorianCalendar(2016, 0, 1));
        holidays.add(new GregorianCalendar(2016, 0, 18));
        holidays.add(new GregorianCalendar(2016, 1, 15));
        holidays.add(new GregorianCalendar(2016, 4, 30));
        holidays.add(new GregorianCalendar(2016, 6, 4));
        holidays.add(new GregorianCalendar(2016, 8, 5));
        holidays.add(new GregorianCalendar(2016, 9, 10));
        holidays.add(new GregorianCalendar(2016, 10, 11));
        holidays.add(new GregorianCalendar(2016, 10, 24));
        holidays.add(new GregorianCalendar(2016, 11, 25));
    }

    @Override
    public int getAvailable(Calendar c) {
        if (calendar.containsKey(c)) {
            return calendar.get(c);
        }
        return MAX_PER_DAY;
    }

    //@Override
    private void updateAvailable(Calendar c, int count) {
        int value = MAX_PER_DAY;
        if (calendar.containsKey(c)) {
            value = calendar.get(c);
        }
        value -= count;
        calendar.put((Calendar) c.clone(), value);

    }

    @Override
    public int determineDaysForLoad(Calendar c, int count) {
        return calculateDaysForLoad(c, count, false);
    }

    @Override
    public int bookDaysForLoad(Calendar c, int count) {
        return calculateDaysForLoad(c, count, true);
    }

    private int calculateDaysForLoad(Calendar c, int count, boolean set) {
        Calendar gc = (Calendar) c.clone();
        int days = 0;
        int remainder = count;
        while (remainder > 0) {
            if (isWeekend(gc)) {
                moveToNextWeekday(gc);
            }
            if (holidays.contains(gc)) {
                moveToNextWeekday(gc);
            }
            int value = getAvailable(gc);
            if (value > 0) {
                int toUse = Math.min(remainder, value);
                remainder -= toUse;
                if (set) {
                    updateAvailable(gc, toUse);
                }
            }
            gc.add(Calendar.DATE, 1);
            days++;
        }
        return days;
    }

    private boolean isWeekend(Calendar c) {
        return c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    private void moveToNextWeekday(Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            c.add(Calendar.DATE, 2);
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            c.add(Calendar.DATE, 3);
        } else {
            c.add(Calendar.DATE, 1);
        }

    }

    @Override
    public void dumpSchedule(Calendar start, Calendar end) {

        SimpleDateFormat format1 = new SimpleDateFormat(" E, MM/dd/yyyy");
        ArrayList<Calendar> tmp = new ArrayList<>(calendar.keySet());
        Collections.sort(tmp);

        Calendar current = (Calendar) start.clone();

        while (!current.after(end)) {

            if (isWeekend(current)) {
                System.out.println(format1.format(current.getTime()) + ", Weekend");
                current.add(Calendar.DATE, 1);
                continue;
            }
            if (holidays.contains(current)) {
                System.out.println(format1.format(current.getTime()) + ", Holiday");
                current.add(Calendar.DATE, 1);
                continue;
            }
            System.out.println(format1.format(current.getTime()) + ", Free: " + (calendar.get(current) == null ? MAX_PER_DAY : calendar.get(current)) + " hrs");
            current.add(Calendar.DATE, 1);
        }
    }
}
