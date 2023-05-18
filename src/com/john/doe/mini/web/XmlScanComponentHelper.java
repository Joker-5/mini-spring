package com.john.doe.mini.web;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
@Slf4j
public class XmlScanComponentHelper {
    public static List<String> getNodeValue(URL xmlPath) {
        List<String> packages = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document = null;

        try {
            document = reader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        Iterator<Element> it = rootElement.elementIterator();
        while (it.hasNext()) {
            Element element = it.next();
            // get all base-package nodes
            packages.add(element.attributeValue(WebConstant.BASE_PACKAGE));
        }

        return packages;
    }

    public static List<String> scanPackages(List<String> packageNames, Class<?> clazz) {
        List<String> controllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            controllerNames.addAll(scanPackage(packageName, clazz));
        }
        log.info("controllerNames: {}", controllerNames);
        return controllerNames;
    }


    private static List<String> scanPackage(String packageName, Class<?> clazz) {
        List<String> controllerNames = new ArrayList<>();
        URL url = clazz.getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));

        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // TODO to check
                controllerNames.addAll(scanPackage(packageName + "." + file.getName(), clazz));
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                controllerNames.add(controllerName);
                log.info("scanPackage, get controller name: {}", controllerName);
            }
        }
        return controllerNames;
    }
}
