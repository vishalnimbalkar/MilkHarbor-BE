package com.milkharbor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milkharbor.entity.MilkCollection;
import com.milkharbor.entity.ResponseLogin;
import com.milkharbor.entity.User;

@Repository
public class MilkCollectionDao {


	@Autowired
	private SessionFactory sf;

	public boolean collectMilk(MilkCollection milkCollection) throws ParseException {
		Session session = sf.openSession();
	    Transaction transaction = null;
	    try {
	        transaction = session.beginTransaction();
	        session.save(milkCollection);
	        transaction.commit();
	        return true;
	        
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback(); 
	        }
	        return false;
	  
	    } finally {
	        session.close();
	    }  
	}
	
	public List<MilkCollection> getMilkCollectionDetails(){
		Session s = sf.openSession();
		List<MilkCollection> milkCollection=null;
		try {
		    Criteria cr = s.createCriteria(MilkCollection.class);
		     milkCollection = cr.list();
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			s.close();
		}	
			return milkCollection;
	}
	
	public List<MilkCollection> getSupplyMilkDetails(int id){
		Session s = sf.openSession();
		List<MilkCollection> milkCollection=null;
		try {
		    Criteria cr = s.createCriteria(MilkCollection.class);
		    cr.add(Restrictions.eq("f_id",id));
		    milkCollection = cr.list();
		   s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			s.close();
		}
		   return milkCollection;
	}
	
	public boolean updateMilkCollection(MilkCollection milkCollection) {
		Session s = sf.openSession();
		Transaction tra=s.beginTransaction();
		try {
                s.update(milkCollection);
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			s.close();
			tra.commit();
		}
		return true;
	}
	
	public boolean deleteMIlkDetails(int id) {
		Session s = sf.openSession();
		Transaction tra=s.beginTransaction();
		try {
			 MilkCollection milkCollection=s.get(MilkCollection.class, id);
			 if(milkCollection!=null) {
				 s.delete(milkCollection);				 
			 }
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			s.close();
			tra.commit();
		}
		return true;
	}
}
