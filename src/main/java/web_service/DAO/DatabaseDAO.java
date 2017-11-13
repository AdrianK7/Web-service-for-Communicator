package web_service.DAO;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import web_service.model.Employees;
import web_service.model.Messages;

@Repository
public class DatabaseDAO<T> {
	
	
	private SessionFactory sessionFactory;
	
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
    }
    
    @Async
	public boolean save(T t) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		boolean saved = false;
		try 
		{
			tx = session.beginTransaction();
			session.persist(t);
			tx.commit();
			saved =  true;
		}
		catch (HibernateException e)
		{
			if (tx != null)
					tx.rollback();
			saved = false;
			throw e;
		}
		finally
		{
			session.close();
		}
		return saved;

	}
    @Async
	public List<T> list(String query) {
		Session session = this.sessionFactory.openSession();
		List<T> list = null;
		try 
		{
			list = session.createQuery(query).list();
			return list;
		}
		catch (HibernateException e)
		{
			throw e;
		}
		finally
		{
			session.close();
		}
	}
    
    @Async
	public T select(String query) {
		Session session = this.sessionFactory.openSession();
		T object = null;
		try 
		{
			List<T> list = session.createQuery(query).list();
			if(!list.isEmpty() && !(list.size() > 1)) {
				object = list.get(0);
			}
			return object;
		}
		catch (HibernateException e)
		{
			throw e;
		}
		finally
		{
			session.close();
		}
	}
    
    @Async
	public void deletePersistent(Serializable param, Class<T> t) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();
			Object persistentInstance = session.load(t, param);
			if (persistentInstance != null) 
			{
				session.delete(persistentInstance);
			}
			tx.commit();
		}
		catch (HibernateException e)
		{
			if (tx != null)
					tx.rollback();
			throw e;
		}
		finally
		{
			session.close();
		}
	}
    
    @Async
    public boolean updateRecived(Serializable id, long received) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		Messages message;
		boolean update = false;
		try {
			tx = session.beginTransaction();
			message = session.load(Messages.class, id);
			tx.commit();
			Transaction tx2 = session.beginTransaction();
			message.setReceived(received);
			session.update(message);
			tx2.commit();
			update = true;
		}
		catch (HibernateException e)
		{
			throw e;
		}
		return update;

    }
    
    
    @Async
    public boolean updateFcmToken(Serializable id, String fcmToken) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		Employees employee;
		boolean update = false;
		try {
			tx = session.beginTransaction();
			employee = session.load(Employees.class, id);
			tx.commit();
			Transaction tx2 = session.beginTransaction();
			employee.setFcmToken(fcmToken);
			session.update(employee);
			tx2.commit();
			update = true;
		}
		catch (HibernateException e)
		{
			throw e;
		}
		return update;

    }
    
    @Async
    public boolean updateKeys(Serializable id, String publicEncryptKey, String publicSignKey, String private_key) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		Employees employee;
		boolean update = false;
		try {
			tx = session.beginTransaction();
			employee = session.load(Employees.class, id);
			tx.commit();
			Transaction tx2 = session.beginTransaction();
			employee.setPrivateKey(private_key);
			employee.setPublicKey(publicEncryptKey);
			employee.setSigningKey(publicSignKey);
			session.update(employee);
			tx2.commit();
			update = true;
		}
		catch (HibernateException e)
		{
			throw e;
		}
		return update;

    }
    
}
