package org.jquant.serie;


/**
 * Fund Net Asset Values and Performances Serie 
 * <p> 
 * A mutual fund's price per share or exchange-traded fund's (ETF) per-share value. In both cases, the per-share dollar amount of the fund is calculated by dividing the total value of all the securities in its portfolio, less any liabilities, by the number of fund shares outstanding.
 * <p>
 * In the context of mutual funds, NAV per share is computed once a day based on the closing market prices of the securities in the fund's portfolio. All mutual funds' buy and sell orders are processed at the NAV of the trade date. However, investors must wait until the following day to get the trade price.
 * <a href = "http://www.investopedia.com/terms/n/nav.asp#ixzz1ie7TMILs">Read more</a>
 * @author patrick.merheb
 *
 */
public final class NavPerfSerie extends TimeSerie<NavPerf> {

	@Override
	public ITimeSerie<NavPerf> clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<NavPerf> getChildClass() {
		// TODO Auto-generated method stub
		return NavPerf.class;
	}

}
