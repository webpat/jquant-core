package org.jquant.data;

import java.util.HashMap;
import java.util.Map;

import org.jquant.data.reader.ICandleReader;
import org.jquant.data.reader.IQuoteReader;
import org.springframework.stereotype.Service;

@Service
public class ProviderAdapterFactory {

	private Map<DataProvider, ICandleReader> candleReaderMap = new HashMap<DataProvider, ICandleReader>(2);
	private Map<DataProvider, IQuoteReader> quoteReaderMap = new HashMap<DataProvider, IQuoteReader>(2);
	
	public void registerQuoteReader (IQuoteReader reader, DataProvider provider){
		quoteReaderMap.put(provider, reader);
	}
	
	public void registerCandleReader (ICandleReader reader, DataProvider provider){
		candleReaderMap.put(provider, reader);
	}
	
	public ICandleReader getCandleReader(DataProvider provider){
		return candleReaderMap.get(provider);
	}

	public IQuoteReader getQuoteReader(DataProvider provider) {
		return quoteReaderMap.get(provider);
	}
	
}
