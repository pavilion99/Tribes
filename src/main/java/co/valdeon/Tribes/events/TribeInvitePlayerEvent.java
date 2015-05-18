package co.valdeon.Tribes.events;

import co.valdeon.Tribes.components.Tribe;
import co.valdeon.Tribes.util.TribesEventCancellable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TribeInvitePlayerEvent extends TribesEventCancellable {

    private Player inviter;
    private OfflinePlayer invitee;
    private Tribe tribe;

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
