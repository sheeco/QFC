package pers.sunyunmiao.qfc.contentcounter.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pers.sunyunmiao.qfc.Utils;
import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.io.IOException;

public class CountServiceImpl implements CountService {

	private static int TIMEOUT = 10000;

//	@Autowired
//	private RecordMapper recordMapper;

	@Override
	public Record count(String url) throws IOException {

		if(url == null ||
				url.equals(""))
			throw new IOException("Given URL is empty.");

//		Document document = Jsoup.connect(url).timeout(TIMEOUT).get();
		Document document = Jsoup.connect(url).timeout(TIMEOUT)
				.header("User-Agent",
						"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) " +
								"Gecko/20100316 Firefox/3.6.2")
				.get();

		//Throw IOException if no <title> tag is found (to identify 403 response)
		if (document.getElementsByTag("title").first() == null)
			throw new IOException("Cannot find the title for given URL.");

		String title = document.title();
		//Strip text out from html documents
		String text = document.text();
		int countCharacter = countCharacter(text),
				countChineseCharacter = countChinese(text),
				countEnglishCharacter = countEnglish(text),
				countPunctuation = countPunctuation(text);

		Record result = new Record(url, title, countCharacter, countChineseCharacter,
				countEnglishCharacter, countPunctuation);
		return result;
	}

	/**
	 * Count non-whitespace characters in <a>text</a>.
	 *
	 * @param text
	 * @return
	 */
	public static int countCharacter(String text) {
		return Utils.countMatch(text, "\\S");
	}

	public static int countChinese(String text) {
//		//java7
//		return Utils.countMatch(text, "\\p{Han}");
		//java8
		return Utils.countMatch(text, "\\p{sc=Han}");
	}

	public static int countEnglish(String text) {
		return Utils.countMatch(text, "[a-zA-z]");
	}

	/**
	 * Consider all the non-whitespace characters except Chinese/English characters as punctuation.
	 * (including number digits)
	 *
	 * @param text
	 * @return
	 */
	public static int countPunctuation(String text) {
		return countCharacter(text) - countChinese(text) - countEnglish(text);
	}
}
