package org.jmagic.core.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Cards.
 * <p>
 * Hides an ordered collection of cards.
 * Can be used to represent decks, hands, fields and graveyards.
 *
 * Usage:
 *
 * <pre>
 * {@code
 * Cards deck = new Cards(new Land(...), new Creature(...), new Boost(...), ...);
 * Cards hand = new Cards(new Land(...), new Creature(...), new Boost(...));
 *
 * State.PlayerState p = new State.PlayerState(
 *     new RandomPlayer("jane"), 20, 20,
 *     deck, hand, Cards.EMPTY, Cards.EMPTY,
 *     ...);
 * }
 * </pre>
 */
public class Cards {

    public static final Cards EMPTY = new Cards();

    private List<ICard> cards;

    public Cards() {
        this(new ArrayList<>());
    }

    public Cards(ICard... cards) {
        this(Arrays.asList(cards));
    }

    public Cards(List<ICard> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public List<ICard> cards() {
        return new ArrayList<>(cards);
    }

    public boolean any(Predicate<? super ICard> predicate) {
        return this.cards.stream().anyMatch(predicate);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean contains(ICard card) {
        return cards.contains(card);
    }

    public ICard validated(ICard card) {
        return cards.get(cards.indexOf(card));
    }

    public int size() {
        return cards.size();
    }

    public Cards update(ICard... updatedCards) {
        return update(List.of(updatedCards));
    }

    public Cards update(List<ICard> updatedCards) {
        List<ICard> newCards = new ArrayList<>(cards);
        for (ICard card : updatedCards) {
            newCards.set(newCards.indexOf(card), card);
        }
        return new Cards(newCards);
    }

    public Cards add(ICard... addingCards) {
        return add(List.of(addingCards));
    }

    public Cards add(List<ICard> addingCards) {
        List<ICard> newCards = new ArrayList<>(cards);
        newCards.addAll(addingCards);
        return new Cards(newCards);
    }

    public Cards remove(ICard... removingCards) {
        return remove(List.of(removingCards));
    }

    public Cards remove(List<ICard> removingCards) {
        List<ICard> newCards = new ArrayList<>(cards);
        newCards.removeAll(removingCards);
        return new Cards(newCards);
    }

    @Override
    public boolean equals(Object o) {
        try {
            return cards.equals(((Cards) o).cards);
        } catch (ClassCastException | NullPointerException ex) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 137 * hash + Objects.hashCode(this.cards);
        return hash;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean detailed) {
        return cards.stream()
            .map(c -> c.toString(detailed))
            .collect(Collectors.toList())
            .toString();
    }
}
