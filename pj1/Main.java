package pj1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;


import java.io.*;

public class Main extends Application {


    @Override
    public  void start(Stage primaryStage) throws Exception{
        Button compressFile = new Button("compressFile");
        Button compressFolder = new javafx.scene.control.Button("compressFolder");
        Button depressFile = new javafx.scene.control.Button("depressFile");
        Button depressFolder = new Button("depressFolder");

        Pane pane = new Pane();




        final Label label = new Label();
        label.setLayoutX(235);
        label.setLayoutY(90);
        pane.getChildren().add(label);


        compressFile.setLayoutX(50);
        compressFile.setLayoutY(115);
        compressFolder.setLayoutY(115);
        compressFolder.setLayoutX(160);
        depressFile.setLayoutX(290);
        depressFile.setLayoutY(115);
        depressFolder.setLayoutX(390);
        depressFolder.setLayoutY(115);

        pane.getChildren().addAll(compressFile, compressFolder,depressFile,depressFolder);

        Scene scene = new Scene(pane, 530, 250);

        primaryStage.setTitle("极简压缩");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add(getClass().getResource("mystyle.css").toExternalForm());
        Application.setUserAgentStylesheet(STYLESHEET_MODENA);

        compressFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                label.setText("compressing");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose File");
                //new ExtensionFilter("Text Files", "*.txt"),
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
                //这里是选择文件的时候，还可以通过下拉框选择文件类型，这样在有N多文件的时候经筛选就会更方便一些，但懒得打了...
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {

                        compressSingleFile compressSingleFile = new compressSingleFile();
                        compressSingleFile.compress(file.getAbsolutePath());
                        label.setText("success!!!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    }
                }


            }
        });

        depressFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                label.setText("decompressing");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose File");
                //new ExtensionFilter("Text Files", "*.txt"),
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
                //这里是选择文件的时候，还可以通过下拉框选择文件类型，这样在有N多文件的时候经筛选就会更方便一些，但懒得打了...
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        deCompressSingleFile deCompressSingleFile = new deCompressSingleFile();
                        deCompressSingleFile.deCompress(file.getAbsolutePath());
                        label.setText("success!!!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    }
                }


            }
        });

        compressFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                label.setText("compressing");
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Choose Folder");
                File directory = directoryChooser.showDialog(new Stage());
                if (directory != null) {
                    try {
                        Comfolder comfolder  = new Comfolder();
                        comfolder.comfloder(directory.getAbsolutePath());
                        label.setText("success!!!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    }
                }


            }
        });
        

        depressFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)  {
                label.setText("decompressing");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose File");
                //new ExtensionFilter("Text Files", "*.txt"),
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
                //这里是选择文件的时候，还可以通过下拉框选择文件类型，这样在有N多文件的时候经筛选就会更方便一些，但懒得打了...
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        Decomfolder decomfolder = new Decomfolder();
                        decomfolder.decomfloder(file);
                        label.setText("success!!!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        label.setText("error!!!");
                    }
                }


            }
        });

    }


    public static void main(String[] args) throws Exception {

        //long  startTime = System.currentTimeMillis();
        launch(args);
       // Comfolder comfolder = new Comfolder();
       // comfolder.comfloder("C://Users//dhg//Desktop//2");
       // Decomfolder decomfolder = new Decomfolder();
       // decomfolder.decomfloder(new File("C://Users//dhg//Desktop//2.lzip"));
       // long endTime = System.currentTimeMillis();    //获取结束时间
       // System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

    }





}





