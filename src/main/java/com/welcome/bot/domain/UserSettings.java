package com.welcome.bot.domain;

import javax.persistence.CascadeType;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "usersettings")
public class UserSettings {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  @Column(nullable = false)
	    private String theme;
	 @Column(nullable = false)
	 private String language;
	  public UserSettings() {
		  this.theme="Light";
		  this.language="en";
	    }
	  public UserSettings(UserSettings userSettings) {
	        this.id = userSettings.getId();
	        this.theme = userSettings.getTheme();
	        this.language=userSettings.getLanguage();
	        
	    }
	  public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	  public String getTheme() {
	        return theme;
	    }

	    public void setTheme(String theme) {
	        this.theme= theme;
	    }
	    public String getLanguage() {
	        return language;
	    }

	    public void setLanguage(String language) {
	        this.language= language;
	    }
	  
	 
}