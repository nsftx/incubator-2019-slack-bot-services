package com.welcome.bot.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TranslationSettings {
	 @NotBlank
	    @Email
	    private String title;

	    @NotBlank
	    private String theme;
	    @NotBlank
	    private String selectColor;
	    @NotBlank
	    private String selectLanguage;
	    @NotBlank
	    private String language;
	    @NotBlank
	    private String save;
	    public void setSave(String save) {
	        this.save=save;
	    }
	    public String getSave() {
	        return save;
	    }
	    public void setTitle(String title) {
	        this.title=title;
	    }
	    public String getTitle() {
	        return title;
	    }
	    public void setLanguage(String language) {
	        this.language= language;
	    }
	    public String getLanguage() {
	        return language;
	    }
	    public String getTheme() {
	        return theme;
	    }

	    public void setTheme(String theme) {
	        this.theme= theme;
	    }
	    
	    public void setSelectColor(String selectColor) {
	    	this.selectColor = selectColor;
	    }
	    public String getSelectColor() {
	        return selectColor;
	    }
	    public String getSelectLanguage() {
	        return selectLanguage;
	    }
	    public void setSelectLanguage(String selectLanguage) {
	        this.selectLanguage = selectLanguage;
	    }
	  
	  
}
