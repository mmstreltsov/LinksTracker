package scrapper.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    public String description;
    public String code;
    public String exceptionName;
    public String exceptionMessage;
    public List<String> stacktrace;
}
