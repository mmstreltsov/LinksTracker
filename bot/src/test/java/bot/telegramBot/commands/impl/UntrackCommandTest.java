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

class UntrackCommandTest {

    private UntrackCommand untrackCommand;
    private Update update;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void init() {
        update = Mockito.mock(Update.class);
        scrapperClient = Mockito.mock(ScrapperClient.class);

        untrackCommand = new UntrackCommand(scrapperClient);
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
        String expected = "/untrack";
        String actual = untrackCommand.command();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_checkValidResponse() {
        String text = "/untrack ahahahah";
        Mockito.when(update.message().text()).thenReturn(text);

        String actual = untrackCommand.handle(update);

        Assertions.assertTrue(actual.contains("Successful"), "Incorrect message: " + actual);

        Mockito.verify(update.message(), Mockito.times(1)).text();
    }

    @Test
    void handle_checkInvalidResponseNoPassedALink() {
        String text = "/untrack";
        Mockito.when(update.message().text()).thenReturn(text);

        Assertions.assertThrows(IllegalArgumentException.class, () -> untrackCommand.handle(update));

        Mockito.verify(update.message(), Mockito.times(1)).text();
    }

    @Test
    void handle_checkInvalidResponseURIException() {
        String text = "/untrack ^_^ahaha^_^";
        Mockito.when(update.message().text()).thenReturn(text);

        Assertions.assertThrows(RuntimeException.class, () -> untrackCommand.handle(update));

        Mockito.verify(update.message(), Mockito.times(1)).text();
    }

    @Test
    void handle_checkInvalidResponseClientError() {
        String text = "/untrack link";
        Mockito.when(update.message().text()).thenReturn(text);

        String ex = "Unsuccessful";
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .description(ex)
                .build();
        Mockito.doThrow(apiErrorResponse).when(scrapperClient).removeLink(Mockito.any(), Mockito.any());


        Assertions.assertThrows(ApiErrorResponse.class, () -> untrackCommand.handle(update));
    }
}