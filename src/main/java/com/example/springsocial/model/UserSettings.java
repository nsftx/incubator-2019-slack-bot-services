package com.example.springsocial.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usersettings")
public class UserSettings {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	  @Column
	  @Enumerated(EnumType.STRING)
	    private Theme theme;
	 @Column
	 @Enumerated(EnumType.STRING)
	 private Language language;
	  public UserSettings() {
		  this.theme=Theme.light;
		  this.language=Language.en;
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
	  public Theme getTheme() {
	        return theme;
	    }

	    public void setTheme(Theme theme) {
	        this.theme= theme;
	    }
	    public Language getLanguage() {
	        return language;
	    }

	    public void setLanguage(Language language) {
	        this.language= language;
	    }
	  
	 
}
