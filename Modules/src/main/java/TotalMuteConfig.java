import java.util.ArrayList;
import java.util.List;

public class TotalMuteConfig {

    private String prefix = "&2[&aTotalMute&2] &a";
    private String noChat = "&cYou are muted and can not talk!";
    private String blockedCommand = "&cYou are muted and can not do that command!";
    private String noPerm = "&cYou do no have permission to do that!";
    private String specifyPlayer = "&cYou must specify a player!";
    private String invalidArgs = "&cInvalid arguments!";
    private String invalidPlayer = "&cThat player does not exist or has not played before!";
    private String alreadyMuted = "&cThat player has already been muted!";
    private String notMuted = "&cThat player is not muted!";
    private String canNotMute = "&cYou can not mute that player!";
    private String muted = "You have been muted!";
    private String mutedPlayer = "You have muted '&2{player}&a' till '&2{timestamp}&a!'";
    private String unMuted = "You have been un-muted!";
    private String unMutedPlayer = "You have un-muted '&2{player}&a'!";

    private boolean muteFromChat = true;
    private List<String> blockedCommands = new ArrayList<String>();

    public String getPrefix() {
        return prefix;
    }

    public String getNoChat() {
        return noChat;
    }

    public String getBlockedCommand() {
        return blockedCommand;
    }

    public boolean muteFromChat() {
        return muteFromChat;
    }

    public String getNoPerm() {
        return noPerm;
    }

    public String getSpecifyPlayer() {
        return specifyPlayer;
    }

    public String getInvalidArgs() {
        return invalidArgs;
    }

    public String getInvalidPlayer() {
        return invalidPlayer;
    }

    public String getAlreadyMuted() {
        return alreadyMuted;
    }

    public String getNotMuted() {
        return notMuted;
    }

    public String getCanNotMute() {
        return canNotMute;
    }

    public String getMuted() {
        return muted;
    }

    public String getMutedPlayer() {
        return mutedPlayer;
    }

    public String getUnMuted() {
        return unMuted;
    }

    public String getUnMutedPlayer() {
        return unMutedPlayer;
    }

    public List<String> getBlockedCommands() {
        return blockedCommands;
    }

}
