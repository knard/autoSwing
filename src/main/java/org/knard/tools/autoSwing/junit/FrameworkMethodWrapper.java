package org.knard.tools.autoSwing.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.junit.runners.model.FrameworkMethod;

public class FrameworkMethodWrapper implements AnnotatedElement {

	private FrameworkMethod method;

	public FrameworkMethodWrapper(FrameworkMethod method) {
		this.method = method;
	}

	@Override
	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		return getAnnotation(annotationClass) != null;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return method.getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return method.getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return method.getAnnotations();
	}

}
