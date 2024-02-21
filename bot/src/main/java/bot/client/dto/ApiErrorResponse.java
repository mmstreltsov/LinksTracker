package bot.client.dto;

import lombok.*;

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
