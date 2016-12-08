package hu.zelena.guide.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hu.zelena.guide.modell.DbVersion;

/**
 Copyright Patrik G. Zelena

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

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