package prak.com.sproject;

/**
 * Created by Yoza on 16/08/2016.
 */

public class ChatMessage {
    private long id;
    private boolean isMe;
    private String message;
    private String chatname;
    private Long userId;
    private String dateTime;

    public String getChatname(){ return chatname;}

    public void setChatname(String chatname){ this.chatname=chatname; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsme() {
        return isMe;
    }

    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }
}