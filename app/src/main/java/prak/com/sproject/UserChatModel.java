package prak.com.sproject;

/**
 * Created by Yoza on 27/08/2016.
 */

public class UserChatModel {
    int _id;
    String _token;
    String _name_chat;

    public UserChatModel() {
    }

    public UserChatModel(int _id, String _token, String _name_chat) {
        this._id = _id;
        this._token = _token;
        this._name_chat = _name_chat;
    }

    public UserChatModel(String _token, int _id) {
        this._token = _token;
        this._id = _id;
    }

    public UserChatModel(int _id, String _name_chat) {
        this._id = _id;
        this._name_chat = _name_chat;
    }

    public int getID() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public String get_name_chat() {
        return _name_chat;
    }

    public void set_name_chat(String _name_chat) {
        this._name_chat = _name_chat;
    }
}
