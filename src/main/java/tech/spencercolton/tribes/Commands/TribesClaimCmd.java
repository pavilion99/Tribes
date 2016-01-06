package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Components.TribeRank;
import tech.spencercolton.tribes.Util.Config;
import tech.spencercolton.tribes.Util.Direction;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

class TribesClaimCmd {

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1 && args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t claim [chunks]");
            return;
        }

        int radius;

        try {
            radius = Integer.parseInt(args[1]);
        } catch(Exception e) {
            radius = 1;
        }

        if(radius > 5) {
            Message.message(sender, TribesCmd.err(), Config.maxRadius);
            return;
        }

        if (TribeLoader.getTribe((Player) sender) == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        Tribe t = TribeLoader.getTribe((Player) sender);

        if(t == null)
            return;

        TribeRank tr = t.getRank((Player)sender);

        if(tr == null)
            return;

        Message.message(sender, TribesCmd.err(), "Made it here.");

        if (!(tr.getPower() >= 2)) {
            Message.message(sender, TribesCmd.err(), Config.noPower);
            return;
        }

        if(radius % 2 == 0) {
            int realRadius = radius - 1; // 3

            Direction d = Direction.getCardinalFromYaw(((Player) sender).getLocation());

            int dir1 = (int)Math.floor(realRadius / 2.0d); // 1
            int dir2 = (int)Math.ceil(realRadius / 2.0d); // 2

            List<Chunk> toClaim = new ArrayList<>();

            Chunk playerChunk = ((Player)sender).getLocation().getChunk();

            if(d == null)
                return;

            switch(d) {
                case NORTH:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toClaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() + i, playerChunk.getZ() - j));
                        }
                    }
                    break;
                case EAST:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toClaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() + j, playerChunk.getZ() + i));
                        }
                    }
                    break;
                case SOUTH:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toClaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() - i, playerChunk.getZ() + j));
                        }
                    }
                    break;
                case WEST:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toClaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() - i, playerChunk.getZ() - j));
                        }
                    }
                    break;
                default:
                    break;
            }

            List<Chunk> fin = new ArrayList<>();

            int counter = 0;
            int alreadyOwned = 0;
            int ownedByOthers = 0;

            boolean broke = false;

            Chunk[] finArr = toClaim.toArray(new Chunk[1]);

            for(Chunk c : finArr) {
                if(TribeLoader.ownedChunks.get(t).size() + (counter) >= t.getTier().getChunks()) {
                    broke = true;
                    break;
                }

                Tribe tOwner = TribeLoader.getChunkOwner(c);

                if(tOwner == null) {
                    fin.add(c);
                    counter++;
                } else if(!tOwner.equals(t)) {
                    ownedByOthers++;
                } else if(tOwner.equals(t)) {
                    alreadyOwned++;
                }
            }

            fin.forEach(t::addChunk);

            t.push();

            Message.message(sender, Message.format(Config.claim, Config.colorOne, Config.colorTwo, Integer.toString(counter), Integer.toString(alreadyOwned), Integer.toString(ownedByOthers)));
            if(broke)
                Message.message(sender, TribesCmd.err(), Config.claimFail);

        } else {
            int realRadius = radius - 1; // 3

            Direction d = Direction.getCardinalFromYaw(((Player) sender).getLocation());

            int dir = realRadius/2;
            List<Chunk> toClaim = new ArrayList<>();

            Chunk playerChunk = ((Player)sender).getLocation().getChunk();

            if(d == null)
                return;

            Message.message(sender, TribesCmd.err(), "D isn't null");

            for(int i = -dir; i <= dir; i++) {
                for(int j = -dir; j <= dir; j++) {
                    toClaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() + i, playerChunk.getZ() - j));
                }
            }

            List<Chunk> fin = new ArrayList<>();

            int counter = 0;
            int alreadyOwned = 0;
            int ownedByOthers = 0;

            boolean broke = false;

            Chunk[] finArr = toClaim.toArray(new Chunk[1]);

            for(Chunk c : finArr) {
                if(TribeLoader.ownedChunks.get(t).size() + (counter) >= t.getTier().getChunks()) {
                    broke = true;
                    break;
                }

                Tribe ta = TribeLoader.getChunkOwner(c);

                if(ta == null) {
                    fin.add(c);
                    counter++;
                } else if(!ta.equals(t)) {
                    ownedByOthers++;
                } else if(ta.equals(t)) {
                    alreadyOwned++;
                }
            }

            fin.forEach(t::addChunk);

            t.push();

            Message.message(sender, Message.format(Config.claim, Config.colorOne, Config.colorTwo, Integer.toString(counter), Integer.toString(alreadyOwned), Integer.toString(ownedByOthers)));
            if(broke)
                Message.message(sender, TribesCmd.err(), Config.claimFail);
        }
    }

}
