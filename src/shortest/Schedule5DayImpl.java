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
    private final int maxPerDay = 10;
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
        return maxPerDay;
    }

    @Override
    public void updateAvailable(Calendar c, int count) {
        int value = maxPerDay;
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

    public int calculateDaysForLoad(Calendar c, int count, boolean set) {
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
    public void dumpSchedule() {
        
        if (calendar.isEmpty()) {
            System.out.println("Schedule Empty");
            return;
        }
        SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy EEE");
        ArrayList<Calendar> tmp = new ArrayList<>(calendar.keySet());
        Collections.sort(tmp);

        Calendar current = (Calendar) tmp.get(0).clone();
        Calendar last = tmp.get(tmp.size() - 1);
        while (!current.equals(last)) {
            System.out.println(format1.format(current.getTime()) + ": " + (calendar.get(current) == null ? maxPerDay : calendar.get(current)) + " remaining");
            current.add(Calendar.DATE, 1);
            if (isWeekend(current)) {
                moveToNextWeekday(current);
            }
            if (holidays.contains(current)) {
                moveToNextWeekday(current);
            }
        }
        System.out.println(format1.format(last.getTime()) + ": " + calendar.get(last) + " remaining");
    }
}
