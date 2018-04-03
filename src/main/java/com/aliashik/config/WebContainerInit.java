
package com.aliashik.config;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.LogManager;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebContainerInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setInitParameter("contextConfigLocation", "com.aliashik.config");
    }
    

}
