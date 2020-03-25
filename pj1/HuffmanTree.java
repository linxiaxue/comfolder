package pj1;

import java.util.Collections;
import java.util.LinkedList;

//哈夫曼节点类
class Node implements Comparable<Node>{
    private Object data; //数据
    private int weight; // 权重
    private Node left; //左孩子
    private Node right; //右孩子
    private Node parent;//父节点
    private String huffcode="";
    private int index;
    Node(Object data, int weight){
        this.data = data;
        this.weight = weight;
    }
    public Object getData(){
        return data;
    }
    public int getWeight(){
        return weight;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public void setData(Object data){
        this.data = data;
    }

    public void setLeft(Node left){
        this.left = left;
    }
    public void setRight(Node right){
        this.right = right;
    }
    public Node getLeft(){
        return left;
    }
    public Node getRight(){
        return right;
    }
    public Node getParent(){
        return parent;
    }
    public void setParent(Node parent){
        this.parent = parent;
    }
    public void setHuffcode(String huffcode){
        this.huffcode = huffcode;
    }
    public String getHuffcode(){
        return huffcode;
    }
    public boolean isLeaf(){
        return this.left==null && this.right==null;
    }

    @Override
    public int compareTo(Node other){
        if(other.getWeight()>this.getWeight())
            return 1;
        if (other.getWeight()<this.getWeight())
            return -1;
        return 0;
    }

    @Override
    public String toString(){
        return "data:"+this.data+",weight:"+this.weight+",code:"+this.huffcode;
    }

}

//哈夫曼树
class HuffmanTree {

    //根据权值构建哈夫曼树
    //利用Collection.sort()排序方法，使元素从小到大排列
    //每次选取两个权值最小的节点构建新节点，并将新节点加入集合，旧节点删除
    public Node buildtree( LinkedList<Node> nodes){
        while (nodes.size()>1){
            Collections.sort(nodes);
            Node left = nodes.get(nodes.size()-1);
            Node right = nodes.get(nodes.size()-2);
            Node parent = new Node(null,left.getWeight()+right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(parent);
        }

        return nodes.get(0);
    }

    //遍历建好的树得到对应node的哈夫曼编码
    //从根节点开始，往左编码+0.往右+1
    public void createHuffcode(Node root){
        if (root == null){
            return;
        }
        if(root.getLeft()!=null){
            root.getLeft().setHuffcode(root.getHuffcode()+"0");

        }
        if(root.getRight()!=null){
            root.getRight().setHuffcode(root.getHuffcode()+"1");

        }
        createHuffcode(root.getLeft());
        createHuffcode(root.getRight());

    }

    LinkedList<Node>leafList = new LinkedList<>();

    //遍历得到叶子节点.储存在链表中
    public LinkedList<Node> leafChild(Node root){

        if(root!=null) {
            if (root.isLeaf()) {
                leafList.add(root);
            }
            leafChild(root.getRight());
            leafChild(root.getLeft());
        }
        return leafList;
    }


}
