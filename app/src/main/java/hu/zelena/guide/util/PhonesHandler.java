package hu.zelena.guide.util;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

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
public class PhonesHandler extends DefaultHandler {
    private final StringBuilder characters = new StringBuilder(64);
    private List<PhonesModell> phonesList;
    private PhonesModell currentItem;
    private boolean parsingName;
    private boolean parsingSim;
    private boolean parsingOS;
    private boolean parsingMA;
    private boolean parsingPic;


    public PhonesHandler() {
        phonesList = new ArrayList<PhonesModell>();
    }

    public List<PhonesModell> getphoneList() {
        return phonesList;
    }


    //Kezdő elem
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("phone"))
            currentItem = new PhonesModell();
        else if (qName.equals("name"))
            parsingName = true;
        else if (qName.equals("sim"))
            parsingSim = true;
        else if (qName.equals("os"))
            parsingOS = true;
        else if (qName.equals("mobilarena"))
             parsingMA = true;
        else if (qName.equals("picture"))
            parsingPic = true;

        characters.setLength(64);
    }

    //Záró elem
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("phone")) {
            //End of an item so add the currentItem to the list of items.
            phonesList.add(currentItem);
            currentItem = null;
        } else if (qName.equals("name"))
            parsingName = false;
        else if (qName.equals("sim"))
            parsingSim = false;
        else if (qName.equals("os"))
            parsingOS = false;
        else if (qName.equals("mobilarena"))
             parsingMA = false;
        else if (qName.equals("picture"))
            parsingPic = false;

        characters.setLength(0);
    }

    //Kezdő és záró tag közötti feldolgozás
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        characters.append(new String(ch,start,length));

        if (currentItem != null) {
            if (parsingName)
                currentItem.setName(new String(ch, start, length));
            else if (parsingSim)
                currentItem.setSim(new String(ch, start, length));
            else if (parsingOS)
                currentItem.setOs(new String(ch, start, length));
            else if ( parsingPic)
                currentItem.setPicURL(new String(ch, start, length));
            else if ( parsingMA)
                currentItem.setMobilarena(new String(ch, start, length));
        }
    }
}
