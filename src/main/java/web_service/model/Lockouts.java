package web_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Lockouts")
public class Lockouts {
	@Id
	@Column(name = "id_lockout")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_lockout;
	
	@Column(name = "login_employee")
	private String login_employee;
	
	public int getId() {
		return id_lockout;
	}

	public void setId(int id_lockout) {
		this.id_lockout = id_lockout;
	}

	public String getLoginEmployee() {
		return login_employee;
	}

	public void setLoginEmployee(String login_employee) {
		this.login_employee = login_employee;
	}
}
