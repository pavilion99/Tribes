package tech.spencercolton.tribes.Events;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.TribesEventCancellable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TribeInvitePlayerEvent extends TribesEventCancellable {

    private final Player inviter;
    private final OfflinePlayer invitee;
    private final Tribe tribe;

    public TribeInvitePlayerEvent(Player inviter, OfflinePlayer invitee, Tribe tribe) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.tribe = tribe;
    }

    public OfflinePlayer getInvitee() {
        return this.invitee;
    }

    public Player getInviter() {
        return this.inviter;
    }

    public Tribe getTribe() {
        return this.tribe;
    }

}
