package tpa.ride.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.domain.Card;
import tpa.ride.domain.Customer;
import tpa.ride.domain.TicketValidator;
import tpa.ride.domain.exception.CardCancelledException;
import tpa.ride.domain.exception.InvalidTicketException;
import tpa.ride.domain.exception.NoTicketException;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ValidateEntryCommandHandler {

    private final CustomerRepository repository;

    public void handle(TicketValidator ticketValidator, UUID cardId)
            throws InvalidTicketException, NoTicketException, CardCancelledException {

        final Customer customer = repository.findByCardId(cardId);

        final Card card = customer.getCardWithId(cardId);
        card.validateEntry(ticketValidator);

        log.info("Customer entered");

        repository.save(customer);

    }

}
