package pj1;

import java.io.*;
import java.util.LinkedList;

class deCompressSingleFile {

    private int[] times = new int[256];
    private int[] codelengths = new int[256];
    // 对应的哈夫曼编码值（还原得到）
    private String[] HuffmanCodes = new String[256];
    byte[] b = new byte[8 * 1024];
    public LinkedList<Node> list = new LinkedList<Node>();

    public void deCompress(String srcpath) throws Exception {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcpath));
        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
        String[] name = srcpath.split(".lzip");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(name[0]));



        int size = dataInputStream.readInt();

        for (int i = 0; i < 256; i++) {
            times[i] = dataInputStream.readInt();
            if (times[i] != 0) {
                Node node = new Node(i, times[i]);

                list.add(node);
            }
        }
        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildtree(list);
        huffmanTree.createHuffcode(root);
        System.out.println("over rebuild tree");

        // 读取压缩的文件内容

        int currentSize = 0;
        int length = 0;
        Node node = root;

        StringBuilder str = new StringBuilder("");
        int value1 = dataInputStream.read();

        while (value1!=-1&currentSize!=size){
            while ( str.length()<1024) {
            str.append(changeIntToString(value1));
            value1 = (dataInputStream.read());

            }
            String codes = "";
             codes +=str.toString();
             str = new StringBuilder("");



        for (int i = 0; i < codes.length(); i++) {
            if (codes.charAt(i) == 49) {//往右边
                node = node.getRight();
            } else {//往左边
                node = node.getLeft();
            }
            if (node.isLeaf()) {
                bufferedOutputStream.write((int) node.getData()&0xff);
                currentSize++;
                if (currentSize == size) {
                    break;
                }
                node = root;
                codes=codes.substring(i+1);
                i=-1;

                continue;
            } else {
                continue;

            }

        }
}

        bufferedOutputStream.close();
        dataInputStream.close();

}

    // 将整数转化成字符串
    public String changeIntToString(int value) {
        String s = "";
        for (int i = 0; i < 8; i++) {
            s = value % 2 + s;
            value = value / 2;
        }
        return s;
    }

    public void deCompress (DataInputStream dataInputStream, BufferedOutputStream bufferedOutputStream,int size) throws Exception {


        // 还原码表

        System.out.println(size);
        for (int i = 0; i < 256; i++) {
            times[i] = dataInputStream.readInt();
            if (times[i] != 0) {
                Node node = new Node(i, times[i]);

                list.add(node);
            }
        }
        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildtree(list);
        huffmanTree.createHuffcode(root);

        LinkedList<Node> list1 = huffmanTree.leafChild(root);
        for(Node node : list1){

            HuffmanCodes[(int) node.getData()]=node.getHuffcode();
        }
        System.out.println("over rebuild tree");
        // 读取压缩的文件内容
        int currentSize = 0;
        Node node = root;

        int value1;

        String codes = "";

        while (currentSize!=size){

         value1=dataInputStream.read();
         codes += changeIntToString(value1);



            for (int i = 0; i < codes.length(); i++) {
                if (currentSize == size) {
                    return;
                }
                if (codes.charAt(i) == 49) {//往右边
                    node = node.getRight();
                } else {//往左边
                    node = node.getLeft();
                }
                if (node.isLeaf()) {
                    bufferedOutputStream.write((int)node.getData()&0xff);
                    currentSize++;
                    node = root;
                    codes=codes.substring(i+1);
                    i=-1;

                    continue;

                }else {continue;}

            }
//有bug qaq，解压之后会变小 解压单个文件没问题
           if(currentSize==size){
                return;
            }
            node = root;
        }
        if(currentSize==size){
            return;
        }
    }
}
