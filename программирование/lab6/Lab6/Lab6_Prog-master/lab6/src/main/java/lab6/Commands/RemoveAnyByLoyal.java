package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс команды remove_any_by_loyal
 *
 * @author Остряков Егор, P3112
 */
public class RemoveAnyByLoyal extends AbstractCommand {
    public RemoveAnyByLoyal() {
        name = "remove_any_by_loyal";
        help = "удаляет из коллекции один элемент, значение поля loyal которого эквивалентно заданному";
    }

    /**
     * Удаляет из коллекции один элемент с указанным значением лояльности
     *
     * @param priorityQueue   коллекция, из которой удаляется элемент
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        sorry: if (args.length != 1) {
            commandsManager.printToClient("Команда принимает лишь один аргумент");
            logger.warn("Команда принимает лишь один аргумент");
        }else {
            try {
                if (priorityQueue.size() > 0) {
                    Boolean loyal = null;
                    if (args[0].equalsIgnoreCase("true")) loyal = true;
                    else if (args[0].equalsIgnoreCase("false")) loyal = false;
                    else if (args[0].equals("null")) loyal = null;
                    else {
                        commandsManager.printToClient("Неверный аргумент");
                        break sorry;
                    }
                    AtomicBoolean breakme = new AtomicBoolean(false);
                    Boolean finalLoyal = loyal;
                    if (!priorityQueue.removeIf(spaceMarine -> {
                        if (spaceMarine.getLoyal() == finalLoyal && !breakme.get()) {
                            breakme.set(true);
                            commandsManager.printToClient("Элемент с id = " + spaceMarine.getId() + " удалён");
                            return true;
                        }
                        return false;
                    })) {
                        commandsManager.printToClient("Элемент с loyal = " + args[0] + " не найден");
                    }
                } else commandsManager.printToClient("Список пуст");
            } catch (Exception e) {
                commandsManager.printToClient("Неверный тип аргумента");
            }
        }
    }
}
