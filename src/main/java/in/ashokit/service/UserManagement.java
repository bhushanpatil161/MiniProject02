package in.ashokit.service;

import java.util.Map;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.UnlockForm;
import in.ashokit.bindings.UserForm;

public interface UserManagement {

	public String login(LoginForm loginForm);

	public String emailCheck(String emailId);

	public Map<Integer, String> loadCountries();

	public Map<Integer, String> loadStates(Integer countryId);

	public Map<Integer, String> loadCities(Integer stateId);

	public String registerUser(UserForm userForm);
	
	public String unlockAccount(UnlockForm unlockAccForm);
	
	public String forgotPwd(String emailId);
}
