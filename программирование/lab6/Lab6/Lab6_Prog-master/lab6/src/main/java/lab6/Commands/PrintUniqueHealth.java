package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс команды print_unique_health
 *
 * @author Остряков Егор, P3112
 */
public class PrintUniqueHealth extends AbstractCommand {
    public PrintUniqueHealth() {
        name = "print_unique_health";
        help = " выводит уникальные значения поля health";
    }

    /**
     * Выводит уникальные значения здоровья
     *
     * @param priorityQueue   коллекция, с которой работает пользователь
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length != 0) {
            commandsManager.printToClient("Команда не принимает аргументов");
            logger.warn("Команда не принимает аргументы");
        } else {
            try {
                if (priorityQueue.size() > 0) {
                    HashSet<Float> healthSet = new HashSet<>(priorityQueue.size());
                    PriorityQueue<Float> priorityNonUnique =  new PriorityQueue<>(priorityQueue.size());
                    priorityQueue.forEach(spaceMarine -> {
                        healthSet.add(spaceMarine.getHealth());
                        priorityNonUnique.add(spaceMarine.getHealth());
                    });
                    priorityNonUnique.removeIf(health -> {
                        if (healthSet.contains(health)){
                            healthSet.remove(health);
                            return true;
                        }
                        return false;
                    });
                    commandsManager.printToClient("Уникальные значения health: ");
                    priorityQueue.forEach(spaceMarine -> {
                        if (!priorityNonUnique.contains(spaceMarine.getHealth())) commandsManager.printToClient(spaceMarine.getHealth() + " ");
                    });

                } else commandsManager.printToClient("Список пуст");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
