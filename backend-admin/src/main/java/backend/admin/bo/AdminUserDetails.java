package backend.admin.bo;

import backend.mbg.model.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AdminUserDetails implements UserDetails {
    private Admin admin;
    public AdminUserDetails(Admin admin){
        this.admin=admin;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }
    @Override
    public String getPassword(){
        return admin.getPassword();
    }
    @Override
    public String getUsername(){
        return admin.getUsername();
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return true;
    }

}
