package privblock.gerald.ryan.dao;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import privblock.gerald.ryan.dbConnection.DBConnection;
import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;

public class UserDao extends DBConnection implements UserDaoI {

	@Override
	public User addUser(User user) {
		this.connect();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		this.disconnect();
		return null;
	}

	@Override
	public User getUser(String username) {
		this.connect();
		User u = em.find(User.class, username);
		this.disconnect();
		return u;
	}

//	@Override
//	public User updateUser(User user) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public User removeUser(String username) {
		this.connect();
		User u = em.find(User.class, username);
		em.remove(u); // how the heck does this work???
		this.disconnect();
		return u;
	}

	@Override
	public Wallet addWallet(String username, Wallet wallet) {
		this.connect();
		User u = em.find(User.class, username);
		em.getTransaction().begin();
		u.setWallet(wallet);
		em.getTransaction().commit();
		this.disconnect();
		return wallet;
	}

	@Override
	public boolean authenticateUser(String username, String password) {
		this.connect();
		User u = em.find(User.class, username);
		boolean result = false;
		if (u == null) {
			throw new NoSuchElementException("The selected user was not found in the database");
		}
		if (u.getPassword() == password) {
			result = true;
		}
		this.disconnect();
		return result;
	}
}
