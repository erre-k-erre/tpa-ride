package tpa.ride.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.domain.Card;
import tpa.ride.domain.Customer;
import tpa.ride.domain.Ticket;
import tpa.ride.domain.exception.CardAlreadyChargedException;
import tpa.ride.domain.exception.CardCancelledException;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class LoadTicketCommandHandler {

    private final CustomerRepository repository;

    public void handle(UUID cardId, Ticket ticket) throws CardCancelledException, CardAlreadyChargedException {

        final Customer customer = repository.findByCardId(cardId);

        final Card card = customer.getCardWithId(cardId);

        card.loadTicket(ticket);

        log.info("Ticked charged");
        repository.save(customer);

    }

}
