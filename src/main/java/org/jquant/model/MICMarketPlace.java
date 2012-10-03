package org.jquant.model;


/**
 * Implementation de l'interface Market. 
 * Les codes MIC sont les codes ISO des place de march�s.
 * <br> Le plus simpel est d'utiliser la methode MICFactory.getMarket() pour recuperer une place de march� renseign�e.
 * 
 * @author SGAM HDG
 * @version $Revision: 1.3 $
 */
public class MICMarketPlace {

	/**
	 * Marchés Composites ou fictifs
	 */
	public final static MICMarketPlace NO_MIC=new MICMarketPlace("NO_MIC","EARTH");
	
    /**
     * Well known Market Places
     */
	public final static MICMarketPlace XCBT=new MICMarketPlace("XCBT","US");
    public final static MICMarketPlace XNYM=new MICMarketPlace("XNYM","US");
    public final static MICMarketPlace XNMS=new MICMarketPlace("XNMS","US");
    public static final MICMarketPlace XNYS = new MICMarketPlace("XNYS","US");
    public final static MICMarketPlace XLON=new MICMarketPlace("XLON","GB");
    public final static MICMarketPlace XLME=new MICMarketPlace("XLME","GB");
    public final static MICMarketPlace XCME=new MICMarketPlace("XCME","US");
    public final static MICMarketPlace XCEC=new MICMarketPlace("XCEC","US");
    public final static MICMarketPlace XEUR=new MICMarketPlace("XEUR","DE");
    public final static MICMarketPlace XLIF=new MICMarketPlace("XLIF","GB");
    public final static MICMarketPlace XNYF=new MICMarketPlace("XNYF","US");
    public final static MICMarketPlace XPAR=new MICMarketPlace("XPAR","FR");
    public final static MICMarketPlace XETR=new MICMarketPlace("XETR","DE");
    public final static MICMarketPlace XBER=new MICMarketPlace("XBER","DE");
    public final static MICMarketPlace XTKS=new MICMarketPlace("XTKS","JP");
    public final static MICMarketPlace XFRA=new MICMarketPlace("XFRA","DE");
    public final static MICMarketPlace XMIL=new MICMarketPlace("XMIL","IT");
    public final static MICMarketPlace XTFF=new MICMarketPlace("XTFF","JP");
    public final static MICMarketPlace XMON=new MICMarketPlace("XMON","FR");
    public final static MICMarketPlace XIPE=new MICMarketPlace("XIPE","GB");
    public final static MICMarketPlace XOSE=new MICMarketPlace("XOSE","JP");
    public final static MICMarketPlace XLUX=new MICMarketPlace("XLUX","LU");
	
    
    
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
	public MICMarketPlace(String MICCode)  {
          
		this.MICCode = MICCode;
	}

    public MICMarketPlace(String MICCode,String countryCode) {
        this.MICCode = MICCode;
        this.countryCode=countryCode;
    }

    // Accesseurs

	
	/**
	 * Retourne le code MIC de la place de marche
	 * 
	 * @return return le nom de la place de marche
	 */
	public String getCode() {
		return MICCode;
	}
	// Implementation
    
    /** Redefinition de la methode de Object. 
     * @return le code
     */
    @Override
    public int hashCode() {  
        return MICCode.hashCode();
    }

	/**
	 * Retourne TRUE si les deux places sont identiques
	 * @param market une place de marche
	 * @return TRUE ou FALSE
	 */
	@Override
    public boolean equals(Object o) {
		return (MICCode.equals(((MICMarketPlace) o).getCode()));
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
    /**
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }
  
 
    /**
     * @return Returns the webSite.
     */
    public String getWebSite() {
        return webSite;
    }
    /**
     * @param webSite The webSite to set.
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    /**
     * @return Returns the countryCode.
     */
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode The countryCode to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return Returns the countryName.
     */
    public String getCountryName() {
        return countryName;
    }
    /**
     * @param countryName The countryName to set.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the mICId
     */
    public String getMICId() {
        return MICId;
    }
    /**
     * @param id the mICId to set
     */
    public void setMICId(String id) {
        MICId = id;
    }
    
    
}
