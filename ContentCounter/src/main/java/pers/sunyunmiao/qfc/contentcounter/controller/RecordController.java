package pers.sunyunmiao.qfc.contentcounter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pers.sunyunmiao.qfc.contentcounter.po.Record;
import pers.sunyunmiao.qfc.contentcounter.service.CountService;
import pers.sunyunmiao.qfc.contentcounter.service.CountServiceImpl;
import pers.sunyunmiao.qfc.contentcounter.service.RecordService;
import pers.sunyunmiao.qfc.contentcounter.service.RecordServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class RecordController {

	private RecordService recordService = new RecordServiceImpl();
	private CountService countService = new CountServiceImpl();

	@RequestMapping("/query")
	public ModelAndView displayRecordList(
//			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

//        //调用service查找数据库，查询商品列表，这里使用静态数据模拟
//        List<Record> recordList = new ArrayList<Record>();
//        //向list中填充静态数据
//        Record record_1 = new Record();
//        Record record_2 = new Record();
//        recordList.add(record_1);
//        recordList.add(record_2);

		List<Record> recordList = recordService.getRecordList();

		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//相当于request的setAttribute方法,在jsp页面中通过records取数据
		modelAndView.addObject("recordList", recordList);
		modelAndView.addObject("message", "");

		//指定视图
		modelAndView.setViewName("records");

		return modelAndView;
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public ModelAndView doQuery(
			@RequestParam("urlQuery") String urlQuery
//			, HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		ModelAndView modelAndView = null;
		String feedback = "";
//		String urlQuery = (String) request.getParameter("urlQuery");
		Record record = null;
		try {

			record = countService.count(urlQuery);

		} catch (IOException e) {

			feedback = e.getMessage();
			System.out.println("[URL解析异常] " + e.getMessage());

		}
		if (record != null) {

			try {

				if (recordService.ifExists(urlQuery)) {

					recordService.update(record);
					feedback = "该文章已存在，统计结果已更新。";

				} else {

					recordService.insert(record);
				}

			} catch (Exception e) {

				feedback = e.getMessage();
				System.out.println("[数据库操作异常] " + e.getMessage());

			}
		}

		modelAndView = displayRecordList();
//		modelAndView = new ModelAndView("redirect:/query.action");
		modelAndView.addObject("message", feedback);

//		response.sendRedirect("/query.action");
//		request = request;
//		request.removeAttribute("urlQuery");
//		request.setAttribute("message", feedback);
//		request.getRequestDispatcher("/query.action").forward(request, response);

		return modelAndView;
	}
}
