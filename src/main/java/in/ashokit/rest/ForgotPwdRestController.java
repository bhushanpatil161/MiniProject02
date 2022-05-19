package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.service.UserManagement;

@RestController
public class ForgotPwdRestController {

	@Autowired
	private UserManagement service;
	
	@GetMapping("/forgotPwd/{email}")
	public String forgotPwd(@PathVariable String email) {
		return service.forgotPwd(email);
	}
}
