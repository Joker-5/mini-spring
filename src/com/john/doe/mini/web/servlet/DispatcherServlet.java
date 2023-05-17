package com.john.doe.mini.web.servlet;

import com.john.doe.mini.beans.BeansException;
import com.john.doe.mini.beans.factory.annotation.Autowired;
import com.john.doe.mini.util.ArrayUtils;
import com.john.doe.mini.web.AnnotationConfigWebApplicationContext;
import com.john.doe.mini.web.WebApplicationContext;
import com.john.doe.mini.web.WebConstant;
import com.john.doe.mini.web.XmlScanComponentHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by JOHN_DOE on 2023/5/13.
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    // applicationContext from dispatcherServlet
    private WebApplicationContext wac;

    // applicationContext from listener
    private WebApplicationContext parent;

    private HandlerMapping handlerMapping;

    private HandlerAdapter handlerAdapter;

    // packages to scan
    private List<String> packageNames = new ArrayList<>();

    private List<String> controllerNames = new ArrayList<>();

    // controller name to controller obj
    private Map<String, Object> ControllerObjects = new HashMap<>();

    // controller name to controller class
    private Map<String, Class<?>> controllerClasses = new HashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO 代码测试RUN Tomcat还有些问题，还需在看下
        super.init(config);
        String contextConfigLocation = config.getInitParameter(WebConstant.CONTEXT_CONFIG_LOCATION);
        // get parent webApplicationContext which had created in listener from servletContext
        parent = (WebApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        wac = new AnnotationConfigWebApplicationContext(contextConfigLocation, parent);

        URL xmlPath = null;
        try {
            xmlPath = getServletContext().getResource(contextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        refresh();
    }

    /**
     * instantiate all class which registered in mappingValues
     */
    protected void refresh() {
        // first, init controller(scan packages, fill settings)
        initController();
        // then, init url mappings
        initHandlerMapping(wac);
        initHandlerAdapter(parent);
        log.info("refresh MVC dispatcherServlet success");
    }

    protected void initHandlerMapping(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapter(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
    }

    protected void initController() {
        controllerNames = XmlScanComponentHelper.scanPackages(packageNames, getClass());
        for (String controllerName : controllerNames) {
            Object obj = null;
            Class<?> clazz = null;

            try {
                clazz = Class.forName(controllerName);
                obj = clazz.newInstance();
                populateBean(obj, controllerName);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | BeansException e) {
                e.printStackTrace();
            }

            controllerClasses.put(controllerName, clazz);
            ControllerObjects.put(controllerName, obj);
        }
    }

    protected Object populateBean(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        if (!ArrayUtils.isEmpty(fields)) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    String fieldName = field.getName();
                    Object autowiredObj = wac.getBean(fieldName);
                    field.setAccessible(true);
                    try {
                        field.set(bean, autowiredObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log.info("populate bean success, bean name: {}", beanName);
        return bean;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerMethod handlerMethod = handlerMapping.getHandler(request);
        log.info("in method doDispatch");
        if (handlerMethod == null) {
            return;
        }
        handlerAdapter.handle(request, response, handlerMethod);
    }

}
