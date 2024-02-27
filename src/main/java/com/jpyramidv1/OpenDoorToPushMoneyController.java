package com.jpyramidv1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OpenDoorToPushMoneyController {

	private static boolean acceptingBills = false;

	@Autowired
	private InsertBilltoMachine billtoMachine;

	@GetMapping("/start-bill-insertion")
	@ResponseBody
	public String startBillInsertion() {
		if (!acceptingBills) {
			billtoMachine.openDoortoPushMoney();
			acceptingBills = true;
			return "Bill insertion started";
		} else {
			return "Bill insertion already in progress";
		}
	}

	@GetMapping("/stop-bill-insertion")
	@ResponseBody
	public String stopBillInsertion() {
		if (acceptingBills) {
			billtoMachine.stopInsertion();
			acceptingBills = false;
			return "Bill insertion stopped";
		} else {
			return "No bill insertion in progress";
		}
	}

	@GetMapping("/disconnect-bill-insertion")
	@ResponseBody
	public String disconnectBillInsertion() {
		if (acceptingBills) {
			billtoMachine.disconnect();
			acceptingBills = false;
			return "Bill insertion disconnected";
		} else {
			return "No bill insertion in progress";
		}
	}

}