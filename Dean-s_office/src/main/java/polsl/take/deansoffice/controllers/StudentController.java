package polsl.take.deansoffice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentController {

	@GetMapping("/students")
	public String hello() {
		return "Tu się wypiszą studenci";
	}
}
