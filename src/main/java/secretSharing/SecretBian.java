package secretSharing;

public class SecretBian {
    private static int mold;//固定有限域的范围。
    private static int split_k;//固定秘密拆成的份数。
    private static int kk;//这是多项式的k值

    public static int getMold() {
        return mold;
    }

    public static int getSplit_k() {
        return split_k;
    }

    public static int getKk() {
        return kk;
    }

    public static void setMold(int mold) {
        SecretBian.mold = mold;
    }

    public static void setSplit_k(int split_k) {
        SecretBian.split_k = split_k;
    }

    public static void setKk(int kk) {
        SecretBian.kk = kk;
    }
}
