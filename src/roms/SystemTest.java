/**
 *
 */
package roms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pbj
 */
public class SystemTest extends TestBasis {   

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for your system below.
     */

    /*
        "Include all your tests as JUnit test methods in the SystemTest class. Feel free to add
        extra auxiliary methods that handle repeatedly needed sequences of input and output events;
        avoid cut and pasting the same sequences multiple times."

        Tests are self contained, and setting up of system state is done separately for each test
        case. There may be portions of duplicated code for initializing the Menu for example.

        This is done so that tests will be independent of each other, and the expected output of other
        tests will not need to be changed should a single menu item be changed for another test.
     */
    /*
        BEGIN PHASE 1 TESTS
     */

    /**
     * Checks show menu capability of office KVM.
     */
    @Test
    public void showOfficeMenuTest() {
        logger.info(makeBanner("showOfficeMenuTest"));
        input("1 19:15, OfficeKVM, okvm, showMenu");
        expect("1 19:15, OfficeKVM, okvm, viewMenu, tuples, 3, \n" +
                "    ID, Description, Price,");
        runAndCheck();
    }

    /**
     * Checks that items can be added from menu and retrieved.
     */
    @Test
    public void addToMenuTest() {
        logger.info(makeBanner("addToMenuTest"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        input("1 19:15, OfficeKVM, okvm, showMenu");
        expect("1 19:15, OfficeKVM, okvm, viewMenu, tuples, 3, \n" +
                "    ID, Description, Price, \n" +
                "    D1,  Soft Drink,  1.50, \n" +
                "    D2,        Wine,  3.25, \n" +
                "    M1,        Fish,  7.95, \n" +
                "    M2,   Veg Chili,  6.70");
        // overriding existing menu item
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 4.25");
        input("1 19:15, OfficeKVM, okvm, showMenu");
        expect("1 19:15, OfficeKVM, okvm, viewMenu, tuples, 3, \n" +
                "    ID, Description, Price, \n" +
                "    D1,  Soft Drink,  1.50, \n" +
                "    D2,        Wine,  4.25, \n" +
                "    M1,        Fish,  7.95, \n" +
                "    M2,   Veg Chili,  6.70");

        runAndCheck();
    }

    /**
     * Checks that items in menu can be removed and menu is updated.
     */
    @Test
    public void removeFromMenuTest() {
        logger.info(makeBanner("removeFromMenuTest"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        input("1 12:00, OfficeKVM, okvm, removeFromMenu, D2 ");
        input("1 19:15, OfficeKVM, okvm, showMenu");
        expect("1 19:15, OfficeKVM, okvm, viewMenu, tuples, 3, \n" +
                "    ID, Description, Price, \n" +
                "    D1,  Soft Drink,  1.50, \n" +
                "    M1,        Fish,  7.95, \n" +
                "    M2,   Veg Chili,  6.70");
        runAndCheck();
    }

    /**
     * Complete sanity test of ALL PHASE 1 requirements.
     */
    @Test
    public void phase1CompleteSanityTest() {
        logger.info(makeBanner("phase1CompleteSanityTest"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        input("1 19:15, OfficeKVM, okvm, showMenu");
        expect("1 19:15, OfficeKVM, okvm, viewMenu, tuples, 3,\n" +
                "    ID, Description, Price,\n" +
                "    D1,  Soft Drink,  1.50,\n" +
                "    D2,        Wine,  3.25,\n" +
                "    M1,        Fish,  7.95,\n" +
                "    M2,   Veg Chili,  6.70");
        input("1 19:15, OfficeKVM, okvm, removeFromMenu, M2");
        input("1 19:15, OfficeKVM, okvm, showMenu");
        expect("1 19:15, OfficeKVM, okvm, viewMenu, tuples, 3,\n" +
                "    ID, Description, Price,\n" +
                "    D1,  Soft Drink,  1.50,\n" +
                "    D2,        Wine,  3.25,\n" +
                "    M1,        Fish,  7.95");
        input("1 20:00, OfficeKVM, okvm, addToMenu, D2, Wine, 4.25");
        input("1 20:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 20:00, OfficeKVM, okvm, showMenu");
        expect("1 20:00, OfficeKVM, okvm, viewMenu, tuples, 3,\n" +
                "    ID, Description, Price,\n" +
                "    D1,  Soft Drink,  1.50,\n" +
                "    D2,        Wine,  4.25,\n" +
                "    M1,        Fish,  7.95,\n" +
                "    M2,   Veg Chili,  6.70");
        runAndCheck();
    }

    /**
     * Tests AssertionError thrown when assert fails, ref Section 3.4
     */
    @Test(expected = AssertionError.class)
    public void testAssertionError() {
        Menu menu = new Menu();
        menu.removeMenuItem("invalid");
    }

    /*
        END PHASE 1 TESTS
     */

    /*
        BEGIN PHASE 2 TESTS
     */

    /**
     * Test that order can be started.
     * If this fails, all other PHASE 2 tests MUST FAIL.
     */
    @Test
    public void startOrderTest() {
        logger.info(makeBanner("startOrderTest"));
        input("1 20:00, TableDisplay, td1, startOrder");
        runAndCheck();
    }

    /**
     * Test that table can display menu
     */
    @Test
    public void showTableMenuTest() {
        logger.info(makeBanner("showTableMenuTest"));
        // Set up menu from office
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        // End Setup
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:00, TableDisplay, td1, showMenu");
        expect("1 20:00, TableDisplay, td1, viewMenu, tuples, 3,\n" +
                "    ID, Description, Price,\n" +
                "    D1,  Soft Drink,  1.50,\n" +
                "    D2,        Wine,  3.25,\n" +
                "    M1,        Fish,  7.95,\n" +
                "    M2,   Veg Chili,  6.70");
        runAndCheck();
    }

    /**
     * Check that table can display order ticket.
     * <p>
     * Required to test next 3.
     */
    @Test
    public void showTicketTest() {
        logger.info(makeBanner("showTicketTest"));
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3,\n" +
                "    ID, Description, Count");
        runAndCheck();
    }

    @Test
    public void addToTicketTest() {
        logger.info(makeBanner("addToTicketTest"));
        // Set up menu from office
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        // End Setup
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3,\n" +
                "    ID, Description, Count,\n" +
                "    D1,  Soft Drink,     3,\n" +
                "    D2,        Wine,     1,\n" +
                "    M1,        Fish,     2,\n" +
                "    M2,   Veg Chili,     1");
        runAndCheck();
    }

    /**
     * Tests both cases when quantity = 1 and quantity > 1.
     */
    @Test
    public void removeFromTicketTest() {
        logger.info(makeBanner("removeFromTicketTest"));
        // Set up menu from office
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        // End Setup
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3,\n" +
                "    ID, Description, Count,\n" +
                "    D1,  Soft Drink,     3,\n" +
                "    D2,  Wine,     1,\n" +
                "    M1,        Fish,     2,\n" +
                "    M2,   Veg Chili,     1");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D2");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D1");
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3,\n" +
                "    ID, Description, Count,\n" +
                "    D1,  Soft Drink,     2,\n" +
                "    M1,        Fish,     2,\n" +
                "    M2,   Veg Chili,     1");
        runAndCheck();
    }

    /**
     * Checks that order can be submitted.
     * <p>
     * Verification that order submitted is correct will be checked in {@link #showRackTest()}.
     */
    @Test
    public void submitOrderTest() {
        logger.info(makeBanner("submitOrderTest"));
        // Set up menu from office
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        // End Setup
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:20, TableDisplay, td1, submitOrder");
        runAndCheck();
    }

    /**
     * Check bill payment
     */
    @Test
    public void payBillTest() {
        logger.info(makeBanner("payBillTest"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:20, TableDisplay, td1, submitOrder");
        input("1 21:30, TableDisplay, td1, payBill");
        expect("1 21:30, TableDisplay, td1, approveBill, Total:, 1.50");
        input("1 21:32, CardReader, cr1, acceptCardDetails, XYZ1234");
        expect("1 21:32, BankClient, bc, makePayment, XYZ1234, 1.50");
        input("1 21:33, BankClient, bc, acceptAuthorisationCode, ABCD");
        expect("1 21:33, ReceiptPrinter, rp1, takeReceipt, Total:, 1.50, AuthCode:, ABCD");
        runAndCheck();
    }

    /**
     * Complete sanity test of ALL PHASE 2 requirements.
     */
    @Test
    public void phase2CompleteSanityTest() {
        logger.info(makeBanner("phase2CompleteSanityTest"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:00, TableDisplay, td1, showMenu");
        expect("1 20:00, TableDisplay, td1, viewMenu, tuples, 3,\n" +
                "    ID, Description, Price,\n" +
                "    D1,  Soft Drink,  1.50,\n" +
                "    D2,        Wine,  3.25,\n" +
                "    M1,        Fish,  7.95,\n" +
                "    M2,   Veg Chili,  6.70");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D2");
        input("1 20:01, TableDisplay, td1, showTicket");
        expect("1 20:01, TableDisplay, td1, viewTicket, tuples, 3,\n" +
                "    ID, Description, Count,\n" +
                "    D1,  Soft Drink,     3,\n" +
                "    M1,        Fish,     2,\n" +
                "    M2,   Veg Chili,     1");
        input("1 20:20, TableDisplay, td1, submitOrder");
        input("1 21:30, TableDisplay, td1, payBill");
        expect("1 21:30, TableDisplay, td1, approveBill, Total:, 27.10");
        input("1 21:32, CardReader, cr1, acceptCardDetails, XYZ1234");
        expect("1 21:32, BankClient, bc, makePayment, XYZ1234, 27.10");
        input("1 21:33, BankClient, bc, acceptAuthorisationCode, ABCD");
        expect("1 21:33, ReceiptPrinter, rp1, takeReceipt, Total:, 27.10, AuthCode:, ABCD");
        runAndCheck();
    }
    /*
        END PHASE 2 TESTS
     */

    /*
        BEGIN PHASE 3 TESTS
     */
    @Test
    public void showRackTest() {
        logger.info(makeBanner("showRackTest"));
        // Set up menu from office
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        // End Setup
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:20, TableDisplay, td1, submitOrder");
        input("1 20:21, Clock, c, tick");
        expect("1 20:21, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready,\n" +
                "      1,       1,     D1,  Soft Drink,        3,      0,\n" +
                "      1,       1,     M1,        Fish,        2,      0,\n" +
                "      1,       1,     M2,   Veg Chili,        1,      0,\n");
        runAndCheck();

    }

    /**
     * Check indicating item ready works.
     * <p>
     * Check that on first item in ticket, a ticket is printed,
     * on last item, ready up light is switched on and ticket is removed from rack,
     * and kitchen display shows correct fulfilled quantity when refreshed.
     */
    @Test
    public void indicateItemReadyTest() {
        logger.info(makeBanner("indicateItemReadyTest"));
        // Set up menu from office
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        // End Setup
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:20, TableDisplay, td1, submitOrder");
        input("1 20:21, Clock, c, tick");
        expect("1 20:21, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready,\n" +
                "      1,       1,     D1,  Soft Drink,        3,      0,\n");
        input("1 20:23, KitchenDisplay, kd, itemReady, 1, D1");
        //print ticket on first ready
        expect("1 20:23, TicketPrinter, tp, takeTicket, tuples, 3,\n" +
                "    Table:,       Tab-1,     _,\n" +
                "        ID, Description, Count,\n" +
                "        D1,  Soft Drink,     3");
        input("1 20:21, Clock, c, tick");
        expect("1 20:21, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready,\n" +
                "      1,       1,     D1,  Soft Drink,        3,      1,\n");
        input("1 20:23, KitchenDisplay, kd, itemReady, 1, D1");
        input("1 20:24, KitchenDisplay, kd, itemReady, 1, D1");
        //switch on ready up light on last ready
        expect("1 20:24, PassLight, pl, viewSwitchedOn");
        input("1 20:21, Clock, c, tick");
        //ticket should be removed when all items ready
        expect("1 20:21, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready");
        runAndCheck();


    }

    @Test
    public void cancelReadyUpLight() {
        logger.info(makeBanner("cancelReadyUpLight"));
        input("1 18:00, PassButton, pb, press");
        expect("1 18:00, PassLight, pl, viewSwitchedOff");
        runAndCheck();
    }

    /**
     * Complete sanity test of ALL PHASE 3 requirements.
     */
    @Test
    public void phase3CompleteSanityTest() {
        logger.info(makeBanner("phase3CompleteSanityTest"));
        input("1 12:00, OfficeKVM, okvm, addToMenu, M1, Fish, 7.95");
        input("1 12:00, OfficeKVM, okvm, addToMenu, M2, Veg Chili, 6.70");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D1, Soft Drink, 1.50");
        input("1 12:00, OfficeKVM, okvm, addToMenu, D2, Wine, 3.25");
        input("1 20:00, TableDisplay, td1, startOrder");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, M2");
        input("1 20:01, TableDisplay, td1, addMenuItem, D2");
        input("1 20:01, TableDisplay, td1, addMenuItem, M1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, addMenuItem, D1");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D2");
        input("1 20:01, TableDisplay, td1, removeMenuItem, D2");
        input("1 20:02, TableDisplay, td1, submitOrder");
        input("1 20:10, TableDisplay, td2, startOrder");
        input("1 20:11, TableDisplay, td2, addMenuItem, M1");
        input("1 20:11, TableDisplay, td2, addMenuItem, M1");
        input("1 20:11, TableDisplay, td2, addMenuItem, D2");
        input("1 20:12, TableDisplay, td2, submitOrder");
        input("1 20:21, Clock, c, tick");
        expect("1 20:21, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready,\n" +
                "      19,       1,     D1,  Soft Drink,        3,      0,\n" +
                "      19,       1,     M1,        Fish,        2,      0,\n" +
                "      19,       1,     M2,   Veg Chili,        1,      0,\n" +
                "       9,       2,     D2,        Wine,        1,      0,\n" +
                "       9,       2,     M1,        Fish,        2,      0");
        input("1 20:23, KitchenDisplay, kd, itemReady, 1, D1");
        expect("1 20:23, TicketPrinter, tp, takeTicket, tuples, 3,\n" +
                "    Table:,       Tab-1,     _,\n" +
                "        ID, Description, Count,\n" +
                "        D1,  Soft Drink,     3,\n" +
                "        M1,        Fish,     2,\n" +
                "        M2,   Veg Chili,     1");
        input("1 20:23, KitchenDisplay, kd, itemReady, 1, D1");
        input("1 20:23, Clock, c, tick");
        expect("1 20:23, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready,\n" +
                "      21,       1,     D1,  Soft Drink,        3,      2,\n" +
                "      21,       1,     M1,        Fish,        2,      0,\n" +
                "      21,       1,     M2,   Veg Chili,        1,      0,\n" +
                "      11,       2,     D2,        Wine,        1,      0,\n" +
                "      11,       2,     M1,        Fish,        2,      0");
        input("1 20:24, KitchenDisplay, kd, itemReady, 1, D1");
        input("1 20:24, KitchenDisplay, kd, itemReady, 1, M1");
        input("1 20:24, KitchenDisplay, kd, itemReady, 1, M1");
        input("1 20:24, KitchenDisplay, kd, itemReady, 1, M2");
        expect("1 20:24, PassLight, pl, viewSwitchedOn");
        input("1 20:24, KitchenDisplay, kd, itemReady, 2, M1");
        expect("1 20:24, TicketPrinter, tp, takeTicket, tuples, 3,\n" +
                "    Table:,       Tab-2,     _,\n" +
                "        ID, Description, Count,\n" +
                "        D2,        Wine,     1,\n" +
                "        M1,        Fish,     2");
        input("1 20:24, Clock, c, tick");
        expect("1 20:24, KitchenDisplay, kd, viewRack, tuples, 6,\n" +
                "    Time, Ticket#, MenuID, Description, #Ordered, #Ready,\n" +
                "      12,       2,     D2,        Wine,        1,      0,\n" +
                "      12,       2,     M1,        Fish,        2,      1");
        runAndCheck();
    }
    /*
        END PHASE 3 TESTS
     */


   /*
    * Put all your JUnit system-level tests above.
    ***********************************************************************
    * END MODIFICATION AREA
    ***********************************************************************
    */


    /**
     * Set up system to be tested and connect interface devices to
     * event distributor and collector.
     * <p>
     * Setup is divided into two parts:
     * <p>
     * 1. Where IO device objects are created and connected to event
     * distributor and event collector.
     * <p>
     * 2. Where core system implementation objects are created and
     * links are added between all the implementation objects and
     * the IO device objects.
     * <p>
     * The first part configures sufficient IO device objects for all 3
     * implementation phases defined in the Coursework 3 handout.  It should
     * not be touched.
     * <p>
     * The second part here only gives the configuration for the demonstration
     * design.  It has to be modified for the system implementation.
     */
    @Override
    protected void setupSystem() {

        /* 
         * PART 1: 
         * - IO Device creation.  
         * - Establishing links between IO device objects and test framework objects.
         */
        
        
        /*
         * IO DEVICES FOR KITCHEN AREA
         * 
         * Create IO objects for kitchen.
         * Connect them to the event distributor and event collector.
         */

        KitchenDisplay display = new KitchenDisplay("kd");
        display.addDistributorLinks(distributor);
        display.setCollector(collector);

        Clock.initialiseInstance();
        Clock.getInstance().addDistributorLinks(distributor);

        TicketPrinter printer = new TicketPrinter("tp");
        printer.setCollector(collector);
      
        /* 
         * IO DEVICES FOR PASS
         */

        /*
         * Create IO objects for PASS
         * Connect them to the event distributor and event collector.
         */

        PassLight light = new PassLight("pl");
        light.setCollector(collector);

        PassButton button = new PassButton("pb");
        button.addDistributorLinks(distributor);


        // IO DEVICES FOR OFFICE

        // Create IO objects for office.
        // Connect them to the event distributor and event collector.

        OfficeKVM officeKVM = new OfficeKVM("okvm");
        officeKVM.addDistributorLinks(distributor);
        officeKVM.setCollector(collector);

        BankClient bankClient = new BankClient("bc");
        bankClient.addDistributorLinks(distributor);
        bankClient.setCollector(collector);


        // IO DEVICE FOR TABLES

        final int NUM_TABLES = 2;

        List<TableDisplay> tableDisplays = new ArrayList<TableDisplay>();
        List<ReceiptPrinter> receiptPrinters = new ArrayList<ReceiptPrinter>();
        List<CardReader> cardReaders = new ArrayList<CardReader>();

        for (int i = 1; i <= NUM_TABLES; i++) {

            String iString = Integer.toString(i);

            // Create IO objects for a table.
            // Connect to event distributor and collector

            TableDisplay tableDisplay =
                    new TableDisplay("td" + iString);
            tableDisplay.addDistributorLinks(distributor);
            tableDisplay.setCollector(collector);
            tableDisplays.add(tableDisplay);


            ReceiptPrinter receiptPrinter =
                    new ReceiptPrinter("rp" + iString);
            receiptPrinter.setCollector(collector);
            receiptPrinters.add(receiptPrinter);

            CardReader cardReader =
                    new CardReader("cr" + iString);
            cardReader.addDistributorLinks(distributor);
            cardReaders.add(cardReader);


        }
        
        /* 
         * PART 2: 
         * - Implementation object creation. 
         * - Establishing links between different implementation 
         *   objects and IO Device objects and implementation objects.
         */

        /*
         ******************************************************************
         * BEGIN MODIFICATION AREA
         ******************************************************************
         * Put below code for creating implementation objects
         * and connecting them to the interface device objects.
         */

        // SYSTEM CORE
        // Create systemCore object and links between it and IO devices.

        SystemCore systemCore = new SystemCore();

        // # INITIALIZE SYSTEM CORE
        // ## Initialize rack
        Rack rack = new Rack();
        systemCore.setRack(rack);
        // ## Initialize kitchen controller
        KitchenController kitchenController = new KitchenController(systemCore);
        kitchenController.addKitchenDisplay(display);
        kitchenController.setTicketPrinter(printer);
        kitchenController.setPassLight(light);
        systemCore.setKitchenController(kitchenController);
        // ## Initialize tableTicketCoordinator
        TableTicketCoordinator tableTicketCoordinator = new TableTicketCoordinator();
        systemCore.setTableTicketCoordinator(tableTicketCoordinator);
        // ## Initialize menu and menuProvider
        Menu menu = new Menu();
        MenuProvider menuProvider = new MenuProvider(menu);
        systemCore.setMenuProvider(menuProvider);

        systemCore.setBankClient(bankClient);
        // END INITIALIZE SYSTEM CORE

        tableTicketCoordinator.setSystemCore(systemCore);

        button.setKitchenController(kitchenController);
        officeKVM.setSystemCore(systemCore);
        Clock.setSystemCore(systemCore);


        // TABLE-RELATED
        for (int i = 1; i <= NUM_TABLES; i++) {

            String iString = Integer.toString(i);

            // This tableID is for later printing on tickets at the pass, in 
            // order to show which table is to receive the order.
            // Go look at the ReceiptPrinter class.

            // DO NOT CHANGE this scheme for naming table IDs.

            String tableID = "Tab-" + iString;
            TableController tableController = new TableController(systemCore, tableID);
            tableController.setCardReader(cardReaders.get(i - 1));
            tableController.setReceiptPrinter(receiptPrinters.get(i - 1));
            tableController.setTableDisplay(tableDisplays.get(i - 1));


            // Create table-related core objects.
            // Connect these objects to table-related IO objects and to other system 
            // components.


        }

        // GENERAL
        // Create implementation objects that link to more than one area
        // and add links between implementation objects and interface device
        // objects in different areas.
        
        
        /*
         * Put above code for creating implementation objects
         * and connecting them to the interface device objects.
         ******************************************************************
         * END MODIFICATION AREA
         ******************************************************************
         */


    }


}
