package com.edu.agh.yolo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Resizer {
    static {System.loadLibrary( Core.NATIVE_LIBRARY_NAME );}

    public static void resize(String base_path,String change_path){
        Mat image = Imgcodecs.imread(base_path);
        Size sz = new Size(608,608);
        Mat resizeimage = new Mat();
        Imgproc.resize( image, resizeimage, sz );
        Imgcodecs.imwrite(change_path,resizeimage);
    }
}
