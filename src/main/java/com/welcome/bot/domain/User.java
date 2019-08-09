package com.welcome.bot.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.welcome.bot.models.AuthProvider;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Email
    @Column(nullable = false)
    private String email;
    @Column
    private String imageUrl;
    
    @OneToOne
    @MapsId
    private UserSettings userSettings;
   
    @Column(name="role")
    private String role;

   
    protected User() {
    	this.userSettings=new UserSettings();
    	//this.email=email;
    	this.role="USER";
    }
    public User(String email) {
    	this.email=email;
    }
    public User(User user) {
        this.imageUrl = user.getImageUrl();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.name = user.getName();
        this.id = user.getId();
        this.userSettings=user.getUserSettings();
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings=userSettings;
    }
    public void setRole(String role) {
    	this.role=role;
    }
    public String getRole() {
    	return this.role;
    }
}
