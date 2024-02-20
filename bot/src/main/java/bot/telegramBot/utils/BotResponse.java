package bot.telegramBot.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BotResponse {

    NOT_SUPPORTED_COMMAND("This command is not supported"),
    SMTH_GONE_WRONG("Something gone wrong"),

    //Start
    USER_ADDED_AND_U_R_WELCOME("Successful registration. You are welcome"),

    //List
    NO_TRACKED_LINKS("Unsuccessful: There is no any tracked links")
    ;

    public final String msg;
}
