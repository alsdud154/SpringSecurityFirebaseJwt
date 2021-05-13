package kr.co.velnova.securityfirebase.entity;

import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@ToString
public class CustomAuthentication implements Authentication {

    private final Collection<? extends GrantedAuthority> authorities;
    private final Object credentials;
    private final Object details;
    private final Object principal;
    private boolean isAuthenticated;
    private final String name;

    public CustomAuthentication(Collection<? extends GrantedAuthority> authorities, Object credentials, Object details, Object principal, boolean isAuthenticated, String name) {
        this.authorities = authorities;
        this.credentials = credentials;
        this.details = details;
        this.principal = principal;
        this.isAuthenticated = isAuthenticated;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
