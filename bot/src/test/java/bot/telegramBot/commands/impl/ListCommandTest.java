package bot.telegramBot.commands.impl;


import bot.client.ScrapperClient;
import bot.client.dto.ApiErrorResponse;
import bot.client.dto.LinkResponse;
import bot.client.dto.ListLinksResponse;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class ListCommandTest {

    private ListCommand listCommand;
    private Update update;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void init() {
        scrapperClient = Mockito.mock(ScrapperClient.class);
        update = Mockito.mock(Update.class);

        listCommand = new ListCommand(scrapperClient);
        mockUpdateGetId();
    }

    private void mockUpdateGetId() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(-1L);
    }


    @Test
    void command_checkActualCommandName() {
        String expected = "/list";
        String actual = listCommand.command();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_checkValidResponseWhenThereAreNoTrackedLinks() {
        ListLinksResponse listLinksResponse = ListLinksResponse.builder()
                .links(new ArrayList<>())
                .size(0)
                .build();
        Mockito.when(scrapperClient.getTrackedLinks(Mockito.any())).thenReturn(listLinksResponse);


        String actual = listCommand.handle(update);

        Assertions.assertTrue(actual.contains("no any tracked links"), "Incorrect message: " + actual);

        Mockito.verify(scrapperClient, Mockito.times(1)).getTrackedLinks(Mockito.any());
    }

    @ParameterizedTest
    @MethodSource
    void handle_checkValidResponse(List<LinkResponse> linkResponses) {
        ListLinksResponse listLinksResponse = ListLinksResponse.builder()
                .links(linkResponses)
                .size(linkResponses.size())
                .build();
        Mockito.when(scrapperClient.getTrackedLinks(Mockito.any())).thenReturn(listLinksResponse);


        String actual = listCommand.handle(update);

        linkResponses.forEach(it -> Assertions.assertTrue(actual.contains(it.url.toString()), "Incorrect message: " + actual));

        Mockito.verify(scrapperClient, Mockito.atLeastOnce()).getTrackedLinks(Mockito.any());
    }

    @SneakyThrows
    private static Stream<List<LinkResponse>> handle_checkValidResponse() {
        return Stream.of(
                List.of(new LinkResponse(1L, new URI("ahaha"))),
                List.of(new LinkResponse(1L, new URI("ahaha")), new LinkResponse(4L, new URI("nononon")))
        );
    }

    @Test
    void handle_checkMessageWhenClientError() {
        String ex = "Unsuccessful";
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .description(ex)
                .build();
        Mockito.doThrow(apiErrorResponse).when(scrapperClient).getTrackedLinks(Mockito.any());

        String actual = listCommand.handle(update);

        Assertions.assertTrue(actual.contains("Unsuccessful"), "Incorrect message: " + actual);
    }
}