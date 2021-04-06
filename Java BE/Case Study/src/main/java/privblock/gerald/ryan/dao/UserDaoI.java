package privblock.gerald.ryan.dao;

import java.util.List;

import privblock.gerald.ryan.entity.User;
import privblock.gerald.ryan.entity.Wallet;

public interface UserDaoI {
	public User addUser(User user);

	public User getUser(String username);

	public User updateUser(User user);

	public User removeUser(String username);

	public Wallet addWallet(String username, Wallet wallet);
//	public List<User> getAllUsers(); // is this safe? It also gets their wallets

}
