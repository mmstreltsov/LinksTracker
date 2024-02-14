package bot.dto;


import java.util.List;

public class ApiErrorResponse {
    public String description;
    public String code;
    public String exceptionName;
    public String exceptionMessage;
    public List<String> stacktrace;
}
