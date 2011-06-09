package org.jquant;


import org.apache.commons.configuration.XMLConfiguration;



public class JQuantConfiguration {

	private static XMLConfiguration config;
	
	
//	static{
//		
//			try {
//				config = new XMLConfiguration("config.xml");
//			} catch (ConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			FileChangedReloadingStrategy strat = new FileChangedReloadingStrategy();
//			strat.setRefreshDelay(300000);
//	
//		
//	}
	
	public static XMLConfiguration getInstance(){
		return config;
	}
	
}
