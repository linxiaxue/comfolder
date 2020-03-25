package pj1;

import java.io.*;
import java.util.LinkedList;


class compressSingleFile {

    // 0~255个位置，存储所有字符出现的次数
    public int[] times = new int[256];
    // 存储每个字符（ASCII码）的哈夫曼编码
    public String[] huffmanCodes = new String[256];
    //按权值排序储存字符的链表
    public LinkedList<Node> list = new LinkedList<Node>();
    //源文件总字节长度
    public int size = 0;
    //缓冲数组
    byte[] b =new byte[1024000000];

    compressSingleFile() {
        // 循环赋值，避免空指针
        for (int i = 0; i < huffmanCodes.length; i++) {
            huffmanCodes[i] = "";
        }
    }

    // 统计文件中各字符出现次数
    private void countTimes(String path) throws Exception {
       // b=new byte[8*1024];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
        int length = 0;
        while ((length=bufferedInputStream.read(b))!=-1){
            for (int i=0;i<length;i++){
                int value = b[i];

                times[value & 0xff]++;
                size++;
            }
        }

        // 关闭流
        bufferedInputStream.close();

        if(size==0){//若空文件，则直接返回
            return;
        }

        for (int i = 0; i < times.length; i++) {
            if (times[i] != 0) {
                Node node = new Node(i, times[i]);
                list.add(node);
            }
        }

    }

    //执行单一文件的压缩时调用此函数
    public void compress(String path) throws Exception {


        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + ".lzip"));
        //bufferoutputstream会极大提高速度
        DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
        //构建哈夫曼树及得到编码
        countTimes(path);
        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildtree(list);
        huffmanTree.createHuffcode(root);

        //将哈夫曼编码利用数组储存，index值为对应的ASCII值
        LinkedList<Node> list1 = huffmanTree.leafChild(root);
        for (Node node : list1) {
            huffmanCodes[(int) node.getData()] = node.getHuffcode();
        }

        //写入文件字节长度
        dataOutputStream.writeInt(size);


        // 记录权重信息 //
        for (int i = 0; i < 256; i++) {
            dataOutputStream.writeInt(times[i]);
            dataOutputStream.flush();
        }
        System.out.println("over write weight");


        String s = "";
        int length,temp;
        int str;
        byte[] b = new byte[4*1024];
        //---------------------------------------------------//
        //开始从原文件中读取数据//
        while ((length=bufferedInputStream.read(b))!=-1){
            for (int j=0;j<length;j++) {
                temp = 0|(b[j]&0x000000ff);
                // 拼接当前字符的哈夫曼码
                s += huffmanCodes[temp];

                //对其进行位操作，
                //String str = "";

                while (s.length() >= 8) {
                    str = (((s.charAt(0) - 48) << 7)
                            + ((s.charAt(1) - 48) << 6) + ((s.charAt(2) - 48) << 5)
                            + ((s.charAt(3) - 48) << 4) + ((s.charAt(4) - 48) << 3)
                            + ((s.charAt(5) - 48) << 2) + ((s.charAt(6) - 48) << 1)
                            + (s.charAt(7) - 48));
                    dataOutputStream.write((byte) str);//第一个By为文件长度
                    s = s.substring(8);
                }
            }
        }

        bufferedInputStream.close();

        // 最后不够8位添0
        int last1 = 8 - s.length();

        for (int i = 0; i < last1; i++) {
            s+= "0";

        }
        if(last1!=8){
            String s1 = s.substring(0, 8);
            int d = changeStringToInt(s1);
            dataOutputStream.write(d);
        }
        // 压缩后不够补0的个数暂

        dataOutputStream.flush();
        dataOutputStream.close();
        // 正式写入压缩数据结束 //

        System.out.println("over compress :"+ path);
    }

    // 将字符串转换成整数
    public int changeStringToInt(String s) {
        int v1 = (s.charAt(0) - 48) * 128;
        int v2 = (s.charAt(1) - 48) * 64;
        int v3 = (s.charAt(2) - 48) * 32;
        int v4 = (s.charAt(3) - 48) * 16;
        int v5 = (s.charAt(4) - 48) * 8;
        int v6 = (s.charAt(5) - 48) * 4;
        int v7 = (s.charAt(6) - 48) * 2;
        int v8 = (s.charAt(7) - 48) * 1;
        return v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8;
    }

    public void compress(String path, DataOutputStream dataOutputStream) throws Exception {

        countTimes(path);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));

        dataOutputStream.writeInt(size);
        System.out.println(size);
        dataOutputStream.flush();
        if (size == 0) {
            return;
        }
        HuffmanTree huffmanTree = new HuffmanTree();
        Node root =  huffmanTree.buildtree(list);
        huffmanTree.createHuffcode(root);

        LinkedList<Node> list1 = huffmanTree.leafChild(root);
        for(Node node : list1){

            huffmanCodes[(int) node.getData()]=node.getHuffcode();
        }


        /** ===============把码表写入文件================ */
        // 记录顺序记录每个哈夫曼编码长度信息开始 //


        for (int i = 0; i < 256; i++) {
            dataOutputStream.writeInt(times[i]);

            dataOutputStream.flush();
        }



        String s = "";
        int length,temp;
        int str;
        byte[] b = new byte[4*1024];
        while ((length=bufferedInputStream.read(b))!=-1){
            for (int j=0;j<length;j++) {

                temp = 0|(b[j]&0x000000ff);
                // 拼接当前字符的哈夫曼码
                s += huffmanCodes[temp];

                while (s.length() >= 8) {
                    str = (((s.charAt(0) - 48) << 7)
                            + ((s.charAt(1) - 48) << 6) + ((s.charAt(2) - 48) << 5)
                            + ((s.charAt(3) - 48) << 4) + ((s.charAt(4) - 48) << 3)
                            + ((s.charAt(5) - 48) << 2) + ((s.charAt(6) - 48) << 1)
                            + (s.charAt(7) - 48));
                    dataOutputStream.write((byte)str);//第一个By为文件长度
                    s = s.substring(8);
                }
            }
        }
        bufferedInputStream.close();

        // 最后不够8位添0
        int last1 = 8 - s.length();
        for (int i = 0; i < last1; i++) {
            s+="0";
        }
        String s1 = s.substring(0, 8);

        if(last1!=8){
        int d = changeStringToInt(s1);
        dataOutputStream.write(d);
        dataOutputStream.flush();
        }

        // 压缩后不够补0的个数暂

        // 正式写入压缩数据结束 //

        System.out.println("over compress :"+ path);
    }




}
