package secretSharing;

import java.util.ArrayList;

public class Split {
    public static void main(String[] args) {

    }

    //产生多项式系数 ai值的函数
    public static int[] ran_ai(int kk, ArrayList arr_color) {
        int index = 0;
        int a[] = new int[kk];//创建一个可以存放r和系数的数组。
        for (int i = 0; i < kk; i++) {
            if (arr_color.size() == 0) {
                break;
            }
            a[i] = (int) arr_color.get(index);//将rgb值存储在a[]数组里面，后面会作为多项式的系数。
            arr_color.remove(index);//删除已经被使用的rgb值
        }
        return a; //返回会作为系数的数组。
    }

    //分块函数
    public static int[] ran_qi(int split_k, int mold, int kk, ArrayList arr_color) {
        int a[] = Split.ran_ai(kk, arr_color);//循环多项式的k次在有限域里面随机取五个数。用来组成多项式中变量的系数（a0、a1、a2.。。。）
        int xi[] = new int[split_k]; //存放x值。
        int q[] = new int[split_k];//存入对应插值的函数值

        for (int i = 0; i < split_k; i++) {
            xi[i] = i + 1;
            for (int j = 0; j < kk; j++) {
                q[i] += (a[j] * Math.pow(xi[i], j));
            }
            q[i] %= mold; //将数值取模
        }
        return q;
    }
}
