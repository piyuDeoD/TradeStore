package com.db.tradestore.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.db.tradestore.constants.Constants;
import com.db.tradestore.vo.Trade;

public class TradeStoreTestHelper {
	
	public static Trade getTrade() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date maturityDate = dateFormat.parse("05/10/2022");
		Trade trade = new Trade("T1",2,"CP1","B1",maturityDate,new Date(),false);
		return trade;
	}
	
	public static Trade getInvalidTrade() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date maturityDate = dateFormat.parse("05/01/2022");
		Trade trade = new Trade("T1",2,"CP1","B1",maturityDate,new Date(),false);
		return trade;
	}

}
