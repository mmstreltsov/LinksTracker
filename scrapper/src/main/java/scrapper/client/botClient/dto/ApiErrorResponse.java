package scrapper.client.botClient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiErrorResponse extends RuntimeException {
    public String description;
    public String code;
    public String exceptionName;
    public String exceptionMessage;
    public List<String> stacktrace;

    @Override
    public String getMessage() {
        return this.toString();
    }
}
