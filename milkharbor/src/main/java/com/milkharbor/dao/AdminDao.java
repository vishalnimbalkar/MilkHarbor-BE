package com.milkharbor.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.milkharbor.encrypt.EncryptDecrypt;
import com.milkharbor.entity.Login;
import com.milkharbor.entity.MilkCollection;
import com.milkharbor.entity.ResponseLogin;
import com.milkharbor.entity.User;

@Repository
public class AdminDao {
	@Autowired
	private SessionFactory sf;
	private EncryptDecrypt encryptDecrypt;
	private RestTemplate restTemplate;
	private EntityManager entityManager;
	
	//registration
	public boolean register(User admin) throws Exception {
	    Session session = sf.openSession();
	    Transaction transaction = null;

	    try {
	        transaction = session.beginTransaction();
	        session.save(admin);
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
	
	// Login
	public Object logIn(Login payload) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_milkharbor", "root", "root");

	        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, payload.getEmail());
	        preparedStatement.setString(2, payload.getPassword());

	        resultSet = preparedStatement.executeQuery();

	        // Check if the result set contains any rows
	        if (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String name = resultSet.getString("name");
	            System.out.println(name);
	            String email = resultSet.getString("email");
	            String m_no = resultSet.getString("m_no");
	            String role = resultSet.getString("role");
	            String status = resultSet.getString("status");
	            boolean is_active = resultSet.getBoolean("is_active");

	            // Check login conditions
	            if (is_active && "APPROVED".equals(status)) {
	                ResponseLogin responseLogin = new ResponseLogin();
	                responseLogin.setId(id);
	                responseLogin.setName(name);
	                responseLogin.setEmail(email);
	                responseLogin.setM_no(m_no);
	                responseLogin.setRole(role);
	                responseLogin.setStatus(status);
	                responseLogin.setActive(is_active);
	                return responseLogin;
	            } else {
	                return false;
	            }
	        } else {
	        	System.out.println("ddd");
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	
	//get User
	public ResponseLogin getUser(String username) {
		Session s = sf.openSession();
		ResponseLogin responseLogin = new ResponseLogin();
		try {
		    Criteria cr = s.createCriteria(User.class);
		    cr.add(Restrictions.or(Restrictions.eq("m_no", username), Restrictions.eq("email", username)));
		    List<User> admins = cr.list();
		   if(!admins.isEmpty()) {
			   User admin=admins.get(0);
			    responseLogin.setId(admin.getId());
			    responseLogin.setName(admin.getName());
			    responseLogin.setEmail(admin.getEmail());
			    responseLogin.setM_no(admin.getM_no());
			    responseLogin.setRole(admin.getRole());
			    responseLogin.setStatus(admin.getStatus());
		   }
		   s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			s.close();
		}
		   return responseLogin;
	}
	
	//invite farmers
	public boolean inviteFarmers(List<String> farmerEmails) throws Exception {
		Session s = sf.openSession();
		try {
		for(String email:farmerEmails) {
			    //check mail is already present or not
			    Criteria cr = s.createCriteria(User.class);
			    cr.add(Restrictions.eq("email", email));
			    List<User> admins = cr.list();
			   
			    //post api call
		    	RestTemplate restTemplate = new RestTemplate();
		    	String url = "http://localhost:8080/invite/{email}";
		    	ResponseEntity<Void> responseEntity = restTemplate.exchange(
		            url,
		            HttpMethod.POST,
		            null,
		            Void.class,
		            email
		    		);
		        HttpStatus statusCode = responseEntity.getStatusCode();
		        int statusCodeValue = statusCode.value();
		}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		finally {
			s.close();
		}
		return true;
	}
	
	//get pending farmers
	public List<ResponseLogin> getPendingFarmers() {
		Session s = sf.openSession();
		try {
	    Criteria cr = s.createCriteria(User.class);
	    cr.add(Restrictions.eq("status", "PENDING"));
	    List<ResponseLogin> list = cr.list();
	    return list;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		finally {
			s.close();
		}
	}
	
	@Transactional
	public boolean onApprove(Map<String,String> map){	
		map.forEach((id, status) -> {
			Session s = sf.openSession();
			Transaction tra=s.beginTransaction();
			try {
	            int userId = Integer.parseInt(id);
	            User user= s.get(User.class, userId);

	            if (user != null) {
	                user.setStatus(status);
	                s.update(user);
	            }
			} catch (Exception e) {
				System.out.println(e);
			}
			finally {
				s.close();
				tra.commit();
			}
	        });
		return true;
	}
	
	@Transactional
	public boolean onDecline(Map<String, String> map) {
	    try (Session session = sf.openSession()) {
	        Transaction tra = session.beginTransaction();

	        // Part 1: Update status
	        map.forEach((id, status) -> {
	            int userId = Integer.parseInt(id);
	            User user = session.get(User.class, userId);

	            if (user != null) {
	                user.setStatus(status);
	                session.update(user);
	            }
	        });

	        // Commit changes for the first part
	        tra.commit();

	        // Part 2: Delete users with status "DECLINE"
	        Transaction tra2 = session.beginTransaction();
	        Criteria cr = session.createCriteria(User.class);
	        cr.add(Restrictions.eq("status", "DECLINE"));
	        List<User> list = cr.list();
	        for (User u : list) {
	            session.remove(u);
	        }

	        // Commit changes for the second part
	        tra2.commit();
	        session.close();

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace(); // Log or handle the exception accordingly
	        return false;
	    }
	}

	//get farmers List
	public List<ResponseLogin> getFarmers() {
		Session s = sf.openSession();
		try {
	    Criteria cr = s.createCriteria(User.class);
	    cr.add(Restrictions.and(Restrictions.eq("status", "APPROVED"),Restrictions.eq("role", "FARMER")));
	    List<ResponseLogin> list = cr.list();
	    return list;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		finally {
			s.close();
		}
	}
	
	//active  
	@Transactional
	public boolean onActive(Map<String, String> map) {
	    map.forEach((id, status) -> {
	        try (Session s = sf.openSession()) {
	            Transaction tra = s.beginTransaction();
	            int userId = Integer.parseInt(id);
	            User user = s.get(User.class, userId);
	            user.setIs_active(true);
                s.update(user);
	            tra.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });

	    return true;
	}
	
	//Inactive  
		@Transactional
		public boolean onInActive(Map<String, String> map) {
		    map.forEach((id, status) -> {
		        try (Session s = sf.openSession()) {
		            Transaction tra = s.beginTransaction();
		            int userId = Integer.parseInt(id);
		            User user = s.get(User.class, userId);
		            user.setIs_active(false);
	                s.update(user);
		            tra.commit();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    });

		    return true;
		}

	//Milk collection
		public boolean onMilkCollection(MilkCollection mc){	
			Session session = sf.openSession();
		    Transaction transaction = null;

		    try {
		        transaction = session.beginTransaction();
		        session.save(mc);
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

}
