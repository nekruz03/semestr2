package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.util.PriorityQueue;

/**
 * Класс команды max_by_health
 *
 * @author Остряков Егор, P3112
 */
public class MaxByHealth extends AbstractCommand {
    public MaxByHealth() {
        name = "max_by_health";
        help = "выводит любой объект из коллекции, значение поля health которого является максимальным";
    }

    /**
     * Выводит элемент коллекции с максимальным здоровьем
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
            try {
                if (priorityQueue.size() > 0) {
                    commandsManager.printToClient("Максимальный элемент по health: " + priorityQueue.stream().max(CommandsManager.GetIdComparator()).get());
                } else commandsManager.printToClient("Список пуст");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
