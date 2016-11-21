package roms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TicketOperationExceptionTest {

    @Test
    public void ticketOperationExceptionTest() throws Exception {
        try {
            throw new Ticket.TicketOperationException("this is a test message");
        } catch (Ticket.TicketOperationException e) {
            assertEquals(e.getMessage(), "this is a test message");
        }
    }
}