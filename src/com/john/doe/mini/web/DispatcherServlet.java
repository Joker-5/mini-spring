package com.john.doe.mini.web;

import com.john.doe.mini.util.ArrayUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
    // URL from @RequestMapping annotation
    private List<String> urlMappingNames = new ArrayList<>();

    // URL to bean obj
    private Map<String, Object> mappingObjects = new HashMap<>();

    // URL to method to invoke
    private Map<String, Method> mappingMethods = new HashMap<>();

    // packages to scan
    private List<String> packageNames = new ArrayList<>();

    private List<String> controllerNames = new ArrayList<>();

    // controller name to controller obj
    private Map<String, Object> ControllerObjects = new HashMap<>();

    // controller name to controller class
    private Map<String, Class<?>> controllerClasses = new HashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String contextConfigLocation = config.getInitParameter(WebConstant.CONTEXT_CONFIG_LOCATION);
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
        initMapping();
    }

    protected void initController() {
        controllerNames = scanPackages(packageNames);
        for (String controllerName : controllerNames) {
            Object obj = null;
            Class<?> clazz = null;

            try {
                clazz = Class.forName(controllerName);
                obj = clazz.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            controllerClasses.put(controllerName, clazz);
            ControllerObjects.put(controllerName, obj);
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> controllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            controllerNames.addAll(scanPackage(packageName));
        }

        return controllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> controllerNames = new ArrayList<>();
        URI uri = null;

        try {
            uri = getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            log.info("scan file name: {}", file.getName());
            if (file.isDirectory()) {
                // TODO to check
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                controllerNames.add(controllerName);
            }
        }
        return controllerNames;
    }

    protected void initMapping() {
        for (String controllerName : controllerNames) {
            Class<?> clazz = controllerClasses.get(controllerName);
            Object obj = ControllerObjects.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();

            if (!ArrayUtils.isEmpty(methods)) {
                for (Method method : methods) {
                    // @RequestMapping annotation
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        String methodName = method.getName();
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        urlMappingNames.add(urlMapping);
                        mappingObjects.put(urlMapping, obj);
                        mappingMethods.put(urlMapping, method);
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get request url
        String path = req.getServletPath();
        log.info("request path: {}", path);
        if (!urlMappingNames.contains(path)) {
            return;
        }

        // get bean instance
        Object obj = mappingObjects.get(path);
        log.info("bean instance: {}", obj);

        // get method to invoke
        Method method = mappingMethods.get(path);
        log.info("invoke method name: {}", method.getName());

        Object result = null;
        try {
            result = method.invoke(obj);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        resp.getWriter().append(result.toString());
    }
}
