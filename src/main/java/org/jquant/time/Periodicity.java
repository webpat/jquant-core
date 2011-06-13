package org.jquant.time;

public enum Periodicity {
    ONCE(0),             // zero coupon
    ANNUAL(1),           
    SEMI_ANNUAL(2),       
    EVERY_FOURTH_MONTH(3), 
    QUARTERLY(4),        
    BIMONTHLY(6),        
    MONTHLY(12),         
    BIWEEKLY(26),        
    WEEKLY(52),          
    DAILY(365),           
    NO_FREQUENCY(-1);
    
    private final int value;
    
    private Periodicity(int value) {
		this.value = value;
	}
    public int getValue() {
		return this.value;
	}
    
    public TimeFrame getPeriod() {
    	TimeFrame period = new TimeFrame();
    	if (this.value==1) {
    		period.unit = TimeUnit.YEAR; 
    		period.length = 1;
    	}
    	else if(this.value<=12) {
    		period.unit = TimeUnit.MONTH; 
    		period.length = 12/this.value;
    	}
    	else if(this.value<=52) {
    		period.unit = TimeUnit.WEEK; 
    		period.length = 52/this.value;
    	}    
    	else if(this.value==365) {
    		period.unit = TimeUnit.DAY; 
    		period.length = 1;
    	}  
		return period;    	
    }
}
