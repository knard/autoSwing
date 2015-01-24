package org.knard.tools.autoSwing.core.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassContentServiceImpl implements ClassContentService {

	private ClassLoader classLoader;

	public ClassContentServiceImpl(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public byte[] getClassContent(String name) throws ClassRetrievalException {
		String classRessource = "/" + name.replace('.', '/') + ".class";
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = classLoader.getResourceAsStream(classRessource);
			byte[] buffer = new byte[1024];
			int read = -1;
			out = new ByteArrayOutputStream();
			while ((read = in.read(buffer)) >= 0) {
				out.write(buffer, 0, read);
			}
			return out.toByteArray();
		} catch (IOException e) {
			throw new ClassRetrievalException("can't load class " + name, e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
