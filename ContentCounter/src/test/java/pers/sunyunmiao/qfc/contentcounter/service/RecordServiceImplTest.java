package pers.sunyunmiao.qfc.contentcounter.service;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RecordServiceImplTest {
	@Test
	public void getRecordList() throws Exception {

		List<Record> list = service.getRecordList();
//		System.out.println(Arrays.toString(list.toArray()));
		String str = list.stream().map(Record::toString).collect(Collectors.joining(",\n"));
		System.out.println("Results of getRecordList:");
		System.out.println(str);
	}

	@Test
	public void ifExists() throws Exception {

		String url = "https://www.douban.com/note/63364660/";
		boolean exists = service.ifExists(url);
		Assert.assertTrue(exists);

		exists = service.ifExists("fake url");
		Assert.assertTrue(!exists);
	}

	@Ignore
	@Test
	public void insert() throws Exception {


		String url = "https://www.douban.com/note/63364660/";
		Record record = new CountServiceImpl().count(url);
		System.out.print("Insert the counting result for url \"" + url + "\" into database ...");
		service.insert(record);
		System.out.println("done");
	}

	@Test
	public void update() throws Exception {

		List<Record> list = service.getRecordList();
		Record record = list.get(list.size() - 1);
		System.out.println("Original record: \n\t" + record.toString());
		System.out.print("Redo the query ... ");
		try {
			record = new CountServiceImpl().count(record.getUrl());
		} catch (IOException e) {
			record = null;
			System.out.println(e.getMessage());
		}
		if (record != null) {
			System.out.println("done");
			service.update(record);

			list = service.getRecordList();
			record = list.get(list.size() - 1);
			System.out.println("Updated record: \n\t" + record.toString());
		}
	}

	private static RecordService service = new RecordServiceImpl();

	@Test
	public void getSqlSession() throws Exception {

		SqlSession sqlSession = RecordServiceImpl.getSqlSession();
		Assert.assertNotNull(sqlSession);
	}


}