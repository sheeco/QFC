package pers.sunyunmiao.qfc.contentcounter.service;

import pers.sunyunmiao.qfc.contentcounter.po.Record;

import java.io.IOException;

public interface CountService {
	public Record count(String url) throws IOException;
}
