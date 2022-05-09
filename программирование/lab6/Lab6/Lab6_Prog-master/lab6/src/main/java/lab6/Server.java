package lab6;

import lab6.Collections.SpaceMarine;
import lab6.Commands.AbstractCommand;
import lab6.Commands.CommandsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.util.*;
import java.util.PriorityQueue;

/**
 * Класс сервера Server
 *
 * @author Остряков Егор, P3112
 */
public class Server implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());
    Selector selector;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private AbstractCommand command = null;
    private String[] args;
    private static Date creationDate;
    private static int port = 1488;

    /**
     * Ну это main()...
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
     // args = new String[]{"C:\\Users\\Lenovo\\Desktop\\Lab6\\Lab6_Prog-master\\lab6\\in.csv", ";"};
        Server server = new Server();
        server.setArguments(args);
        logger.info("Запускаем работу сервера по порту " + port);
        server.run();
    }


    /**
     * Получает сообщение от клиента
     *
     * @param priorityQueue коллекция, с которой работаем
     * @throws IOException IOException
     */
    private void receive(PriorityQueue<SpaceMarine> priorityQueue) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100000000);
        byteBuffer.clear();
        socketAddress = datagramChannel.receive(byteBuffer);
        byteBuffer.flip();
        if (socketAddress != null) {
            Object o = new Serialization().DeserializeObject(byteBuffer.array());
            if (o == null) {
                logger.info("Получена несуществующая команда");
                datagramChannel.send(ByteBuffer.wrap("Команда не найдена или имеент неверное количество аргументов. Для просмотра доступных команд введите help".getBytes()), socketAddress);
                datagramChannel.send(ByteBuffer.wrap("I am fucking seriously, it's fucking EOF!!!".getBytes()), socketAddress);
                return;
            }
            if (!o.getClass().getName().contains(".SpaceMarine")) {
                command = (AbstractCommand) o;
                logger.info("Сервер получил команду: " + command.getName());
                if (!command.isNeedObjectToExecute()) {
                    logger.info("Команда не требует объекта для выполнения. Начинаем выполненение");
                    CommandsManager.ExecuteCommand(command, priorityQueue, datagramChannel, socketAddress);
                }
            } else if (command != null) {
                SpaceMarine spaceMarine = new SpaceMarine((SpaceMarine) o);
                command.setSpaceMarine(spaceMarine);
                logger.info("Получен объект {}, необходимый команде. Начинаем выполнение", spaceMarine);
                CommandsManager.ExecuteCommand(command, priorityQueue, datagramChannel, socketAddress);
            }
        }
    }

    /**
     * Ну это run()...
     */
    @Override
    public void run() {
        PriorityQueue<SpaceMarine> priorityQueue = new PriorityQueue<>(CommandsManager.GetIdComparator());
        creationDate = new Date();
        if (args.length < 1 || args.length > 2) {
            logger.error("Неверное количество аргументов: {}, ожидалось: 1 или 2", args.length);
            System.exit(-1);
        }
        if (args.length == 1) {
            args = new String[]{args[0], ";"};
            args = args;
            logger.info("Был принят один аргумент, стандартным разделителем выбран: ';'");
        }
        if (args.length == 2 && !args[1].equals(";") && !args[1].equals(",")) {
            logger.error("Вторым аргументом может являться только ',' или ';'");
            System.exit(-1);
        }
        InputHandler.InputLoader(args, priorityQueue);
        logger.info("Коллекция загружена");
        socketAddress = new InetSocketAddress(port);
        try {
            selector = Selector.open();
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(socketAddress);
            datagramChannel.configureBlocking(false);
            datagramChannel.register(selector, datagramChannel.validOps());
            logger.info("Канал открыт и готов для приёма сообщений");
            while (true) {
                receive(priorityQueue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param arguments имя файла с коллекцией
     */
    public void setArguments(String[] arguments) {
        this.args = arguments;
    }

    /**
     *
     * @return время создания коллекции
     */
    public static Date GetCreationDate() {
        return creationDate;
    }
}
