package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.util.PriorityQueue;

/**
 * Класс команды clean
 *
 * @author Остряков Егор, P3112
 */
public class Clear extends AbstractCommand {
    public Clear() {
        name = "clear";
        help = "очищает коллекцию";
    }

    /**
     * Удаляет все элементы коллекции
     *
     * @param priorityQueue   коллекция, которую нужно очистить
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("Команда не принимает аргументы");
            logger.warn("Команда не принимает аргументы");
        }else {
            priorityQueue.clear();
            commandsManager.printToClient("Коллекция очищена");
        }
    }
}
