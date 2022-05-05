package com.db.tradestore.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.db.tradestore.vo.Trade;

public class TradeMapperTest {
	
	@InjectMocks
	private TradeMapper tradeMapper;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testTradeMapper() throws Exception {
		String message = "{\r\n"
				+ "   \"id\":\"T1\",\r\n"
				+ "   \"counterpartyId\":\"CP1\",\r\n"
				+ "   \"bookId\":\"B1\",\r\n"
				+ "   \"version\":1,\r\n"
				+ "   \"maturityDate\":\"05/06/2022\"\r\n"
				+ "}";
		Trade trade =tradeMapper.mapMessageToTrade(message);
		assertEquals("T1", trade.getId());
	}
}
