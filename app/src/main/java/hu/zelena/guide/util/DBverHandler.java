package hu.zelena.guide.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hu.zelena.guide.modell.DbVersion;

/**
 Copyright (C) <2017>  <Patrik G. Zelena>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

public class DBverHandler extends DefaultHandler {
    private final StringBuilder characters = new StringBuilder(64);
    private DbVersion dbVer;
    private boolean parsingVersion;


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