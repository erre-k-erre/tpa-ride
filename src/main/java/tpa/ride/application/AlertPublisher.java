package tpa.ride.application;

public interface AlertPublisher {

    void publish(Alert alert);

    class Alert {
    }

}
