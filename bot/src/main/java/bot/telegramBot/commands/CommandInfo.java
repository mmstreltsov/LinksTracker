package bot.telegramBot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandInfo {
    START("/start", "sing up"),
    HELP("/help", "show commands"),
    TRACK("/track", "track link"),
    UNTRACK("/untrack", "untrack link"),
    LIST("/list", "show tracked link");

    private final String command;
    private final String description;
}