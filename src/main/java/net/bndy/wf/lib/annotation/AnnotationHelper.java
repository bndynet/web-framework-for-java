/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib.annotation;

import java.lang.annotation.Annotation;

public class AnnotationHelper {

	public static <T extends Annotation, TSource> T getFieldAnnotation(Class<T> annotationClass, Class<TSource> source, String fieldName) throws NoSuchFieldException, SecurityException {
		return source.getField(fieldName).getAnnotation(annotationClass);
	}

	public static <T extends Annotation, TSource> T getClassAnnotation(Class<T> annotationClass, Class<TSource> source) throws NoSuchFieldException, SecurityException {
		return source.getAnnotation(annotationClass);
	}
}
