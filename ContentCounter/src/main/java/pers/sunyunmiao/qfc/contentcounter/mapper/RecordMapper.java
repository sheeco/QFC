package pers.sunyunmiao.qfc.contentcounter.mapper;

import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.util.List;

public interface RecordMapper {

	public List<Record> findRecordList() throws Exception;

	public List<Record> findByUrl(String url) throws Exception;

	public void insert(Record record) throws Exception;

	public void update(Record record) throws Exception;

}
