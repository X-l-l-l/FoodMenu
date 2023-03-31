import java.io.Serializable;

public class UserData implements Serializable {
    public String username;
    public String password;
    int id;

    public UserData() {
    }

    public UserData(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

}
