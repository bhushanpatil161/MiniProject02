package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.UserAccountEntity;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Integer> {

	//select * from user_accounts where user_email=? and user_pwd=?
	public UserAccountEntity findByEmailAndPazzword(String email, String pwd);
	
	public UserAccountEntity findByEmail(String emailId);
}
