package chapter_01.step2;

public class Bag {
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Long hold(Ticket ticket) {
        setTicket(ticket);
        if (hasInvitation()) {
            return 0L;
        }
        minusAmount(ticket.getFee());
        return ticket.getFee();
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private boolean hasInvitation() {
        return invitation != null;
    }


    private void minusAmount(Long amount) {
        this.amount -= amount;
    }
}
