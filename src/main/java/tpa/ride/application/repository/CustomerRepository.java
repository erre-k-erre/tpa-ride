package tpa.ride.application.repository;

import tpa.ride.domain.Customer;

import java.util.UUID;

public interface CustomerRepository {

    Customer findById(UUID uuid);

    Customer findByCardId(UUID uuid);

    void save(Customer customer);

}
