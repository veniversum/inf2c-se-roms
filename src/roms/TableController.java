package roms;

import java.util.logging.Logger;

public class TableController {
    protected static final Logger logger = Logger.getLogger("roms");
    private String tableId;
    private SystemCore systemCore;
    private CardReader cardReader;
    private ReceiptPrinter receiptPrinter;
    private TableDisplay tableDisplay;

    public TableController(SystemCore systemCore, String tableId) {
        this(tableId);
        this.systemCore = systemCore;
    }

    public TableController(String tableId) {
        this.tableId = tableId;
    }

    public void setCardReader(CardReader cardReader) {
        this.cardReader = cardReader;
    }

    public void setReceiptPrinter(ReceiptPrinter receiptPrinter) {
        this.receiptPrinter = receiptPrinter;
    }

    public void setTableDisplay(TableDisplay tableDisplay) {
        this.tableDisplay = tableDisplay;
    }

    public void startOrder() {
        logger.fine(tableId);
        systemCore.getTableTicketCoordinator().createTicket(tableId);
        // TO BE COMPLETED
    }

    public void showMenu() {
        logger.fine(tableId);
        tableDisplay.displayMenu(systemCore.getMenuProvider().getDefaultMenu());
    }

    public void showTicket() {
        logger.fine(tableId);
        tableDisplay.displayTicket(systemCore.getTableTicketCoordinator().getTicket(tableId));
    }

    public void addMenuItem(String menuID) {
        logger.fine(tableId);
        systemCore.getTableTicketCoordinator().addMenuItem(tableId, menuID);
    }

    public void removeMenuItem(String menuID) {
        logger.fine(tableId);
        systemCore.getTableTicketCoordinator().removeMenuItem(tableId, menuID);
    }

    public void submitOrder() {
        logger.fine(tableId);
        systemCore.getTableTicketCoordinator().submitTicket(tableId);
    }

    public void payBill() {
        Money payableAmount = systemCore.getTableTicketCoordinator().getTicket(tableId).getPayableAmount();
        tableDisplay.displayBill(payableAmount);
        String authCode = systemCore.getBankClient().authorisePayment(cardReader.waitForCardDetails(), payableAmount);
        receiptPrinter.printReceipt(payableAmount, authCode);
    }
}
