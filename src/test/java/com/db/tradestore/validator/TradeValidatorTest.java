package com.db.tradestore.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.db.tradestore.exception.TradeStoreException;
import com.db.tradestore.repository.TradeCapturingRepository;
import com.db.tradestore.util.TradeStoreTestHelper;

public class TradeValidatorTest {
	@InjectMocks
	private TradeValidator tradeValidator;
	
	@Mock
	private TradeCapturingRepository tradeCapturingRepository;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testTradeValidatorSuccess() throws TradeStoreException, ParseException {
		Mockito.when(tradeCapturingRepository.getLatestVersionOfTrade(Mockito.anyString())).thenReturn(Long.valueOf(1));
		assertTrue(tradeValidator.isValidTrade(TradeStoreTestHelper.getTrade()));
	}
	
	@Test
	public void testTradeValidatorMAturityDateFail() throws Exception {
		Mockito.when(tradeCapturingRepository.getLatestVersionOfTrade(Mockito.anyString())).thenReturn(Long.valueOf(1));
		assertFalse(tradeValidator.isValidTrade(TradeStoreTestHelper.getInvalidTrade()));
	}
	
	@Test
	public void testTradeValidatorVersionFail() {
		Mockito.when(tradeCapturingRepository.getLatestVersionOfTrade(Mockito.anyString())).thenReturn(Long.valueOf(3));
		try {
			tradeValidator.isValidTrade(TradeStoreTestHelper.getTrade());
		} catch (Exception e) {
			assertTrue(e instanceof TradeStoreException);
		}
	}
}
