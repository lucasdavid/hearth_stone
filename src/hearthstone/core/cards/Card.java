package hearthstone.core.cards;

import java.util.Objects;
import java.util.UUID;

public abstract class Card {

    private final UUID id;
    private final String name;
    private final int manaCost;

    public Card(UUID id, String name, int manaCost) {
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
    }

    public Card(String name, int manaCost) {
        this(UUID.randomUUID(), name, manaCost);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public String toString() {
        return String.format("%s cost: %d", name, id, manaCost);
    }

    public abstract Card copy();

    @Override
    public boolean equals(Object o) {
        try {
            return id.equals(((Card) o).id);
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }
}