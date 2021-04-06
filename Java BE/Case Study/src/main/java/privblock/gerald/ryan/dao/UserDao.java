package privblock.gerald.ryan.dao;

import org.springframework.beans.factory.annotation.Autowired;

import privblock.gerald.ryan.dbConnection.DBConnection;
import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;

public class UserDao extends DBConnection implements UserDaoI {

	@Override
	public User addUser(User user) {
		this.connect();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User removeUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Wallet addWallet(String username, Wallet wallet) {
		// TODO Auto-generated method stub
		return null;
	}

}
