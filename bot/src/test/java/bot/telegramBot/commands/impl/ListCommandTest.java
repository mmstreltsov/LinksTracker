package bot.telegramBot.commands.impl;


import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

class ListCommandTest {

    private ListCommand listCommand;
    private Update update;

    @BeforeEach
    public void init() {
        listCommand = new ListCommand();
        update = Mockito.mock(Update.class);
    }

    @Test
    void testCommandName() {
        String expected = "/list";
        String actual = listCommand.command();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testHandleNotThatId() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(-1L);

        String actual = listCommand.handle(update);
        Assertions.assertTrue(Objects.nonNull(actual) && actual.length() > 20);

        Mockito.verify(update, Mockito.atLeastOnce()).message();
        Mockito.verify(message, Mockito.atLeastOnce()).chat();
        Mockito.verify(chat, Mockito.atLeastOnce()).id();
    }
}