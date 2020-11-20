package org.springframework.samples.petclinic.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	public String pyong;

	@RequestMapping(value = "/context")
	public String context() {
		return "GetBean : " + applicationContext.getBean(OwnerRepository.class) + " My name : " + pyong;
	}
}
