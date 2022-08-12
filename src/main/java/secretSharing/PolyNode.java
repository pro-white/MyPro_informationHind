package secretSharing;

public class PolyNode {
//    private double a;
//    private double i;
    private int a;
    private int i;
    PolyNode next;

    //有参数构造方法。
    public PolyNode(int a, int i) {
        this.a = a;
        this.i = i;
        this.next = null;
    }

    //构造方法
    public PolyNode() {
        this(0, 0);
    }

    //set和get方法。
    public int getA() {
        return a;
    }

    public int getl() {
        return i;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setl(int i) {

        this.i = i;
    }
}

