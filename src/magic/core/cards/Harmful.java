package magic.core.cards;

import magic.core.State;
import magic.core.contracts.ICard;
import magic.core.contracts.IDamageable;
import magic.core.contracts.ITargetable;
import magic.core.exceptions.IllegalCardUsageException;
import magic.core.exceptions.MagicException;

import java.util.List;
import java.util.UUID;

public abstract class Harmful extends Card {

    private final int damage;

    public Harmful(String name, int damage, int cost) {
        this(UUID.randomUUID(), name, damage, cost);
    }

    public Harmful(UUID id, String name, int damage, int cost) {
        super(id, name, cost);
        this.damage = damage;
    }

    @Override
    public State use(State state, List<ITargetable> targets) {
        List<State.PlayerInfo> playersInfo = state.getPlayersInfo();

        targets.forEach(t -> {
            if (t instanceof State.PlayerInfo) {
                // Update playersInfo with their recomputed lives.
                int indexOfPlayer = playersInfo.indexOf(t);
                playersInfo.add(indexOfPlayer,
                        (State.PlayerInfo) playersInfo
                                .remove(indexOfPlayer)
                                .takeDamage(effectiveDamage()));

            } else if (t instanceof Card) {
                // Find the player to whom that card belongs.
                State.PlayerInfo p = playersInfo.stream()
                        .filter(_p -> _p.field.contains((Card) t))
                        .findFirst().get();

                // Update the card with its recomputed life.
                List<ICard> fieldCards = p.field.getCards();
                int indexOfCard = fieldCards.indexOf(t);
                fieldCards.add(indexOfCard,
                        (Card) ((IDamageable) fieldCards
                                .remove(indexOfCard))
                                .takeDamage(effectiveDamage()));

                // Update player's info with the updated cards.
                int indexOfPlayer = playersInfo.indexOf(p);
                playersInfo.remove(indexOfPlayer);
                playersInfo.add(indexOfPlayer,
                        new State.PlayerInfo(p.player, p.life, p.maxLife,
                                p.deck, p.hand, new Cards(fieldCards), p.graveyard,
                                p.playing));
            }
        });

        return new State(playersInfo, state.turn, state.done, state.turnsCurrentPlayerId);
    }

    @Override
    public void validUseOrRaisesException(State state, List<ITargetable> targets)
            throws MagicException {
        if (targets.isEmpty()) {
            throw new IllegalCardUsageException("must target at least one enemy");
        }

        if (!targets.stream().allMatch((t) -> t instanceof IDamageable)) {
            throw new IllegalCardUsageException("can only target damageable targets");
        }

        if (targets.stream()
                .map(t -> (IDamageable) t)
                .anyMatch(p -> !p.isAlive())) {
            throw new IllegalCardUsageException("cannot target a card that's not alive");
        }

        if (targets.stream()
                .filter(t -> t instanceof Card)
                .map(t -> (Card) t)
                .anyMatch(c ->
                        state.getPlayersInfo().stream().noneMatch(i ->
                                i.field.contains(c)))) {
            throw new IllegalCardUsageException("can only target cards that are on the field");
        }
    }

    public int damage() {
        return damage;
    }

    public int effectiveDamage() {
        return damage();
    }
}