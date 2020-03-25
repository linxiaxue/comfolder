package pj1;

import javafx.scene.control.Label;

import java.awt.*;
import java.io.*;

public class Comfolder {
    public Comfolder(){

    }


    public  void comfloder (String path) throws IOException {

        File file = new File(path);
        File[] files = file.listFiles();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(file.getAbsolutePath()+".lzip")));
        DataOutputStream dataOutputStream = new DataOutputStream(bos);
        compressFolder1(file,dataOutputStream);
        dataOutputStream.close();
    }


    private  <dataOutputStream> void compressFolder1(File file, DataOutputStream dataOutputStream){
        File[] inputFiles = file.listFiles();



        try {
            dataOutputStream.writeUTF(file.getName()+".hzip");
            System.out.println(file.getName());
            dataOutputStream.writeInt(inputFiles.length);
            System.out.println(inputFiles.length);
            if(inputFiles.length!=0){
                for (File inputFile1 : inputFiles) {
                    String str1=inputFile1.getName();
                    if(!inputFile1.isFile()){

                        compressFolder1(inputFile1,dataOutputStream);
                        continue;
                    }
                    dataOutputStream.writeUTF(str1);
                    System.out.println(str1);
                    compressSingleFile compressSingleFile = new compressSingleFile();
                    compressSingleFile.compress(inputFile1.getAbsolutePath(),dataOutputStream);


                    dataOutputStream.flush();
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }







}
