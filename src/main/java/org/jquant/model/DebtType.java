package org.jquant.model;

public enum DebtType {
    SENIOR(1),             // zero coupon
    SUBORDINATED(2),           
    OTHER(3),
    UNKNOWN(4);

    
    private final int value;
    
    private DebtType(int value) {
		this.value = value;
	}
    public int getValue() {
		return this.value;
	}

}
