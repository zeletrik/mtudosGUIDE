package hu.zelena.guide.util;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import hu.zelena.guide.modell.Specs;

/**
 * Created by patrik on 2016.11.26..
 */

public class SpecsReader {

    private String path;

    public SpecsReader(String file) {
        path = file;
    }

    public Specs getSpecs() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        SpecsHandler handler = new SpecsHandler();
        File file = new File (path);
        saxParser.parse(file, handler);
        return handler.getSpecs();
    }
}