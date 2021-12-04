/**
	 * @author Nithish	
*/
package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.DashRTMModel;
import com.example.demo.model.DashReqModel;
import com.example.demo.model.DashTestModel;
import com.example.demo.model.Defect;
import com.example.demo.model.DefectHistory;
import com.example.demo.model.FileCount;
import com.example.demo.model.IdOnly;
import com.example.demo.model.PrevDayCount;
import com.example.demo.model.ProjectModel;
import com.example.demo.model.RequirementModel;
import com.example.demo.model.TestCaseModel;
import com.example.demo.model.TestHistory;

@Service
public class DashboardService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private TestCaseService testcaseservice;

	@Autowired
	private ProjectService projectservice;

	@Autowired
	private DefectService defectservice;

	/**
	 * Method to add File into db
	 * @param FileModel which contains the file details.
	 * @return FileModel with respective status and information.
	 * @throws BadRequestException handles Exception.

	 */
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

	public List<DashRTMModel> getRTM() {

		List<TestCaseModel> testcases = testcaseservice.getAllTestCase();
		List<ProjectModel> projects = projectservice.getAllProjects();
		List<RequirementModel> requirements = projectservice.getAllRequirements();
		List<Defect> defects = defectservice.getAllDefects(new HashMap<String, String>());

		List<DashRTMModel> response = new ArrayList<DashRTMModel>();

//			for (ProjectModel project : projects) 
		for (int i = 0; i < 4; i++) {

			DashRTMModel rtmModel = new DashRTMModel();
			List<DashReqModel> tempRequirements = new ArrayList<DashReqModel>();

			for (RequirementModel requirement : requirements) {

				List<DashTestModel> tempTests = new ArrayList<DashTestModel>();

				if (requirement.getProjectId() != null && requirement.getProjectId().equals(projects.get(i).getId())) {
					DashReqModel reqModel = new DashReqModel();
					for (TestCaseModel test : testcases) {
						if (test.getProjectId() != null
								&& test.getRequirementId().equals(requirement.getRequirementId())
								&& test.getProjectId().equals(projects.get(i).getId())) {
							DashTestModel testModel = new DashTestModel();
							testModel.setName(test.getName());
							testModel.setStatus(test.getStatus());
							testModel.setTestcaseId(test.getTestcaseId());
							tempTests.add(testModel);

						}

					}
					reqModel.setRequirementId(requirement.getRequirementId());
					reqModel.setDescription(requirement.getDescription());
					reqModel.setTestCases(tempTests);
					tempRequirements.add(reqModel);
				}

			}
			rtmModel.setId(projects.get(i).getId());
			rtmModel.setName(projects.get(i).getName());
			rtmModel.setRequirements(tempRequirements);
			response.add(rtmModel);

		}

		return response;
	}

}
