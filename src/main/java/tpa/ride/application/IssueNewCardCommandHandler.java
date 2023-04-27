package tpa.ride.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.domain.Card;
import tpa.ride.domain.Customer;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class IssueNewCardCommandHandler {

    private final CustomerRepository customerRepository;

    public UUID handle(UUID customerId) {

        final Customer customer = customerRepository.findById(customerId);

        final Card card = customer.issueNewCard();

        log.info("New card issued");
        return card.getId();

    }

}
