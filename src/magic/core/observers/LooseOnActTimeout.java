package magic.core.observers;

import magic.core.actions.Action;
import magic.core.states.State;

/**
 * @author ldavid
 */
public class LooseOnActTimeout extends Observer {

    private final long timeout;

    public LooseOnActTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public State afterPlayerAct(State state, Action action, long actStartedAt, long actEndedAt) {
        if (actEndedAt - actStartedAt >= this.timeout) {
            State.PlayerState p = state.activePlayerState();

            LOG.warning(String.format(
                "%s lost because they exceeded the allowed act time-frame of %d",
                p.player, this.timeout));

            return _disqualify(state);
        }

        return state;
    }
}