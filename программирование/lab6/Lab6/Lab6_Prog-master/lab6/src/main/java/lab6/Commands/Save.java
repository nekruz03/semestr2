package lab6.Commands;

import lab6.InputHandler;
import lab6.Collections.SpaceMarine;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.util.PriorityQueue;

/**
 * Класс команды save
 *
 * @author Остряков Егор, P3112
 */
public class Save extends AbstractCommand {
    public Save() {
        name = "save";
        help = "сохраняет коллекцию в файл";
    }

    /**
     * Сохраняет коллекцию в файл
     *
     * @param priorityQueue   коллекция, которую нужно сохоранить
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length > 0) {
            commandsManager.printToClient("Команда не принимает аргументы");
            logger.warn("Команда не принимает аргументы");
        } else {
            try (FileWriter fw = new FileWriter(InputHandler.getArguments()[0]); CSVWriter writer = new CSVWriter(fw, InputHandler.getArguments()[1].charAt(0), '"', '"', "\n")) {
                for (SpaceMarine spaceMarine : priorityQueue)
                    writer.writeNext(new String[]{spaceMarine.getName(), String.valueOf(spaceMarine.getCoordinates().getX()), String.valueOf(spaceMarine.getCoordinates().getY()), spaceMarine.getHealth().toString(), String.valueOf(spaceMarine.getHeartCount()), spaceMarine.getLoyal() == null ? "" : String.valueOf(spaceMarine.getLoyal()), spaceMarine.getMeleeWeapon().name(), spaceMarine.getChapter().getName(), spaceMarine.getChapter().getWorld()});
                commandsManager.printToClient("Коллекция успешно сохранена");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
