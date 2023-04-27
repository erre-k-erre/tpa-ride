package tpa.ride.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Subscriptions {

    private static Map<Class, List<Consumer>> subscribers = new HashMap<>();

    public static <T> void subscribe(Class<T> eventType, Consumer<T> consumer) {

        final List<Consumer> consumers1 = subscribers.get(eventType);

        if (consumers1 == null) {
            final List<Consumer> consumers = new LinkedList<>();
            consumers.add(consumer);
            subscribers.put(eventType, consumers);
        } else {
            consumers1.add(consumer);
        }

    }

    public static <T> void dispatch(T event) {

        final List<Consumer> consumers = subscribers.get(event.getClass());

        if (consumers != null) {
            consumers.forEach(consumer -> consumer.accept(event));
        }

    }

}
