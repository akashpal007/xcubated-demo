package xcubateddemo.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xcubateddemo.assignment.dto.response.DefaultResponse;
import xcubateddemo.assignment.service.AssignmentService;

@RestController
@RequestMapping("/api")
public class AssignmentController {
	@Autowired
	AssignmentService assignmentService;

	@PostMapping("/numberoperations")
	public DefaultResponse numberoperations(@RequestBody List<Double> numericArr) {
//		numericArr.get(0)%1 == 0);

		return assignmentService.numberoperations(numericArr);
	}

}
