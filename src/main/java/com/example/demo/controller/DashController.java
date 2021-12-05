/**
	 * @author Nithish	
*/
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DashRTMModel;
import com.example.demo.model.IdOnly;
import com.example.demo.model.TestCaseTrackerModel;
import com.example.demo.model.TicketGroupModel;
import com.example.demo.service.DashboardService;
import com.example.demo.service.TestCaseService;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashController {

	@Autowired
	private DashboardService dashservice;

	@Autowired
	private TestCaseService testCaseService;

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
	
	/**
	 * Method to get current count of all defect types in db
	 * @return  TicketGroupModel with respective status and information.
	 */
	
	@GetMapping("/getticketgroups")
	public TicketGroupModel getTicketGroupCounts(){
		return dashservice.ticketGrouping();
	}
	
	@GetMapping("/gettracking")
	public List<TestCaseTrackerModel> getTracking(){
		return dashservice.trackTests();
	}
	

}
