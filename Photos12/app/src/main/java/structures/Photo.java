package structures;



import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import com.example.photos12.ProxyBitmap;

import java.io.*;
import java.net.URI;
import java.util.*;
/**
 * The Photo class
 * @author Junjie He (jh1285)
 * @author Ruimin Li (rl751)
 */
public class Photo implements java.io.Serializable {

    public static int currentPhotoPosition;
    /**
     * serialVersion UID
     */
    private static final long serialVersionUID = 1L;

//    /**
//     * the path of the photo
//     */
//    public ProxyBitmap bitmap;
//    public Uri image;
    public String uriPath;

    /**
     * the tags of the photo
     */
    public HashMap<String, ArrayList<String>> tags=new HashMap<String, ArrayList<String>>();

    /**
     * This constructor is going to initialize the Photo class
     *
     */
//    public Photo(ProxyBitmap bitmap){
//        this.bitmap=bitmap;
//    }
    public Photo(Uri image){
        this.uriPath=image.toString();
        filename = image.getPath();
    }

    public String filename;
    /**
     * This constructor is going to make a copy of photo p
     * @param p
     */

    public Photo(Photo p) {
        this.tags=new HashMap<String, ArrayList<String>>();
        for(Map.Entry<String, ArrayList<String>> e:p.tags.entrySet()){
            ArrayList<String> temp=new ArrayList<>();
            for(String str:e.getValue()){
                temp.add(str);
            }
            this.tags.put(e.getKey(),temp);
        }
        this.uriPath=p.uriPath;
        filename = p.filename;
    }



    /**
     * Retrieve the image by path
     * @return
     */
//    public Image getImage(){
//        File file = new File(path);
//        return new Image(file.toURI().toString());
//    }



}
