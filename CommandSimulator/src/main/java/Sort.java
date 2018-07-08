import pers.sunyunmiao.qfc.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sort implements Command {

    private static void sort(List<String> lines, List<String> opts) throws IOException {
        Collections.sort(lines);
    }

    @Override
    public String process(String input, List<String> args) throws IOException {
        List<String> filenames, opts;
        // todo: support options
        filenames = args;
        opts = new ArrayList<String>();

        List<String> lines = Utils.splitIntoLines(input);

        for (String filename : filenames) {
            if (!filename.isEmpty()) {
                String temp = Utils.readFile(filename);
                lines.addAll(Utils.splitIntoLines(temp));
            }
        }

        if(lines.isEmpty())
            return "";

        sort(lines, opts);

        return Utils.catenateLines(lines);
    }

}
