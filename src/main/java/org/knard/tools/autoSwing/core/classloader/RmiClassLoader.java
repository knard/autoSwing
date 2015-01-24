package org.knard.tools.autoSwing.core.classloader;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClassLoader extends ClassLoader {

	private ClassContentService classContentService;

	public RmiClassLoader() throws InitializationException {
		this(null);
	}

	public RmiClassLoader(ClassLoader parent) throws InitializationException {
		super(parent);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			// TODO make the port number configurable
			Registry registry = LocateRegistry.getRegistry(9999);
			classContentService = (ClassContentService) registry
					.lookup(ClassContentService.class.getName());
		} catch (RemoteException e) {
			throw new InitializationException("can't initialize classloader", e);
		} catch (NotBoundException e) {
			throw new InitializationException("can't initialize classloader", e);
		}

	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classContent;
		try {
			classContent = classContentService.getClassContent(name);
		} catch (RemoteException e) {
			throw new ClassRetrievalException("can't retrieve " + name, e);
		}
		if (classContent == null) {
			throw new ClassNotFoundException("can't find class " + name);
		}
		return defineClass(name, classContent, 0, classContent.length);
	}

}
