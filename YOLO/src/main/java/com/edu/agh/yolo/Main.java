package com.edu.agh.yolo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    JLabel image;
    private JFrame j;
    private JMenu jmenu;
    private JMenuBar jbar;
    private JMenuItem jmi, jexit;
    private JPanel jpanel, jpanelbar;
    private YoloModel yoloModel;
    File noise;

    Main() {
        noise = new File("./src/main/resources/Noise.jpg");
        yoloModel = new YoloModel();
        j = new JFrame("YOLO.V2");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLocationByPlatform(true);
        j.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        image = new JLabel(" ");
        jpanel.add(image, BorderLayout.CENTER);

        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = c.gridy = 0;
        c.gridwidth = 2;
        c.weighty = 0.1;
        c.ipady = 600;
        c.insets = new Insets(5, 5, 10, 5);
        j.add(jpanel, c);


        // Creating Menu
        jbar = new JMenuBar();
        jmenu = new JMenu("File");
        jmi = new JMenuItem("Open");
        jmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        Resizer.resize(file.getAbsolutePath(),noise.getAbsolutePath());
                        Mat base = Imgcodecs.imread(noise.getAbsolutePath());
                        yoloModel.detectWolf(base, 0.1);
                        Imgcodecs.imwrite(noise.getAbsolutePath(), base);
                        image.setIcon(new ImageIcon(ImageIO.read(noise)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        jexit = new JMenuItem("Exit");
        jexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        jmenu.add(jmi);
        jmenu.add(jexit);
        jbar.add(jmenu);
        j.setJMenuBar(jbar);

        j.setSize(608, 608);
        j.setResizable(true);
        j.setVisible(true);
        try {
            image.setIcon(new ImageIcon(ImageIO.read(new File("./src/main/resources/YOLO.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}