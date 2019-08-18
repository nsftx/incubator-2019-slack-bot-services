package com.welcome.bot.payload;

import javax.validation.constraints.NotBlank;

public class TranslationSettings {
	@NotBlank
	private String text;
	@NotBlank
	private String settings;
	@NotBlank
	private String active;
    @NotBlank
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
    private String messages;
	@NotBlank
	private String schedules;
	@NotBlank
	private String triggers;
	@NotBlank
	private String users;
	@NotBlank
	private String repeat;
	@NotBlank
	private String channel;
	@NotBlank
	private String trigger;
	@NotBlank
	private String message;
	@NotBlank
	private String nextRun;
	@NotBlank
	private String activeAt;
	@NotBlank
	private String name;
	@NotBlank
	private String role;
    @NotBlank
    private String save;

	public TranslationSettings() {
	}

	public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(String selectColor) {
        this.selectColor = selectColor;
    }

    public String getSelectLanguage() {
        return selectLanguage;
    }

    public void setSelectLanguage(String selectLanguage) {
        this.selectLanguage = selectLanguage;
    }


    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

	public String getSchedules() {
		return schedules;
	}

	public void setSchedules(String schedules) {
		this.schedules = schedules;
	}

	public String getTriggers() {
		return triggers;
	}

	public void setTriggers(String triggers) {
		this.triggers = triggers;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNextRun() {
		return nextRun;
	}

	public void setNextRun(String nextRun) {
		this.nextRun = nextRun;
	}

	public String getActiveAt() {
		return activeAt;
	}

	public void setActiveAt(String activeAt) {
		this.activeAt = activeAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}
