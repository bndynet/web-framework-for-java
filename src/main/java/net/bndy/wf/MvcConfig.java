package net.bndy.wf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.bndy.wf.interceptors.*;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	AuthenticationInterceptor authenticationInterceptor;
	
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	//registry.addInterceptor(authenticationInterceptor);
		super.addInterceptors(registry);
	}	
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/home").setViewName("home");
        //registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }

}