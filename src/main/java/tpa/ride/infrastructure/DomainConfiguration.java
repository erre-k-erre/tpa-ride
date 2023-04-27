package tpa.ride.infrastructure;

import tpa.ride.domain.PoliceDepartmentService;

public class DomainConfiguration {

    public PoliceDepartmentService policeDepartmentService() {
        return identification -> false;
    }

}
