package hu.zelena.guide.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hu.zelena.guide.modell.WatchModell;

/**
 * Created by patrik on 2017.02.19..
 */

public class WatchSpecHandler extends DefaultHandler {
    private final StringBuilder characters = new StringBuilder(64);
    private WatchModell specs;
    private boolean parsingName;
    private boolean parsingAndroidVer;
    private boolean parsingIOSVer;
    private boolean parsingWinVer;
    private boolean parsingIPCert;
    private boolean parsingIPOther;
    private boolean parsingDesc;


    public WatchSpecHandler() {
        specs = new WatchModell();
    }

    public WatchModell getSpecs() {
        return specs;
    }


    //Kezdő elem
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("name"))
            parsingName = true;
        else if (qName.equals("androidVer"))
            parsingAndroidVer = true;
        else if (qName.equals("iosVer"))
            parsingIOSVer = true;
        else if (qName.equals("winVer"))
            parsingWinVer = true;
        else if (qName.equals("IPCert"))
            parsingIPCert = true;
        else if (qName.equals("IPOther"))
            parsingIPOther = true;
        else if (qName.equals("desc"))
            parsingDesc = true;
    }

    //Záró elem
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("name"))
            parsingName = false;
        else if (qName.equals("androidVer"))
            parsingAndroidVer = false;
        else if (qName.equals("iosVer"))
            parsingIOSVer = false;
        else if (qName.equals("winVer"))
            parsingWinVer = false;
        else if (qName.equals("IPCert"))
            parsingIPCert = false;
        else if (qName.equals("IPOther"))
            parsingIPOther = false;
        else if (qName.equals("desc"))
            parsingDesc = false;
    }

    //Kezdő és záró tag közötti feldolgozás
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingName)
            specs.setName(new String(ch, start, length));
        else if (parsingAndroidVer)
            specs.setWatchAndroidVersion(new String(ch, start, length));
        else if (parsingIOSVer)
            specs.setWatchIosVersion(new String(ch, start, length));
        else if (parsingWinVer)
            specs.setWatchWinVersion(new String(ch, start, length));
        else if (parsingIPCert)
            specs.setWatchIPcert(new String(ch, start, length));
        else if (parsingIPOther)
            specs.setWatchIPother(new String(ch, start, length));
        else if (parsingDesc)
            specs.setWatchDesc(new String(ch, start, length));
    }
}