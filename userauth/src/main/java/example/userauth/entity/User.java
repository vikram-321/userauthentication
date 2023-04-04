package example.userauth.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails{

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String userId;
	
	@Column
	private String userPassword;
	
//	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name="user",referencedColumnName = "userId"),inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "userId"))
//	private Set<Role> roles = new HashSet<>();
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	public User() {
	//	super();
	}

	

	public User(String userId, String userPassword, Roles role) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
		this.role = role;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
	
	
	public Roles getRole() {
		return role;
	}



	public void setRole(Roles role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", userPassword=" + userPassword + ", roles=" + role + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//List <SimpleGrantedAuthority> authorities =this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
