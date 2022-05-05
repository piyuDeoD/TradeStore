package com.db.tradestore.transmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tradeBooking")
@RestController
public class TradeBookingController { //ForTesting

	@Autowired
	private TradeBookingHandler tradeBookingHandler;
	
	@RequestMapping("/")
	public void startProcessing() {
		String message = "{\r\n"
				+ "   \"id\":\"T1\",\r\n"
				+ "   \"counterpartyId\":\"CP1\",\r\n"
				+ "   \"bookId\":\"B1\",\r\n"
				+ "   \"version\":1,\r\n"
				+ "   \"maturityDate\":\"05/11/2022\"\r\n"
				+ "}";
		for(int i=0;i<10;i++) {
			tradeBookingHandler.handleMessage(message);
		}
		
	}
}
