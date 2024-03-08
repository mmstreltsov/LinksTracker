package scrapper.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Chat {
    private Long id;
    private Long chatId;
    private Long linkId;
}
