/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import net.bndy.wf.interceptors.*;

import java.util.Locale;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	ApplicationConfig applicationConfig;
	@Autowired
	AuthenticationInterceptor authenticationInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(authenticationInterceptor);
		registry.addInterceptor(localeChangeInterceptor());
		super.addInterceptors(registry);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// documentation for API
		registry.addRedirectViewController("/docs/api", "/docs/api/index.html");
	}

	// Languages
	@Bean
	public LocaleResolver localeResolver() {
		// Options: SessionLocaleResolver, AcceptHeaderLocaleResolver, FixedLocaleResolver
		CookieLocaleResolver lr = new CookieLocaleResolver();
		lr.setDefaultLocale(Locale.forLanguageTag(this.applicationConfig.getDefaultLang()));
		lr.setCookieName("LOCALE");	// if not set, default `org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE`
		return lr;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("locale");
		return lci;
	}
}
