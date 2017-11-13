package web_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Messages")
public class MessagesFromClient {

		@Id
		@Column(name = "id_message")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id_message;
		
		@Column(name = "sent")
		private long sent;
		
		@Column(name = "received")
		private long received;
		
		@Column(name = "sender")
		private int sender;
		
		@Column(name = "receiver")
		private int receiver;

		@Column(name = "symmetric_encrypt_key")
		private String symmetric_encrypt_key;
		
		@Column(name = "message")
		private String message;	
		
		@Column(name = "signature")
		private String signature;	


    public int getId() {
        return id_message;
    }

    public void setId(int id_message) {
        this.id_message = id_message;
    }

    public long getSent() {
        return sent;
    }

    public void setSent(long sent) {
        this.sent = sent;
    }

    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getEncryptionKey() {
        return symmetric_encrypt_key;
    }

    public void setEncryptionKey(String symmetric_encrypt_key) {
        this.symmetric_encrypt_key = symmetric_encrypt_key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
