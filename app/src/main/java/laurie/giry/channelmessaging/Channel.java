package laurie.giry.channelmessaging;

/**
 * Created by Wibou on 08/02/2016.
 */
public class Channel {
    private int channelID;
    private String name;
    private int connectedusers;

    public Channel(){}

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConnectedusers() {
        return connectedusers;
    }

    public void setConnectedusers(int connectedusers) {
        this.connectedusers = connectedusers;
    }


}
