package tpa.ride.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.domain.Card;
import tpa.ride.domain.Customer;
import tpa.ride.domain.TicketValidator;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ValidateExitCommandHandler {

    private final CustomerRepository repository;

    public void handle(TicketValidator ticketValidator, UUID cardId) {

        final Customer customer = repository.findByCardId(cardId);

        final Card card = customer.getCardWithId(cardId);
        card.validateExit(ticketValidator);

        log.info("Customer exited");

        repository.save(customer);

    }

}
