package tpa.ride.domain;

import lombok.Value;

@Value
public class Identification {

    private Type type;

    private String code;

    public enum Type {
        SSN, PASSPORT, DRIVERS_LICENCE
    }

}
