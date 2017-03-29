package hu.zelena.guide.util;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import hu.zelena.guide.modell.PhonesModell;


/**
 * Created by patrik on 2016.11.24..
 */

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
public class PhoneListReader {

    private String path;
    private boolean off;

    public PhoneListReader(String url, boolean offline) {

        path = url;
        off = offline;
    }

    public List<PhonesModell> getItems() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        PhonesHandler handler = new PhonesHandler();
        if(off) {
            File file = new File (path);
            saxParser.parse(file, handler);
        }else{
            saxParser.parse(path, handler);
        }
        return handler.getphoneList();
    }
}
