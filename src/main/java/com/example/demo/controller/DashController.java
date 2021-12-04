/**
	 * @author Nithish	
*/
package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DashRTMModel;
import com.example.demo.model.IdOnly;
import com.example.demo.model.TestCaseModel;
import com.example.demo.service.DashboardService;
import com.example.demo.service.TestCaseService;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashController {

	@Autowired
	private DashboardService dashservice;

	@Autowired
	private TestCaseService testCaseService;

//	@GetMapping("/getprevcount")
//	public List<IdOnly> getPrevDayCount(@RequestBody HashMap<String, String> dataHashMap) {
//
//		return dashservice.getPrevDayListAndUpdate(dataHashMap.get("historyType"),
//				Long.parseLong(dataHashMap.get("currCount")));
//	}
	/**
	 * Method to get all open tests with only its id.
	 * @return List<IdOnly> with respective status and information.
	 */

	@GetMapping("/getopentests")
	public List<IdOnly> getOpenTests() {
		return testCaseService.getOpenTests();

	}
	/**
	 * Method to get RTM of all projects in db
	 * @return  List<DashRTMModel> with respective status and information.
	 */
	
	@GetMapping("/getRTM")
	public List<DashRTMModel> getRTM(){
		return dashservice.getRTM();
	}

}
