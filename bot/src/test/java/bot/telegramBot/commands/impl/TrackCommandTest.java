package bot.telegramBot.commands.impl;

import bot.client.ScrapperClient;
import bot.client.dto.ApiErrorResponse;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TrackCommandTest {

    private TrackCommand trackCommand;
    private Update update;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void init() {
        update = Mockito.mock(Update.class);
        scrapperClient = Mockito.mock(ScrapperClient.class);

        trackCommand = new TrackCommand(scrapperClient);
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
        String expected = "/track";
        String actual = trackCommand.command();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_checkValidResponse() {
        String text = "/track ahahahah";
        Mockito.when(update.message().text()).thenReturn(text);

        String actual = trackCommand.handle(update);

        Assertions.assertTrue(actual.contains("Successful"), "Incorrect message: " + actual);

        Mockito.verify(update.message(), Mockito.times(1)).text();
    }

    @Test
    void handle_checkInvalidResponseNoPassedALink() {
        String text = "/track";
        Mockito.when(update.message().text()).thenReturn(text);

        Assertions.assertThrows(IllegalArgumentException.class, () -> trackCommand.handle(update));

        Mockito.verify(update.message(), Mockito.times(1)).text();
    }

    @Test
    void handle_checkInvalidResponseURIException() {
        String text = "/track ^_^ahaha^_^";
        Mockito.when(update.message().text()).thenReturn(text);

        Assertions.assertThrows(RuntimeException.class, () -> trackCommand.handle(update));

        Mockito.verify(update.message(), Mockito.times(1)).text();
    }

    @Test
    void handle_checkInvalidResponseClientError() {
        String text = "/track link";
        Mockito.when(update.message().text()).thenReturn(text);

        String ex = "Unsuccessful";
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .description(ex)
                .build();
        Mockito.doThrow(apiErrorResponse).when(scrapperClient).addLink(Mockito.any(), Mockito.any());


        Assertions.assertThrows(ApiErrorResponse.class, () -> trackCommand.handle(update));
    }
}