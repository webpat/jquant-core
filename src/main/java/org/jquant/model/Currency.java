package org.jquant.model;

import org.jquant.time.daycounter.Actual360;
import org.jquant.time.daycounter.Actual365Fixed;
import org.jquant.time.daycounter.DayCounter;

public enum Currency implements Comparable<Currency> {
	ARS(), // Argentine Peso
	ATS, // Austrian Schilling
	AUD, // Australian Dollar
	BEF, // Belgian Franc
	BRL, // Bresilian REAL
	CAD, // Canadian Dollar
	CHF, // Swiss Franc
	CLP, // CHILIAN PESA
	CNY, // CHINA RENMINBI
	COP, // COLUMBIAN_PESO
	CZK, // CZECH KORONA
	DEM, // Deutsch Mark
	DKK, // Danish Krone
	EGP, // EGYPT POUND
	ESP, // Spanish Peseta
	EUR, // Euro
	FIM, // Finnish Markka
	FRF, // French Franc
	GBP, // British Pound
	GRD, // Greek Drachma
	HKD, // HONGKONG DOLLARD
	HUF, // HUNGARIA_FORIN
	IDR, // Indonesian Rupiah
	IEP, // Irish Pound
	ILS, // Shekel
	INR, // INDIAN RUPEE
	ISK, // Iceland Krona
	ITL, // ITALIAN LIRA
	JOD, // JORDANIAN_DINAR
	JPY, // Japanese Yen
	KRW, // South Corean Won
	LUF, // LUXEMBOURG FRANC
	MAD, // MORROCO DIRAM
	MXN, // Mexican Peso
	MYR, // Malesian Ringgit
	NAV, // NOT AVAILABLE
	NLG, // DUTCH GUILDER SPOT
	NOK, // Norvegian Krone
	NZD, // NEW ZEALAND DOLLARD
	PEN, // Peruvian New Sol
	PHP, // PHILIPPINES PESO
	PKR, // Pakistani Rupee
	PLN, // Polish Zloty
	PTE, // Portugese Escudo
	SEK, // Swedish Krone
	SGD, // SINGAPORE DOLLARD
	THB, // Thai Bath
	TRL, // Turkish Lira
	TRY, // New Turkish Lira
	TWD, // Taiwan Dollar
	USD, // US Dollar
	VAL, // Lira
	VEB, // Venezuelan Bolivar
	XEU, // European Currency
	ZAR; // Rand
	
	public DayCounter getDayCounter() {
		DayCounter dayCounter = null;
		switch (this) {
		case GBP:
		case AUD:
		case CAD:
		case NZD:
			dayCounter = new Actual365Fixed();
			break;
		default:
			dayCounter = new Actual360();
		break;
		}
		
		return dayCounter;
	}
}
