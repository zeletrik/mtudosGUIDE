package hu.zelena.guide.util;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import hu.zelena.guide.modell.WatchModell;

/**
 * Created by patrik on 2017.02.19..
 */

public class WatchSpecReader {

    private String path;
    private boolean off;

    public WatchSpecReader(String url, boolean offline) {

        path = url;
        off = offline;
    }

    public WatchModell getWatchSpecs() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        WatchSpecHandler handler = new WatchSpecHandler();
        if (off) {
            File file = new File(path);
            saxParser.parse(file, handler);
        } else {
            saxParser.parse(path, handler);
        }
        return handler.getSpecs();
    }
}