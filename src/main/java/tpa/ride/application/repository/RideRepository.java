package tpa.ride.application.repository;

import tpa.ride.domain.Customer;
import tpa.ride.domain.Ride;

import java.util.UUID;

public interface RideRepository {

    Ride findActive(UUID customerId);

    void save(Ride ride);

}
