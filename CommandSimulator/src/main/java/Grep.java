import pers.sunyunmiao.qfc.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements Command {
    //todo: slight differences between grep & java regex syntax

    private static List<String> matchLines(List<String> lines, String regex, List<String> opts) {

        List<String> results = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        for (String line : lines) {
            matcher = pattern.matcher(line);
            if (matcher.find())
                results.add(line);
        }
        return results;
    }

    private static List<String> matchFiles(String regex, List<String> opts) throws IOException {

        List<String> results = new ArrayList<String>();
        List<String> files = Utils.listDirectory("./", true);
        //todo: support options

        regex = regex.replace("/", "\\\\");
        regex = escapeForRegex(regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        for(String file: files) {
            matcher = pattern.matcher(file);
            if(matcher.matches())
                results.add(file);
        }
        return results;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{}
     */
    private static String escapeForRegex(String str) {
        if(str == null ||
                str.equals("")){
            return str;
        }

        //需要第一个替换，否则replace方法替换时会有逻辑bug
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }

    //grep ‘th*’ *.txt
    @Override
    public String process(String input, List<String> args) throws IOException {

        if (args.size() == 0) {
            return input;
        }

        String regexLines = args.get(0);
        List<String> filenames = new ArrayList<String>();
        // todo: support options
        List<String> opts = new ArrayList<String>();

        List<String> lines = new ArrayList<String>();
        if(input != null &&
                !input.isEmpty()) {

            lines = Utils.splitIntoLines(input);
        }
        if (args.size() > 1) {
            for (String regexFile : args.subList(1, args.size())) {

                filenames.addAll(matchFiles(regexFile, opts));
            }
            for (String filename : filenames) {
                String text = Utils.readFile(filename);
                lines.addAll(Utils.splitIntoLines(text));
            }
        }

        lines = matchLines(lines, regexLines, opts);

        return Utils.catenateLines(lines);
    }
}
