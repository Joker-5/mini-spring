package com.john.doe.mini.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JOHN_DOE on 2023/5/14.
 */
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
}
