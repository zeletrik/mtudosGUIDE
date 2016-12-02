package hu.zelena.guide.util;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import hu.zelena.guide.modell.DbVersion;

/**
 * Created by patrik on 2016.11.28..
 */

public class DBVersionReader {

    private String path;

    public DBVersionReader(String file) {
        path = file;
    }

    public DbVersion getDBVer() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        DBverHandler handler = new DBverHandler();
        File file = new File (path);
        DbVersion db = new DbVersion();

        if(file.exists()){
            saxParser.parse(file, handler);
            return handler.getDbVer();
        }else{
            db.setVersion("0");
        }
        return db;
    }
}