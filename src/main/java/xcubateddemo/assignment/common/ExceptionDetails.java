package xcubateddemo.assignment.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xcubateddemo.assignment.dto.response.DefaultResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails extends DefaultResponse {
	private String msg;
}
