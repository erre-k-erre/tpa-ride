package tpa.ride.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tpa.ride.domain.exception.CardAlreadyCancelledException;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    @Getter
    private UUID id;

    @Getter
    private Identification identification;

    @Getter
    private Address address;

    private final List<Card> cards = new LinkedList<>();

    public List<Card> getInactiveCards() {
        return cards.stream().filter(Card::isCancelled).collect(Collectors.toList());
    }

    public Card getActiveCard() {
        return cards.stream().filter(c -> !c.isCancelled()).findAny().orElse(null);
    }

    public Card issueNewCard() {

        final Card newCard;
        final Card activeCard = getActiveCard();

        if (activeCard != null) {

            final Ticket ticket = activeCard.getTicket();

            try {
                activeCard.cancel();
            } catch (CardAlreadyCancelledException e) {
                e.printStackTrace();
            }

            newCard = Card.of(ticket, this);

        } else {

            newCard = Card.of(this);

        }

        cards.add(newCard);

        return newCard;

    }

    public Card getCardWithId(UUID uuid) {
        return cards.stream().filter(c -> c.getId().equals(uuid)).findAny().orElse(null);
    }

    public static Customer of(Identification identification, Address address) {

        final Customer customer = new Customer(UUID.randomUUID(), identification, address);
        customer.cards.add(Card.of(customer));

        return customer;

    }

}
