/**
	 * @author Nithish	
*/
package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.DefectHistory;
import com.example.demo.model.FileCount;
import com.example.demo.model.IdOnly;
import com.example.demo.model.PrevDayCount;
import com.example.demo.model.TestHistory;

@Service
public class DashboardService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<IdOnly> getPrevDayListAndUpdate(String historyType, List<IdOnly> currTestLists) {
		Query q = new Query(Criteria.where("historyType").is(historyType));
		PrevDayCount prevdaycount = mongoTemplate.findOne(q, PrevDayCount.class);
		Update update = new Update().set("historyTypeLists", currTestLists);
		mongoTemplate.upsert(q, update, PrevDayCount.class);

		return (prevdaycount != null) ? prevdaycount.getHistoryTypeLists() : Collections.emptyList();

	}

	public String addEntryToDefectHistory(DefectHistory entry) {
		mongoTemplate.save(entry);
		return entry.getTime() + " start " + entry.getDefectCountStartOfDay() + " " + entry.getDefectCountEndOfDay()
				+ " added";

	}

	public String addEntryToTestHistory(TestHistory entry) {
		mongoTemplate.save(entry);
		return entry.getTime() + " start " + entry.getTestCountStartOfDay() + " " + entry.getTestCountEndOfDay()
				+ " added";

	}

}
