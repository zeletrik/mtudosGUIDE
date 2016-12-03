package hu.zelena.guide.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import hu.zelena.guide.modell.Specs;

/**
 * Created by patrik on 2016.11.26..
 */

public class SpecsHandler extends DefaultHandler {
    private Specs specs;
    private boolean parsingName;
    private boolean parsingDisplayType;
    private boolean parsingDisplaySize;
    private boolean parsingDisplayRes;
    private boolean parsingDisplayProtect;
    private boolean parsingOS;
    private boolean parsingChipset;
    private boolean parsingCPU;
    private boolean parsingGPU;
    private boolean parsingRAM;
    private boolean parsingROM;
    private boolean parsingExpand;
    private boolean parsingRCam;
    private boolean parsingFCam;
    private boolean parsingBattery;
    private boolean parsingSpeaker;
    private boolean parsingNFC;
    private boolean parsingRadio;
    private boolean parsingIP;
    private final StringBuilder characters = new StringBuilder(64);


    public SpecsHandler() {
        specs = new Specs();
    }

    public Specs getSpecs() {
        return specs;
    }


    //Kezdő elem
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("name"))
            parsingName = true;
        else if (qName.equals("displayType"))
            parsingDisplayType = true;
        else if (qName.equals("displaySize"))
            parsingDisplaySize = true;
        else if (qName.equals("displayRes"))
            parsingDisplayRes = true;
        else if (qName.equals("displayProtect"))
            parsingDisplayProtect = true;
        else if (qName.equals("os_spec"))
            parsingOS = true;
        else if (qName.equals("chipset"))
            parsingChipset = true;
        else if (qName.equals("cpu"))
            parsingCPU = true;
        else if (qName.equals("gpu"))
            parsingGPU = true;
        else if (qName.equals("ram"))
            parsingRAM = true;
        else if (qName.equals("rom"))
            parsingROM = true;
        else if (qName.equals("expand"))
            parsingExpand = true;
        else if (qName.equals("rCam"))
            parsingRCam = true;
        else if (qName.equals("fCam"))
            parsingFCam = true;
        else if (qName.equals("batt"))
            parsingBattery = true;
        else if (qName.equals("speaker"))
            parsingSpeaker = true;
        else if (qName.equals("nfc"))
            parsingNFC = true;
        else if (qName.equals("radio"))
            parsingRadio = true;
        else if (qName.equals("ipCertified"))
            parsingIP = true;
    }

    //Záró elem
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
     if (qName.equals("name"))
            parsingName = false;
     else if (qName.equals("displayType"))
            parsingDisplayType = false;
     else if (qName.equals("displaySize"))
            parsingDisplaySize = false;
     else if (qName.equals("displayRes"))
            parsingDisplayRes = false;
     else if (qName.equals("displayProtect"))
            parsingDisplayProtect = false;
     else if (qName.equals("os_spec"))
            parsingOS = false;
        else if (qName.equals("chipset"))
            parsingChipset = false;
        else if (qName.equals("cpu"))
            parsingCPU = false;
        else if (qName.equals("gpu"))
            parsingGPU = false;
        else if (qName.equals("ram"))
            parsingRAM = false;
        else if (qName.equals("rom"))
            parsingROM = false;
        else if (qName.equals("expand"))
            parsingExpand = false;
     else if (qName.equals("rCam"))
            parsingRCam = false;
     else if (qName.equals("fCam"))
            parsingFCam = false;
     else if (qName.equals("batt"))
            parsingBattery = false;
        else if (qName.equals("speaker"))
            parsingSpeaker = false;
        else if (qName.equals("nfc"))
            parsingNFC = false;
        else if (qName.equals("radio"))
            parsingRadio = false;
     else if (qName.equals("ipCertified"))
            parsingIP = false;
    }

    //Kezdő és záró tag közötti feldolgozás
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingName)
            specs.setName(new String(ch, start, length));
        else if (parsingDisplayType)
            specs.setDisplayType(new String(ch, start, length));
        else if (parsingDisplaySize)
            specs.setDisplaySize(new String(ch, start, length));
        else if (parsingDisplayRes)
            specs.setDisplayRes(new String(ch, start, length));
        else if (parsingDisplayProtect)
            specs.setDisplayProtect(new String(ch, start, length));
        else if (parsingOS)
            specs.setOs_spec(new String(ch, start, length));
        else if (parsingChipset)
            specs.setChipset(new String(ch, start, length));
        else if (parsingCPU)
            specs.setCpu(new String(ch, start, length));
        else if (parsingGPU)
            specs.setGpu(new String(ch, start, length));
        else if (parsingRAM)
            specs.setRam(new String(ch, start, length));
        else if (parsingROM)
            specs.setRom(new String(ch, start, length));
        else if (parsingExpand)
            specs.setExpand(new String(ch, start, length));
        else if (parsingRCam)
            specs.setrCam(new String(ch, start, length));
        else if (parsingFCam)
            specs.setfCam(new String(ch, start, length));
        else if (parsingBattery)
            specs.setBatt(new String(ch, start, length));
        else if (parsingSpeaker)
            specs.setSpeaker(new String(ch, start, length));
        else if (parsingNFC)
            specs.setNfc(new String(ch, start, length));
        else if (parsingRadio)
            specs.setRadio(new String(ch, start, length));
        else if (parsingIP)
            specs.setIpCertified(new String(ch, start, length));

    }
}