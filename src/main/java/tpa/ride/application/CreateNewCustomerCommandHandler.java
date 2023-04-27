package tpa.ride.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.domain.Address;
import tpa.ride.domain.Customer;
import tpa.ride.domain.Identification;

@RequiredArgsConstructor
@Slf4j
public class CreateNewCustomerCommandHandler {

    private final CustomerRepository repository;

    public void handle(Identification identification, Address address) {

        final Customer customer = Customer.of(identification, address);

        log.info("New customer created");
        repository.save(customer);

    }

}
