package model;

import lombok.Getter;
import lombok.Setter;


public class Client {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	
	public Client(Long id,String firstName, String lastName, String email , String password ,String role ) {
		this.id = id;
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.password=password;
		this.setRole(role);
	}

	public Client(String firstName, String lastName, String email , String password) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.password=password;
	}
	public Client(String firstName, String lastName, String email , String password,String role) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.password=password;
		this.role= role;
	}


	public Client() {
		this.id=-1L;
		this.firstName="";
		this.lastName="";
		this.email="";
		this.password="";
	}
	public Client(Long id ) {
		this.id=id;
		this.firstName="";
		this.lastName="";
		this.email="";
		this.password="";
	}
	 @Override
	    public String toString() {
	        return "Client {" +
	                "id=" + id +
	                ", firstName='" + firstName + '\'' +
	                ", lastName='" + lastName + '\'' +
	                ", email='" + email + '\'' +
	                '}';
	    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

}
