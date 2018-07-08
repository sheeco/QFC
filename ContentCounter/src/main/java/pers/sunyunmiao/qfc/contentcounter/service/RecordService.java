package pers.sunyunmiao.qfc.contentcounter.service;

import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.io.IOException;
import java.util.List;

public interface RecordService {

	public List<Record> getRecordList() throws Exception;

	public boolean ifExists(String url) throws Exception;

	public void insert(Record record) throws Exception;

	public void update(Record record) throws Exception;

}
