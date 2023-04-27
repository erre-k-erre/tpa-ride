package tpa.ride.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tpa.ride.domain.event.CardValidatedOnEntryEvent;
import tpa.ride.domain.event.CardValidatedOnExitEvent;
import tpa.ride.domain.exception.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Card {

    @Getter
    private UUID id;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private boolean cancelled;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private Instant cancellationTime;

    @Getter
    private Ticket ticket;

    private Customer customer;

    public void loadTicket(Ticket ticket) throws CardCancelledException, CardAlreadyChargedException {

        if (cancelled) {
            throw new CardCancelledException();
        } else if (this.ticket != null && this.ticket.isValid()) {
            throw new CardAlreadyChargedException();
        }

        this.ticket = ticket;

    }

    public void cancel() throws CardAlreadyCancelledException {

        if (isCancelled()) {
            throw new CardAlreadyCancelledException();
        } else {
            cancelled = true;
            cancellationTime = Instant.now();
        }

    }

    public void validateEntry(TicketValidator ticketValidator) throws NoTicketException, InvalidTicketException, CardCancelledException {

        if (this.isCancelled()) {
            throw new CardCancelledException();
        }

        if (ticket == null) {
            throw new NoTicketException();
        } else {

            if (!ticket.isValid()) {
                throw new InvalidTicketException();
            }

            ticket = ticket.validate();

            Subscriptions.dispatch(new CardValidatedOnEntryEvent(customer.getId(), customer.getIdentification(), ticketValidator, Instant.now()));

        }

    }

    public void validateExit(TicketValidator ticketValidator) {
        Subscriptions.dispatch(new CardValidatedOnExitEvent(customer.getId(), customer.getIdentification(), ticketValidator, Instant.now()));
    }

    static Card of(Customer parent) {
        return new Card(UUID.randomUUID(), false, null, null, parent);
    }

    static Card of(Ticket oldTicket, Customer parent) {
        return new Card(UUID.randomUUID(), false, null, oldTicket, parent);
    }

}
