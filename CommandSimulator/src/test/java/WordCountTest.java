import org.junit.Test;
import pers.sunyunmiao.qfc.Utils;

import java.util.ArrayList;

public class WordCountTest {

    private static String[] testFiles = new String[]{"a.txt", "b.txt", "c.txt"};

    @Test
    public void process() throws Exception {
        System.out.println("Testing filenames ...");
        WordCount wc = new WordCount();
        String output = wc.process(null, Utils.toList(testFiles));
        System.out.println(output);
        System.out.println("Testing pipe ...");
        String input = Utils.readFile(testFiles[0]);
        output = wc.process(input, new ArrayList<String>());
        System.out.println(output);
        System.out.println("Done");
    }

    @Test
    public void getLineCount() throws Exception {
    }

    @Test
    public void getWordCount() throws Exception {
    }

    @Test
    public void getMatchCount() throws Exception {
    }

}