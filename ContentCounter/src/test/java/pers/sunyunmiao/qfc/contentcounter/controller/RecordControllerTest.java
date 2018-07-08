package pers.sunyunmiao.qfc.contentcounter.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;

public class RecordControllerTest {
	@Test
	public void displayRecordList() throws Exception {
	}

	@Test
	public void doQuery() throws Exception {
		ModelAndView modelAndView = new RecordController().doQuery("https://www.douban.com/note/40652974/");
	}

}