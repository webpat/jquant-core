package org.jquant.model;

public enum MarketDataProvider {
    BLOOMBERG(2),   
    HDG(7),
    PER_SECURITY(20),
    GOLDMAN_SACHS(47),
    OTC(200);
    
    private final int value;
    
    private MarketDataProvider(int value) {
		this.value = value;
	}
    public int getValue() {
		return this.value;
	}
    
    public static MarketDataProvider matchMarketDataProvider(long providerId) {
    	
    	MarketDataProvider[] types = MarketDataProvider.values();
    	for (int i = 0; i < types.length; i++)
            if (types[i].getValue() == providerId) return types[i];
    	return null;
    }
}
