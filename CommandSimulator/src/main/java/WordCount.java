import pers.sunyunmiao.qfc.Utils;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static pers.sunyunmiao.qfc.Utils.countMatch;

public class WordCount implements Command {

//    private static String[] opts = {"-l", "-w", "-c"};

    private static String wordCount(String input, List<String> opts) throws IOException {
        StringBuffer buffer = new StringBuffer();
        int lineCount;
        lineCount = getLineCount(input);
        int wordCount;
        wordCount = getWordCount(input);
        int byteCount;
        byteCount = input.getBytes().length;

        buffer.append(lineCount + " " + wordCount + " " + byteCount);
        return buffer.toString();
    }

    @Override
    public String process(String input, List<String> args) throws IOException {

        List<String> filenames, opts;
        StringBuffer buffer = new StringBuffer();

        // todo: support options
        filenames = args;
        opts = new ArrayList<String>();

        if (input != null &&
                !input.isEmpty()) {

            buffer.append(wordCount(input, opts)).append(Utils.getNewLine());

        } else if (!filenames.isEmpty()) {

            for (String filename : filenames) {
                if (!filename.isEmpty()) {
                    input = Utils.readFile(filename);
                    buffer.append(wordCount(input, opts)).append(" " + filename).append(Utils.getNewLine());
                }
            }
        }

        return buffer.toString();
    }

    public static int getLineCount(String text) {

//        return text.split("\r\n|\r|\n", -1).length;
        return countMatch(text, "(\\r\\n)|(\\n)|(\\r)") + 1;
    }

    public static int getWordCount(String text) {

        return countMatch(text, "(\\S+)");
    }

}
