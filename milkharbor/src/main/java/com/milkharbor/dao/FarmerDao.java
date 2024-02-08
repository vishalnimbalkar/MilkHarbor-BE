package com.milkharbor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milkharbor.encrypt.EncryptDecrypt;
import com.milkharbor.entity.User;
import com.milkharbor.entity.ResponseLogin;

@Repository
public class FarmerDao {

	@Autowired
	SessionFactory sf;
	EncryptDecrypt encryptDecrypt;
	
	//registration
	public boolean register(User farmer) throws Exception {
	    Session session = sf.openSession();
	    Transaction transaction = null;
	    try {
	        transaction = session.beginTransaction();
	    	User farmerToUpdate = session.get(User.class, farmer.getId());
	    	farmerToUpdate.setM_no(farmer.getM_no());
	    	farmerToUpdate.setName(farmer.getName());
	    	farmerToUpdate.setPassword(farmer.getPassword());
	    	farmerToUpdate.setStatus(farmer.getStatus());
	        transaction.commit();
	        return true;
	        
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback(); 
	        }
	        return false;
	  
	    } finally {
	    	session.clear();
	        session.close();
	    }
	  
	}
	
	//get farmer by id
	public String getUser(int id) {
		Session s = sf.openSession();
		ResponseLogin responseLogin = new ResponseLogin();
		User user=null;
		try {
		    Criteria cr = s.createCriteria(User.class);
		    cr.add(Restrictions.or(Restrictions.eq("id", id)));
		    List<User> admins = cr.list();
		   if(!admins.isEmpty()) {
			    user=admins.get(0);
		   }
		   s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			s.close();
		}
		   return user.getName();
	}
	
}
