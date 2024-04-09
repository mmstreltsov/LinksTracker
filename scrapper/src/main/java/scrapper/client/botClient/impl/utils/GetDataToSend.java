package scrapper.client.botClient.impl.utils;

import scrapper.client.botClient.dto.LinkUpdateRequest;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;

public interface GetDataToSend {
    LinkUpdateRequest getData(List<ChatDTO> chats, LinkDTO link);
}
