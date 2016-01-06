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

class TribesUnclaimCmd {
    
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1 && args.length != 2) {
            Message.message(sender, TribesCmd.err(), Config.invalidSubargs);
            Message.message(sender, TribesCmd.err(), "/t claim [chunks]");
            return;
        }

        int diameter = 1;

        if(args.length == 2) {
            try {
                diameter = Integer.parseInt(args[1]);
            } catch(Exception e) {
                diameter = 1;
            }
        }

        if(diameter > 5) {
            Message.message(sender, TribesCmd.err(), Config.maxRadius);
            return;
        }

        if (TribeLoader.getTribe((Player) sender) == null) {
            Message.message(sender, TribesCmd.err(), Config.notInTribe);
            return;
        }

        Tribe tha = TribeLoader.getTribe((Player) sender);

        if(tha == null)
            return;

        TribeRank tr = tha.getRank((Player)sender);

        if(tr == null)
            return;

        if (!(tr.getPower() >= 2)) {
            Message.message(sender, TribesCmd.err(), Config.noPower);
            return;
        }

        if(diameter % 2 == 0) {
            int realRadius = diameter - 1; // 3

            Direction d = Direction.getCardinalFromYaw(((Player) sender).getLocation());

            int dir1 = (int)Math.floor(realRadius / 2.0d); // 1
            int dir2 = (int)Math.ceil(realRadius / 2.0d); // 2

            List<Chunk> toUnclaim = new ArrayList<>();

            Chunk playerChunk = ((Player)sender).getLocation().getChunk();

            if(d == null)
                return;

            switch(d) {
                case NORTH:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toUnclaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() + i, playerChunk.getZ() - j));
                        }
                    }
                    break;
                case EAST:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toUnclaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() + j, playerChunk.getZ() + i));
                        }
                    }
                    break;
                case SOUTH:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toUnclaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() - i, playerChunk.getZ() + j));
                        }
                    }
                    break;
                case WEST:
                    for(int i = -dir1; i <= dir2; i++) {
                        for(int j = -dir1; j <= dir2; j++) {
                            toUnclaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() - i, playerChunk.getZ() - j));
                        }
                    }
                    break;
                default:
                    break;
            }

            List<Chunk> fin = new ArrayList<>();

            int counter = 0;
            int notOwned = 0;
            int ownedByOthers = 0;

            Chunk[] finArr = toUnclaim.toArray(new Chunk[1]);

            for(Chunk c : finArr) {
                Tribe ta = TribeLoader.getChunkOwner(c);

                if(ta == null) {
                    notOwned++;
                } else if(ta.equals(tha)) {
                    fin.add(c);
                    counter++;
                } else if(!ta.equals(tha)) {
                    ownedByOthers++;
                }
            }

            fin.forEach(tha::removeChunk);

            tha.push();

            Message.message(sender, Message.format(Config.unclaim, Config.colorOne, Config.colorTwo, Integer.toString(counter), Integer.toString(notOwned), Integer.toString(ownedByOthers)));
        } else {
            int realRadius = diameter - 1; // 3

            Direction d = Direction.getCardinalFromYaw(((Player) sender).getLocation());

            int dir1 = (int)Math.floor(realRadius / 2.0d); // 1
            int dir2 = (int)Math.ceil(realRadius / 2.0d); // 2

            List<Chunk> toUnclaim = new ArrayList<>();

            Chunk playerChunk = ((Player)sender).getLocation().getChunk();

            if(d == null)
                return;

            for(int i = -dir1; i <= dir2; i++) {
                for(int j = -dir1; j <= dir2; j++) {
                    toUnclaim.add(((Player)sender).getWorld().getChunkAt(playerChunk.getX() + i, playerChunk.getZ() - j));
                }
            }

            List<Chunk> fin = new ArrayList<>();

            int counter = 0;
            int notOwned = 0;
            int ownedByOthers = 0;

            Chunk[] finArr = toUnclaim.toArray(new Chunk[1]);

            for(Chunk c : finArr) {
                Tribe ta = TribeLoader.getChunkOwner(c);

                if(ta == null) {
                    notOwned++;
                } else if(ta.equals(tha)) {
                    fin.add(c);
                    counter++;
                } else if(!ta.equals(tha)) {
                    ownedByOthers++;
                }
            }

            fin.forEach(tha::removeChunk);

            tha.push();

            Message.message(sender, Message.format(Config.unclaim, Config.colorOne, Config.colorTwo, Integer.toString(counter), Integer.toString(notOwned), Integer.toString(ownedByOthers)));
        }
    }
    
}
