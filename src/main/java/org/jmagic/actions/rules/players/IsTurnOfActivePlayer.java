package org.jmagic.actions.rules.players;

import org.jmagic.core.states.State;
import org.jmagic.infrastructure.validation.rules.ValidationRule;


public class IsTurnOfActivePlayer extends ValidationRule {

    @Override
    public void onValidate(State state) {
        if (state.activePlayerIndex != state.turnsPlayerIndex) {
            errors.add(String.format("Active {%s} and turn's player {%s} are not the same",
                state.activePlayerState().player, state.turnsPlayerState().player));
        }
    }
}
