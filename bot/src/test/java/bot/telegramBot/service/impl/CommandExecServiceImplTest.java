package bot.telegramBot.service.impl;

import bot.telegramBot.commands.Command;
import bot.telegramBot.commands.impl.*;
import bot.telegramBot.service.CommandExecService;
import bot.telegramBot.service.ResponseFromCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

class CommandExecServiceImplTest {
    private CommandExecService commandExecService;
    private List<Command> commands;
    private Update update;
    private final Long COMMON_ID = 42L;

    private static List<Class<?>> getTestedClasses() {
        return List.of(StartCommand.class, HelpCommand.class, TrackCommand.class, UntrackCommand.class, ListCommand.class);
    }

    private Command pickCmdFromListByClass(Class<?> classImpl) {
        Command cmd = null;
        for (var c : commands) {
            var checkClass = Mockito.mock(classImpl).getClass();
            if (checkClass.equals(c.getClass())) {
                cmd = c;
                break;
            }
        }
        Assertions.assertNotNull(cmd, "Commands not matched");
        return cmd;
    }

    @BeforeEach
    public void init() {
        commands = getTestedClasses()
                .stream()
                .map(it -> (Command) Mockito.mock(it))
                .toList();

        update = Mockito.mock(Update.class);
        mockUpdateGetId();

        commandExecService = new CommandExecServiceImpl(commands);
    }

    private void mockUpdateGetId() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(COMMON_ID);
    }


    @ParameterizedTest
    @MethodSource
    void execCommand_checkValidMessageForEveryCommand(Class<?> classImpl) {
        Command cmd = pickCmdFromListByClass(classImpl);

        Mockito.when(cmd.command()).thenCallRealMethod();
        String commandName = cmd.command();
        Mockito.when(update.message().text()).thenReturn(commandName);

        String response = "ahahahah";
        Mockito.when(cmd.handle(Mockito.any())).thenReturn(response);

        ResponseFromCommand responseFromCommand = commandExecService.execCommand(update);
        String actual = responseFromCommand.message();
        Assertions.assertEquals(response, actual, "Incorrect message: " + actual);

        Mockito.verify(cmd, Mockito.atLeastOnce()).handle(Mockito.any());
    }

    private static Stream<Class<?>> execCommand_checkValidMessageForEveryCommand() {
        return getTestedClasses().stream();
    }

    @ParameterizedTest
    @MethodSource
    void execCommand_checkValidIdForEveryCommand(Class<?> classImpl) {
        Command cmd = pickCmdFromListByClass(classImpl);

        Mockito.when(cmd.command()).thenCallRealMethod();
        String commandName = cmd.command();
        Mockito.when(update.message().text()).thenReturn(commandName);

        Mockito.when(cmd.handle(Mockito.any())).thenReturn("");

        ResponseFromCommand responseFromCommand = commandExecService.execCommand(update);
        Long actual = responseFromCommand.id();
        Assertions.assertEquals(COMMON_ID, actual, "Incorrect message: " + actual);
    }

    private static Stream<Class<?>> execCommand_checkValidIdForEveryCommand() {
        return getTestedClasses().stream();
    }

    @ParameterizedTest
    @MethodSource
    void execCommand_checkValidResponseWhenCommandThrowException(Class<?> classImpl) {
        Command cmd = pickCmdFromListByClass(classImpl);

        Mockito.when(cmd.command()).thenCallRealMethod();
        String commandName = cmd.command();
        Mockito.when(update.message().text()).thenReturn(commandName);

        String ex = "ahahah";
        Mockito.when(cmd.handle(Mockito.any())).thenThrow(new RuntimeException(ex));

        ResponseFromCommand responseFromCommand = commandExecService.execCommand(update);
        String actual = responseFromCommand.message();
        Assertions.assertTrue(actual.contains(ex), "Incorrect message: " + actual);
    }

    private static Stream<Class<?>> execCommand_checkValidResponseWhenCommandThrowException() {
        return getTestedClasses().stream();
    }
}