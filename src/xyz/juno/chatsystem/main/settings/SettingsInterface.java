package xyz.juno.chatsystem.main.settings;

public interface SettingsInterface {
	String destroy(boolean prefix);
	
	public enum Settings {
		HELP("messages.help", false),
		MUST_BE_PLAYER("messages.must-be-player", true),
		NO_PERMISSION("messages.no-permission", true),
		RELOAD_SUCCESS("messages.reload-success", true),
		RELOAD_ERROR("messages.reload-error", true),
		HAD_CLEAN("messages.had-clean-success", true),
		PLAYER_IS_NOT_EXIST("messages.player-is-not-exist", true),
		CLEARALL_SUCCESS("messages.clearall-success", true),
		WAIT_TO_CHAT("messages.wait-to-chat", true),
		CLEAR_SUCCESS("messages.clear-success", true);
		
		private String path;
		private boolean prefix;
		
		private Settings(String path, boolean prefix) {
			this.path = path;
			this.prefix = prefix;
		}
		
		public String getPath() {
			return path;
		}
		
		public boolean isPrefix() {
			return prefix;
		}
	}
}