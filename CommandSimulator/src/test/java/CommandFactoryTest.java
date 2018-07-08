import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandFactoryTest {
    @Test
    public void process() throws Exception {
        String output = CommandFactory.process("cat", null,
                new ArrayList<String>(Arrays.asList("a.txt", "b.txt")));
        System.out.println(output);
    }

}