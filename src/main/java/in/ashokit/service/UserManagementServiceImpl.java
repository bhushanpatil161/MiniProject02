package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.LoginForm;
import in.ashokit.bindings.UnlockForm;
import in.ashokit.bindings.UserForm;
import in.ashokit.entity.CitiesMasterEntity;
import in.ashokit.entity.CountryMasterEntity;
import in.ashokit.entity.StateMasterEntity;
import in.ashokit.entity.UserAccountEntity;
import in.ashokit.repository.CityRepository;
import in.ashokit.repository.CountryRepository;
import in.ashokit.repository.StateRepository;
import in.ashokit.repository.UserAccountRepository;
import in.ashokit.utils.EmailUtils;

@Service
public class UserManagementServiceImpl implements UserManagement {

	@Autowired
	private UserAccountRepository userRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String login(LoginForm loginForm) {

		UserAccountEntity entity = userRepo.findByEmailAndPazzword(loginForm.getEmail(), loginForm.getPwd());
		if (entity == null) {
			return "Invalid Credentials";
		}
		if (entity != null && entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		return "SUCCESS";
	}

	@Override
	public String emailCheck(String emailId) {
		UserAccountEntity entity = userRepo.findByEmail(emailId);
		if (entity == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> loadCountries() {
		List<CountryMasterEntity> countries = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();
		for (CountryMasterEntity entity : countries) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}
		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStates(Integer countryId) {
		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> statesMap = new HashMap<>();
		for (StateMasterEntity state : states) {
			statesMap.put(state.getStateId(), state.getStateName());
		}
		return statesMap;
	}

	@Override
	public Map<Integer, String> loadCities(Integer stateId) {
		Map<Integer, String> citiesMap = new HashMap<>();
		List<CitiesMasterEntity> cities = cityRepo.findByStateId(stateId);
		for (CitiesMasterEntity entity : cities) {
			citiesMap.put(entity.getCityId(), entity.getCityName());
		}
		return citiesMap;
	}

	@Override
	public String registerUser(UserForm userForm) {
		UserAccountEntity entity = new UserAccountEntity();
		BeanUtils.copyProperties(userForm, entity);
		entity.setAccStatus("LOCKED");
		entity.setPazzword(generateRandomPwd());
		UserAccountEntity savedEntity = userRepo.save(entity);
		String email = userForm.getEmail();
		String subject = "User Registration - Bhushan";
		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		String body = readMailBodyContent(fileName, entity);
		boolean isSent = emailUtils.sendMail(email, subject, body);
		if (savedEntity.getUserId() !=null && isSent) {
			return "SUCCESS";
		}
		return "FAIL";
	}

	@Override
	public String unlockAccount(UnlockForm unlockAccForm) {
		if (!(unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd()))) {
			return "Password and Confirm Password Should be Match";
		}
		UserAccountEntity entity = userRepo.findByEmailAndPazzword(unlockAccForm.getEmail(),
				unlockAccForm.getTempPwd());
		if (entity == null) {
			return "Incorrect Temporary Password";
		}

		entity.setPazzword(unlockAccForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");
		userRepo.save(entity);

		return "Account Unlocked";
	}

	@Override
	public String forgotPwd(String emailId) {
		UserAccountEntity entity = userRepo.findByEmail(emailId);
		if (entity == null) {
			return "Invalid Email Id";
		}
		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String body = readMailBodyContent(fileName, entity);
		String subject = "Recover Password - Ashok IT";
		boolean isSent = emailUtils.sendMail(emailId, subject, body);
		if(isSent) {
			return "Password sent to registered email";
		}
		return "ERROR";
	}

	private String generateRandomPwd() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	private String readMailBodyContent(String fileName, UserAccountEntity entity) {
		String mailBody = null;
		try {
			StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			mailBody = sb.toString();
			mailBody = mailBody.replace("{FNAME}", entity.getFName());
			mailBody = mailBody.replace("{LNAME}", entity.getLName());
			mailBody = mailBody.replace("{TEMP-PWD}", entity.getPazzword());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmail());
			mailBody = mailBody.replace("{PWD}", entity.getPazzword());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}

}
