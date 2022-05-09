package lab6.Commands;

import lab6.Collections.SpaceMarine;
import lab6.InputHandler;

import java.util.PriorityQueue;

/**
 * Класс команды exit
 *
 * @author Остряков Егор, P3112
 */
public class Exit extends AbstractCommand {
    public Exit() {
        name = "exit";
        help = "завершает программу (без сохранения в файл)";
    }

    /**
     * Завершает работу с коллекций, выходит без сохранения
     *
     * @param priorityQueue   коллекция, с которой работает пользователь
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("Команда не принимает аргументы");
            logger.warn("Команда не принимает аргументы");
        }
        else {
            priorityQueue.clear();
            SpaceMarine.idSetter = 1;
            InputHandler.InputLoader(InputHandler.getArguments(), priorityQueue);
            logger.info("Клиент завершил работу с коллекцией");
        }
    }
}
