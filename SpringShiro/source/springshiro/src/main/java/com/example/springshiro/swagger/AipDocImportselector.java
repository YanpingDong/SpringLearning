package com.example.springshiro.swagger;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class AipDocImportselector implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] {SwaggerConfig.class.getCanonicalName() };
	}

}
