public abstract class AbstractCommand {
    protected String name; //имя команды
    protected  String help;//описовает что делает наша команда
    public String getName(){
        return  name;
    }
    public String getHelp() {
        return help;
    }
}
