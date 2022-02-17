package xcubateddemo.assignment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import xcubateddemo.assignment.common.InvalidDataException;
import xcubateddemo.assignment.common.NumericInputException;
import xcubateddemo.assignment.common.Status;
import xcubateddemo.assignment.common.Util;
import xcubateddemo.assignment.dto.response.DefaultResponse;
import xcubateddemo.assignment.dto.response.NumberOperationsResponse;
import xcubateddemo.assignment.dto.response.Results;
import xcubateddemo.assignment.service.AssignmentService;

@Service
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {

	@Override
	public DefaultResponse numberoperations(List<Double> numericArr) {
		log.info("NUMBER OPERATIONS IMPL REQUEST BODY: " + numericArr);
		if(numericArr == null || numericArr.isEmpty()) {
			throw new InvalidDataException("Numeric input is blank.");
		}
		
		NumberOperationsResponse response = new NumberOperationsResponse();
		Results results = new Results();

		/* Sum operation */
		Double sum = 0.0;
		for (Double num : numericArr) {
			if (Util.checkDecimal(num)) {
				sum += num;
			} else {
				throw new NumericInputException("Numeric input expected, received invalid numbers in input");
			}
		}

		log.info("sum: " + sum);

		/* Average operation */
		Double average = 0.0;
		Integer numericArrSize = numericArr.size();
		average = sum / numericArrSize;

		log.info("average: " + average);

		/* Greater Than Average operation */
		List<Integer> greaterThanAverage = new ArrayList<>();
		for (Double temp : numericArr) {
			if (temp > average) {
				greaterThanAverage.add(temp.intValue());
			}
		}

		log.info("greaterThanAverage: " + greaterThanAverage);

		results.setSum(sum.intValue());
		if (Util.checkDecimal(average)) {
			results.setAverage(average.intValue());
		} else {
			results.setAverage(average);
		}
		results.setGreaterThanAverage(greaterThanAverage);

		response.setResults(results);
		response.setStatus(Status.SUCCESS);

		log.info("NUMBER OPERATIONS IMPL RESPONSE BODY: " + response);
		return response;
	}

}
