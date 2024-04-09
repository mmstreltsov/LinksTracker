package bot.mq;

import bot.dto.LinkUpdateRequest;
import bot.service.HandleLinkUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@RabbitListener(queues = "${app.queue-name-for-m-q}")
public class ScrapperQueueListener {

    private final HandleLinkUpdate handler;
    private final ObjectMapper objectMapper;

    @RabbitHandler
    public void process(String request) {
        try {
            LinkUpdateRequest json = objectMapper.readValue(request, LinkUpdateRequest.class);
            handler.handleLinkUpdate(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


