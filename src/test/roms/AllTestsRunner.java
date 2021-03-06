package roms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static roms.LoggerUtil.logger;

@RunWith(Parameterized.class)
public class AllTestsRunner {
    private final String LS = System.lineSeparator();
    private final PrintStream defaultOut = System.out;
    private final PrintStream defaultErr = System.err;
    private final ByteArrayOutputStream outCapture = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errCapture = new ByteArrayOutputStream();

    private String filenameRoot;

    public AllTestsRunner(String filenameRoot) {
        this.filenameRoot = "data/" + filenameRoot;
    }

    @Parameterized.Parameters(name="{0}")
    public static Collection<String> testFiles() {
        return Arrays.stream(new File("data/").list((dir, name) -> name.endsWith(".in.txt"))).map(s -> s.replace(".in.txt","")).collect(Collectors.toList());
    }

    @Before
    public void makeBanner(){
        logger.info(LS
                + "#############################################################" + LS
                + "RUNNING TEST FILE : " + filenameRoot + LS
                + "#############################################################");
    }

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

    private boolean checkAsserts() {
        String output = outCapture.toString();
        assertTrue(output.contains("ACTUAL EVENTS AS EXPECTED"));
        assertFalse(output.contains("ACTUAL EVENTS NOT AS EXPECTED"));
        assertFalse(output.contains("ERROR EXCEPTION THROWN"));
        assertFalse(output.contains("EXCEPTION THROWN"));
        return true;
    }

    @Test
    public void runTestFromFiles(){
        AllTests.runTestFromFiles(filenameRoot);
        assertTrue(checkAsserts());
    }

//    @Test
//    public void addShowMenuTest(){
//        AllTests.runTestFromFiles("data/addShowMenu");
//        assertTrue(checkAsserts());
//    }
//
//    @Test
//    public void addShowTicketTest() {
//        AllTests.runTestFromFiles("data/addShowTicket");
//        assertTrue(checkAsserts());
//    }
//
//    @Test
//    public void addTicketPayBillTest() {
//        AllTests.runTestFromFiles("data/addTicketPayBill");
//        assertTrue(checkAsserts());
//    }
//
//    @Test
//    public void addShowTicketsShowRackTest() {
//        AllTests.runTestFromFiles("data/addTicketsShowRack");
//        assertTrue(checkAsserts());
//    }
//
//    @Test
//    public void cancelReadyUpLightTest() {
//        AllTests.runTestFromFiles("data/cancelReadyUpLight");
//        assertTrue(checkAsserts());
//    }
}
