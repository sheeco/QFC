package pers.sunyunmiao.qfc;

import pers.sunyunmiao.qfc.Utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShenDiaoXiaLv {
//现有 2 个文件，地址在：
//sdxl_prop.txt（http://qfc.qunar.com/homework/sdxl_prop.txt）
//sdxl_template.txt（http://qfc.qunar.com/homework/sdxl_template.txt）
//【要求】：
//根 据 sdxl_prop.txt 中 内 容 替 换 掉 sdxl_template.txt 里
//$function(index)形式文字，将其还原成一本完整小说，写到文件
//sdxl.txt 中，输出在 classpath 下。
//其中 function 有 4 种函数，替换规则如下：
//1) natureOrder 自然排序，即文本中排列顺序
//2) indexOrder 索引排序，文本中每行第一个数字为索引
//3) charOrder 文本排序， java 的字符排序
//4) charOrderDESC 文本倒序， java 的字符倒序
//注意： txt 文件需要程序中现读现解析，而不是下载成本地文件。
//结果文件可以参考附件中的 sdxl.txt

	private static String urlProp = "http://qfc.qunar.com/homework/sdxl_prop.txt",
			urlTemplate = "http://qfc.qunar.com/homework/sdxl_template.txt";

	public static void restoreByTemplate() throws Exception {

		String prop = Utils.readRemoteTextFile(urlProp),
				template = Utils.readRemoteTextFile(urlTemplate);

//		URL url = ShenDiaoXiaLv.class.getProtectionDomain().getCodeSource().getLocation();
//		String path = java.net.URLDecoder.decode (url.getPath(), "utf-8");
//		String path = "ShenDiaoXiaLv";
		String filename_prop = "sdxl_prop.txt",
				filename_template = "sdxl_template.txt",
				filename_result = "sdxl.txt";
//		Utils.writeToFile(filename_prop, prop, false);
//		Utils.writeToFile(filename_template, template, false);

//		String prop = pers.sunyunmiao.qfc.Utils.readFile(filename_prop),
//				template = pers.sunyunmiao.qfc.Utils.readFile(filename_template);

		String result = decode(template, prop);

		Utils.writeToFile(filename_result, result, false);
	}

	private static String decode(String template, String prop) {

		List<String> linesProp = Utils.splitIntoLines(prop);
		int nProp = linesProp.size();

		//Prepare for the 4 different encoding schemes

		//1) list by original("nature") order
		List<String> listProp = Lists.newArrayListWithExpectedSize(nProp);

		//2) map by index
		Map<String, String> mapProp = Maps.newHashMapWithExpectedSize(nProp);
		for (String line : linesProp) {

			//Extract indice & strings
			String[] splitted = line.split("\\s", -1);
			String index = splitted[0],
					str = splitted[1];

			listProp.add(str);
			mapProp.put(index, str);
		}

		//3&4) list by char order
		List<String> sortedProp = null;
		//Sort by char(dictionary) order
		Ordering<String> charOrdering = Ordering.natural();
		sortedProp = charOrdering.sortedCopy(listProp);

		//Process template

		//Prepare Pattern objects
		String[] keywords = {
				"natureOrder",
				"indexOrder",
				"charOrder",
				"charOrderDESC",
		};
		List<Pattern> patterns = Lists.newArrayList();
		for (String keyword : keywords) {
			Pattern pattern = Pattern.compile("\\$" + keyword + "\\(([\\d]+)\\)");
			patterns.add(pattern);
		}

		//Process by lines
		List<String> lines = Utils.splitIntoLines(template),
				resultLines = Lists.newArrayList();
		for (int iline = 0; iline < lines.size(); iline++) {

			String line = lines.get(iline);
			if (line.length() > 13) {  //skip if the line is too short to have code in it

				//Assume it possible to have more than 1 code in a single line
				for (int i = 0; i < 4; i++) {

					Matcher matcher = patterns.get(i).matcher(line);
					while (matcher.find()) {
						//Extract code & index of ordering
						String code = matcher.group(0),
								indexString = matcher.group(1),
								target = "#";
						int index = Integer.parseInt(indexString);

						//Decode
						switch (i) {
							case 0:
								target = listProp.get(index);
								break;
							case 1:
								target = mapProp.get(indexString);
								break;
							case 2:
								target = sortedProp.get(index);
								break;
							case 3:
								target = sortedProp.get(nProp - 1 - index);
								break;
							default:
								break;
						}

						//Do replace
						System.out.print("[" + iline + "] " + line);
						line = line.replace(code, target);
						System.out.println(" -> " + line);
					}

				}
			}
			resultLines.add(line);
		}

		return Utils.catenateLines(resultLines);
	}

//	/**
//	 * 从输入流中获取字节数组
//	 *
//	 * @param inputStream
//	 * @return
//	 * @throws IOException
//	 */
//	public static byte[] readIntoByteArray(InputStream inputStream) throws IOException {
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		while ((len = inputStream.read(buffer)) != -1) {
//			bos.write(buffer, 0, len);
//		}
//		bos.close();
//		return bos.toByteArray();
//	}

}

