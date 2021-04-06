package privblock.gerald.ryan.service;

import privblock.gerald.ryan.dao.UserDao;
import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;

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

}
