Simple implementation of Jalali calendar for Java.

This class had implemented "java.util.Calendar" abstract class, so you can use it in all Java applications simply.

```java
final JalaliCalendar c = new JalaliCalendar();

c.setTime(new Date());
System.out.println("Year: " + c.get(Calendar.YEAR));
System.out.println("Month: " + c.get(Calendar.MONTH));
System.out.println("Day: " + c.get(Calendar.DAY_OF_MONTH));
```
