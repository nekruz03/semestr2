package lab6.Commands;

import lab6.Collections.SpaceMarine;
import lab6.Exceptions.InvalidCountOfArgumentsException;

import java.util.PriorityQueue;

/**
 * Класс команды remove_first
 *
 * @author Остряков Егор, P3112
 */
public class RemoveFirst extends AbstractCommand {

    public RemoveFirst() {
        name = "remove_first";
        help = "удаляет первый элемент из коллекции";
    }

    /**
     * Удаляет первый элемент коллекции
     *
     * @param priorityQueue   коллекция, из которой удаляется элемент
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("Команда не принимает аргументы");
            logger.warn("Команда не принимает аргументы");
        } else {
            try {
                commandsManager.printToClient("Элемент с id = " + priorityQueue.poll().getId() + " удалён");
            } catch (Exception e) {
                commandsManager.printToClient("Список пуст");
            }
        }
    }

    @Override
    public void setArgs(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length == 0) this.args = args;
        else throw new InvalidCountOfArgumentsException("На данном этапе команда не принимает аргументы");

    }
}
