package scrapper.client.botClient.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import scrapper.client.botClient.BotClient;
import scrapper.client.botClient.dto.LinkUpdateRequest;
import scrapper.client.botClient.impl.utils.GetDataToSend;
import scrapper.model.dto.ChatDTO;
import scrapper.model.dto.LinkDTO;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapperQueueProducerForBotClient implements BotClient {

    private final RabbitTemplate rabbitTemplate;
    private final GetDataToSend dataMapper;

    private final DirectExchange directExchange;
    private final Queue queue;

    @Override
    public void updateLink(List<ChatDTO> chatDTO, LinkDTO linkDTO) {
        log.info("Sending message to queue");
        LinkUpdateRequest data = dataMapper.getData(chatDTO, linkDTO);

        rabbitTemplate.convertAndSend(directExchange.getName(), queue.getName(), data);
    }
}
