package lab6.Commands;

import lab6.Collections.SpaceMarine;
import lab6.InputHandler;
import lab6.Exceptions.InvalidCountOfArgumentsException;

import java.util.PriorityQueue;

/**
 * Класс команды add_if_min
 *
 * @author Остряков Егор, P3112
 */
public class AddIfMin extends AbstractCommand {
    public AddIfMin() {
        name = "add_if_min";
        help = "добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
        needObjectToExecute = true;
        args = new String[0];
    }

    /**
     * Добавляет новый элемент в коллекцию, если значение его здоровья минимально
     *
     * @param priorityQueue   коллекция, в которую нужно добавить элемент
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("На данном этапе команда не принимает аргументы");
            logger.warn("На данном этапе команда не принимает аргументы");
            commandsManager.commandRewider();
        } else {
            try {
                PriorityQueue<SpaceMarine> priorityQueueWithMin = new PriorityQueue<>(CommandsManager.GetIdComparator());
                priorityQueueWithMin.addAll(priorityQueue);
                if (commandsManager.isScript()) priorityQueueWithMin.add(InputHandler.ArgumentsReader(commandsManager));
                else priorityQueueWithMin.add(spaceMarine);
                SpaceMarine minMarine = priorityQueueWithMin.stream().min(CommandsManager.GetIdComparator()).get();
                if (priorityQueue.peek().getHealth() <= minMarine.getHealth()) {
                    logger.warn("Элемент не добавлен, так как не является минимальным");
                    commandsManager.printToClient("Элемент не добавлен, так как не является минимальным (значение Health >= " + priorityQueue.peek().getHealth() + ")");
                } else {
                    priorityQueue.add(minMarine);
                    commandsManager.printToClient("Элемент добавлен в коллекцию");
                    logger.info("Элемент добавлен в коллекцию");
                }
            } catch (NullPointerException ignored) {
            } catch (Exception e) {
                commandsManager.printToClient("Неверный тип аргумента");
                logger.error("Неверный тип аргумента: {}", e.getMessage());
                if (commandsManager.isScript()) {
                    commandsManager.commandRewider();
                }
            }
        }
    }

    /**
     *
     * @param args аргументы команды
     * @throws InvalidCountOfArgumentsException если ввели количество аргументов не равное 0
     */
    @Override
    public void setArgs(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length == 0) this.args = args;
        else throw new InvalidCountOfArgumentsException("На данном этапе команда не принимает аргументы");

    }
}