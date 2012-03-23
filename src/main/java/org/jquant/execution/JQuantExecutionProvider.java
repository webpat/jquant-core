package org.jquant.execution;

/**
 * JQuant Compatible execution providers
 * @author patrick.merheb
 *
 */
public enum JQuantExecutionProvider {

	PAPER,/*Mock Broker, used for backtesting*/
	IB,/*Interactive Brokers*/
	MBT/*MB Trading */;
}
