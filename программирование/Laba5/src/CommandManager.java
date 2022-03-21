import java.util.HashSet;
import java.util.logging.Filter;

public class CommandManager {
    private static HashSet<AbstractCommand> commands = new HashSet<>();
    private static CommandManager commandsManager = new CommandManager();
    private  CommandManager(){
        commands.add(new Help());
        commands.add(new Info());
        commands.add(new Show());
        commands.add(new Add());
        commands.add(new Update_id());
        commands.add(new Remove_by_id());
        commands.add(new Clear());
        commands.add(new Save());
        commands.add(new Execute_script_file_name());
        commands.add(new Exit());
        commands.add(new Remove_first());
        commands.add(new Remove_greater());
        commands.add(new History());
        commands.add(new Count_less_than_mood());
        commands.add(new Filter_by_car());
        commands.add(new Print_field());
    }


}
