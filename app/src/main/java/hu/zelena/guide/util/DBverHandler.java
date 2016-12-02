package hu.zelena.guide.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hu.zelena.guide.modell.DbVersion;

/**
 * Created by patrik on 2016.11.28..
 */

public class DBverHandler extends DefaultHandler {
    private DbVersion dbVer;
    private boolean parsingVersion;
    private final StringBuilder characters = new StringBuilder(64);


    public DBverHandler() {
        dbVer = new DbVersion();
    }

    public DbVersion getDbVer() {
        return dbVer;
    }


    //Kezdő elem
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("version"))
            parsingVersion = true;
    }

    //Záró elem
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("version"))
            parsingVersion = false;
    }

    //Kezdő és záró tag közötti feldolgozás
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingVersion)
            dbVer.setVersion(new String(ch, start, length));

    }
}