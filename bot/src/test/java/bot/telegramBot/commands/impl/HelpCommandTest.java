package bot.telegramBot.commands.impl;


import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HelpCommandTest {

    private HelpCommand helpCommand;
    private Update update;

    @BeforeEach
    public void init() {
        helpCommand = new HelpCommand();
        update = Mockito.mock(Update.class);
    }

    @Test
    void command_checkActualCommandName() {
        String expected = "/help";
        String actual = helpCommand.command();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_checkValidResponse() {
        String actual = helpCommand.handle(update);
        Assertions.assertTrue(actual.contains("Supported commands"));
    }
}