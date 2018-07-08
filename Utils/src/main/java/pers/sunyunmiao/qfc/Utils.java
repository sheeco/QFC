package pers.sunyunmiao.qfc;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String readFile(String filename) throws IOException {

        List<String> lines = readLines(filename);
        return catenateLines(lines);
    }

    public static List<String> toList(String[] array) {
        return new ArrayList<String>(Arrays.asList(array));
    }

    public static String catenateLines(List<String> lines) {

        StringBuffer buffer = new StringBuffer();
        for (String line : lines)
            buffer.append(line).append(Utils.getNewLine());
        return buffer.toString();
    }


    public static List<String> listDirectory(String root, boolean relative) throws IOException {

        File directory = new File(root);
        File[] tempFiles;
        List<String> filenames = new ArrayList<String>();
        List<String> results = new ArrayList<String>();

        if (directory.isDirectory() &&
                (tempFiles = directory.listFiles()) != null) {

            for (File file : tempFiles) {
                if (file.isFile()) {
                    filenames.add(file.getPath());
                } else if (file.isDirectory()) {
                    filenames.addAll(listDirectory(file.getPath(), false));
                }
            }
        }
        if (relative) {
            Path base = directory.toPath();
            for (String file : filenames) {
                Path path = new File(file).toPath();
                file = base.relativize(path).toString();
                results.add(file);
            }
        } else
            results = filenames;

        return results;
    }

    public static String getNewLine() {
        return "\n";
    }

    public static List<String> splitIntoLines(String text) {
        if (text == null ||
                text.isEmpty())
            return new ArrayList<>();
        return toList(text.split("(\\r\\n)|(\\n)|(\\r)"));
    }

    public static void writeToFile(String filename, String text, boolean append) throws IOException {
        File file = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
        writer.write(text);
        writer.close();
    }

    public static void writeToFile(String filename, List<String> lines, boolean append) throws IOException {
        writeToFile(filename, catenateLines(lines), append);
    }

    public static List<String> readLines(String filename) throws IOException {

        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    public static void printLines(List<String> lines) {
        int i = 1;
        for (String line : lines) {
            System.out.println("[" + i + "] " + line);
            i++;
        }
    }

    /**
	 * Download text file from given URL and return the whole text.
	 *
	 * @param strUrl URL of the text file to download
	 * @return the whole text in the file
	 * @throws IOException
	 */
	public static String readRemoteTextFile(String strUrl) throws IOException {

		URL url = new URL(strUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// set timeout duration
		connection.setConnectTimeout(3 * 1000);
//			// add user agent to avoid 403
//			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		InputStream inputStream = connection.getInputStream();

		return readIntoString(inputStream);
	}

	/**
	 * Read <tt>InputStream</tt> into <tt>String</tt>.
	 *
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String readIntoString(InputStream inputStream) throws IOException {

		String content = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
		Closeables.closeQuietly(inputStream);
		return content;
	}

	    public static int countMatch(String text, String regex) {

        Matcher m = Pattern.compile(regex).matcher(text);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }




}
