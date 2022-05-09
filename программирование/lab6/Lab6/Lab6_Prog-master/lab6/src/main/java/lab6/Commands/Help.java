package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.util.PriorityQueue;

/**
 * Класс команды help
 *
 * @author Остряков Егор, P3112
 */
public class Help extends AbstractCommand {
    public Help() {
        name = "help";
        help = "выводит справку по доступным командам";
    }

    /**
     * Выводит справку по командам
     *
     * @param priorityQueue   коллекция, с которой работает пользователь
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("Команда не принимает аргументы");
            logger.warn("Команда не принимает аргументы");
        } else {
            for (AbstractCommand command : CommandsManager.GetCommands())
                commandsManager.printToClient("Команда " + command.getName() + ": " + command.getHelp());
        }
    }
}
