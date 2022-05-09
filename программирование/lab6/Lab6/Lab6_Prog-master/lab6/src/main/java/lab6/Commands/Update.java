package lab6.Commands;

import lab6.Collections.SpaceMarine;
import lab6.Exceptions.InvalidCountOfArgumentsException;

import java.util.PriorityQueue;

/**
 * Класс команды update
 */
public class Update extends AbstractCommand {
    public Update() {
        name = "update";
        help = "обновляет значение элемента коллекции, id которого равен заданному";
        needObjectToExecute = true;
        args = new String[1];
    }

    /**
     * Обновляет элемент коллекции по id
     *
     * @param priorityQueue   коллекция, элемент которой нужно обновить
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        try {
            if (args.length != 1) {
                commandsManager.printToClient("Команда принимает лишь один аргумент");
                logger.warn("Команда принимает лишь один аргумент");
                if (commandsManager.isScript()) {
                    commandsManager.commandRewider();
                }
            } else {
                long oldIdSetter = SpaceMarine.idSetter;
                SpaceMarine.idSetter = Long.parseLong(args[0]);
                if (priorityQueue.removeIf(spaceMarine -> spaceMarine.getId() == Integer.parseInt(args[0]))) {
                    spaceMarine.setId(SpaceMarine.idSetter);
                    priorityQueue.add(spaceMarine);
                    commandsManager.printToClient("Элемент с id = " + (SpaceMarine.idSetter) + " обновлён");
                } else commandsManager.printToClient("Элемент с id = " + (SpaceMarine.idSetter) + " не существует");
                SpaceMarine.idSetter = oldIdSetter;
            }
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            commandsManager.printToClient("Неверный тип аргумента");
            if (commandsManager.isScript()) {
                commandsManager.commandRewider();
            }
        }
    }

    /**
     * @param args аргументы команды
     * @throws InvalidCountOfArgumentsException если ввели количество аргументов не равное 1
     */
    @Override
    public void setArgs(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length == 1) this.args = args;
        else throw new InvalidCountOfArgumentsException("Команда принимает лишь один аргумент");
    }
}
