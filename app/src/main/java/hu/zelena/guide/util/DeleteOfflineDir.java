package hu.zelena.guide.util;

import android.content.Intent;
import android.util.Log;

import java.io.File;

import hu.zelena.guide.ErrorActivity;

/**
 * Created by patrik on 2016.11.28..
 */

public class DeleteOfflineDir {

    public DeleteOfflineDir(String dirPath){
        File dir = new File(dirPath);
        if(dir.exists()){
            deleteRecursive(dir);
            Log.d("File:","deleted");
        }else{
            Log.e("File error:","Not exist");

        }
    }


    public void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }

}
