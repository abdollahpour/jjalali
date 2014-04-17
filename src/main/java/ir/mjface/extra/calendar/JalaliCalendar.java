/*
 * Copyright (c) 2009-2010, MJFace project
 * Hamed Abdollahpoor
 * http://www.mjface.com
 */

package ir.mjface.extra.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Use calendar az jalali time base. You can also you some of extra features 
 * for converting time from gregoaian base calendar with jalali base
 * @author Hamed Abdollahpour
 * @version 0.5
 */
public class JalaliCalendar extends Calendar{

    public final static int SHANBE       = SATURDAY;
    public final static int YEKSHANBE    = SATURDAY;
    public final static int DOSHANBE     = MONDAY;
    public final static int SESHANBE     = THURSDAY;
    public final static int CHAHARSHANBE = WEDNESDAY;
    public final static int PANGSHANBE   = TUESDAY;
    public final static int JOMEE        = FRIDAY;
    
    public final static int FARAVARDIN  = 0;
    public final static int ORDIBEHESHT = 1;
    public final static int KHORDAD     = 2;
    public final static int TIR         = 3;
    public final static int MORDAD      = 4;
    public final static int SHAHRIVAR   = 5;
    public final static int MEHR        = 6;
    public final static int ABAN        = 7;
    public final static int AZAR        = 8;
    public final static int DEI         = 9;
    public final static int BAHMAN      = 10;
    public final static int ESFAND      = 11;
    
    public JalaliCalendar(){
        setTime(new Date());
        setTimeZone(TimeZone.getDefault());
    }
    
    protected void computeFields() {
        Calendar defaultCalendar = Calendar.getInstance();
        defaultCalendar.setTimeZone(getTimeZone());
        defaultCalendar.setTime(new Date(time));
        
        SimpleDate simpleDate = new SimpleDate();
        simpleDate.setYear(defaultCalendar.get(YEAR));
        simpleDate.setMonth(defaultCalendar.get(MONTH));
        simpleDate.setDay(defaultCalendar.get(DAY_OF_MONTH));

        SimpleDate convertedDate = gregorian2jalali(simpleDate);

        int[] items = new int[]{Calendar.DAY_OF_WEEK, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND};
        // get other values
        for(int i=0; i<items.length; i++){
            fields[items[i]] = defaultCalendar.get(items[i]);
        }
        
        fields[YEAR]         = convertedDate.getYear();
        fields[MONTH]        = convertedDate.getMonth();
        fields[DAY_OF_MONTH] = convertedDate.getDay();
    }

    protected void computeTime() {
        SimpleDate simpleDate = new SimpleDate();
        
        simpleDate.setYear(fields[YEAR]);
        simpleDate.setMonth(fields[MONTH]);
        simpleDate.setDay(fields[DAY_OF_MONTH]);
        
        SimpleDate convertedDate = jalali2gregorian(simpleDate);
        
        Calendar defaultCalendar = Calendar.getInstance(getTimeZone());
        defaultCalendar.setTime(new Date(time));
        
        // set other values
        for(int i=0; i<fields.length; i++){
            defaultCalendar.set(i, fields[i]);
        }
        
        defaultCalendar.set(YEAR, convertedDate.getYear());
        defaultCalendar.set(MONTH, convertedDate.getMonth());
        defaultCalendar.set(DAY_OF_MONTH, convertedDate.getDay());
        
        time = defaultCalendar.getTime().getTime();
    }
    
    private static int[] g_days_in_month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] j_days_in_month = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

    public static SimpleDate gregorian2jalali(SimpleDate date) {
        int gy, gm, gd;
        int jy, jm, jd;
        long g_day_no, j_day_no;
        int j_np;
        
        int i;
        /*
                printf("Current Gregorian date: %04d-%02d-%02d\n", g.da_year,
                           g.da_mon, g.da_day);
         */
        
        gy = date.getYear()-1600;
        gm = date.getMonth();
        gd = date.getDay()-1;
        
        g_day_no = 365*gy+(gy+3)/4-(gy+99)/100+(gy+399)/400;
        for (i=0;i<gm;++i)
            g_day_no += g_days_in_month[i];
        if (gm>1 && ((gy%4==0 && gy%100!=0) || (gy%400==0)))
            /* leap and after Feb */
            ++g_day_no;
        g_day_no += gd;
        
        j_day_no = g_day_no-79;
        
        j_np = (int)(j_day_no / 12053);
        j_day_no %= 12053;
        
        jy = (int)(979+33*j_np+4*(j_day_no/1461));
        j_day_no %= 1461;
        
        if (j_day_no >= 366) {
            jy += (j_day_no-1)/365;
            j_day_no = (j_day_no-1)%365;
        }
        
        for (i = 0; i < 11 && j_day_no >= j_days_in_month[i]; ++i) {
            j_day_no -= j_days_in_month[i];
        }
        
        jm = i+1;
        jd = (int)(j_day_no+1);
        
        return new SimpleDate(jy, jm-1, jd);
    }
    
    public static SimpleDate jalali2gregorian(SimpleDate date){
        int jy=date.getYear()-979;
        int jm=date.getMonth();
        int jd=date.getDay()-1;
        
        int j_day_no = 365*jy + ((int)(jy/33)*8) + ((int)((jy%33+3)/4));
        int i;
        for (i=0; i < jm; ++i)
            j_day_no += j_days_in_month[i];
        j_day_no += jd;
        int g_day_no = j_day_no+79;
        int gy = 1600 + 400*((int)(g_day_no/146097)); /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
        g_day_no = g_day_no % 146097;
        boolean leap = true;
        if (g_day_no >= 36525) /* 36525 = 365*100 + 100/4 */
        {
            g_day_no--;
            gy += 100*((int)(g_day_no/36524)); /* 36524 = 365*100 + 100/4 - 100/100 */
            g_day_no = g_day_no % 36524;
            if (g_day_no >= 365)
            g_day_no++;
            else
            leap = false;
        }
        gy += 4*((int)(g_day_no/1461)); /* 1461 = 365*4 + 4/4 */
        g_day_no %= 1461;
        if (g_day_no >= 366) {
            leap = false;
            g_day_no--;
            gy += ((int)(g_day_no/365));
            g_day_no = g_day_no % 365;
        }
        for (i = 0; g_day_no >= g_days_in_month[i] +((i == 1 && leap)?1:0); i++)
            g_day_no -= g_days_in_month[i] + ((i == 1 && leap)?1:0);
        int m=i+1;
        int d=g_day_no+1;
        
        return new SimpleDate(gy, m-1, d);
    }
    public int getJalaliMonthLength(int year, int month){
        if(month != 11){
            return j_days_in_month[month];
        }else{
            // check for kabise
            return (((year-1387)%4)==0 ? 1 : 0) + j_days_in_month[month];
        }
    }
    
    public int getJalaliMonthLength(){
        return getJalaliMonthLength(get(YEAR), get(MONTH));
    }

    /** Fix JavaSE problem -- These method are not supported yet */
	public void add(int field, int amount) {
	}
	public void roll(int field, boolean up) {		
	}
	public int getMinimum(int field) {
		return 0;
	}
	public int getMaximum(int field) {
		return 0;
	}
	public int getGreatestMinimum(int field) {
		return 0;
	}
	public int getLeastMaximum(int field) {
		return 0;
	}

}
