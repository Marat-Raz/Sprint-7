package courierModel;

public class CouriersLogin {
    private String login;
    private String password;

    public CouriersLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public CouriersLogin() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
