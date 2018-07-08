package pers.sunyunmiao.qfc.contentcounter.service;

import org.junit.Assert;
import org.junit.Test;
import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.io.IOException;

public class CountServiceImplTest {

	private static String[] demoUrls = {
			"http://www.dispositio.net/archives/1975",
			"https://site.douban.com/129080/widget/notes/6290577/note/194889247/",
			"https://www.poetryfoundation.org/poems/47311/the-waste-land",
	};

	@Test
	public void count() throws Exception {
		String sample = "english chracters 中文字符 .,?;-";
		int[] answers = {25, 4, 16, 5};
		System.out.println("Counting characters in sample string \"" + sample + "\":");
		Assert.assertEquals(CountServiceImpl.countCharacter(sample), answers[0]);
		System.out.println("Total(non-whitespace): " + CountServiceImpl.countCharacter(sample));
		Assert.assertEquals(CountServiceImpl.countChinese(sample), answers[1]);
		System.out.println("Chinese: " + CountServiceImpl.countChinese(sample));
		Assert.assertEquals(CountServiceImpl.countEnglish(sample), answers[2]);
		System.out.println("English: " + CountServiceImpl.countEnglish(sample));
		Assert.assertEquals(CountServiceImpl.countPunctuation(sample), answers[3]);
		System.out.println("Punctuation: " + CountServiceImpl.countPunctuation(sample));

		for (String url : demoUrls) {

			System.out.println("\nCounting content from url \"" + url + "\":");
			try {

				Record result = new CountServiceImpl().count(url);
				System.out.println(result.toString());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

}