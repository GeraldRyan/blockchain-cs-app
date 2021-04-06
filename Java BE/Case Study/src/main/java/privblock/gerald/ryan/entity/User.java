package privblock.gerald.ryan.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	String username;
	String password;
	String hint;
	String answer;
	String email;
	@Embedded
	Wallet wallet;

	public User(String username, String password, String hint, String answer, String email, Wallet wallet) {
		super();
		this.username = username;
		this.password = password;
		this.hint = hint;
		this.answer = answer;
		this.email = email;
		this.wallet = wallet;
	}

	public User(String username, String password, String hint, String answer, String email) {
		super();
		this.username = username;
		this.password = password;
		this.hint = hint;
		this.answer = answer;
		this.email = email;
		this.wallet = null;
	}

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}
