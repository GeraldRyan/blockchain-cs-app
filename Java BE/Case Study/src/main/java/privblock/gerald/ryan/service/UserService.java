package privblock.gerald.ryan.service;

import org.springframework.stereotype.Service;

import privblock.gerald.ryan.dao.UserDao;
import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;

@Service
public class UserService {
	private UserDao userD = new UserDao();

	public User addUserService(User user) {
		return userD.addUser(user);
	}

	public User getUserService(String username) {
		return userD.getUser(username);

	}

	public Wallet addWalletService(String username, Wallet wallet) {
		return userD.addWallet(username, wallet);

	}

	public User removeUserService(String username) {
		return userD.removeUser(username);

	}

	public boolean authenticateUserService(String username, String password) {
		return userD.authenticateUser(username, password);

	}

	public static void main(String[] args) {
		new UserService().addUserService(new User("zelda", "ganon", "powerwisdom", "love", "zelda@hyrule.hr"));
	}

}
