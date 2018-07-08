import pers.sunyunmiao.qfc.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ValidLine {

    /**
     * @param path   path of given file
     * @param silent will show total line count & found comment lines in console if <tt>silent</tt> is <tt>true</tt>
     * @return countValidLine of valid lines
     * @throws IOException if given file is not found or not readable
     */
    public static int countValidLine(String path, boolean silent) throws IOException {
        int count = 0;
        List<String> lines = Utils.readLines(path),
                comments = new ArrayList<>();

        String line = null;
        for (int i = 0; i < lines.size(); ) {

            line = lines.get(i);

            if (isEmptyLine(line))
                i++;

            else if (isSingleLineComment(line)) {

                comments.add(line);
                i++;

            } else if (startsMultiLineComment(line)) {

                int j = i + 1;
                while (!endsMultiLineComment(lines.get(j))) {

                    j++;
                }
                comments.addAll(lines.subList(i, j + 1));
                i = j + 1;

            } else {

                count++;
                i++;
            }
        }
        if (!silent) {

            System.out.println("Total line count: " + lines.size());
            System.out.println("Found comments: ");
            Utils.printLines(comments);
        }

        return count;
    }

    private static boolean isEmptyLine(String line) {
        return line.trim().length() == 0;
    }

    private static boolean isSingleLineComment(String line) {
        line = line.trim();
        return line.startsWith("//") ||
                (line.startsWith("/*") && line.endsWith("*/")) ||
                (line.startsWith("<!--") && line.endsWith("-->"));
    }

    public static boolean startsMultiLineComment(String line) {
        line = line.trim();
        return (line.startsWith("/*") && !line.endsWith("*/")) ||
                (line.startsWith("<!--") && !line.endsWith("-->"));
    }

    public static boolean endsMultiLineComment(String line) {
        line = line.trim();
        return (!line.startsWith("/*") && line.endsWith("*/")) ||
                (!line.startsWith("<!--") && line.endsWith("-->"));
    }

}