package k4unl.minecraft.colorchat.lib;


public class User {
	private SpecialChars userColor;
	private String tag;
	private String nick;
	private String realUserName;
	private boolean hasNick;
	
	public User(String _username, SpecialChars _userColor, String _nick, String _tag){
		realUserName = _username;
		userColor = _userColor;
		nick = _nick;
		tag = _tag;
	}
	
	public User(String _username) {
		realUserName = _username;
		userColor = SpecialChars.GOLD;
	}

	public String getUserName() {
		return realUserName;
	}
	
	public String getTag(){
		return tag;
	}
	
	public String getNick(){
		return nick;
	}
	
	public SpecialChars getColor(){
		return userColor;
	}
	
	public void setNick(String newNick){
		nick = newNick;
		hasNick = true;
	}
	
	public void resetNick(){
		hasNick = false;
	}
	
	public void setUserColor(SpecialChars newColor){
		userColor = newColor;
	}

	public boolean hasNick() {
		return hasNick;
	}
}
