import org.junit.Test;
import pers.sunyunmiao.qfc.Utils;

import java.util.ArrayList;
import java.util.List;

public class CutTest {

    private static String[] testFiles = new String[]{"a.txt", "b.txt", "c.txt"};

    @Test
    public void process() throws Exception {
        System.out.println("Testing filenames ...");
        Cut cut = new Cut();
        List<String> args = new ArrayList<>();
        args.add(testFiles[2]);
        args.add("-c");
        args.add("-3,3-6,2-7,13-10,80-");
        String output = cut.process(null, args);
        System.out.println(output);
        System.out.println("Testing pipe ...");
        String input = Utils.readFile(testFiles[2]);
        output = cut.process(input, args.subList(1, 3));
        System.out.println(output);
        System.out.println("Done");
    }

}