package xcubateddemo.assignment.service;

import java.util.List;

import xcubateddemo.assignment.dto.response.DefaultResponse;

public interface AssignmentService {

	DefaultResponse numberoperations(List<Double> numericArr);

}
