package cn.codeleven.realm;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.User;
import org.apache.catalina.realm.GenericPrincipal;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/7/7
 */
public class SimpleRealm implements Realm {
    Map<String, User> users = new HashMap<>();
    private Container container;

    public SimpleRealm() {
        createUser();
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    private void createUser() {
        User user1 = new User("test1", "password1");
        user1.addRole("Manager");
        user1.addRole("User");
        User user2 = new User("test1", "password1");
        user2.addRole("User");
        User user3 = new User("test1", "password1");
        user3.addRole("User");

        users.put(user1.getUsername(), user1);
        users.put(user2.getUsername(), user2);
        users.put(user3.getUsername(), user3);

    }

    @Override
    public Principal authenticate(String username, String credentials) {
        if (username == null || credentials == null) {
            return null;
        }
        User user = getUser(username);
        if (user == null) {
            return null;
        }

        return new GenericPrincipal(this, user.username, user.password);
    }

    private User getUser(String username) {
        return users.get(username);
    }

    @Override
    public Principal authenticate(String username, byte[] credentials) {
        return null;
    }

    @Override
    public Principal authenticate(String username, String digest, String nonce, String nc, String cnonce, String qop, String realm, String md5a2) {
        return null;
    }

    @Override
    public Principal authenticate(X509Certificate[] certs) {
        return null;
    }

    @Override
    public boolean hasRole(Principal principal, String role) {
        if(principal == null || role == null || !(principal instanceof GenericPrincipal)){
            return false;
        }

        if(!(((GenericPrincipal) principal).getRealm() == this)){
            return false;
        }

        return ((GenericPrincipal) principal).hasRole(role);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    public static class User {
        private String username;
        private String password;
        private List<String> roles = new ArrayList<>();
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void addRole(String role) {
            roles.add(role);
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
