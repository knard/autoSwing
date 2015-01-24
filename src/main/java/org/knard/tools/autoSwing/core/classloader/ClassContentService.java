package org.knard.tools.autoSwing.core.classloader;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClassContentService extends Remote {

	public byte[] getClassContent(String name) throws ClassRetrievalException, RemoteException;
	
}
