package javafxControllers.pictureProcess.irreversible.hind;


import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxControllers.Main;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerHindImageMy1 {
    @FXML
    private Text arrowL;

    @FXML
    private Text textRun;

    @FXML
    private Button TrueHind;

    @FXML
    private FlowPane flowPaneZT;

    @FXML
    private ScrollPane ScrollPaneOK;

    @FXML
    private FlowPane flowPaneOK;

    @FXML
    private VBox updatePane;

    @FXML
    private Text arrowR;

    @FXML
    private ScrollPane ScrollPaneYZ;

    @FXML
    private FlowPane flowPaneYZ;

    @FXML
    private Button buttZT;

    @FXML
    private ProgressBar pbHind;

    @FXML
    private Button buttYZ;

    @FXML
    private Button secretSave;

    @FXML
    private Text textYZ;

    @FXML
    private ScrollPane ScrollPaneZT;

    @FXML
    private Text textZT1;

    @FXML
    private Text textZT;

    @FXML
    private Button secretSaveYJ;

    Utils utils = new Utils();

    public static Image imageCarrier[];

    public static Image imageHind[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOK = new ArrayList<ImageView>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    public static ArrayList<Text> textimgNamesZT = new ArrayList<Text>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    List<File> listfile;

    int number = 1;

    int matrixSize = 3;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    ScheduledService<Double> sche;

    @FXML
    void HFtoImageSave(ActionEvent event) throws IOException {
        if (ivsOK.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOK.size(); i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            ImageIO.write(SwingFXUtils.fromFXImage(ivsOK.get(i).getImage(), null), "png", new File(filename.get(i)));
        }

        //调用提示框函数
        Stage stage1 = (Stage) secretSave.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1, "保存成功！", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HFtoImageSaveYJ(ActionEvent event) throws IOException {
        if (ivsOK.isEmpty()) {
            return;
        }

        ArrayList<BufferedImage> bi = new ArrayList<BufferedImage>();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOK.size(); i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            bi.add(SwingFXUtils.fromFXImage(ivsOK.get(i).getImage(), null));
            ImageIO.write(bi.get(i), "png", new File(filename.get(i)));
        }

        Stage stage1 = (Stage) secretSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1, "保存成功！", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void buttYZChoose(ActionEvent event) throws IOException {
        fps.clear();
        ivs.clear();
        flowPaneYZ.getChildren().clear();

        List<File> listfileYZ = null;
        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            Stage stage = new Stage(); //创建一个场景
            FileChooser fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片多选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"));
            listfileYZ = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfileYZ == null) {
                return;
            }
            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        imageHind = new Image[listfileYZ.size()];
        in = new FileInputStream[listfileYZ.size()];
        pr = new PixelReader[listfileYZ.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfileYZ, in, imageHind, ivs, fps, textimgNames, pr, flowPaneYZ);
    }

    @FXML
    void buttZTChoose(ActionEvent event) throws IOException {
        fps.clear();
        ivsZT.clear();
        flowPaneZT.getChildren().clear();


        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            Stage stage = new Stage(); //创建一个场景
            FileChooser fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片多选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"));
            listfile = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfile == null) {
                return;
            }

            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        imageCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, imageCarrier, ivsZT, fps, textimgNamesZT, pr, flowPaneZT);
    }


    @FXML
    void TrueHind(ActionEvent event) {
        fps.clear();
        ivsOK.clear();
        flowPaneOK.getChildren().clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivs.isEmpty() || ivsZT.isEmpty()) {
            return;
        }

        System.out.println("运行中。。。。。");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("提示框：");
        alert.headerTextProperty().set("点击成功，正在运行中。。。。。");
        alert.show();

        if (imageHind.length != imageCarrier.length) {//如果隐藏图像和载体图像的不是一对一的就直接结束运行。
            Stage stage1 = (Stage) TrueHind.getScene().getWindow();
            try {
                utils.createTipsFrame(stage1, "隐藏图像和载体图像的数量不相等！\n 请重新选择。", 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        /*
              1.不用存储数组的方式，而是在执行一次运算就将载体图像的像素值替换。
              2.直接用最后的三个值的bit值的序号就像运算
              3.将运算的结果与需要隐藏的bit值进行比对。
              4.隐藏的步骤写出来，按照规定好的算法。

              代码的思路：
              1.用第几幅图像作为最外层循环，一次循环就是隐藏好一张图像。
              2.将该大循环的图像的像素值读取出来，那么就是以图像的宽高作为循环的结束判断条件，但是累加的条件写在for循环的内部。
              3.将三个读取到的像数值将后三位进行运算以此来获得Cn的值。
              4.将获得的Cn的值与需要隐藏的图像的

         */
        ControllerHindImageMy1 number1 = new ControllerHindImageMy1();//创建一个对象
        for (int theNumberPicture = 0; theNumberPicture < imageHind.length; theNumberPicture++) {
            // 获得隐藏图片的宽高。
            int widthHind = (int) imageHind[theNumberPicture].getWidth();
            int heightHind = (int) imageHind[theNumberPicture].getHeight();
            // 获得载体图像的宽高。
            int widthCarrier = (int) imageCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            PixelReader pixelReaderHind = imageHind[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。
            PixelReader pixelReaderCarrier = imageCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅载体图像的像素值。
            //写入像素值。
            WritableImage wi[] = new WritableImage[imageCarrier.length]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(imageCarrier[theNumberPicture].getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            PixelWriter pw[] = new PixelWriter[imageCarrier.length];//创建读取image的数组对象。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            int iCarrier = 0;//依次读取载体图像的像素值的坐标值。
            int jCarrier = 0;
            int iHind = 0;//隐藏图像的坐标值坐标值。
            int jHind = 0;
            int bit = 7;
            int intColorHind = 16;
            boolean flag = true;
            while (flag) {
                int[][] matrix = new int[number1.matrixSize][number1.matrixSize];//运算的矩阵，也就是载体图像中取三个像素值的最后三个bit值组成的。
                for (int xi = 0; xi < number1.matrixSize; xi++) { //遍历一个图像的
                    int rgbCarrier = (pixelReaderCarrier.getArgb(iCarrier, jCarrier));
                    for (int xj = 0, intColorCarrier = 16; xj < number1.matrixSize; xj++, intColorCarrier -= 8) {
                        matrix[xi][xj] = (rgbCarrier >> intColorCarrier) & 0xff;
                    }
                }
                int[] Cn = new int[6]; //异或运算之后的值
                int[] Sn = new int[6]; // 需要隐藏的bit值。所有的bit值为 0 。

                for (int num = 0; num < 6; num++) { //获得需要隐藏的bit值。
                    int rgbeHind = (pixelReaderHind.getArgb(iHind, jHind));
                    int rgbDecimalHind = (rgbeHind >> intColorHind) & 0xff;
                    Sn[num] = (rgbDecimalHind >>> bit--) & 1;
                    if (bit == -1) {
                        intColorHind -= 8;
                        if (intColorHind == -8) {
                            jHind++;
                            if (jHind == widthHind) {
                                jHind = 0;
                                iHind++;
                                if (iHind == heightHind) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                int maxMn = 0;
                int mini = 0;
                int minj = 0;
                int minNn = 0;
                //将Cn与Sn进行比较。
                while (flag) {
                    Cn[0] = matrix[2][0] ^ matrix[1][1] ^ matrix[0][2];
                    Cn[1] = matrix[0][0] ^ matrix[0][1] ^ matrix[0][2];
                    Cn[2] = matrix[1][0] ^ matrix[1][1] ^ matrix[1][2];
                    Cn[3] = matrix[2][0] ^ matrix[2][1] ^ matrix[2][2];
                    Cn[4] = matrix[0][0] ^ matrix[1][1] ^ matrix[2][2];
                    Cn[5] = matrix[0][2] ^ matrix[1][2] ^ matrix[2][2];
                    int Nn[][] = {{2, 1, 3}, {1, 3, 2}, {2, 1, 3}};//算法3*3的矩阵中每一个元素被线经过的数量。
                    int Mn[][] = new int[number1.matrixSize][number1.matrixSize];//记录3*3中每一个元素被不满足要求的线经过的数量。
                    for (int i = 0; i < 6; i++) {
                        if (Cn[i] != Sn[i]) {//如果不相等，那么就是不满足要求。
                            switch (i) {
                                case 0:
                                    Mn[2][0]++;
                                    Mn[1][1]++;
                                    Mn[0][2]++;
                                    Nn[2][0]--;
                                    Nn[1][1]--;
                                    Nn[0][2]--;
                                    break;
                                case 1:
                                    Mn[0][0]++;
                                    Mn[0][1]++;
                                    Mn[0][2]++;
                                    Nn[0][0]--;
                                    Nn[0][1]--;
                                    Nn[0][2]--;
                                    break;
                                case 2:
                                    Mn[1][0]++;
                                    Mn[1][1]++;
                                    Mn[1][2]++;
                                    Nn[1][0]--;
                                    Nn[1][1]--;
                                    Nn[1][2]--;
                                    break;
                                case 3:
                                    Mn[2][0]++;
                                    Mn[2][1]++;
                                    Mn[2][2]++;
                                    Nn[2][0]--;
                                    Nn[2][1]--;
                                    Nn[2][2]--;
                                    break;
                                case 4:
                                    Mn[0][0]++;
                                    Mn[1][1]++;
                                    Mn[2][2]++;
                                    Nn[0][0]--;
                                    Nn[1][1]--;
                                    Nn[2][2]--;
                                    break;
                                case 5:
                                    Mn[0][2]++;
                                    Mn[1][2]++;
                                    Mn[2][2]++;
                                    Nn[0][2]--;
                                    Nn[1][2]--;
                                    Nn[2][2]--;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    maxMn = 0;
                    mini = 0;
                    minj = 0;
                    minNn = 0;

                    for (int xi = 0; xi < number1.matrixSize; xi++) {
                        for (int xj = 0; xj < number1.matrixSize; xj++) {
                            if (maxMn != Mn[xi][xj]) { //如果与Mn最大值不相同的元素的Mn的值就处理为0
                                Mn[xi][xj] = 0;
                            }
                        }
                    }
                    int num = 0;
                    for (int xi = 0; xi < number1.matrixSize; xi++) {
                        for (int xj = 0; xj < number1.matrixSize; xj++) {
                            if (Mn[xi][xj] != 0) {
                                if (num == 0) minNn = Nn[xi][xj];
                                if (Nn[xi][xj] < minNn) {
                                    minNn = Nn[xi][xj];
                                }
                                num++;
                            }
                        }
                    }
                    boolean flag1 = true;
                    for (int xi = 0; xi < number1.matrixSize; xi++) {
                        if (flag1 == false) break;
                        for (int xj = 0; xj < number1.matrixSize; xj++) {
                            if (Mn[xi][xj] != 0) {
                                if (minNn == Nn[xi][xj]) {
                                    mini = xi;
                                    minj = xj;
                                    flag1 = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (Mn[0][0] + Mn[0][1] + Mn[0][2] + Mn[1][0] + Mn[1][1] + Mn[1][2] + Mn[2][0] + Mn[2][1] + Mn[2][2] == 0) {
                        flag = false;
                    } else {
                        if (matrix[mini][minj] == 1) {
                            matrix[mini][minj] = 0;
                            System.out.print("改 （" + mini + " , " + minj + " )" + "\t");
                        } else {
                            matrix[mini][minj] = 1;
                            System.out.print("改 （" + mini + " , " + minj + " )" + "\t");
                        }
                    }
                }

            }

            System.out.println("运行结束！");
            alert.close();
        }
    }

    public static void fillCarrier(int mini, int minj, int[][] Cn, int rgbDecimalCarrier) {
        if (minj == 2) {
            if (Cn[mini][minj] == 1) {
                rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFE));
            } else rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFE) | 1);
        } else if (minj == 1) {
            if (Cn[mini][minj] == 1) {
                rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFD));
            } else rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFD) | 2);
        } else if (minj == 0) {
            if (Cn[mini][minj] == 1) {
                rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFB));
            } else rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFB) | 4);
        }
    }

    public static ArrayList arrAdd(Map map1, Map map2, Map map3) {
        ArrayList<Map> arr = new ArrayList<>();
        arr.add(map1);
        arr.add(map2);
        arr.add(map3);
        return arr;
    }

    public static HashMap mapAdd(int key, int vaule1, int vaule2) {
        HashMap<Integer, int[]> map = new HashMap<>();
        int[] vaule = new int[2];
        vaule[0] = vaule1;
        vaule[1] = vaule2;
        map.put(key, vaule);
        return map;
    }

    public static int Bit(PixelReader pixelReader, int iCarrier, int jCarrier, int intColorCarrier, int bit) {
        int bitNum = ((((pixelReader.getArgb(iCarrier, jCarrier)) >> intColorCarrier) & 0xff) >>> bit) & 1; // 与1&取最低位，并保证最低位为0或1(得到隐藏byte对应的bit值。）
        return bitNum;
    }
}
