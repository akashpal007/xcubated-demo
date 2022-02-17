package xcubateddemo.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import xcubateddemo.assignment.common.InvalidDataException;
import xcubateddemo.assignment.common.NumericInputException;
import xcubateddemo.assignment.common.Status;
import xcubateddemo.assignment.dto.response.DefaultResponse;
import xcubateddemo.assignment.dto.response.NumberOperationsResponse;
import xcubateddemo.assignment.dto.response.Results;
import xcubateddemo.assignment.service.impl.AssignmentServiceImpl;

class AssignmentApplicationTest {

	@Test
	void test1() {
		AssignmentServiceImpl assignment = new AssignmentServiceImpl();
		List<Double> request = Arrays.asList(10.0, 20.0, 30.0);

		NumberOperationsResponse defResExpected = new NumberOperationsResponse();
		defResExpected.setStatus(Status.SUCCESS);
		Integer sum = 60;
		Object average = 20;
		List<Integer> greaterThanAverage = Arrays.asList(30);
		Results results = new Results(sum, average, greaterThanAverage);
		defResExpected.setResults(results);

		DefaultResponse defRespActual = assignment.numberoperations(request);

		assertEquals(defResExpected, defRespActual);
	}

	@Test
	void test2() {
		AssignmentServiceImpl assignment = new AssignmentServiceImpl();
		List<Double> request = Arrays.asList(1.5, 2.0);
		assertThrows(NumericInputException.class, () -> assignment.numberoperations(request));
	}

	@Test
	void test3() {
		AssignmentServiceImpl assignment = new AssignmentServiceImpl();
		List<Double> request = Arrays.asList();
		assertThrows(InvalidDataException.class, () -> assignment.numberoperations(request));
	}

	@Test
	void test4() {
		AssignmentServiceImpl assignment = new AssignmentServiceImpl();
		List<Double> request = Arrays.asList(10.0, 10.0, 10.0);

		NumberOperationsResponse defResExpected = new NumberOperationsResponse();
		defResExpected.setStatus(Status.SUCCESS);
		Integer sum = 30;
		Object average = 10;
		List<Integer> greaterThanAverage = Arrays.asList();
		Results results = new Results(sum, average, greaterThanAverage);
		defResExpected.setResults(results);

		DefaultResponse defRespActual = assignment.numberoperations(request);

		assertEquals(defResExpected, defRespActual);
	}

}
