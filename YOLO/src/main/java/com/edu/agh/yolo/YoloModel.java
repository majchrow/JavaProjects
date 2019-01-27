package com.edu.agh.yolo;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.layers.objdetect.DetectedObject;
import org.deeplearning4j.nn.layers.objdetect.Yolo2OutputLayer;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.opencv.imgproc.Imgproc.rectangle;

public class YoloModel {

    private static final String[] CLASSES = {"wolf"};
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(YoloModel.class);
    private static ComputationGraph NETWORK;
    private final int IMAGE_INPUT_W = 608;
    private final int IMAGE_INPUT_H = 608;
    private final int CHANNELS = 3;
    private final int GRID_W = 19;
    private final int GRID_H = 19;
    //private final double DETECTION_THRESHOLD = .5;
    private final String MODEL_PATH = "./src/main/resources/trained/data";

    public YoloModel() {
        File net = new File(MODEL_PATH);
        boolean modelexists = net.exists() && !net.isDirectory();
//        System.out.println(modelexists);

        if (modelexists) {
            try {
                NETWORK = ModelSerializer.restoreComputationGraph(MODEL_PATH);
                System.out.println(NETWORK.summary());
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        } else {
            log.error("Can't find model file \"model.data\"\n"
                    + "Please Train the dataset first to provide the model file");
        }
    }

    public ComputationGraph getNETWORK() {
        return NETWORK;
    }

    public void drawBoxes(Mat image, List<DetectedObject> objects) {
        for (DetectedObject obj : objects) {
            double[] xy1 = obj.getTopLeftXY();
            double[] xy2 = obj.getBottomRightXY();
            int predictedClass = obj.getPredictedClass();
            System.out.println("Predicted class " + CLASSES[predictedClass]);
            int x1 = (int) Math.round(IMAGE_INPUT_W * xy1[0] / GRID_W);
            int y1 = (int) Math.round(IMAGE_INPUT_H * xy1[1] / GRID_H);
            int x2 = (int) Math.round(IMAGE_INPUT_W * xy2[0] / GRID_W);
            int y2 = (int) Math.round(IMAGE_INPUT_H * xy2[1] / GRID_H);
            rectangle(image, new Point(x1, y1), new Point(x2, y2), new Scalar(255, 0, 0));

        }
    }

    public void detectWolf(Mat image, double detectionthreshold) {
        Yolo2OutputLayer yout = (Yolo2OutputLayer) NETWORK.getOutputLayer(0);
        NativeImageLoader loader = new NativeImageLoader(IMAGE_INPUT_W, IMAGE_INPUT_H, CHANNELS);
        INDArray ds = null;
        try {
            ds = loader.asMatrix(image);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        ImagePreProcessingScaler scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(ds);
        INDArray results = NETWORK.outputSingle(ds);
        List<DetectedObject> objs = yout.getPredictedObjects(results, detectionthreshold);
        List<DetectedObject> objects = UOI.getObjects(objs);
        drawBoxes(image, objects);
        System.out.println(results);
    }

}