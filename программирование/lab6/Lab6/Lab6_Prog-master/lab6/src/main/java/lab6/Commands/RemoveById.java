package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.util.PriorityQueue;

/**
 * Класс команды remove_by_id
 *
 * @author Остряков Егор, P3112
 */
public class RemoveById extends AbstractCommand {
    public RemoveById() {
        name = "remove_by_id";
        help = "удаляет элемент из коллекции по его id";
    }

    /**
     * Удаляет элемент по id
     *
     * @param priorityQueue   коллекция, из которой удаляется элемент
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length != 1) {
            commandsManager.printToClient("Команда принимает лишь один аргумент");
            logger.warn("Команда принимает лишь один аргумент");
        } else {
            try {
                long id = Long.parseLong(args[0]);
                if (priorityQueue.removeIf(spaceMarine -> spaceMarine.getId() == id))
                    commandsManager.printToClient("Элемент коллекции с id = " + args[0] + " удалён");
                else commandsManager.printToClient("Элемент коллекции с id = " + args[0] + " не найден");
            } catch (Exception e) {
                commandsManager.printToClient("Неверный тип аргумента");
            }
        }
    }
}
