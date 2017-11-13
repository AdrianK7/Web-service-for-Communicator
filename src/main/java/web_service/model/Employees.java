package web_service.model;
	
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Employees")
public class Employees {

	@Id
	@Column(name="id_employee")
	private int id_employee;
	
	@Column(name="first_name")
	private String first_name;
	
	@Column(name="second_name")
	private String second_name;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;
	
	@Column(name="fcm_token")
	private String fcm_token;
	
	@Column(name="private_key")
	private String private_key;
	
	@Column(name="public_key")
	private String public_key;
	
	@Column(name="sign_key")
	private String sign_key;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Dept_Emp",
	joinColumns = @JoinColumn(name = "id_employee", referencedColumnName = "id_employee"),
	inverseJoinColumns = @JoinColumn(name = "id_department", referencedColumnName = "id_department"))
	private Set<Departments> departments;

	@JsonBackReference
	public Set<Departments> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Departments> departments) {
		this.departments = departments;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Dept_Managers",
	joinColumns = @JoinColumn(name = "id_manager", referencedColumnName = "id_employee"),
	inverseJoinColumns = @JoinColumn(name = "id_department", referencedColumnName = "id_department"))
	private Set<Departments> managerDepartments;

	@JsonBackReference
	public Set<Departments> getManagerDepartments() {
		return managerDepartments;
	}

	public void setManagerDepartments(Set<Departments> managerDepartments) {
		this.managerDepartments = managerDepartments;
	}
    
	public int getId() {
		return id_employee;
	}

	public void setId(int id_employee) {
		this.id_employee = id_employee;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getSecondName() {
		return second_name;
	}

	public void setSecondName(String second_name) {
		this.second_name = second_name;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	/*public String getPassword() {
		return null;
	}

	public void setPassword(String password) {
		this.password = password;
	}*/

	public String getFcmToken() {
		return fcm_token;
	}

	public void setFcmToken(String fcm_token) {
		this.fcm_token = fcm_token;
	}

	public String getPrivateKey() {
		return private_key;
	}

	public void setPrivateKey(String private_key) {
		this.private_key = private_key;
	}
	

	public String getPublicKey() {
		return public_key;
	}

	public void setPublicKey(String public_key) {
		this.public_key = public_key;
	}
	
	public String getSigningKey() {
		return sign_key;
	}

	public void setSigningKey(String sign_key) {
		this.sign_key = sign_key;
	}
}
