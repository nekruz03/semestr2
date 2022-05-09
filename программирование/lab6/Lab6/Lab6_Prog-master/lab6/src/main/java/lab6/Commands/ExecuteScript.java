package lab6.Commands;

import lab6.Collections.SpaceMarine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Класс команды execute_script
 *
 * @author Остряков Егор, P3112
 */
public class ExecuteScript extends AbstractCommand {
    LinkedHashSet<String> scriptsNames = new LinkedHashSet<>();

    public ExecuteScript() {
        name = "execute_script";
        help = "запускает исполняемый скрипт";
    }

    /**
     * Запускает указанный скрипт
     *
     * @param priorityQueue   коллекция, с которой работает пользователь
     * @param commandsManager объект класса CommandsManager
     */
    @Override
    public void execute(PriorityQueue<SpaceMarine> priorityQueue, CommandsManager commandsManager) {
        if (args.length != 1) {
            commandsManager.printToClient("Команда принимает лишь один аргумент");
            logger.warn("Команда принимает лишь один аргумент");
        }
        else {
            commandsManager.setScript(true);
            try {
                String sfn = args[0];
                commandsManager.setScriptFileName(sfn);
                commandsManager.setScriptBufferedReader(new BufferedReader(new FileReader(commandsManager.getScriptFileName())));
                String line = "";
                while (true) {
                    line = commandsManager.getScriptBufferedReader().readLine();
                    System.out.println(line);
                    String[] nargs = line.split(" ");
                    if (nargs[0].equals("execute_script")) {
                        if (!scriptsNames.contains(nargs[1])) {
                            scriptsNames.add(sfn);
                        } else {
                            commandsManager.printToClient("Вы не можете выполнить команду в исполняемом скрипте, которая вызывает исполняемый скрипт, содержащий команду вызова другого исполняемого скрипта, который уже исполнялся ранее\n" + "Не удалось выполнить: execute_script " + sfn + ". Запущенные скрипты: " + scriptsNames);
                            continue;
                        }
                    }
                    scriptsNames.add(sfn);
                    CommandsManager.ExecuteCommand(Objects.requireNonNull(CommandsManager.CommandDeterminator(nargs)), priorityQueue, commandsManager.getServerDatagramChannel(), commandsManager.getSocketAddress());
                }

            } catch (NullPointerException ignored) {
            } catch (FileNotFoundException e) {
                commandsManager.printToClient("Файл не найден");

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        commandsManager.setScript(false);
        scriptsNames.clear();
    }
}
