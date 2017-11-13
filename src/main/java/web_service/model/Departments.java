package web_service.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="Departments")
public class Departments{

	@Id
	@Column(name="id_department")
	private int id_department;
	
	@Column(name="name")
	private String name;
		
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Dept_Emp",
	joinColumns = @JoinColumn(name = "id_department", referencedColumnName = "id_department"),
	inverseJoinColumns = @JoinColumn(name = "id_employee", referencedColumnName = "id_employee"))
    private Set<Employees> employees;

    @JsonManagedReference
	public Set<Employees> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Employees> employees) {
		this.employees = employees;
	}
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Dept_Managers",
	joinColumns = @JoinColumn(name = "id_department", referencedColumnName = "id_department"),
	inverseJoinColumns = @JoinColumn(name = "id_manager", referencedColumnName = "id_employee"))
    private Set<Employees> managers;

    @JsonManagedReference
	public Set<Employees> getManagers() {
		return this.managers;
	}

	public void setManagers(Set<Employees> managers) {
		this.managers = managers;
	}
		
	public int getId() {
		return id_department;
	}

	public void setId(int id_deparment) {
		this.id_department = id_deparment;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

