package hu.zelena.guide.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import hu.zelena.guide.MainActivity;
import hu.zelena.guide.modell.PhonesModell;


/**
 * Created by patrik on 2016.11.24..
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
