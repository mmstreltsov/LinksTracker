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

class StartCommandTest {

    private StartCommand startCommand;
    private Update update;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void init() {
        update = Mockito.mock(Update.class);
        scrapperClient = Mockito.mock(ScrapperClient.class);

        startCommand = new StartCommand(scrapperClient);
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
        String expected = "/start";
        String actual = startCommand.command();

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void handle_checkMessageWhenPositiveRegistration() {
        Mockito.doNothing().when(scrapperClient).registerAccount(Mockito.any());

        String actual = startCommand.handle(update);

        Assertions.assertTrue(actual.contains("Success"), "Incorrect message: " + actual);
    }

    @Test
    void handle_checkMessageWhenClientError() {
        String ex = "Unsuccessful";
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .description(ex)
                .build();
        Mockito.doThrow(apiErrorResponse).when(scrapperClient).registerAccount(Mockito.any());

        String actual = startCommand.handle(update);

        Assertions.assertTrue(actual.contains("Unsuccessful"), "Incorrect message: " + actual);
    }
}