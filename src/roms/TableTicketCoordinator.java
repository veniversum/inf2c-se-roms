package roms;

import java.util.HashMap;
import java.util.Map;

public class TableTicketCoordinator {
    private Map<String, Ticket> tableTicketMap = new HashMap<>();
    private SystemCore systemCore;

    public void setSystemCore(SystemCore systemCore) {
        this.systemCore = systemCore;
    }

    public Ticket createTicket(String tableId) {
        Ticket ticket = new Ticket(tableId);
        ticket.setSystemCore(systemCore);
        tableTicketMap.put(tableId, ticket);
        return ticket;
    }

    public Ticket getTicket(String tableId) {
        return tableTicketMap.get(tableId);
    }
}
