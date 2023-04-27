package tpa.ride.infrastructure;

import tpa.ride.application.*;
import tpa.ride.application.repository.CustomerRepository;
import tpa.ride.application.repository.CustomerStatsRepository;
import tpa.ride.application.repository.RideRepository;
import tpa.ride.application.repository.StationStatsRepository;
import tpa.ride.domain.PoliceDepartmentService;

public class ApplicationServicesConfiguration {

    private final RepositoryConfiguration repositoryConfiguration = new RepositoryConfiguration();

    private final CustomerRepository customerRepository = repositoryConfiguration.customerRepository();
    private final RideRepository rideRepository = repositoryConfiguration.rideRepository();
    private final CustomerStatsRepository customerStatsRepository = repositoryConfiguration.customerStatsRepository();
    private final StationStatsRepository stationStatsRepository = repositoryConfiguration.stationStatsRepository();

    private final DomainConfiguration domainConfiguration = new DomainConfiguration();

    private final PoliceDepartmentService policeDepartmentService = domainConfiguration.policeDepartmentService();

    private final CancelCardCommandHandler cancelCardCommandHandler = new CancelCardCommandHandler(customerRepository);
    private final CreateNewCustomerCommandHandler createNewCustomerCommandHandler = new CreateNewCustomerCommandHandler(customerRepository);
    private final IssueNewCardCommandHandler issueNewCardCommandHandler = new IssueNewCardCommandHandler(customerRepository);
    private final LoadTicketCommandHandler loadTicketCommandHandler = new LoadTicketCommandHandler(customerRepository);
    private final TicketValidatedEventHandler ticketValidatedEventHandler =
            new TicketValidatedEventHandler(rideRepository, customerStatsRepository, stationStatsRepository, policeDepartmentService, alert -> {});
    private final ValidateEntryCommandHandler validateEntryCommandHandler = new ValidateEntryCommandHandler(customerRepository);
    private final ValidateExitCommandHandler validateExitCommandHandler = new ValidateExitCommandHandler(customerRepository);

    public CancelCardCommandHandler cancelCardCommandHandler() {
        return cancelCardCommandHandler;
    }

    public CreateNewCustomerCommandHandler createNewCustomerCommandHandler() {
        return createNewCustomerCommandHandler;
    }

    public IssueNewCardCommandHandler issueNewCardCommandHandler() {
        return issueNewCardCommandHandler;
    }

    public final LoadTicketCommandHandler loadTicketCommandHandler() {
        return loadTicketCommandHandler;
    }

    public final TicketValidatedEventHandler ticketValidatedEventHandler() {
        return ticketValidatedEventHandler;
    }

    public final ValidateEntryCommandHandler validateEntryCommandHandler() {
        return validateEntryCommandHandler;
    }

    public final ValidateExitCommandHandler validateExitCommandHandler() {
        return validateExitCommandHandler;
    }

    public RepositoryConfiguration getRepositoryConfiguration() {
        return repositoryConfiguration;
    }

    public DomainConfiguration getDomainConfiguration() {
        return domainConfiguration;
    }

}
