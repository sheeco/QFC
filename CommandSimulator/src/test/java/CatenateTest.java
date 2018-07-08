import org.junit.Test;
import pers.sunyunmiao.qfc.Utils;

public class CatenateTest {
    private static String[] testFiles = new String[]{"a.txt", "b.txt", "c.txt"};
    private static String mockfile = "mock.txt";

    @Test
    public void process() throws Exception {
        String out = new Catenate().process(null, Utils.toList(testFiles));
        System.out.println(out);
    }

}