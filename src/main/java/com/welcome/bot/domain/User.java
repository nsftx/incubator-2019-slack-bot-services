package com.welcome.bot.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.welcome.bot.models.AuthProvider;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(length = 10485760)
    @Size(min = 5, max = 10485760)
    private String name;

    @Email
    @Column(nullable = false,length = 200)
    @Size(min = 2, max = 200)
    private String email;
   
    @Column(length = 10485760)
    @Size(min = 20, max = 10485760)
    private String imageUrl;
    @OneToOne
    @JoinColumn(name = "user_settings_id")
    private UserSettings userSettings;
    @OneToOne
    @JoinColumn(name = "invite_id")
    private Invite invite;
   
    
   
    
    
    @Column(name="role",length = 10485760)
    @Size(min = 2, max = 10485760)
    private String role;

   
    protected User() {
    	this.userSettings=new UserSettings();
    	this.role="USER";
            this.invite=new Invite();
    }
    public User(String email) {
    	this.email=email;
    	this.invite=new Invite();
    	this.userSettings=new UserSettings();
    	this.invite=new Invite();
    }
    public User(User user) {
    	 this.invite=user.getInvite();
        this.imageUrl = user.getImageUrl();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.name = user.getName();
        this.id = user.getId();
        
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Invite getInvite() {
        return invite;
    }

    public void setInvite(Invite invite) {
        this.invite = invite;
    }
    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings= userSettings;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public void setRole(String role) {
    	this.role=role;
    }
    public String getRole() {
    	return this.role;
    }
}
