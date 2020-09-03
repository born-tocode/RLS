package pl.kirg.rls.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Table
        (name = "users",
         uniqueConstraints =
                 {@UniqueConstraint
                          (
                                  name = "UNQ_EMAIL_USERNAME",
                                  columnNames = {"username", "email"}
                          )
                 }
        )
public class User implements UserDetails
{

    public static final String USERNAME_PATTERN = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    @Id
    @Column(name = "username", length = 20, updatable = false)
    @Pattern
            (
                regexp = USERNAME_PATTERN,
                message = "Username must have minimum 5 and max 20 chars. Permitted" +
                          " chars are: lowercase and uppercase letters, digits, dot" +
                          " and underscore, except dot and underscore at the end and begin."
            )
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Boolean enabled;

    @CreationTimestamp
    @DateTimeFormat
    @Column(name = "registration", updatable = false)
    private final Timestamp timestamp;

    @OneToOne
            (fetch = FetchType.EAGER,
             cascade = CascadeType.ALL,
             mappedBy = "user",
             optional = false
            )
    private Authority authority;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(authority);
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "User{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", enabled=" + enabled +
               ", registration=" + timestamp +
               ", authority=" + authority.getAuthority() +
               '}';
    }
}

