package lab6;

import java.io.*;

/**
 * Класс, проводящий сериализацию и десериализацию объектов
 *
 * @author Остряков Егор, P3112
 */
public class Serialization {
    /**
     * Сериализует объект
     * @param object объект
     * @param <T> тип объекта
     * @return массив байтов
     */
    public <T> byte[] SerializeObject(T object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);) {
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Ошибка сериализации");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Десериализует объект
     * @param buffer массив байтов
     * @param <T> тип объекта
     * @return объект
     */
    public <T> T DeserializeObject(byte[] buffer) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {
            return (T) objectInputStream.readObject();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка десериализации");
            e.printStackTrace();
        }
        return null;
    }
}
