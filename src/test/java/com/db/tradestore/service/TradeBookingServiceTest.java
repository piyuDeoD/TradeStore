package com.db.tradestore.service;

import java.text.ParseException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.db.tradestore.repository.TradeCapturingRepository;
import com.db.tradestore.util.TradeStoreTestHelper;

public class TradeBookingServiceTest {
	
	@InjectMocks
	private TradeBookingService tradeBookingService;
	
	@Mock
	private TradeCapturingRepository tradeCapturingRepository;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testTradeBookingServiceNewTrade() throws ParseException {
		Mockito.when(tradeCapturingRepository.getExistingTrade(Mockito.anyString(), Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		tradeBookingService.startTradeProcessing(TradeStoreTestHelper.getTrade());
		Mockito.verify(tradeCapturingRepository,Mockito.times(1)).insertNewTrade(Mockito.any());
	}
	
	@Test
	public void testTradeBookingServiceExistingTrade() throws ParseException {
		Mockito.when(tradeCapturingRepository.getExistingTrade(Mockito.anyString(), Mockito.anyLong())).thenReturn(Optional.ofNullable(TradeStoreTestHelper.getTrade()));
		tradeBookingService.startTradeProcessing(TradeStoreTestHelper.getTrade());
		Mockito.verify(tradeCapturingRepository,Mockito.times(1)).updateExistingTrade(Mockito.any());
	}

}
