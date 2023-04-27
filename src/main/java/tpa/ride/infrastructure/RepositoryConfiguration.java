package tpa.ride.infrastructure;

import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.application.repository.CustomerStatsRepository;
import tpa.ride.application.repository.RideRepository;
import tpa.ride.application.repository.StationStatsRepository;
import tpa.ride.domain.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class RepositoryConfiguration {

    private Map<UUID, Customer> customerMap = new HashMap<>();
    private Map<UUID, Ride> rideMap = new HashMap<>();
    private Map<Map.Entry<UUID, LocalDate>, CustomerStatsRecord> customerStatsMap = new HashMap<>();
    private Map<Map.Entry<Station, LocalDate>, StationStatsRecord> stationStatsMap = new HashMap<>();

    public CustomerRepository customerRepository() {

        return new CustomerRepository() {

            @Override
            public Customer findById(UUID uuid) {
                return customerMap.get(uuid);
            }

            @Override
            public Customer findByCardId(UUID uuid) {
                return customerMap.values().stream().filter(c -> c.getCardWithId(uuid) != null)
                        .findAny().orElse(null);
            }

            @Override
            public void save(Customer customer) {
                customerMap.put(customer.getId(), customer);
            }

        };

    }

    public RideRepository rideRepository() {
        return new RideRepository() {
            @Override
            public Ride findActive(UUID customerId) {
                return rideMap.values().stream()
                        .filter(r -> r.getCustomerId().equals(customerId))
                        .filter(Ride::isActive)
                        .findAny()
                        .orElse(null);
            }

            @Override
            public void save(Ride ride) {
                rideMap.put(ride.getId(), ride);
            }
        };
    }

    public CustomerStatsRepository customerStatsRepository() {
        return new CustomerStatsRepository() {


            @Override
            public void updateRecord(CustomerStatsRecord record) {
                customerStatsMap.put(Map.entry(record.getCustomerId(), record.getDate()), record);
            }

            @Override
            public Optional<CustomerStatsRecord> findRecord(UUID customerId, LocalDate date) {
                return Optional.ofNullable(customerStatsMap.get(Map.entry(customerId, date)));
            }

        };
    }

    public StationStatsRepository stationStatsRepository() {

        return new StationStatsRepository() {
            @Override
            public void updateRecord(StationStatsRecord record) {
                stationStatsMap.put(Map.entry(record.getStation(), record.getDate()), record);
            }

            @Override
            public Optional<StationStatsRecord> findRecord(Station station, LocalDate date) {
                return Optional.ofNullable(stationStatsMap.get(Map.entry(station, date)));
            }

        };

    }
}
