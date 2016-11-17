/**
 * 
 */
package roms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * This is a singleton class.
 * 
 * The clock object holds current time, as set by the most recent input event.
 * 
 * Before the first input event the clock is set to 1 Jan 1970 00:00
 * 
 * Times are represented using java.util.Date objects.
 * 
 * The class assumes that elsewhere in the system the values of date objects 
 * are never mutated.
 * 
 * Various static utility methods are provided, including:
 * 
 * - Clock getInstance() 
 * - String format(Date) 
 * - Date parse(String)  
 * - int minutesBetween(Date,Date) 
 * 
 * The implementation of the tick() method should be completed so as to 
 * send out notification messages to any objects that need them.
 * 
 * @author pbj
 *
 */

public class Clock extends AbstractInputDevice {
         
    private static final DateFormat dateFormat;
    private static final Date startDate;
    private static final Date errorDate;
    private static Clock instance;
       
    private Date dateAndTime;
   
    static {  
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        dateFormat = new SimpleDateFormat("d HH:mm");

        startDate = new Date(0L); // 1 Jan 1970, 00:00
        
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(1999, 12, 31, 23, 59);
        errorDate = cal.getTime();   
         
    }
    
    private Clock() {
        super("c");  // Set instance name to "c"
        dateAndTime = startDate;
    }
    
    /** 
     * Create and initialise a new instance.
     * 
     * This should be called at the start of each test.
     */
    public static void initialiseInstance() {
        instance = new Clock();        
    }
    
    public static Clock getInstance() {
        return instance;
    }
     
   
    /**
     * Set date and time. 
     * 
     * @param d
     */
    public void setDateAndTime(Date d) {
        dateAndTime = d;
        
    }
    
    /**
     * Get current simulation date and time as a Date object
     *  
     * @return
     */
    public Date getDateAndTime() {
        return dateAndTime;
    }
   
    
    /**
     * Select device action based on input event message
     *   
     * @param e
     */
    @Override
    public void receiveEvent(Event e) {
        if (e.getMessageName().equals("tick") 
                && e.getMessageArgs().size() == 0) {
            
            tick();
            
        } else {
            super.receiveEvent(e);;
        }
    }
    
     /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     *  
     * Add private field(s) for associations to classes that Clock 
     * objects need to send messages to. 
     * 
     * Add public setters for these fields.
     * 
     * Complete the methods for handling trigger input events.
     * 
     */
    
    /*
     * FIELD(S) AND SETTER(S) FOR MESSAGE DESTINATIONS
     */
    
    /*
     * SUPPORT FOR TRIGGER INPUT MESSAGES
     */

    public void tick() {
        logger.fine(getInstanceName());
        
        // TO BE COMPLETED
        //  E.g. add calls to objects that need to be notified of ticks.
        
    }
    
    /*
     * Put all class modifications above.
     **********************************************************************
     * END MODIFICATION AREA
     *********************************************************************
     */  
 
    /*
     * UTILITY METHODS RELATING TO Date CLASS
     */

    /**
     * Pretty print dates in days, hours and minutes.
     * E.g.   "3 17:55"
     * 
     * @param d
     * @return
     */
    public static String format(Date d) {
        return dateFormat.format(d);
    }
 
    /**
     * Parse a date string in the coursework format (i.e. date strings 
     * of form "3 17:55".
     * 
     * @param s
     * @return
     */
    public static Date parse(String s) {
        // Catch exception and return error value to avoid lots of 
        // throws clauses for ParseException
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {     
            return errorDate; 
        }
    }
    
     /**
     * Compute number of minutes startDate is earlier than endDate
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int minutesBetween(Date startDate, Date endDate) {
        long minutes = 
                (endDate.getTime() - startDate.getTime()) / (1000 * 60);
        return (int) minutes;
    }
    public static Date getStartDate() {
        return startDate;
    }
    
    public static Date getErrorDate() {
        return errorDate;
    }
    
    
}
