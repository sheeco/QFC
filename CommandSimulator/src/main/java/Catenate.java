import pers.sunyunmiao.qfc.Utils;

import java.io.*;
import java.util.List;

public class Catenate implements Command{

    // e.g. a.txt b.txt > o.txt
    // e.g. a.txt b.txt c.txt >> o.txt
    @Override
    public String process(String input, List<String> args) throws IOException {
        StringBuffer buffer = new StringBuffer();
        for(String filename: args) {
            buffer.append( Utils.readFile(filename) );
        }
        return buffer.toString();
    }

}
