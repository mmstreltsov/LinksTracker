package scrapper.client.botClient.impl.utils.impl;

import org.springframework.stereotype.Component;
import scrapper.client.botClient.dto.LinkUpdateRequest;
import scrapper.client.botClient.impl.utils.GetDataToSend;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;

@Component
public class GetDataToSendImpl implements GetDataToSend {
    @Override
    public LinkUpdateRequest getData(List<ChatDTO> chats, LinkDTO link) {
        if (chats == null || link == null) {
            throw new IllegalArgumentException("chats and link is null");
        }

        return LinkUpdateRequest.builder()
                .tgChatIds(chats.stream().map(ChatDTO::getChatId).toList())
                .url(link.getUrl())
                .id(-1L)
                .description("stub")
                .build();
    }
}
