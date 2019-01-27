package com.edu.agh.yolo;

import org.datavec.api.io.filters.RandomPathFilter;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.datavec.image.recordreader.objdetect.ObjectDetectionRecordReader;
import org.datavec.image.recordreader.objdetect.impl.VocLabelProvider;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.ConvolutionMode;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.objdetect.Yolo2OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.transferlearning.FineTuneConfiguration;
import org.deeplearning4j.nn.transferlearning.TransferLearning;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.deeplearning4j.util.ModelSerializer;
import org.deeplearning4j.zoo.model.YOLO2;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.RmsProp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;


public class Train {
    private static final String[] extensions = {"jpg","png","jpeg","bmp"};
    private static final Logger log = LoggerFactory.getLogger(Train.class);
    private static final int INPUT_WIDTH = 608;
    private static final int INPUT_HEIGHT = 608;
    private static final int CHANNELS = 3;
    private static final int GRID_WIDTH = 19;
    private static final int GRID_HEIGHT = 19;
    private static final int CLASSES_NUMBER = 1;
    private static final int BOXES_NUMBER = 7;
    private static final double[][] ANCHOR_BOXES = {{3, 12}, {6, 15},{9, 12}, {12, 12}, {12,3}, {15, 6},{12, 9}};
    private static final int BATCH_SIZE = 4;
    private static final int EPOCHS = 30;
    private static final double LEARNIGN_RATE = 0.0001;
    private static final int SEED = 42;
    private static final String DATA_DIR = "./src/main/resources/dataset";
    private static final double LAMDBA_COORD = 1.0;
    private static final double LAMDBA_NO_OBJECT = 0.5;

    public static void main(String[] args) throws IOException, InterruptedException {


        UIServer uiServer = UIServer.getInstance();

        //Configure where the network information (gradients, score vs. time etc) is to be stored. Here: store in memory.
        StatsStorage statsStorage = new InMemoryStatsStorage();

        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
        uiServer.attach(statsStorage);

        Random rng = new Random(SEED);

        File imageDir = new File(DATA_DIR, "images");
        System.out.println(imageDir.getAbsolutePath());

        log.info("Load data...");
        RandomPathFilter pathFilter = new RandomPathFilter(rng) {
            @Override
            protected boolean accept(String name) {
                name = name.replace("/images/", "/Annotations/").replace(".jpg", ".xml");
                try {
                    return new File(new URI(name)).exists();
                } catch (URISyntaxException ex) {
                    System.err.println("Failed loading traing dataset");
                    throw new RuntimeException(ex);
                }
            }
        };
        log.info("Loaded data...");
        InputSplit[] data = new FileSplit(imageDir, extensions, rng).sample(pathFilter, 0.9, 0.1);
        InputSplit trainData = data[0];
        InputSplit testData = data[1];

        ObjectDetectionRecordReader recordReaderTrain = new ObjectDetectionRecordReader(INPUT_HEIGHT, INPUT_WIDTH, CHANNELS,
                GRID_HEIGHT, GRID_WIDTH, new VocLabelProvider(DATA_DIR));
        recordReaderTrain.initialize(trainData);

        ObjectDetectionRecordReader recordReaderTest = new ObjectDetectionRecordReader(INPUT_HEIGHT, INPUT_WIDTH, CHANNELS,
                GRID_HEIGHT, GRID_WIDTH, new VocLabelProvider(DATA_DIR));
        recordReaderTest.initialize(testData);

        RecordReaderDataSetIterator train = new RecordReaderDataSetIterator(recordReaderTrain, BATCH_SIZE, 1, 1, true);
        train.setPreProcessor(new ImagePreProcessingScaler(0, 1));

        RecordReaderDataSetIterator test = new RecordReaderDataSetIterator(recordReaderTest, 1, 1, 1, true);
        test.setPreProcessor(new ImagePreProcessingScaler(0, 1));

        ComputationGraph pretrained = (ComputationGraph) YOLO2.builder().build().initPretrained();
        INDArray priors = Nd4j.create(ANCHOR_BOXES);

        log.info("\n Model Summary \n" + pretrained.summary());

        FineTuneConfiguration fineTuneConf = new FineTuneConfiguration.Builder()
                .seed(SEED)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .gradientNormalization(GradientNormalization.RenormalizeL2PerLayer)
                .gradientNormalizationThreshold(1.0)
                .updater(new RmsProp(LEARNIGN_RATE))
                .activation(Activation.IDENTITY).miniBatch(true)
                .trainingWorkspaceMode(WorkspaceMode.ENABLED)
                .build();

        ComputationGraph model = new TransferLearning.GraphBuilder(pretrained)
                .fineTuneConfiguration(fineTuneConf)
                .setInputTypes(InputType.convolutional(INPUT_HEIGHT, INPUT_WIDTH, CHANNELS))
                .setFeatureExtractor("batch_normalization_22")
                .removeVertexAndConnections("outputs")
                .removeVertexKeepConnections("conv2d_23")
                .addLayer("convolution2d_23",
                        new ConvolutionLayer.Builder(1, 1)
                                .nIn(1024)
                                .nOut(BOXES_NUMBER * (5 + CLASSES_NUMBER))
                                .stride(1, 1)
                                .convolutionMode(ConvolutionMode.Same)
                                .weightInit(WeightInit.UNIFORM)
                                .hasBias(false)
                                .activation(Activation.IDENTITY)
                                .build(), "leaky_re_lu_22")
                .addLayer("outputs",
                        new Yolo2OutputLayer.Builder()
                                .lambbaNoObj(LAMDBA_NO_OBJECT)
                                .lambdaCoord(LAMDBA_COORD)
                                .boundingBoxPriors(priors)
                                .build(), "convolution2d_23")
                .setOutputs("outputs")
                .build();

        log.info("\n Model Summary \n" + model.summary());
//
        log.info("Train model...");
        model.setListeners(new ScoreIterationListener(1));//print score after each iteration on stout
        model.setListeners(new StatsListener(statsStorage));// visit http://localhost:9000 to track the training process
        for (int i = 0; i < EPOCHS; i++) {
            train.reset();
            while (train.hasNext()) {
                model.fit(train.next());
            }
            log.info("*** Completed epoch {} ***", i);
        }

        log.info("*** Saving Model ***");
        ModelSerializer.writeModel(model, "./src/main/resources/trained/data", true);
        log.info("*** Training Done ***");

        System.exit(0);
    }

}
