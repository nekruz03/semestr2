package lab6.Commands;

import lab6.Collections.*;
import lab6.InputHandler;
import lab6.Exceptions.InvalidCountOfArgumentsException;

import java.util.PriorityQueue;

/**
 * Класс команды add
 *
 * @author Остряков Егор, P3112
 */
public class Add extends AbstractCommand {
    public Add() {
        name = "add";
        help = "добавляет новый элемент в коллекцию";
        needObjectToExecute = true;
        args = new String[0];
    }

    /**
     * Добавляет новый элемент в коллекцию
     *
     * @param priorityQueue   коллекция, в которую нужно добавить элемент
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        try {
            if (args.length > 0) {
                commandsManager.printToClient("На данном этапе команда не принимает аргументы");
                logger.warn("На данном этапе команда не принимает аргументы");
                if (commandsManager.isScript()) {
                    commandsManager.commandRewider();
                }
            } else {
                if (commandsManager.isScript()) priorityQueue.add(InputHandler.ArgumentsReader(commandsManager));
                else if (priorityQueue.add(spaceMarine)) {
                    commandsManager.printToClient("Элемент добавлен в коллекцию");
                    logger.info("Элемент добавлен в коллекцию");
                }
            }
        } catch (NullPointerException ignored) {
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
