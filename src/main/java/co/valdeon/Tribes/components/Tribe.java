package co.valdeon.Tribes.components;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.components.abilities.*;
import co.valdeon.Tribes.schedules.PushTribe;
import co.valdeon.Tribes.schedules.PushTribesSchedule;
import co.valdeon.Tribes.storage.*;
import co.valdeon.Tribes.util.Config;
import co.valdeon.Tribes.util.Message;
import co.valdeon.Tribes.util.TribeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tribe {

    private int id;
    private String name;
    private TribeTier tier;
    private HashMap<OfflinePlayer, TribeRank> members = new HashMap<>();
    private int coins;
    private List<OfflinePlayer> invitees = new ArrayList<>();
    private List<AbilityType> abilities = new ArrayList<>();
    private Location home;

    public Tribe(String name, OfflinePlayer creator) {
        this.name = name;
        this.tier = TribeTier.TIER_ONE;
        this.members.put(creator, TribeRank.CHIEF);
        TribeLoader.ownedChunks.put(this, new ArrayList<Chunk>());
    }

    public Tribe(String name, int id, HashMap<OfflinePlayer, TribeRank> members, List<Chunk> ownedChunks, int coins, List<OfflinePlayer> invitees, TribeTier tier, List<AbilityType> abilities, Location home) {
        this.name = name;
        this.tier = tier;
        this.members = members;
        this.id = id;
        this.coins = coins;
        this.invitees = invitees;
        this.abilities = abilities;
        this.home = home;
        TribeLoader.ownedChunks.put(this, ownedChunks);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public TribeTier getTier() {
        return this.tier;
    }

    public HashMap<OfflinePlayer, TribeRank> getMembers() {
        return this.members;
    }

    public boolean addMember(OfflinePlayer p) {
        if(!this.members.keySet().contains(p)) {
            this.members.put(p, TribeRank.MEMBER);
            return true;
        }
        return false;
    }

    public void removeMember(OfflinePlayer p) {
        this.members.remove(p);
    }

    public static Tribe getTribe(OfflinePlayer p) {
        return TribeLoader.getTribe(p);
    }

    public Tribe push() {
        new PushTribe(this).runTaskAsynchronously(TribeLoader.getTribes());
        return this;
    }

    public Tribe pushSync() {
        Database.pushTribe(this);
        return this;
    }

    public Tribe push(boolean setId) {
        if(setId) {
            this.id = Database.pushTribe(this);
        } else{
            new PushTribe(this).runTaskAsynchronously(TribeLoader.getTribes());
        }
        return this;
    }

    public String getChunkString() {
        if(TribeLoader.ownedChunks.get(this) == null || TribeLoader.ownedChunks.get(this).isEmpty())
            return "";
        Chunk[] x = TribeLoader.ownedChunks.get(this).toArray(new Chunk[1]);
        String fin = "";
        for(int i = 0; i < x.length; i++) {
            fin += x[i].getX() + "," + x[i].getZ();
            if(!((i + 1) >= x.length))
                fin += ";";
        }
        return fin;
    }

    public int getCoins() {
        return this.coins;
    }

    public Tribe addCoins(int a) {
        this.coins += a;
        return this;
    }

    public void setCoins(int a) {
        this.coins = a;
    }

    public TribeRank getRank(OfflinePlayer p) {
        return this.getMembers().get(p);
    }

    public List<OfflinePlayer> getInvitees() {
        return this.invitees;
    }

    public String getInviteeString() {
        OfflinePlayer[] x = invitees.toArray(new OfflinePlayer[1]);
        String fin = "";
        for(int i = 0; i < x.length; i++) {
            if(x[i] != null) {
                fin += x[i].getUniqueId().toString();
                if (!((i + 1) >= x.length))
                    fin += ";";

            }
        }
        return fin;
    }

    public void invite(OfflinePlayer p) {
        this.invitees.add(p);
    }

    public void join(OfflinePlayer p){
        this.members.put(p, TribeRank.MEMBER);
        Message.message(p.getPlayer(), Message.format(Config.join, Config.colorOne, Config.colorTwo, this.getName()));

    }

    public void join(OfflinePlayer p, TribeRank t){
        this.members.put(p, t);
    }

    public List<Chunk> getChunks() {
        return TribeLoader.ownedChunks.get(this);
    }

    public Tribe addChunk(Chunk t) {
        TribeLoader.ownedChunks.get(this).add(t);
        return this;
    }

    public Tribe kick(OfflinePlayer p) {
        if(!this.members.keySet().contains(p)) {
            return this;
        }

        this.members.remove(p);
        return this;
    }

    public List<AbilityType> getAbilities() {
        return this.abilities;
    }

    public String getAbilityString() {
        String fin = "";
        AbilityType[] abilitys = this.abilities.toArray(new AbilityType[1]);
        for(int i = 0; i < abilitys.length; i++) {
            if(abilitys[i] != null) {
                fin += abilitys[i].getText();
                fin += ":";
                fin += Integer.toString(abilitys[i].getMultiplier());
            }
            if((i + 1) < abilitys.length)
                fin += ";";
        }
        return fin;
    }

    public String getAbilityString(boolean user) {
        if(!user)
            return getAbilityString();
        String fin = "";
        AbilityType[] abilitys = this.abilities.toArray(new AbilityType[1]);
        for(int i = 0; i < abilitys.length; i++) {
            if(abilitys[i] != null) {
                fin += abilitys[i].getText();
                fin += ":";
                fin += Integer.toString(abilitys[i].getMultiplier() + 1);
            }
            if((i + 1) < abilitys.length)
                fin += ";";
        }
        return fin;
    }

    public Tribe subtractCoins(int i) {
        this.coins -= i;
        return this;
    }

    public Tribe setTier(TribeTier r) {
        this.tier = r;
        return this;
    }

    public Location getHome() {
        return this.home;
    }

    public String getHomeString() {
        if(this.home == null)
            return "";

        String x = Double.toString(this.home.getX());
        String y = Double.toString(this.home.getY());
        String z = Double.toString(this.home.getZ());

        String yaw = Double.toString(this.home.getYaw());
        String pitch = Double.toString(this.home.getPitch());

        return x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
    }

    public Tribe setHome(Location l) {
        this.home = l;
        return this;
    }

    public void setRank(OfflinePlayer p, TribeRank t) {
        this.members.put(p, t);
    }

    public Tribe addAbility(AbilityType a, Tribes t) {
        this.abilities.add(a);
        updatePlayerAbilities(a, t);
        return this;
    }

    public void updatePlayerAbilities(AbilityType a, Tribes t) {
        for(OfflinePlayer p : this.getMembers().keySet()) {
            if(p.isOnline()) {
                switch (a) {
                    case FIRERESISTANCE:
                        new AbilityFireResistance(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case HASTE:
                        new AbilityHaste(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case HEALTHBOOST:
                        new AbilityHealthBoost(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case INVISIBILITY:
                        new AbilityInvisibility(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case JUMP:
                        new AbilityJump(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case NIGHTVISION:
                        new AbilityNightVision(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case REGEN:
                        new AbilityRegen(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case RESISTANCE:
                        new AbilityResistance(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case SATURATION:
                        new AbilitySaturation(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case SPEED:
                        new AbilitySpeed(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case STRENGTH:
                        new AbilityStrength(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    case WATERBREATHING:
                        new AbilityWaterBreathing(p.getPlayer(), a.getMultiplier()).runTaskTimer(t, 0, 20);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
