package secretSharing;

public class Lagrange {
    //多项式插值算法。（拉格朗日）
    public static PolyList Lag(int xx[], int yy[], int mold) {
        PolyList result = new PolyList();
        PolyList x = new PolyList();
        PolyList xi0 = new PolyList();
        PolyList xi1 = new PolyList();
        PolyList xj = new PolyList();
        PolyList yj = new PolyList();
        x.insert(new PolyNode(1, 1));//方程未知数 x
        int m = xx.length;
        //这个循环结束就得到了一个拉格朗日插值。
        for (int ib = 0; ib < m; ib++) {
            PolyList k = new PolyList();
            k.insert(new PolyNode(1, 0)); //设置常数项 k = 1
            //这个整个循环每次一结束就是得到一个拉格朗如基本多项式的值（也就是插值基函数）
            for (int ic = 0; ic < m; ic++) {
                if (ib != ic) {
//                    k = (Area.Judgemult(k, Area.Judgeadd(x0, x[ic], mold), mold) * Area.Inverse_element(Area.Judgeadd(x[ib], x[ic], mold), mold)) % mold;
                    xi0.insert(new PolyNode(xx[ic], 0));//基函数的 xi值（也就是分子上 x - ？）
                    xi1.insert(new PolyNode(xx[ic], 0));//基函数的 xi值（也就是分子上 x - ？）
                    xj.insert(new PolyNode(xx[ib], 0));//基函数的 xj值（也就是分母的 ？ - xi）
                    k = PolyList.multiply(k, PolyList.multiply(PolyList.add(x, xi0, 0, mold), PolyList.add(xj, xi1, 0, mold), 0, mold), 1, mold);
                    xi0.delete();
                    xi1.delete();
                    xj.delete();
                }
            }
            yj.insert(new PolyNode(yy[ib], 0));
            k = PolyList.multiply(k, yj, 1, mold);//将得到的拉格朗日基本多项式的值与对应的固定插值的的函数值相乘
            yj.delete();
            result = PolyList.add(result, k, 1, mold); //这一步就是拉格朗日多项式求插值的叠加步骤。
        }
        return result;
    }

    //多项式计算
    public static int PolyCal(PolyList result_pol, int x, int mold) {
        int result = 0;
        result_pol.current = result_pol.head.next;
        while (result_pol.current != null) {
            result = Area.Judgeadd(Area.Judgemult(result_pol.current.getA(), (int) Math.pow(x, result_pol.current.getl()), mold), result, mold);
            result_pol.current = result_pol.current.next;
        }
        return result;
    }

    public static int[] getCoef(PolyList result_pol, int kk) {
        int a[] = new int[kk];
        result_pol.current = result_pol.head.next;
        for (int i = 0; i < kk; i++) {
            if (result_pol.current != null){
                a[i] = result_pol.current.getA();
                result_pol.current = result_pol.current.next;
            }else a[i] = 0;
        }
        return a;
    }
}


