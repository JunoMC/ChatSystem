package xyz.juno.chatsystem.main.cmds;

public enum Arguments {
	HELP("(help|\\?)", "chatsystem.help"),
	INFO("(info)", "chatsystem.info"),
	RELOAD("(reload|rl)", "chatsystem.reload"),
	CLEARALL("(clearall)", "chatsystem.clearall"),
	CLEAR("(clear)", "chatsystem.clear");
	
	private String regex;
	private String permission;
	
	private Arguments(String regex, String permission) {
		this.regex = regex;
		this.permission = permission;
	}
	
	public String getArgument() {
		return regex;
	}
	
	public String getPermission() {
		return permission;
	}
}