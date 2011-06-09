package org.jquant.model;

public enum InstrumentType {
	UNKNOWN,
	EQUITY,
	INDEX,
	FOREX,
	VARIANCE_SWAP,
	CDS,
	TIME_DEPOSIT,
	OPTION,
	FUTURE;
	
    public static InstrumentType fromLouxorCode(String louxorCode){
		
		if ("F".compareTo(louxorCode)==0)
			return FUTURE;
		else if ("O".compareTo(louxorCode)==0)
			return OPTION;
		else if ("I".compareTo(louxorCode)==0)
			return INDEX;
		else if ("S".compareTo(louxorCode)==0)
			return EQUITY;
		else if ("D".compareTo(louxorCode)==0)
			return CDS;
		else if ("VSW".compareTo(louxorCode)==0)
			return VARIANCE_SWAP;
		else if ("TDP".compareTo(louxorCode)==0)
			return TIME_DEPOSIT;
		else
			return UNKNOWN;
    }

}
