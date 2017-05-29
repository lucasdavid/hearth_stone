package magic.core.actions;

import magic.core.ITargetable;
import magic.infrastructure.validation.rules.ValidationRule;
import magic.core.cards.Card;
import magic.core.states.State;

import java.util.Collections;
import java.util.List;

/**
 * Use Action.
 *
 * @author ldavid
 */
public class UseAction extends Action {

    private final Card card;
    private final List<ITargetable> targets;

    public UseAction(Card card) {
        this(card, Collections.emptyList());
    }

    public UseAction(Card card, List<ITargetable> targets) {
        this.card = card;
        this.targets = Collections.unmodifiableList(targets);
    }

    @Override
    public State update(State state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValidationRule validationRules() {
        return null;
    }
}
