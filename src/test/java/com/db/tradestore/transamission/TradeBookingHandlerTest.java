package com.db.tradestore.transamission;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.db.tradestore.mapper.TradeMapper;
import com.db.tradestore.service.TradeBookingService;
import com.db.tradestore.transmission.TradeBookingHandler;
import com.db.tradestore.util.TradeStoreTestHelper;
import com.db.tradestore.validator.TradeValidator;

public class TradeBookingHandlerTest {
	
	@InjectMocks
	private TradeBookingHandler tradeBookingHandler;
	
	@Mock
	private TradeMapper tradeMapper;
	
	@Mock
	private TradeBookingService tradeBookingService;
	
	@Mock
	private TradeValidator tradeValidator;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testTradeHandleSuccess() throws Exception {
		Mockito.when(tradeMapper.mapMessageToTrade(Mockito.anyString())).thenReturn(TradeStoreTestHelper.getTrade());
		Mockito.doNothing().when(tradeBookingService).startTradeProcessing(Mockito.any());
		Mockito.when(tradeValidator.isValidTrade(Mockito.any())).thenReturn(true);
		tradeBookingHandler.handleMessageEvent("");
		Mockito.verify(tradeBookingService,Mockito.times(1)).startTradeProcessing(Mockito.any());
	}
}
