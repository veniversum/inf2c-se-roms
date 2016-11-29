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
        logger.finer("init");
        this.systemCore = systemCore;
    }

    private TableController(String tableId) {
        this.tableId = tableId;
    }

    public void setCardReader(CardReader cardReader) {
        logger.finer("");
        this.cardReader = cardReader;
    }

    public void setReceiptPrinter(ReceiptPrinter receiptPrinter) {
        logger.finer("");
        this.receiptPrinter = receiptPrinter;
    }

    public void setTableDisplay(TableDisplay tableDisplay) {
        logger.finer("");
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
        Ticket ticket = systemCore.getTableTicketCoordinator().getTicket(tableId);
        if (ticket == null) throw new Ticket.TicketOperationException("Ticket does not exist for table");
        if (ticket.isPaid()) throw new Ticket.TicketOperationException("Bill has already been paid for");
        Money payableAmount = systemCore.getTableTicketCoordinator().getTicket(tableId).getPayableAmount();
        tableDisplay.displayBill(payableAmount);
        String authCode = systemCore.getBankClient().authorisePayment(cardReader.waitForCardDetails(), payableAmount);
        receiptPrinter.printReceipt(payableAmount, authCode);
        ticket.setPaid(true);
    }
}
