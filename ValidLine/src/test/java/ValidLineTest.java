import org.junit.Test;
import pers.sunyunmiao.qfc.Utils;

import static org.junit.Assert.*;

public class ValidLineTest {
	@Test
	public void countValidLine() throws Exception {

		String inputfile = "StringUtils.java",
				outputfile = "validLineCount.txt";

		System.out.println("Input file: \"" + inputfile + "\"");
		int count = ValidLine.countValidLine(inputfile, false);
		Utils.writeToFile(outputfile, Integer.toString(count), false);
		System.out.println("The result has been saved to \"" + outputfile + "\".");
	}

}