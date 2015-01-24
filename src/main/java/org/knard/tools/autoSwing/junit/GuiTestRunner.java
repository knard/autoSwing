package org.knard.tools.autoSwing.junit;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.knard.tools.autoSwing.core.classloader.RmiClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * runner used to start the application before JUnit tests are launched.
 * 
 * @author knard
 *
 */
public class GuiTestRunner extends BlockJUnit4ClassRunner {

	private final static Logger log = LoggerFactory
			.getLogger(GuiTestRunner.class);
	private String[] applicationArgumentClassLevel;
	private String applicationClassnameClassLevel;

	public GuiTestRunner(final Class<?> klass) throws InitializationError {
		super(klass);
		applicationArgumentClassLevel = getApplicationArgument(klass);
		applicationClassnameClassLevel = getApplicationClass(klass);
	}

	@Override
	protected void runChild(final FrameworkMethod method,
			final RunNotifier notifier) {
		FrameworkMethodWrapper wrapper = new FrameworkMethodWrapper(method);
		String[] applicationArgument = getApplicationArgument(wrapper);
		if (applicationArgument == null) {
			applicationArgument = applicationArgumentClassLevel;
		}
		String applicationClassname = getApplicationClass(wrapper);
		if (applicationClassname == null) {
			applicationClassname = applicationClassnameClassLevel;
		}
		if (applicationClassname != null) {
			List<String> commandParts = new ArrayList<String>();
			commandParts.add(getJavaExecutable());
			commandParts.add("-Djava.system.class.loader="
					+ RmiClassLoader.class.getName());
			commandParts.add("-classpath");
			commandParts.add(createClasspathJar());
			commandParts.add(applicationClassname);
			commandParts.addAll(Arrays.asList(applicationArgument));
			ProcessBuilder builder = new ProcessBuilder(
					commandParts.toArray(new String[commandParts.size()]));
			Process applicationProcess;
			try {
				applicationProcess = builder.start();
			} catch (IOException e) {
				notifier.fireTestFailure(new Failure(getDescription(), e));
				return;
			}
			super.runChild(method, notifier);
			applicationProcess.destroy();
		} else {
			notifier.fireTestFailure(new Failure(getDescription(),
					new InitializationError("no application class name found.")));
		}
	}

	private String createClasspathJar() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getJavaExecutable() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * extract application argument from the annotation used in unit test class
	 * 
	 * @see ApplicationArgument
	 * @param annotatedElement
	 *            the test class
	 * @return the argument that should be used during the launch
	 */
	private String[] getApplicationArgument(AnnotatedElement annotatedElement) {
		final ApplicationArgument applicationArgument = annotatedElement
				.getAnnotation(ApplicationArgument.class);
		if (applicationArgument != null) {
			return applicationArgument.value();
		}
		return new String[0];
	}

	/**
	 * extract the application class from the annotation used in unit test class
	 * 
	 * @see ApplicationClass
	 * @param annotatedElement
	 *            the unit test class
	 * @return the class name that will be used to start the application
	 */
	private String getApplicationClass(AnnotatedElement annotatedElement) {
		final ApplicationClass applicationClass = annotatedElement
				.getAnnotation(ApplicationClass.class);
		if (applicationClass == null) {
			return null;
		}
		return applicationClass.value().getName();
	}
}
