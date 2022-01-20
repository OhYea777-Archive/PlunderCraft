import org.bukkit.OfflinePlayer;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity @Table(name = "total_mute_players")
public class TotalMutePlayer {

    @Id
    private UUID id;

    @Nullable
    private Timestamp timeToUnmute;

    public TotalMutePlayer() { }

    public TotalMutePlayer(OfflinePlayer player, Timestamp timeToUnmute) {
        this.id = player.getUniqueId();
        this.timeToUnmute = timeToUnmute;
    }

    public TotalMutePlayer(OfflinePlayer player) {
        this.id = player.getUniqueId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Nullable
    public Timestamp getTimeToUnmute() {
        return timeToUnmute;
    }

    public void setTimeToUnmute(@Nullable Timestamp timeToUnmute) {
        this.timeToUnmute = timeToUnmute;
    }

    public boolean checkMute() {
        Timestamp now = new Timestamp(new Date().getTime());

        return timeToUnmute != null && now.after(timeToUnmute);
    }

}
