package org.jquant;


import org.easymock.EasyMock;
import org.jquant.data.DataProvider;
import org.jquant.data.ProviderAdapterFactory;
import org.jquant.data.reader.ICandleReader;
import org.jquant.manager.MarketManager;
import org.junit.Before;
import org.junit.Test;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext(value="jquant-test-context.xml")
public class MarketManagerTest {

	@Mock
	private ICandleReader candleReader;
	
	private ProviderAdapterFactory readerFactory;
	
	@TestedObject
	private MarketManager marketMgr;
	
	@Before
	public void setUp() throws Exception {
		EasyMock.replay(this);
		EasyMock.expect(readerFactory.getCandleReader(DataProvider.BLOOMBERG)).andReturn(candleReader);
	}
	
	@Test
	public void testReadCandle(){
		
	}

}
