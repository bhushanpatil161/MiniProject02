package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.bindings.UnlockForm;
import in.ashokit.service.UserManagement;

@RestController
public class UnlockRestController {

	@Autowired
	private UserManagement service;
	
	@PostMapping("/unlock")
	public String unlockAccount(@RequestBody UnlockForm unlockForm) {
		return service.unlockAccount(unlockForm);
	}
}
