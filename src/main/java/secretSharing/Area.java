package secretSharing;

/*
域：
    １.交换加群
        １）封闭性  ２）结合律  ３）存在加法单位元  ４）逆元  ５）交换律
     ２．非零元素的交换乘群
        １）封闭性  ２）结合律  ３）存在乘法单位元  ４）逆元  ５）交换律
     ３.加法和乘法之间有分配律
 */
public class Area {
    public static void main(String[] args) {
    }

    // 减法取模函数
    public static int Judgesub(int num1, int num2, int mold) {
        int numbers1 = 0;
        numbers1 = (((num1 - num2) % mold) + mold) % mold;
        return numbers1;
    }

    // 加法取模函数
    public static int Judgeadd(int num1, int num2, int mold) {
        int numbers1 = 0;
        numbers1 = (num1 + num2) % mold;
        return numbers1;
    }

    // 乘法取模函数
    public static int Judgemult(int num1, int num2, int mold) {
        int numbers2 = 0;
        numbers2 = (num1 * num2) % mold;
        return numbers2;
    }

    // 取逆元函数
    public static int Inverse_element(int num, int mold) {
        int number = 0;
        //取mold的值进行验证是否存在逆元
        if (num == 0) {
            return 0; //验证逆元的时候，乘群是要对非零元素进行的。
        }
        for (int j = 1; j < mold; j++) {
            if ((j * num) % mold == 1) {
                number = j;
                break;
            }
        }
        return number;
    }
}