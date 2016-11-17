/**
 * 
 */
package roms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author pbj
 *
 */


@RunWith(Suite.class)
@SuiteClasses({ MoneyTest.class, SystemTest.class })
public class AllTests {

    /**
     * Main method.
     * 
     * 3 modes:
     * 
     * If run with no arguments, it runs all the JUnit classes in the test suite
     * defined above.
     * 
     * If run with one argument foo, then it runs a test with input from
     * file foo.in.txt and expected output from file foo.expected. 
     * 
     * If run with two arguments: -nc foo ("nc" for "no checks")
     * runs a test with input from file foo.in.txt and writes the output
     * to the standard output stream. 
     * 
     * All logging information is written to standard error, so it is
     * easy to separate the two streams.
     * 
     * @param args
     */
    public static void main(String[] args) {
        
       if (args.length == 0){
           runJUnitTests();
         } else if (args.length == 1) {
           runTestFromFiles(args[0]);
        } else if (args.length == 2 && args[0].equals("-nc")) {
           runTestFromFileWithoutCheck(args[1]);
        } else {
            System.err.println("Unrecognised arguments");
        }
    }
    /**
     * 
     */
    public static void runJUnitTests() {

        Result result = JUnitCore.runClasses(AllTests.class);
        System.out.println("TEST RESULTS");
        System.out.println("Number of tests run: " + result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.println("ALL TESTS PASSED");
        } else {
            System.out.println("SOME TESTS FAILED");
            System.out.println("Number of failed tests: " + result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }  
        } 
        
    }
    /**
     * Run test reading input events and expected output events from files.
     * 
     * Input event file is fileNameRoot.in.txt.
     * Expected output file is fileNameRoot.expected.txt
     * 
     * Catches all exceptions and errors.
     * Summary written to stdout
     * Details written to stderr
     * 
     * @param fileNameRoot
     */
    public static void runTestFromFiles(String fileNameRoot) {
        String inputEventsFileName = fileNameRoot + ".in.txt";
        String expectedOutputEventsFileName = fileNameRoot + ".expected.txt";
        
        List<Event> inputEvents = readEventsFromFile(inputEventsFileName);
        List<Event> expectedOutputEvents = 
                readEventsFromFile(expectedOutputEventsFileName);
        
        if (inputEvents == null || expectedOutputEvents == null) {
            return;
        }
        

        SystemTest test = new SystemTest();
        test.runTestWithEvents(inputEvents, expectedOutputEvents);
       
     }
     /**
      * Run test, reading input events from file and writing output events
      * to stdout.  Does not do any checks.
      * 
      * Assumes tests are good - that none throw exceptions.
      * 
      * Input event file is fileNameRoot.in.txt
      * 
      * @param fileNameRoot
      */
    public static void runTestFromFileWithoutCheck(String fileNameRoot) {
        String inputEventsFileName = fileNameRoot + ".in.txt";
        
        List<Event> inputEvents = readEventsFromFile(inputEventsFileName);
        
        if (inputEvents == null) {
            return;
        }
        
       
        SystemTest test = new SystemTest();
        test.runTestWithEventsWithoutCheck(inputEvents);
    }

    /**
     * Read events from a file
     * 
     * Expects events in their print format, each terminated by a ";".
     * See Event.java for further information on their print format.
     * 
     * Returns null if there is some problem with the read.
     * 
     * @param fileName
     * @return
     */
    public static List<Event> readEventsFromFile(String fileName) {

        StringBuffer sb = new StringBuffer();
        
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null) {
                sb.append(line);
                line = in.readLine();
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Problem reading file " + fileName);
            System.err.println(e.getMessage());
            return null;
        } 
        
        String fileContents = sb.toString();
        // -1 arg to split ensures eventStrings always contains string
        // for after the last ";". 
        String[] eventStrings = fileContents.split(";",-1);
        int numEvents = eventStrings.length;
        if (eventStrings.length > 0) {
            // Ignore whitespace string from after last ";". 
            numEvents--; 
        }
        
        List<Event> events = new ArrayList<Event>();

        for (int i = 0; i < numEvents; i++) {
            events.add(new Event(eventStrings[i]));
        }
        return events;
      
    }
    
    
}
