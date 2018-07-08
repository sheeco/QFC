import pers.sunyunmiao.qfc.Utils;

import java.io.*;
import java.util.List;

public class Main {

    private static void execute(String line) throws Exception {

        line = line.replaceAll("(\\s*)(\\|)(\\s*)", " | ");
        line = line.replaceAll("\\s+", " ");
        String[] args = line.split("\\s", -1);

        List<String> listArgs = Utils.toList(args);
        String input = "";

        //resolve redirection

        String filenameRedirection = null;
        if (listArgs.contains(">")) {

            int iRedirection = listArgs.indexOf(">");
            if (iRedirection + 1 >= listArgs.size())
                throw new IllegalArgumentException("Must provide filename for redirection.");

            filenameRedirection = listArgs.get(iRedirection + 1);
            listArgs = listArgs.subList(0, iRedirection);

        } else if (listArgs.contains("<")) {

            int iRedirection = listArgs.indexOf("<");
            String fileInput = listArgs.get(iRedirection + 1);
            input = Utils.readFile(fileInput);
            listArgs = listArgs.subList(0, iRedirection);
        }

        //resolve piping
        while (!listArgs.isEmpty()) {
            int j = listArgs.indexOf("|");
            if (j < 0)
                j = listArgs.size();

            //process
            List<String> currentArgs = listArgs.subList(0, j);
            if (!currentArgs.isEmpty()) {
                String command = currentArgs.get(0);

                input = CommandFactory.process(command, input, currentArgs.subList(1, currentArgs.size()));
            }

            if (j >= listArgs.size())
                break;
            else
                listArgs = listArgs.subList(j + 1, listArgs.size());
        }

        if (filenameRedirection != null &&
                !filenameRedirection.isEmpty()) {

            Utils.writeToFile(filenameRedirection, input, false);
            System.out.println("Results have been saved to '" + filenameRedirection + "'.");
        } else
            System.out.println(input);

    }

    public static void main(String[] args) throws Exception {

        while (true) {

            System.out.print("CommandSimulator> ");

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String line = console.readLine();
            if (line.matches("\\s*(exit)\\s*"))
                break;

            Main.execute(line);
        }
    }

}
