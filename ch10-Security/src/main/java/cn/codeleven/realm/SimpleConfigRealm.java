package cn.codeleven.realm;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.users.MemoryUserDatabase;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/7/7
 */
public class SimpleConfigRealm implements Realm {
    private Container container;

    public SimpleConfigRealm() {

    }

    public SimpleConfigRealm(String path) {
        createDatabase(path);
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

    @Override
    public Principal authenticate(String username, String credentials) {
        User user = database.findUser(username);
        System.err.println("SimpleConfigRealm: user->" + user);

        if(user == null){
            return null;
        }

        System.err.println("SimpleConfigRealm: username->" + username + ",password->" + credentials);

        Iterator<Role> iterator = database.getRoles();
        List<String> roleCombined = new ArrayList<>();
        while(iterator.hasNext()){
            Role role = iterator.next();
            String roleName = role.getName();
            if(!roleCombined.contains(roleName)){
                roleCombined.add(roleName);
            }
        }

        return new GenericPrincipal(this, user.getUsername(), user.getPassword(), roleCombined);
    }

    @Override
    public Principal authenticate(String username, byte[] credentials) {
        return null;
    }


    MemoryUserDatabase database;
    private void createDatabase(String path) {
        database = new MemoryUserDatabase();
        database.setPathname(path);
        try {
            database.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return true;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
