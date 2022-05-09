package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Класс команды show
 *
 * @author Остряков Егор, P3112
 */
public class Show extends AbstractCommand {
    public Show() {
        name = "show";
        help = "показывает элементы коллекции";
    }

    /**
     * Показывает элементы коллекции
     *
     * @param priorityQueue   коллекция, которую нужно показать
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("Команда не принимает аргументы");
            logger.warn("Команда не принимает аргументы");
        } else commandsManager.printToClient(Arrays.toString(priorityQueue.toArray()));
    }
}
