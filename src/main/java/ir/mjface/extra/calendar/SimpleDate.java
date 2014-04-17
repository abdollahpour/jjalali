/*
 * Copyright (c) 2009-2010, MJFace project
 * Hamed Abdollahpoor
 * http://www.mjface.com
 */

package ir.mjface.extra.calendar;

/**
 * This class is usefull in date base conversions
 * @author Hamed Abdollahpoor
 * @version 0.5
 */
class SimpleDate {
    
    private int year;
    private int month;
    private int day;
    
    public SimpleDate(){
        
    }
    
    public SimpleDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public void setYear(int year){
        this.year = year;
    }
    
    public int getYear(){
        return year;
    }
    
    public void setMonth(int month){
        this.month = month;
    }
    
    public int getMonth(){
        return month;
    }
    
    public void setDay(int day){
        this.day = day;
    }
    
    public int getDay(){
        return day;
    }
    
}
