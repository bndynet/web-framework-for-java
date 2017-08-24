package net.bndy.wf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.bndy.wf.interceptors.*;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

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
    	//registry.addInterceptor(authenticationInterceptor);
		super.addInterceptors(registry);
	}	
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	// documentation for API
		registry.addRedirectViewController("/docs/api", "/docs/api/index.html");

//        registry.addViewController("/login").setViewName("login");
    }
}