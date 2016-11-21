package roms;

import static roms.LoggerUtil.logger;

public class TableController {
    private String tableId;
    private SystemCore systemCore;
    private CardReader cardReader;
    private ReceiptPrinter receiptPrinter;
    private TableDisplay tableDisplay;

    public TableController(SystemCore systemCore, String tableId) {
        this(tableId);
        logger.fine("init");
        this.systemCore = systemCore;
    }

    private TableController(String tableId) {
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
        tableDisplay.setTableController(this);
    }

    public void startOrder() {
        logger.fine(tableId);
        systemCore.getTableTicketCoordinator().createTicket(tableId);
    }

    public void showMenu() {
        logger.fine(tableId);
        tableDisplay.displayMenu(systemCore.getMenuProvider().getMenu());
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
        logger.fine(tableId);
        Money payableAmount = systemCore.getTableTicketCoordinator().getTicket(tableId).getPayableAmount();
        tableDisplay.displayBill(payableAmount);
        String authCode = systemCore.getBankClient().authorisePayment(cardReader.waitForCardDetails(), payableAmount);
        receiptPrinter.printReceipt(payableAmount, authCode);
    }
}
