package roms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllTestsRunner {
    private final PrintStream defaultOut = System.out;
    private final PrintStream defaultErr = System.err;
    private final ByteArrayOutputStream outCapture = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errCapture = new ByteArrayOutputStream();

    @Before
    public void teeStreams() {
        System.setOut(new PrintStream(outCapture));
        System.setErr(new PrintStream(errCapture));
    }

    @After
    public void unteeStreams() {
        defaultOut.print(outCapture.toString());
        defaultErr.print(errCapture.toString());
        System.setOut(defaultOut);
        System.setErr(defaultErr);
    }

    public void checkAsserts() {
        String output = outCapture.toString();
        assertTrue(output.contains("ACTUAL EVENTS AS EXPECTED"));
        assertFalse(output.contains("ACTUAL EVENTS NOT AS EXPECTED"));
        assertFalse(output.contains("ERROR EXCEPTION THROWN"));
        assertFalse(output.contains("EXCEPTION THROWN"));
    }

    @Test
    public void addShowMenuTest(){
        AllTests.runTestFromFiles("data/addShowMenu");
        checkAsserts();
    }

    @Test
    public void addShowTicketTest() {
        AllTests.runTestFromFiles("data/addShowTicket");
        checkAsserts();
    }

    @Test
    public void addTicketPayBillTest() {
        AllTests.runTestFromFiles("data/addTicketPayBill");
        checkAsserts();
    }

    @Test
    public void addShowTicketsShowRackTest() {
        AllTests.runTestFromFiles("data/addTicketsShowRack");
        checkAsserts();
    }

    @Test
    public void cancelReadyUpLightTest() {
        AllTests.runTestFromFiles("data/cancelReadyUpLight");
        checkAsserts();
    }
}
