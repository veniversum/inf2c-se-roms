/**
 * 
 */
package roms;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author pbj
 *
 */
public class SystemTest extends TestBasis {   

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for your system below.
     */
    
     
    @Test 
    public void cancelReadyUpLight() {
        logger.info(makeBanner("cancelReadyUpLight"));
        input("1 18:00, PassButton, pb, press");
        expect("1 18:00, PassLight, pl, viewSwitchedOff");
        
        runAndCheck();
    }

 
   /*
    * Put all your JUnit system-level tests above.
    ***********************************************************************
    * END MODIFICATION AREA
    ***********************************************************************
    */

    
    
   /**
    * Set up system to be tested and connect interface devices to 
    * event distributor and collector. 
    * 
    * Setup is divided into two parts:
    * 
    * 1. Where IO device objects are created and connected to event
    *    distributor and event collector.
    *    
    * 2. Where core system implementation objects are created and 
    *    links are added between all the implementation objects and
    *    the IO device objects.
    *    
    * The first part configures sufficient IO device objects for all 3
    * implementation phases defined in the Coursework 3 handout.  It should 
    * not be touched. 
    * 
    * The second part here only gives the configuration for the demonstration
    * design.  It has to be modified for the system implementation.
    *   
    * 
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
       
        button.setSystemCore(systemCore);
        systemCore.setPassLight(light);
               
        
        // TABLE-RELATED
        for (int i = 1; i <= NUM_TABLES; i++) {
            
            String iString = Integer.toString(i);

            // This tableID is for later printing on tickets at the pass, in 
            // order to show which table is to receive the order.
            // Go look at the ReceiptPrinter class.
            
            // DO NOT CHANGE this scheme for naming table IDs.
            
            String tableID = "Tab-" + iString;

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
