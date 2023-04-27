package tpa.ride.application;

import tpa.ride.application.repository.CustomerStatsRepository;
import tpa.ride.application.repository.RideRepository;
import tpa.ride.application.repository.StationStatsRepository;
import tpa.ride.domain.*;
import tpa.ride.domain.event.CardValidatedOnEntryEvent;
import tpa.ride.domain.event.CardValidatedOnExitEvent;
import tpa.ride.domain.event.RideFinishedEvent;

import java.time.LocalDate;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.MINUTES;

public class TicketValidatedEventHandler {

    private final RideRepository rideRepository;

    private final CustomerStatsRepository customerStatsRepository;

    private final StationStatsRepository stationStatsRepository;

    private final PoliceDepartmentService policeDepartmentService;

    private final AlertPublisher alertPublisher;

    public TicketValidatedEventHandler(
            RideRepository rideRepository,
            CustomerStatsRepository customerStatsRepository,
            StationStatsRepository stationStatsRepository,
            PoliceDepartmentService policeDepartmentService,
            AlertPublisher alertPublisher
    ) {

        this.rideRepository = rideRepository;
        this.customerStatsRepository = customerStatsRepository;
        this.stationStatsRepository = stationStatsRepository;
        this.policeDepartmentService = policeDepartmentService;
        this.alertPublisher = alertPublisher;

        Subscriptions.subscribe(CardValidatedOnEntryEvent.class, this::checkCustomer);
        Subscriptions.subscribe(CardValidatedOnExitEvent.class, this::checkCustomer);

        Subscriptions.subscribe(CardValidatedOnEntryEvent.class, this::createRideHandle);
        Subscriptions.subscribe(CardValidatedOnExitEvent.class, this::completeRideHandle);

        Subscriptions.subscribe(CardValidatedOnEntryEvent.class, this::aggregateToStationStats);
        Subscriptions.subscribe(CardValidatedOnExitEvent.class, this::aggregateToStationStats);

        Subscriptions.subscribe(RideFinishedEvent.class, this::aggregateToCustomerStats);

    }

    public void checkCustomer(CardValidatedOnEntryEvent event) {

        if (policeDepartmentService.findInFile(event.getIdentification())) {
            alertPublisher.publish(new AlertPublisher.Alert());
        }

    }

    public void checkCustomer(CardValidatedOnExitEvent event) {

        if (policeDepartmentService.findInFile(event.getIdentification())) {
            alertPublisher.publish(new AlertPublisher.Alert());
        }

    }

    public void createRideHandle(CardValidatedOnEntryEvent event) {

        final Ride ride = Ride.of(event.getCustomerId(), event.getTicketValidator());

        rideRepository.save(ride);

    }

    public void  completeRideHandle(CardValidatedOnExitEvent event) {

        final Ride ride = rideRepository.findActive(event.getCustomerId());

        ride.finish(event.getTicketValidator());

        rideRepository.save(ride);

    }


    public void aggregateToStationStats(CardValidatedOnEntryEvent event) {

        final LocalDate localDate = LocalDate.ofInstant(event.getEntryTime(), ZoneId.systemDefault());

        final StationStatsRecord record =
                stationStatsRepository.findRecord(event.getTicketValidator().getStation(), localDate)
                    .orElse(StationStatsRecord.of(localDate, event.getTicketValidator().getStation()));

        record.addEntry();

        stationStatsRepository.updateRecord(record);

    }

    public void aggregateToStationStats(CardValidatedOnExitEvent event) {

        final LocalDate localDate = LocalDate.ofInstant(event.getExitTime(), ZoneId.systemDefault());

        final StationStatsRecord record =
                stationStatsRepository.findRecord(event.getTicketValidator().getStation(), localDate)
                        .orElse(StationStatsRecord.of(localDate, event.getTicketValidator().getStation()));

        record.addExit();

        stationStatsRepository.updateRecord(record);

    }

    public void aggregateToCustomerStats(RideFinishedEvent event) {

        final LocalDate localDate = LocalDate.ofInstant(event.getExitTime(), ZoneId.systemDefault());

        final CustomerStatsRecord record =
                customerStatsRepository.findRecord(event.getCustomerId(), localDate)
                        .orElse(CustomerStatsRecord.of(localDate,event.getCustomerId()));

        record.addRide(event.getEntryTime().until(event.getExitTime(), MINUTES));

        customerStatsRepository.updateRecord(record);

    }

}
