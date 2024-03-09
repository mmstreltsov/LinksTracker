package scrapper.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Chat {
    private Long id;
    private Long chatId;
    private Long linkId;
}
