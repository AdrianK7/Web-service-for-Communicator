package web_service.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import web_service.DAO.DatabaseDAO;
import web_service.SpringConf.DBConf;
import web_service.model.*;
import web_service.utilities.RequestInterceptor;
import web_service.fcm.FCMRequest;
import web_service.fcm.FCMResponse;
import web_service.fcm.Notification;
import web_service.fcm.NotificationBody;

@RestController
@PropertySource(value = { "classpath:other.properties" })
public class AppController {
	    
    @Autowired
    private Environment env;
    
    @Autowired
    private FCMRequest request;
    
    @RequestMapping(value = "/get_departments_contacts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Employees>> getDepartmentsContacts(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<List<Employees>>(getDepartmentsContactsFromDB(id), HttpStatus.OK);
    }
    
    @CachePut
    private List<Employees> getDepartmentsContactsFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Employees> DAO = context.getBean(DatabaseDAO.class);
		List<Employees> empE = DAO.list("select e from Employees e join e.departments r where r.id='" + id + "'");
		List<Employees> empM = DAO.list("select e from Employees e join e.managerDepartments r where r.id='" + id + "'");
		empE.addAll(empM);    
		context.close();
		return empE;
    }
    
    @RequestMapping(value = "/get_departments", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Departments>> getDepartments(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<List<Departments>>(getDepartmentsFromDB(id), HttpStatus.OK);
    }

    @CachePut
    private List<Departments> getDepartmentsFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Departments> DAO = context.getBean(DatabaseDAO.class);
		List<Departments> deptE = DAO.list("select e from Departments e join e.employees r where r.id='" + id +"'");
		List<Departments> deptM = DAO.list("select e from Departments e join e.managers h where h.id = '" + id +"'");
		List<Departments> dept = new ArrayList<>();
		dept.addAll(deptE);
		dept.addAll(deptM);
		context.close();
		return dept;
    }
    
	@RequestMapping(value = "/get_employee", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Employees> getEmployee(@RequestParam(value="id", defaultValue="error") String id) {
		return new ResponseEntity<Employees>(getEmployeeFromDB(id), HttpStatus.OK);
	}
	
	@CachePut
	private Employees getEmployeeFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Employees> DAO = context.getBean(DatabaseDAO.class);
		Employees employee = DAO.select("select e from Employees e where e.id='" + id + "'");
		context.close();
		return employee;
	}
	
	@RequestMapping(value = "/get_employee_by_login", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Employees> getEmployeeByLogin(@RequestParam(value="login", defaultValue="error") String login) {
		return new ResponseEntity<Employees>(getEmployeeByLoginFromDB(login), HttpStatus.OK);
	}
    
	@CachePut
	private Employees getEmployeeByLoginFromDB(String login) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Employees> DAO = context.getBean(DatabaseDAO.class);
		Employees employee = DAO.select("select e from Employees e where e.login='" + login + "'");
		context.close();
		return employee;
	}
	
    @RequestMapping(value = "/get_id_employee", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Integer> getEmployeeId(@RequestParam(value="login", defaultValue="fail") String login) {
    	return new ResponseEntity<Integer>(getEmployeeIdFromDB(login), HttpStatus.OK);
    }
    
	@CachePut
	private Integer getEmployeeIdFromDB(String login) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Integer> DAO = context.getBean(DatabaseDAO.class);
		Integer employeeId = DAO.select("select e.id_employee from Employees e where e.login='" + login + "'");
		context.close();
		return employeeId;
	}
    
    @RequestMapping(value = "/get_first_name", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getEmployeeFirstName(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<String>(getEmployeeFirstNameFromDB(id), HttpStatus.OK);
    }
    
	@CachePut
	private String getEmployeeFirstNameFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String employeeFirstName = DAO.select("select e.first_name from Employees e where e.id='" + id + "'");
		context.close();
		return employeeFirstName;
	}
      
    @RequestMapping(value = "/get_second_name", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getEmployeeSecondName(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<String>(getEmployeeSecondNameFromDB(id), HttpStatus.OK);
    }
    
	@CachePut
	private String getEmployeeSecondNameFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String employeeSecondName = DAO.select("select e.second_name from Employees e where e.id='" + id + "'");
		context.close();
		return employeeSecondName;
	}
    
    @RequestMapping(value = "/get_fcm_token", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getFcmToken(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<String>(getFcmTokenFromDB(id), HttpStatus.OK);
    }
    
	@CachePut("fcm_token")
	private String getFcmTokenFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String employeeFcmToken = DAO.select("select e.fcm_token from Employees e where e.id='" + id + "'");
		context.close();
		return employeeFcmToken;
	}
    
    @RequestMapping(value = "/get_private_key", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getPrivateKey(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<String>(getPrivateKeyFromDB(id), HttpStatus.OK);
    }
    
	@CachePut("private_key")
	private String getPrivateKeyFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String employeePrivateKey = DAO.select("select e.private_key from Employees e where e.id='" + id + "'");
		context.close();
		return employeePrivateKey;
	}
    
    @RequestMapping(value = "/get_public_key", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getPublicKey(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<String>(getPublicKeyFromDB(id), HttpStatus.OK);
    }
    
	@CachePut("public_key")
	private String getPublicKeyFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String employeePublicKey = DAO.select("select e.public_key from Employees e where e.id='" + id + "'");
		context.close();
		return employeePublicKey;
	}
    
    @RequestMapping(value = "/get_sing_key", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getSigningKey(@RequestParam(value="id", defaultValue="fail") String id) {
    	return new ResponseEntity<String>(getSigningKeyFromDB(id), HttpStatus.OK);
    }
    
	@CachePut("signing_key")
	private String getSigningKeyFromDB(String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAO = context.getBean(DatabaseDAO.class);
		String employeeSigningKey = DAO.select("select e.sign_key from Employees e where e.id='" + id + "'");
		context.close();
		return employeeSigningKey;
	}
              
    @RequestMapping(value = "/get_messages_recieved", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Messages>> getMessagesRecived(@RequestParam(value="id", defaultValue="fail") String id) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Messages> DAO = context.getBean(DatabaseDAO.class);
		List<Messages> messages = DAO.list("select m from Messages m where m.receiver = '" + id + "' AND m.received='0'");
		context.close();
		return new ResponseEntity<List<Messages>>(messages, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/message_sent", method = RequestMethod.POST, produces = "application/json")
    public void getMessagesSent(@RequestBody MessagesFromClient message) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<String> DAOFcm = context.getBean(DatabaseDAO.class);
		String employeeFcmToken = DAOFcm.select("select e.fcm_token from Employees e where e.id='" + message.getReceiver() + "'");
		DatabaseDAO<MessagesFromClient> DAOMessage = context.getBean(DatabaseDAO.class);
		List<RequestInterceptor> headers = new ArrayList<RequestInterceptor>(); 
		headers.add(new RequestInterceptor("Authorization", "key=" + (String) env.getProperty("fcm.server_token")));
		headers.add(new RequestInterceptor("Content-Type", "application/json"));
		Future<FCMResponse> test = request.callForPush(new Notification(employeeFcmToken, new NotificationBody()), new RestTemplate(), headers);
		Boolean saved = DAOMessage.save(message);
		context.close();
    }
    
    @RequestMapping(value = "/message_update", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    public void getUpdate(@RequestBody MessagesFromClient message) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Messages> DAO = context.getBean(DatabaseDAO.class);
		Boolean saved = DAO.updateRecived(message.getId(), message.getReceived());
		context.close();
    }
    
    @RequestMapping(value = "/fcm_update", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    public void getUpdateFcm(@RequestBody Object[] response) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Employees> DAO = context.getBean(DatabaseDAO.class);
		Boolean saved = DAO.updateFcmToken((int)response[0], (String) response[1]);
		clearFcmTokenFromCache(response[0].toString()); 
		context.close();
    }
    
    @CacheEvict("fcm_token")
    private void clearFcmTokenFromCache(String id) {
    	
    }
    
    
    @RequestMapping(value = "/update_keys", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    public void updateKeys(@RequestBody Object[] response) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DBConf.class);
		DatabaseDAO<Employees> DAO = context.getBean(DatabaseDAO.class);
		Boolean saved = DAO.updateKeys((int)response[0], (String) response[1], (String) response[2], (String) response[3]);
		clearKeysFromCache(response[0].toString());
		context.close();
    }
    
    @CacheEvict({"private_key", "public_key", "signing_key"})
    private void clearKeysFromCache(String id) {
    	
    }
}
