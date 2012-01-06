/*
 * Created on 12 juin 07
 */
package org.jquant.serie;





/**
 * A trackRecord Element
 * @author merhebp
 */
public class NavPerf extends AbstractTimeValue {

	private double nav;
    private double returnValue;
    private boolean isOfficial; //Specific for estimated perf/official Nav issue
    
    
    
    /**
	 * Copy Constructor
	 *
	 * @param navPerf a <code>NavPerf</code> object
	 */
	public NavPerf(NavPerf navPerf) 
	{
		super(navPerf.getDate());
	    this.nav = navPerf.nav;
	    this.returnValue = navPerf.returnValue;
	    this.isOfficial = navPerf.isOfficial;
	}



    public NavPerf clone(){
    	return new NavPerf(this);
    }

    /**
     * @return the nav
     */
    public double getNav() {
        return nav;
    }

    /**
     * @param nav the nav to set
     */
    public void setNav(double nav) {
        this.nav = nav;
    }

    /**
     * @return the returnValue
     */
    public double getReturnValue() {
        return returnValue;
    }

    /**
     * @param returnValue the returnValue to set
     */
    public void setReturnValue(double returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * @return the isOfficial
     */
    public boolean isOfficial() {
        return isOfficial;
    }

    /**
     * @param isOfficial the isOfficial to set
     */
    public void setOfficial(boolean isOfficial) {
        this.isOfficial = isOfficial;
    }


	@Override
	public double getValue() {
		return nav;
	}
    
    
    

}
