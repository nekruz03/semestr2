package Program.Common.Command.Commands;

import Program.Common.Command.ICommand;
import Program.Common.Communicator;
import Program.Common.DataClasses.*;
import Program.Server.InnerServerTransporter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.time.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Добавляет новый элемент в коллекцию.
 */
public class AddCommand implements ICommand {
    private Connection connection;
    public AddCommand(Connection connection) {
        this.connection = connection;
    }
    public AddCommand() {}

    @Override
    public Boolean inputValidate(String args) {
        String[] userData = args.split(",");
        for (int i = 0; i < userData.length; i++) {
            userData[i] = userData[i].trim();
        }

        if(userData.length < 13){
            System.out.println("Not all options are specified.");
            return false;
        }else
            return true;
    }

    @Override
    public InnerServerTransporter handle(InnerServerTransporter transporter) {

        String req = "select * from workers where login = '"+transporter.getLogin()+"'";
        ResultSet res;
        try {
            res = connection.prepareStatement(req).executeQuery();

            String name = null;
            int id = -1;
            while(res.next()) {
                name = res.getString("name");
                id = res.getInt("id");
            }
            if(name != null) {
                transporter.setMsg("File with this login is already exist, id = " + id);
                return transporter;
            }
        }catch (SQLException e) {
            transporter.setMsg("SQLException in add command");
            PrintStream printStream;
            try {
                printStream = new PrintStream(System.out, true, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
            printStream.println(e);
            return transporter;
        }

        transporter.setMsg("def");
        //сохранить основную коллекцию
        LinkedList<Worker> buff = new LinkedList<>(transporter.getWorkersData());
        //обнулить текущую
        transporter.setWorkersData(new LinkedList<>());
        //создать новый элемент
        transporter = createNewWorker(transporter);
        //сохранить новый элемент в бд
        Communicator communicator = new Communicator();

        boolean k = communicator.merge_db(connection,transporter.getWorkersData());
        System.out.println(k);
        //добавить новый элемент в коллекцию и обновить главную коллекцию
        if(!transporter.getMsg().equals("def")){
            transporter.setWorkersData(buff);
            return transporter;
        }
        else if(k) {
            try {
                PreparedStatement s = connection.prepareStatement("select * from workers where login = ?");
                s.setString(1,transporter.getLogin());
                res = s.executeQuery();
                int id = -1;
                while(res.next()) {
                    if (res.getInt("id") > id)
                        id = res.getInt("id");
                }
                transporter.getWorkersData().getFirst().setId(id);
            }catch (SQLException e){
                transporter.setMsg("ID definition error in add command.");
            }
            transporter.setMsg("Command completed.");
            buff.add(transporter.getWorkersData().getFirst());
            transporter.setWorkersData(buff);
        }else
            transporter.setWorkersData(buff);
        return transporter;
    }

        @Override
        public String getName () {
            return "add";
        }

        @Override
        public String getHelp () {
            return "Adds a new element to the collection, input order:\n" +
                    "name, X, Y, salary, startDate, endDate(date, time), position, birthday(date, time), height, weight, passportID.\n" +
                    "Example: Kevin 10.5 10.5 15.5 2002-02-02 2020-12-12 15:50 MANAGER 2002-02-02 15:26 180 65 888888.\n" +
                    "To leave the field blank, enter \"\"\n." +
                    "Empty (null) can be: endDate, position, birthday и weight.";
        }

        public PersonCreator createNewPerson(String birthdayDate, String birthdayTime, String height, String weight, String passportID, Person person){

            PersonCreator personCreator = new PersonCreator();

                try {
                    if (birthdayDate.equals("\"\""))
                        person.setBirthday(null);
                    else {
                        String[] perDate = birthdayDate.split("-");
                        String[] perTime = birthdayTime.split(":");

                        person.setBirthday(LocalDateTime.of(Integer.parseInt(perDate[0]),
                                Integer.parseInt(perDate[1]),
                                Integer.parseInt(perDate[2]),
                                Integer.parseInt(perTime[0]),
                                Integer.parseInt(perTime[1])));
                    }

                    } catch(DateTimeException | NumberFormatException e){
                        personCreator.setMsg("Invalid birthday field data. Example: 2000-10-12 16:35.");
                        personCreator.setPerson(null);
                }


                try {
                    person.setHeight(Integer.parseInt(height));
                } catch (NumberFormatException e) {
                    personCreator.setMsg("Incorrect data type height.");
                    personCreator.setPerson(null);
                }

                try {
                    if(weight.equals("\"\""))
                        person.setWeight(null);
                    else
                        person.setWeight(Float.parseFloat(weight));
                } catch (NumberFormatException e) {
                    personCreator.setMsg("Incorrect data type weight.");
                    personCreator.setPerson(null);
                }

                try {
                    if(passportID.length()<30 && passportID.length() > 3)
                        person.setPassportID(passportID);
                    else
                        throw new IOException();
                } catch (IOException e) {
                    personCreator.setMsg("Incorrect length passportID(3<x<30).");
                    personCreator.setPerson(null);
                }

                personCreator.setPerson(person);
                personCreator.setMsg("");

            return personCreator;
        }

        public InnerServerTransporter createNewWorker(InnerServerTransporter transporter){
            String Login = transporter.getLogin();
            String Password = transporter.getPassword();

            LinkedList<Worker> WorkerData = transporter.getWorkersData();
            String args = transporter.getArgs();

            Collections.sort(WorkerData);
            String[] userData = args.split(",");

            Consumer<String[]> consumer = userData1 -> {
                for (int i = 0; i < userData1.length; i++) {
                    userData1[i] = userData1[i].trim();
                }
            };
            consumer.accept(userData);

            if (userData.length < 13) {
                transporter.setMsg("Not all options are specified.");
                return transporter;
            }

            Worker worker;

            Integer id;
            String name;
            Coordinates coordinates;
            Float salary;
            LocalDate startDate;
            LocalDateTime endDate = null;
            Position position = null;
            Person person = new Person();

            try {
                id = WorkerData.getLast().getId() + 1;
            } catch (NoSuchElementException e) {
                id = 1;
            }


            name = userData[0];
            if (name.equals("\"\"")) {
                transporter.setMsg("The name field cannot be empty.");
                return transporter;
            }

            try {
                Float x = Float.parseFloat(userData[1]);
                Double y = Double.parseDouble(userData[2]);
                coordinates = new Coordinates(x, y);
            } catch (NumberFormatException e) {
                transporter.setMsg("Datatype error for Coordinates(x/y). Fields cannot be null.");
                return transporter;
            }

            try {
                salary = Float.parseFloat(userData[3]);
            } catch (NumberFormatException e) {
                transporter.setMsg("Salary field data type error. The field cannot be null.");
                return transporter;
            }

            try {
                String[] stData = userData[4].split("-");
                startDate = LocalDate.of(Integer.parseInt(stData[0]),
                        Integer.parseInt(stData[1]),
                        Integer.parseInt(stData[2]));
            } catch (DateTimeException | NumberFormatException e) {
                transporter.setMsg("Error in startDate data, example: 2000-10-15. Field cannot be null");
                return transporter;
            }

            try {
                String s = userData[5];
                if (!s.equals("\"\"")) {
                    String[] ed = userData[5].split("-");
                    String[] et = userData[6].split(":");
                    endDate = LocalDateTime.of(Integer.parseInt(ed[0]),
                            Integer.parseInt(ed[1]),
                            Integer.parseInt(ed[2]),
                            Integer.parseInt(et[0]),
                            Integer.parseInt(et[1]));
                }
            } catch (DateTimeException | NumberFormatException e) {
                transporter.setMsg("Error in endDate data, example: 2000-10-15.");
                return transporter;
            }

            try {
                String pos;
                pos = userData[7];

                if (!pos.equals("\"\""))
                    position = Position.valueOf(pos);
            } catch (IllegalArgumentException e) {
                transporter.setMsg("Incorrect position data. Example: MANAGER.");
                return transporter;
            }

            PersonCreator personCreator = createNewPerson(userData[8], userData[9], userData[10], userData[11], userData[12], person);

            if (person != null) {
                worker = new Worker(Login,
                        Password,
                        id,
                        name,
                        coordinates,
                        ZonedDateTime.now(),
                        salary,
                        startDate,
                        endDate,
                        position,
                        personCreator.getPerson());

                WorkerData.add(worker);
                transporter.setWorkersData(WorkerData);
                transporter.setMsg("def");
            } else {
                transporter.setMsg(personCreator.getMsg());
            }
            return transporter;
        }

        public static class PersonCreator{
        Person person = new Person();
        String msg;

            public Person getPerson() {
                return person;
            }

            public void setPerson(Person person) {
                this.person = person;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }

    }

