package scrapper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private Long chatId;
    private Long linkId;
}
