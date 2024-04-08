package bot.mq;

import bot.dto.LinkUpdateRequest;
import bot.service.HandleLinkUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "${app.queue-name-for-m-q}")
@Service
@RequiredArgsConstructor
public class ScrapperQueueListener {

    private final HandleLinkUpdate handler;
    private final ObjectMapper objectMapper;

    @RabbitHandler
    @SneakyThrows
    public void process(String request) {
        LinkUpdateRequest json = objectMapper.readValue(request, LinkUpdateRequest.class);
        handler.handleLinkUpdate(json);
    }
}
