package pj1;

import java.io.*;

public class Decomfolder {


    
    public Decomfolder() {
    }

    //主要函数
    public  void decomfloder(File inputFile)  {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
            DataInputStream dataInputStream = new DataInputStream(bis);
                //真的话为文件
                String fileName = inputFile.getName().split("\\.")[0];
                File file = new File(inputFile.getParentFile().getAbsolutePath() + "\\" + fileName);
                file.mkdir();
                String parentFileName = dataInputStream.readUTF();

                extract2(file,dataInputStream,"");

        }
        catch (IOException ignored){} catch (Exception e) {
            e.printStackTrace();
        }
    }



    //对于非嵌套文件夹
    public  void extract2(File file, DataInputStream dataInputStream,String parentFileName) throws Exception {

            //先读出之前文件夹的名字
            int fileNumbers = dataInputStream.readInt();

            file=new File(file.getAbsolutePath()+"\\"+parentFileName);
            file.mkdir();

            if (fileNumbers != 0){
                int i=0;

                while (i<fileNumbers) {
                    String fileName = dataInputStream.readUTF();
                    if(fileName.contains(".hzip")){
                        fileName=fileName.split("\\.")[0];
                        extract2(file,dataInputStream,fileName);
                        continue;
                    }

                    String creatFileName = file.getAbsolutePath() + "\\" + fileName;
                    BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(creatFileName));
                    int size = dataInputStream.readInt();//读出字节种类数量
                    if (size != 0) {//如果不是空文件的话就继续操作
                        deCompressSingleFile deCompressSingleFile = new deCompressSingleFile();
                        deCompressSingleFile.deCompress(dataInputStream, fos, size);
                        System.out.println("over decompress"+fileName);
                        i++;

                        continue;
                    } else {
                        i++;
                    }

                    fos.flush();
                    fos.close();
                }
                }
                System.out.println("over");
            }

    }

