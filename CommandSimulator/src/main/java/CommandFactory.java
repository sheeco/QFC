import java.util.*;

public class CommandFactory {

    static private Hashtable<String, Command> reflects = new Hashtable<String, Command>();
    static {
        reflects.put("cat", new Catenate());
        reflects.put("sort", new Sort());
        reflects.put("cut", new Cut());
        reflects.put("wc", new WordCount());
        reflects.put("grep", new Grep());
    }

    public static String process(String keyword, String input, List<String> args) throws Exception {

        Class cl = null;
        if (reflects.containsKey(keyword)) {
            Command command = reflects.get(keyword);;
            return command.process(input, args);
        } else {
            throw new IllegalArgumentException("Cannot find command '" + keyword + "'.");
        }
    }
}
