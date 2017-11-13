package web_service.fcm;

public class Notification {
	private String to;
	private String priority;
	private NotificationBody notification;
	
	public Notification ()
	{
		this.to = null;
		this.priority = null;
		this.notification = null;
	}
	
	public Notification (String to)
	{
		this.to = to;
		this.priority = "high";
		this.notification = null;
	}
	
	public Notification (String to, NotificationBody notification)
	{
		this.to = to;
		this.priority = "high";
		this.notification = null;
	}
	
	public Notification(String to, String priority, NotificationBody notification)
	{
		this.to = to;
		this.priority = priority;
		this.notification = notification;
	}
	
	public void setTo(String to) 
	{
		this.to = to;
	}
	
	public String getTo() 
	{
		return this.to;
	}
	
	public void setPriority(String priority) 
	{
		this.priority = priority;
	}
	
	public String getPriority() 
	{
		return this.priority;
	}

	public NotificationBody getNotification() {
		return notification;
	}

	public void setNotification(NotificationBody notification) {
		this.notification = notification;
	}
}

