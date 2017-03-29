package hu.zelena.guide.util;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import hu.zelena.guide.modell.DbVersion;

/**
 * Created by patrik on 2016.11.28..
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