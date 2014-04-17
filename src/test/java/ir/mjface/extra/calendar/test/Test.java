package ir.mjface.extra.calendar.test;

import ir.mjface.extra.calendar.JalaliCalendar;

import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        final JalaliCalendar c = new JalaliCalendar();

        c.setTime(new Date());
        System.out.println("Year: " + c.get(Calendar.YEAR));
        System.out.println("Month: " + c.get(Calendar.MONTH));
        System.out.println("Day: " + c.get(Calendar.DAY_OF_MONTH));
    }

}
