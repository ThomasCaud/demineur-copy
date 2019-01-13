package ai12.maven_ai12.data.interfaces;

import java.io.IOException;

import ai12.maven_ai12.data.DataServerEngine;

public class InsideDataSrvForMainSrvImpl implements IInsideDataSrvForMainSrv {

	private DataServerEngine dataServerEngine;

	public InsideDataSrvForMainSrvImpl(DataServerEngine pEngine) {
		this.dataServerEngine = pEngine;
	}

	@Override
	public void startComServerEngine(Integer pPort) throws IOException {
		this.dataServerEngine.getInsideComSrvForDataSrv().startEngine(pPort);
	}

}
