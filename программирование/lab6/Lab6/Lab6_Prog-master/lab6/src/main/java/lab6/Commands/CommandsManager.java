package lab6.Commands;

import lab6.Collections.SpaceMarine;
import lab6.InputHandler;
import lab6.Exceptions.InvalidCountOfArgumentsException;
import lab6.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Класс управляющий выборкой команд
 *
 * @author Остряков Егор, P3112
 */

public class CommandsManager {
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());


    private static HashSet<AbstractCommand> commands = new HashSet<>();
    private static CommandsManager commandsManager = new CommandsManager();
    private DatagramChannel serverDatagramChannel;
    private SocketAddress socketAddress;
    private String scriptFileName;
    private boolean isScript = false;
    private BufferedReader scriptBufferedReader;

    /**
     * Конструктор при вызове которого в HashSet commands будут добавлены все доступные команды
     */
    private CommandsManager() {
        commands.add(new Add());
        commands.add(new AddIfMin());
        commands.add(new Clear());
        commands.add(new ExecuteScript());
        commands.add(new Exit());
        commands.add(new Help());
        commands.add(new Info());
        commands.add(new MaxByHealth());
        commands.add(new PrintUniqueHealth());
        commands.add(new RemoveAnyByLoyal());
        commands.add(new RemoveById());
        commands.add(new RemoveFirst());
        commands.add(new RemoveGreater());
        commands.add(new Save());
        commands.add(new Show());
        commands.add(new Update());

    }

    /**
     * Запускает выполнение команды
     *
     * @param command         команда, выполненеие которой нужно запустить
     * @param priorityQueue   коллекция, с которой команда взаимодействует
     * @param datagramChannel канал для передачи сообщений клиенту
     * @param socketAddress   адрес порта
     */
    public static void ExecuteCommand(AbstractCommand command, PriorityQueue<SpaceMarine> priorityQueue, DatagramChannel datagramChannel, SocketAddress socketAddress) throws IOException {
        commandsManager.setServerDatagramChannel(datagramChannel);
        commandsManager.setSocketAddress(socketAddress);
        logger.info("Выполнение команды");
        command.execute(priorityQueue, commandsManager);
        int i=0;
        while (i < 1000000) {
            i++;
        }
        if (!commandsManager.isScript) {
            logger.info("Отправляем клиенту сообщение о завершении чтения");
            datagramChannel.send(ByteBuffer.wrap("I am fucking seriously, it's fucking EOF!!!".getBytes()), socketAddress);
        }
    }

    /**
     * Определяет, какую команду ввёл пользователь
     *
     * @param args команда и её аргументы в виде массива строк
     * @return введённую команду или null, если такой каманды нет
     */
    public static AbstractCommand CommandDeterminator(String[] args){
        try {
            String cmd = args[0].trim();
            args = Arrays.copyOfRange(args, 1, args.length);
            for (AbstractCommand command : commands)
                if (command.getName().equals(cmd.trim())) {
                    command.setArgs(args);
                    return command;
                }
        } catch (InvalidCountOfArgumentsException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Организует вывод текстового сообщения клиенту
     *
     * @param line строка отслыемая клиенту
     */
    public void printToClient(String line) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap((line.getBytes()));
            commandsManager.getServerDatagramChannel().send(buffer, commandsManager.getSocketAddress());
            logger.info("Отправляем ответ клиенту: {} ", new String(buffer.array()));
        } catch (IOException e) {
            logger.info("Не удалось отправить ответ клиенту {}", e.getMessage());
        }
    }

    /**
     * Возвращает объект класса SpaceMarine, сформированный из пользовательского ввода
     *
     * @return spaceMarine
     */
    public static SpaceMarine GetSpaceMarine() {
        SpaceMarine spaceMarine = InputHandler.ArgumentsReader(commandsManager);
        return spaceMarine;
    }


    /**
     * Проматывает строки в случае ошибки при считывании команды из скрипта
     */
    public void commandRewider() {
        try {
            for (int i = 1; i < 10; i++) scriptBufferedReader.readLine();
        } catch (Exception ignored) {
        }
    }

    /**
     * @return HashSet с командами
     */
    public static HashSet<AbstractCommand> GetCommands() {
        return commands;
    }

    /**
     * @param script работает в данный момент пользователь со скриптом или нет
     */
    public void setScript(boolean script) {
        isScript = script;
    }

    /**
     * @return работает в данный момент пользователь со скриптом или нет
     */
    public boolean isScript() {
        return isScript;
    }

    /**
     * @return имя скрипта
     */
    public String getScriptFileName() {
        return scriptFileName;
    }

    /**
     * @param scriptFileName имя скрипта
     */
    public void setScriptFileName(String scriptFileName) {
        this.scriptFileName = scriptFileName;
    }

    /**
     * @return считыватель скрипта
     */
    public BufferedReader getScriptBufferedReader() {
        return scriptBufferedReader;
    }

    /**
     * @param scriptBufferedReader считыватель скрипта
     */
    public void setScriptBufferedReader(BufferedReader scriptBufferedReader) {
        this.scriptBufferedReader = scriptBufferedReader;
    }

    /**
     * Переопределение интерфейса Comparator для сравнения элементов коллекции по полю Health
     */
    private static Comparator<SpaceMarine> idComparator = (o1, o2) -> (int) (o1.getHealth() - o2.getHealth());

    /**
     * @return компаратор для сравнения элементов коллекции по полю Health
     */
    public static Comparator<SpaceMarine> GetIdComparator() {
        return idComparator;
    }

    /**
     * @param datagramChannel datagramChannel сервера
     */
    public void setServerDatagramChannel(DatagramChannel datagramChannel) {
        serverDatagramChannel = datagramChannel;
    }

    /**
     * @return datagramChannel сервера
     */
    public DatagramChannel getServerDatagramChannel() {
        return serverDatagramChannel;
    }

    /**
     * @param socketAddress socketAddress сервера
     */
    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    /**
     * @return socketAddress сервера
     */
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
