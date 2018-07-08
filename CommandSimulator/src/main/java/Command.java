
import java.util.List;

public interface Command {
    public String process(String input, List<String> args) throws Exception;
}
