import org.junit.Test;
import pers.sunyunmiao.qfc.Utils;

public class SortTest {

    private static String[] testFiles = new String[]{"a.txt", "b.txt", "c.txt"};

    @Test
    public void process() throws Exception {
        System.out.println("Testing Sort ...");
        String input = Utils.readFile(testFiles[0]);
        String output = new Sort().process(input, Utils.toList(testFiles).subList(1, 3));
        System.out.println(output);
        System.out.println("Done");
    }

}