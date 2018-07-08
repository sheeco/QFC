import org.junit.Test;
import pers.sunyunmiao.qfc.Utils;

import java.util.List;

public class GrepTest {

    private static String[] testFiles = new String[]{"a.txt", "b.txt", "c.txt"};

    @Test
    public void process() throws Exception {
        System.out.println("Testing Grep ...");
        String input = Utils.readFile(testFiles[0]);
        String[] _args = {"th.*", "*.txt"};
        List<String> args = Utils.toList(_args);
        String output = new Grep().process(input, args);
        System.out.println(output);
        System.out.println("Done");
    }

}