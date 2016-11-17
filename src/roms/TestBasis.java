/**
 * 
 */
package roms;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author pbj
 *
 */
public abstract class TestBasis {
    protected static final String LS = System.getProperty("line.separator");
    protected static Logger logger;
    
    protected EventDistributor distributor;
    protected EventCollector collector;
    
    private List<Event> inputEvents;
    private List<Event> expectedOutputEvents;
    private List<Event> actualOutputEvents;
 
    /*
     * ===================================================================
     * TEST SETUP
     * ===================================================================
     */
    
    /**
     * Initialise logging framework so all log records FINER and above
     * are reported.
     * 
     */
    @BeforeClass
    public static void setupLogger() {
         
        // Enable log record filtering at FINER level.
        logger = Logger.getLogger("roms"); 
        logger.setLevel(Level.FINER);
        
        Logger rootLogger = Logger.getLogger("");
        Handler handler = rootLogger.getHandlers()[0];
        handler.setLevel(Level.FINER);
    }
    
    /**
     * Initialise event framework objects   
     */
    protected void setupTestEnvironment() {
 
        distributor = new EventDistributor();
        collector = new EventCollector(); 
        
        inputEvents = new ArrayList<Event>();
        expectedOutputEvents = new ArrayList<Event>();
        
    }

    /** 
     * Create implementation objects and link them both to each other and 
     * to the event distributor and the event collector.
     * 
     * Defined in SystemTest subclass.
     * 
     */
    abstract protected void setupSystem();

 
    /**
     * Set up test environment and starting system configuration.
     */
    @Before
    public void setupTestEnvAndSystem() {
       
        setupTestEnvironment();
        setupSystem();
    }

   
    /*
     * ===================================================================
     * UTILITY METHODS FOR USE IN TEST METHODS
     * ===================================================================
     */
 
    /** 
     * Make banner text for including in test log
     */
    
    protected String makeBanner(String testCaseName) {
        return  LS 
          + "#############################################################" + LS
          + "TESTCASE: " + testCaseName + LS
          + "#############################################################";
    }
     
    /**
     * Specify an input event to drive in.
     * 
     * For use in test methods.
     * 
     * @param inputEventString
     */
    protected void input(String inputEventString) {
        inputEvents.add(new Event(inputEventString));
    }
    
    /**
     * Specify an expected output event.
     * 
     * For use in test methods. .
     * 
     * Relies on test object field expectedOutputEvents for passing
     * argument output event to checking method. 
     * 
     * @param outputEventString
     */
    protected void expect(String outputEventString) {
        expectedOutputEvents.add(new Event(outputEventString));
    }
    
    /**
     * Save input and expected events.
     * 
     * Insert into test methods before running test
     * 
     * @param filePrefix
     */
    protected void saveEvents(String filePrefix) {
        writeEventsToFile(filePrefix + ".in.txt", inputEvents);
        writeEventsToFile(filePrefix + ".expected.txt", expectedOutputEvents);
    }
 
    /*
     * ===================================================================
     * AUXILIARY METHODS SUPPORTING ABOVE UTILITY METHODS FOR TEST METHODS
     * ===================================================================
     */
    
    /**
     * Write events to fileName.
     * 
     * @param fileName
     * @param events
     */
    private static void writeEventsToFile(String fileName, List<Event> events) {
        try {
            File f = new File(fileName);
            PrintWriter out = new PrintWriter(new FileWriter(f));
            for (Event e : events) {
                out.print(e.toString());
                out.println(";");
            }           
            out.close();
        } catch (IOException e) {
            System.err.println("Problem writing file " + fileName);
            System.err.println(e.getMessage());
            return;
        }
        return;
    }
    


    /*
     * ===================================================================
     * MAIN METHODS FOR RUNNING TESTS
     * ===================================================================
     */
    
     /**
     * Run test and check results. 
     * 
     * Can be called both at end of JUnit test methods and directly.
     * 
     * Run this after input events have been loaded into event queue in 
     * event distributor and expected output events have been loaded into
     * expectedOutputEvents field.
     * 
     * If called directly, not via JUnit runner, the AssertionError, thrown
     * when some assertion fails, should be caught.
     */ 
 
    public void runAndCheck() {
        run();
        checkTestResults();
    } 
    
    /**
     * Log input and expected output events, inject input events into system,
     * and collect actual output events.
     */
    private void run() {
        StringBuilder sb = new StringBuilder();
        sb.append(LS);
        sb.append("Input events:");
        sb.append(LS);
        for (Event e : inputEvents) {
            sb.append(e);
            sb.append(";");
            sb.append(LS);
        }
        sb.append(LS);
        sb.append("Expected output events:");
        sb.append(LS);
        for (Event e : expectedOutputEvents) {
            sb.append(e);
            sb.append(";");
            sb.append(LS);
        }
        logger.info(sb.toString());
        
        
        enqueueInputEvents(inputEvents);
        distributor.sendEvents();
        actualOutputEvents = collector.fetchEvents();
        
    }
       
   
    /**
     * Compare expected and actual output events.  
     * 
     * Uses Event.listEqual() to do the comparison.  This not the same as
     * the normal list equality. 
     * 
     * @see Event
     * 
     * @param expectedOutputEvents
     * @param actualOutputEvents
     */
    public void checkTestResults() {
            
        // Log output event sequences for easy comparison when different.

        
        StringBuilder sb = new StringBuilder();
        sb.append(LS);
        sb.append("Expected output events:");
        sb.append(LS);
        for (Event e : expectedOutputEvents) {
            sb.append(e);
            sb.append(";");
            sb.append(LS);
        }
        sb.append(LS);
        sb.append("Actual output events:");
        sb.append(LS);
        for (Event e : actualOutputEvents) {
            sb.append(e);
            sb.append(";");
            sb.append(LS);
        }
        
        int testResultsfirstDiff = Event.listFirstDiff(expectedOutputEvents,actualOutputEvents);
        boolean testResultsGood = (testResultsfirstDiff == -1);
        
        if (testResultsGood) {
            sb.insert(0, LS);
            sb.insert(0, "EVENT LISTS EQUAL");
            logger.info(sb.toString());
        } else  {
            if (expectedOutputEvents.size() > 0 
                    && actualOutputEvents.size() > 0
                    && expectedOutputEvents.get(expectedOutputEvents.size() - 1).equals(
                            actualOutputEvents.get(actualOutputEvents.size() - 1))) {
                
                sb.insert(0, LS);
                sb.insert(0, "EVENTS LISTS ENDS EQUAL");
            }
            sb.insert(0,  LS);
            sb.insert(0,  "EVENT LISTS DIFFER AT POSITION " + (testResultsfirstDiff + 1));
            logger.warning(sb.toString());
        }
        //
        assertEquals(expectedOutputEvents, actualOutputEvents);
                      
    }

    
    
        
    /**
     * Run test using provided events.
     * 
     * Bypasses JUnit, calling test sequencing methods directly.
     * 
     * @param inEvents
     * @param expectedEvents
     */
    public void runTestWithEvents(
            List<Event> inEvents, 
            List<Event> expectedEvents) {
        
       setupLogger();
       setupTestEnvAndSystem();
       setInputEvents(inEvents);
       setExpectedOutputEvents(expectedEvents);
       try {
           runAndCheck();
           System.out.println("ACTUAL EVENTS AS EXPECTED");
           
       } catch (AssertionError e) {
           System.out.println("ACTUAL EVENTS NOT AS EXPECTED");
           System.out.println(e.getMessage());
           
       } catch (Error e) {
           // Don't print message.  If Stack overflow can be very long.
           System.out.println("ERROR EXCEPTION THROWN");
           System.out.println(e.toString()); // To stdout
           e.printStackTrace(); // To stderr
           
       } catch (Exception e) {
           System.out.println("EXCEPTION THROWN");
           e.printStackTrace(System.out); // To stdout
           e.printStackTrace(); // To stderr
       }
       return;
    }        
    
    /**
     * Run test with provided events.
     * Bypasses JUnit, calling test sequencing methods directly.
     * Expects no exceptions thrown.
     * Does no checks.  Actual output events written to standard out.
     * 
     * @param inEvents
     */
    public void runTestWithEventsWithoutCheck(List<Event> inEvents) {

        setupLogger();
        setupTestEnvAndSystem();
        setInputEvents(inEvents);
        run();
        for (Event e : actualOutputEvents) {
            System.out.print(e);
            System.out.println(";");
        }
     }

    /*
     * ===================================================================
     * AUXILIARY METHODS FOR RUNNING TESTS
     * ===================================================================
     */
    
    /**
     * Queue up input events at event distributor.
     * 
     * Intended for calling from other classes, when input events are
     * read from a file, for example.
     * 
     * @param es input events
     */
    public void enqueueInputEvents(List<Event> es) {
        for (Event e : es) {
            distributor.enqueue(e);
        }
    }
    
    
    /**
     * Set expected output events.  These are compared with actual 
     * output events after a test is run.
     * 
     * Intended for calling from other classes, when input events are
     * read from a file, for example.
     *
     * @param es expected output events
     */
    public void setExpectedOutputEvents(List<Event> es) {
        expectedOutputEvents = es;
    }
    
    public void setInputEvents(List<Event> es) {
        inputEvents = es;
    }
    
}
