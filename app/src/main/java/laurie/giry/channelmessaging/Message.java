package laurie.giry.channelmessaging;

/**
 * Created by Wibou on 07/03/2016.
 */
public class Message {

    int userID;
    String message;
    String date;
    String imageUrl;
    String username;

    public int getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
