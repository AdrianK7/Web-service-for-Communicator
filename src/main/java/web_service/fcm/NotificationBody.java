package web_service.fcm;

public class NotificationBody {
	private String title;
	private String body;
	
	public NotificationBody()
	{
		this.title = "New message!";
		this.body =  "Check your mail.";
	}

	public NotificationBody(String title)
	{
		this.title = title;
		this.body = null;
	}	
	
	public NotificationBody(String title, String body)
	{
		this.title = title;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
