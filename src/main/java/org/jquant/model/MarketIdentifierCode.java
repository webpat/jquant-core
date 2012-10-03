package org.jquant.model;

import java.io.Serializable;


/**
 * A four-character code used to identify stock markets and other trading exchanges 
 * within global trading and referencing computer systems. 
 * <p>
 * The first letter of any MIC is "X", 
 * followed by a three-digit alphanumeric code for the market in which a trade takes place. 
 * <p>
 * <a href="http://www.investopedia.com/terms/m/mic.asp#ixzz28HFIRS9C">Read more</a>
 */
public class MarketIdentifierCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7984421211067735044L;

	/**
	 * Marchés Composites ou fictifs
	 */
	public final static MarketIdentifierCode NO_MIC=new MarketIdentifierCode("NO_MIC","EARTH");
	
    /**
     * Well known Market Places
     */
	public final static MarketIdentifierCode XCBT=new MarketIdentifierCode("XCBT","US");
    public final static MarketIdentifierCode XNYM=new MarketIdentifierCode("XNYM","US");
    public final static MarketIdentifierCode XNMS=new MarketIdentifierCode("XNMS","US");
    public static final MarketIdentifierCode XNYS = new MarketIdentifierCode("XNYS","US");
    public final static MarketIdentifierCode XLON=new MarketIdentifierCode("XLON","GB");
    public final static MarketIdentifierCode XLME=new MarketIdentifierCode("XLME","GB");
    public final static MarketIdentifierCode XCME=new MarketIdentifierCode("XCME","US");
    public final static MarketIdentifierCode XCEC=new MarketIdentifierCode("XCEC","US");
    public final static MarketIdentifierCode XEUR=new MarketIdentifierCode("XEUR","DE");
    public final static MarketIdentifierCode XLIF=new MarketIdentifierCode("XLIF","GB");
    public final static MarketIdentifierCode XNYF=new MarketIdentifierCode("XNYF","US");
    public final static MarketIdentifierCode XPAR=new MarketIdentifierCode("XPAR","FR");
    public final static MarketIdentifierCode XETR=new MarketIdentifierCode("XETR","DE");
    public final static MarketIdentifierCode XBER=new MarketIdentifierCode("XBER","DE");
    public final static MarketIdentifierCode XTKS=new MarketIdentifierCode("XTKS","JP");
    public final static MarketIdentifierCode XFRA=new MarketIdentifierCode("XFRA","DE");
    public final static MarketIdentifierCode XMIL=new MarketIdentifierCode("XMIL","IT");
    public final static MarketIdentifierCode XTFF=new MarketIdentifierCode("XTFF","JP");
    public final static MarketIdentifierCode XMON=new MarketIdentifierCode("XMON","FR");
    public final static MarketIdentifierCode XIPE=new MarketIdentifierCode("XIPE","GB");
    public final static MarketIdentifierCode XOSE=new MarketIdentifierCode("XOSE","JP");
    public final static MarketIdentifierCode XLUX=new MarketIdentifierCode("XLUX","LU");
	
    
    
    private String description;
	private final String MICCode;
    private String MICId;
	private String countryName;
	private String countryCode;
	private String city;
	private String webSite;
	private String acronym;
	
    /**
     * @return Returns the acronym.
     */
    public String getAcronym() {
        return acronym;
    }
    /**
     * @param acronym The acronym to set.
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
	/***
	 *  Constructeur 
	 * @param MICCode
	 */
	public MarketIdentifierCode(String MICCode)  {
          
		this.MICCode = MICCode;
	}

    public MarketIdentifierCode(String MICCode,String countryCode) {
        this.MICCode = MICCode;
        this.countryCode=countryCode;
    }

 
	
	/**
	 * @return {@link String} the X??? Form code 
	 */
	public String getCode() {
		return MICCode;
	}
	// Implementation
    
    /** 
     * @return {@link Integer} Hash code on the Mic code
     */
    @Override
    public int hashCode() {  
        return MICCode.hashCode();
    }

	/**
	 * Retourne <tt>TRUE</tt> si les deux places sont identiques
	 * @param o an other {@link MarketIdentifierCode}
	 * @return TRUE ou FALSE
	 */
	@Override
    public boolean equals(Object o) {
		return (MICCode.equals(((MarketIdentifierCode) o).getCode()));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString() {
      return MICCode;
	}

    /**
     * @return Returns the city.
     */
    public String getCity() {
        return city;
    }
   
    public void setCity(String city) {
        this.city = city;
    }
  

    /**
     * @return Returns the webSite.
     */
    public String getWebSite() {
        return webSite;
    }
   
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    /**
     * @return Returns the countryCode.
     */
    public String getCountryCode() {
        return countryCode;
    }
   
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return Returns the countryName.
     */
    public String getCountryName() {
        return countryName;
    }
   
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }
   
    public String getMICId() {
        return MICId;
    }
   
    public void setMICId(String id) {
        MICId = id;
    }
    
    
}
