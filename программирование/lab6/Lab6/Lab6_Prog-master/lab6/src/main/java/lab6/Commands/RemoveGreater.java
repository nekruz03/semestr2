package lab6.Commands;

import lab6.Collections.SpaceMarine;
import lab6.InputHandler;
import lab6.Exceptions.InvalidCountOfArgumentsException;

import java.util.PriorityQueue;

/**
 * Класс команды remove_greater
 *
 * @author Остряков Егор, P3112
 */
public class RemoveGreater extends AbstractCommand {
    public RemoveGreater() {
        name = "remove_greater";
        help = "удаляет из коллекции все элементы, превышающие заданный";
        needObjectToExecute = true;
        args = new String[0];
    }

    /**
     * Удаляет из коллекции элементы, здоровье которых больше указанного
     *
     * @param priorityQueue   коллекция, из которой удаляются элементы
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
                if (priorityQueue.size() > 0) {
                    PriorityQueue<SpaceMarine> priorityQueueWithComp = new PriorityQueue<>(CommandsManager.GetIdComparator());
                    priorityQueueWithComp.addAll(priorityQueue);
                    if (commandsManager.isScript()) priorityQueueWithComp.add(InputHandler.ArgumentsReader(commandsManager));
                    else priorityQueueWithComp.add(spaceMarine);
                    if (priorityQueue.removeIf(spaceMarine -> spaceMarine.getHealth() > priorityQueueWithComp.stream().max((o1, o2) -> (int) (o1.getId() - o2.getId())).get().getHealth()))
                        commandsManager.printToClient("Элементы превышающие заданный удалены из коллекции");
                    else commandsManager.printToClient("Элементов превышающих заданный нет");
                } else commandsManager.printToClient("Список пуст");
                SpaceMarine.idSetter--;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args аргументы команды
     * @throws InvalidCountOfArgumentsException если ввели количество аргументов не равное 0
     */
    @Override
    public void setArgs(String[] args) throws InvalidCountOfArgumentsException {
        if (args.length == 0) this.args = args;
        else throw new InvalidCountOfArgumentsException("На данном этапе команда не принимает аргументы");
    }
}
