package data;

public class MessageClackData extends ClackData {
	
	private String message;
	
	MessageClackData(String userName, String message, int type) {
		super(userName, type);
		this.message = message;
	}
	
	MessageClackData(){
		this("Anon", "", 0);
	}
	
	public String getData() {
		return this.message;
	}
	
}