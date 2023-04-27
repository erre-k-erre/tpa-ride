package tpa.ride.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.domain.Card;
import tpa.ride.domain.Customer;
import tpa.ride.domain.exception.CardAlreadyCancelledException;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class CancelCardCommandHandler {

    private final CustomerRepository repository;

    public void handle(UUID uuid) throws CardAlreadyCancelledException {

        final Customer customer = repository.findById(uuid);

        final Card activeCard = customer.getActiveCard();

        if (activeCard == null) {
            log.warn("No active card for user");
        } else {
            activeCard.cancel();
            log.info("Card cancelled");
            repository.save(customer);
        }

    }

}
