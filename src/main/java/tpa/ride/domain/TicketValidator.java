package tpa.ride.domain;

import lombok.Value;

@Value
public class TicketValidator {

    private String serialNumber;

    private Station station;

}
