/**
	 * @author Nithish	
*/
package com.example.demo.schedulers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.DefectHistory;
import com.example.demo.model.FileCount;
import com.example.demo.model.IdOnly;
import com.example.demo.model.TestHistory;
import com.example.demo.service.DashService;
import com.example.demo.service.DashboardService;
import com.example.demo.service.DefectService;
import com.example.demo.service.TestCaseService;

@Component
public class ScheduledTasks {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@PostConstruct
    public void onStartup() {
//		long currDefectCount = defectservice.getDefectCount();
		List<IdOnly> currTestLists = testCaseService.getOpenTests();
//		dashboardService.getPrevDayListAndUpdate("defect", currDefectCount);
		dashboardService.getPrevDayListAndUpdate("test", currTestLists);
		
    }

	@Autowired
	private DashService dashservice;

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private DefectService defectservice;
	
	@Autowired
	private TestCaseService testCaseService;

	@Scheduled(cron = "0 19 21 * * *")
	public void scheduleTaskWithFixedRate() {

		long files_count = dashservice.countFiles();
		FileCount entry = new FileCount();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		entry.setFilesCount(files_count);
		entry.setTime(dtf.format(now));

		logger.info(dashservice.addEntry(entry));
	}

//	@Scheduled(cron = "0/60 * * * * ?")
//	public void defectHistory() {
//
//		long currDefectCount = defectservice.getDefectCount();
//		DefectHistory entry = new DefectHistory();
//
//		List<IdOnly> prevDefectList = dashboardService.getPrevDayListAndUpdate("defect", currDefectCount);
//
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//		LocalDateTime now = LocalDateTime.now();
//
//		entry.setTime(dtf.format(now));
//		entry.setDefectCountStartOfDay(prevDefectList.size());
//		entry.setDefectCountEndOfDay(currDefectCount);
//		entry.setDefectClosedCount(defectservice.getClosedDefectCount());
//
//		logger.info(dashboardService.addEntryToDefectHistory(entry));
//
//	}
	
	@Scheduled(cron = "0/60 * * * * ?")
	public void testHistory() {

		List<IdOnly> currTestsList = testCaseService.getOpenTests();
		TestHistory entry = new TestHistory();

		List<IdOnly> prevTestList = dashboardService.getPrevDayListAndUpdate("test", currTestsList);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		entry.setTime(dtf.format(now));
		entry.setTestCountEndOfDay(currTestsList.size());
		entry.setTestCountStartOfDay(prevTestList.size());
		
		entry.setTestListDayStart(prevTestList);
		entry.setTestListDayEnd(currTestsList);
		
		entry.setTestPassCount(testCaseService.getPassedTestsCount());

		logger.info(dashboardService.addEntryToTestHistory(entry));

	}
	
	
	
	
	
	

}
