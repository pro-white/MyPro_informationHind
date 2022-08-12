package javafxControllers.pictureProcess.reversible.hind;

import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerHindImagePKK {
    @FXML
    private Text textRun;

    @FXML
    private Button TrueHind;

    @FXML
    private FlowPane flowPaneZT;

    @FXML
    private ScrollPane ScrollPaneOK;

    @FXML
    private FlowPane flowPaneHFYuan;

    @FXML
    private FlowPane flowPaneOK;

    @FXML
    private VBox updatePane;

    @FXML
    private Button HFHind;

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
    private Text textZT1;

    @FXML
    private Text textZT11;

    @FXML
    private ScrollPane ScrollPaneZT;

    @FXML
    private Text textZT;

    @FXML
    private Button secretSaveYJ;

    Utils utils = new Utils();

    public static Image imageCarrier[];

    public static Image imageCarrierOk[];

    public static Image imageHind[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOK = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOKk = new ArrayList<ImageView>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    public static ArrayList<Text> textimgNamesZT = new ArrayList<Text>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    List<File> listfile;

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
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.jpg");
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
    void TrueHind(ActionEvent event) throws IOException {
        fps.clear();
        ivsOK.clear();
        flowPaneOK.getChildren().clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivs.isEmpty() || ivsZT.isEmpty()) {
            return;
        }

        if (imageHind.length != imageCarrier.length) {//如果隐藏图像和载体图像的不是一对一的就直接结束运行。
            Stage stage1 = (Stage) TrueHind.getScene().getWindow();
            try {
                utils.createTipsFrame(stage1, "隐藏图像和载体图像的数量不相等！\n 请重新选择。！", 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        int imageCarrierNumber2 = 0;
        int imageCarrierNumber1 = 0;
        int number = 0;

        for (int theNumberPicture = 0; theNumberPicture < imageHind.length; theNumberPicture++) { //按顺序获得用户选择的图像。
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

            imageCarrierNumber1 = number++; // 复制的载体图像1
            imageCarrierNumber2 = number++; // 复制的载体图像2
            WritableImage wi[] = new WritableImage[imageCarrier.length * 2]; // 创建写入image的数组对象。
            PixelWriter pw[] = new PixelWriter[imageCarrier.length * 2];//创建读取image的数组对象。
            // 复制的载体图像1
            wi[imageCarrierNumber1] = new WritableImage(imageCarrier[theNumberPicture].getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            pw[imageCarrierNumber1] = wi[imageCarrierNumber1].getPixelWriter();//调用图像写入函数。
            // 复制的载体图像2
            wi[imageCarrierNumber2] = new WritableImage(imageCarrier[theNumberPicture].getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            pw[imageCarrierNumber2] = wi[imageCarrierNumber2].getPixelWriter();//调用图像写入函数。

            int b1 = 3; // 确认使用的矩阵分块大小。
            int b2 = 3;
            int bitNumber = 7;// 二进制位数。
            int carrierHomei = 0;
            int carrierHomej = 0;
            int carrierEndi = 0;
            int carrierEndj = 0;
            int hindHomei = 0;
            int hindHomej = 0;

            //嵌入信息。
            //步骤一 ： 确定矩阵分块的大小,然后将其放入二维矩阵中。
            boolean flag = true;
            while (flag) {
                // 备份在这次循环使用的所有变量的值，以防后面如果跳过这一组。

                // 将载体图像的像素值分块。
                int[] p = new int[b1 * b2];
                for (int b1b2 = 0; b1b2 < b1 * b2; b1b2++) {
                    int pixelValue = (pixelReaderCarrier.getArgb(carrierHomej++, carrierHomei)) & 0xff;
                    if (carrierHomej == widthCarrier) {
                        carrierHomej = 0;
                        carrierHomei++;
                    }
                    if (carrierHomei == heightCarrier){
                        flag = false;
                        break;
                    }
                    if (pixelValue < 4 || pixelValue > 251) {
                        b1b2--;
                        continue;
                    }
                    p[b1b2] = pixelValue; // 将获得rgb值转为十进制。
                }
                if (flag == false){
                    System.out.println("不够");
                    break;
                }

                for (int i = 0; i < b1 * b2; i++) {

                    if (flag == false) break;
                    // 步骤二 ： 计算每个子块的每个元素的平均值 ，使用公式（3）
                    int max = 0;
                    for (int j = 1; j < b1 * b2; j++) {
                        if (j == i) continue;
                        if (max < Math.abs(p[i] - p[j])){
                            max = Math.abs(p[i] - p[j]);
                        }
                    }
                    int m = max;  // 公式（3）计算的到的 mi 值。

                    //步骤三 ： 获得每个 mi 可以确定的嵌入位数 ei。
                    int e = 0; // 由于Java中没有log为0的指数，所以需要分开，判断当 m = 0 时那么嵌入的位数是 1。
                    if (m == 1 || m == 0) {
                        e = 1; // 计算隐藏信息bit值的位数。
                    } else {
                        e = (int) (Math.log(m) / Math.log(2)); // 计算隐藏信息bit值的位数。
                    }

                    //步骤四 ： 将 ei 为比特流转换成十进制 bi，然后使用公式（5）得到 ki 和 k（i+1）。
                    int b = 0;// 是ei 位比特流的十进制值。
                    for (int k = 1; k <= e; k++) {
                        if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                            bitNumber = 7;
                            hindHomej++;
                            if (hindHomej == widthHind) {
                                hindHomei++;
                                hindHomej = 0;
                            }
                            if (hindHomei == heightHind) {
                                flag = false;
                                break;
                            }
                        }
                        int bit = ((pixelReaderHind.getArgb(hindHomej, hindHomei) & 0xff) >>> bitNumber--) & 1; // 获得字节中具体下标的二进制值。
                        b += bit * Math.pow(2, e - k);
                    }
                    int k1 = b / 2; // 向下取整得到 ki
                    int k2 = (int) Math.ceil(b * 1.0 / 2); // 向上取整得到 k（i+1）
                    if (carrierEndj == widthCarrier) {
                        carrierEndj = 0;
                        carrierEndi++;
                    }
                    int pixelValue = (pixelReaderCarrier.getArgb(carrierEndj, carrierEndi)) & 0xff;

                    int newPixelValues1 = pixelValue + k1;
                    int newPixelValues2 = pixelValue - k2;
                    if (newPixelValues1 > 255 || newPixelValues1 < 0 || newPixelValues2 > 255 || newPixelValues2 < 0) {
                        pw[imageCarrierNumber1].setColor(carrierEndj, carrierEndi, Color.grayRgb(pixelValue));
                        pw[imageCarrierNumber2].setColor(carrierEndj, carrierEndi, Color.grayRgb(pixelValue));
                        carrierEndj++;
                        if (carrierEndj == widthCarrier) {
                            carrierEndj = 0;
                            carrierEndi++;
                        }
                         continue;
                    }

                    pw[imageCarrierNumber1].setColor(carrierEndj, carrierEndi, Color.grayRgb(newPixelValues1));
                    pw[imageCarrierNumber2].setColor(carrierEndj, carrierEndi, Color.grayRgb(newPixelValues2));
                    carrierEndj++;

                    if (flag == false) {
                        break;
                    }
                }
            }
            ArrayList<Integer> recoverNecessaryInf = new ArrayList<Integer>();//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
            recoverNecessaryInf.add(widthHind); // 1. 隐藏图像的宽
            recoverNecessaryInf.add(heightHind); // 2. 隐藏图像的高
            recoverNecessaryInf.add(carrierHomei); // 3. 载体图像结束位的y轴坐标值
            recoverNecessaryInf.add(carrierHomej); // 4. 载体图像结束位的x轴坐标值。
            recoverNecessaryInf.add(0); // 5.最后剩余的颜色的像素值个数。
            int iCarrierLast = widthCarrier - 20;// 载体图像最后坐标像素值的x，y轴值。
            int jCarrierLast = heightCarrier - 1;
            int intColorCarrierLast = 16;
            ArrayList<Integer> NumCarrierLast = new ArrayList<Integer>();//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
            for (int theNumRecoverNecInf = 0; theNumRecoverNecInf < 5; theNumRecoverNecInf++) {//循环遍历列表recoverNecessaryInf中的元素。
                for (int bit = 11; bit >= 0; bit--) {
                    int b = (recoverNecessaryInf.get(theNumRecoverNecInf) >>> bit) & 1; // 与1&取最低位，并保证最低位为0或1
                    int DecimalCarrierLast = ((pixelReaderCarrier.getArgb(iCarrierLast, jCarrierLast)) >> intColorCarrierLast) & 0xff;//判断进行一个像素值中的第几个rgb进行进制换算。
                    NumCarrierLast.add((DecimalCarrierLast & 0xFFFFFFFE) | b);// 用逻辑运算，替换载体图像的最低有效位的值。
                    intColorCarrierLast -= 8;
                    if (NumCarrierLast.size() == 3) {
                        pw[theNumberPicture].setColor(iCarrierLast, jCarrierLast, Color.rgb(NumCarrierLast.get(0), NumCarrierLast.get(1), NumCarrierLast.get(2))); // 写入隐藏有秘密的像素值替换原来的载体图像的值。
                        iCarrierLast++;
                        intColorCarrierLast = 16;
                        NumCarrierLast.clear();
                    }
                }
            }

            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(imageCarrierNumber1).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(imageCarrierNumber1).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(imageCarrierNumber1).setImage(wi[imageCarrierNumber1]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneOK.getChildren().add(ivsOK.get(imageCarrierNumber1));//将imageview的集合放入根节点中，显示出来。\
            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(imageCarrierNumber2).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(imageCarrierNumber2).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(imageCarrierNumber2).setImage(wi[imageCarrierNumber2]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneOK.getChildren().add(ivsOK.get(imageCarrierNumber2));//将imageview的集合放入根节点中，显示出来。
        }
    }

    @FXML
    void HFHind(ActionEvent event) {

        int imageCarrierNumber2 = 0;
        int imageCarrierNumber1 = 0;
        int number = 0;
        int pixelValues = 0;
        for (int theNumberPicture = 0; theNumberPicture < ivsOK.size() / 2; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) ivsOK.get(theNumberPicture).getImage().getWidth();
            int heightCarrier = (int) ivsOK.get(theNumberPicture).getImage().getHeight();

            // 读取像素值。
            imageCarrierNumber1 = number++; // 复制的载体图像1
            imageCarrierNumber2 = number++; // 复制的载体图像2
            PixelReader pixelReaderCarrier1 = ivsOK.get(imageCarrierNumber1).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。
            PixelReader pixelReaderCarrier2 = ivsOK.get(imageCarrierNumber2).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。

            //获得载体图像中隐藏的恢复隐藏图的重要坐标信息。
            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier1);
            int ShadowImageWidth = recoverNecessaryInf[0]; //影子图像的宽高。
            int ShadowImageHeight = recoverNecessaryInf[1];

            //写入像素值。
            WritableImage wi[] = new WritableImage[ivsOK.size() / 2]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(ShadowImageWidth, ShadowImageHeight);// 在载体图像中写入新的像素值。
            PixelWriter pw[] = new PixelWriter[ivsOK.size() / 2];//创建读取image的数组对象。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            int b1 = 3; // 确认使用的矩阵分块大小。
            int b2 = 3;
            int bitNumber = 7;// 二进制位数。
            int carrierHomei = 0;
            int carrierHomej = 0;
            int hindHomei = 0;
            int hindHomej = 0;

            boolean flag = true;
            while (flag) {
                int[] p = new int[b1 * b2];
                int[] p1 = new int[b1 * b2];
                int[] p2 = new int[b1 * b2];
                for (int b1b2 = 0; b1b2 < b1 * b2; b1b2++) {
                    p1[b1b2] = (pixelReaderCarrier1.getArgb(carrierHomej, carrierHomei)) & 0xff; // 将获得rgb值转为十进制。
                    p2[b1b2] = (pixelReaderCarrier2.getArgb(carrierHomej++, carrierHomei)) & 0xff;
                    if (carrierHomej == widthCarrier) {
                        carrierHomej = 0;
                        carrierHomei++;
                    }
                    if (recoverNecessaryInf[2] == carrierHomei && recoverNecessaryInf[3] == carrierHomej) {
                        flag = false;
                        break;
                    }
                }

                for (int i = 0; i < b1 * b2; i++) {
                    int m = 0; // 每块的平均值 mi。
                    int sum = 0; // 求平均值中 mi的累加和。
                    //步骤一 ： 通过公式计算得到 m 的值
                    int averageValue2 = (int) Math.ceil((p1[i] + p2[i]) / 2.0); //向上取整。
                    for (int j = 0; j < b1 * b2; j++) {
                        if (i == j) continue;
                        sum += (int) Math.ceil((p1[j] + p2[j]) / 2.0);
                    }
                    m = Math.abs(averageValue2 - sum / (b1 * b2 - 1)); // abs是取绝对值的函数。
                    //步骤二 ：获得嵌入秘密信息bit位数 e。
                    int e = 0;
                    if (m == 0 || m == 1) {
                        e = 1; // 计算隐藏信息bit值的位数。
                    } else {
                        e = (int) (Math.log(m) / Math.log(2)); // 计算隐藏信息bit值的位数
                    }

                    //步骤三 ：通过两幅载体图像相同位置像素值进行平均值计算的原载体图像的像素值。
                    p[i] = (int) Math.ceil((p1[i] + p2[i]) / 2.0);// ！！！！！！注意：这里向上取整，那么除数或者被除数一定要有一个是小数。
                    //步骤四 ：通过两个载体图像减去原载体图像像素值的绝对值相加得到秘密信息的十进制 bi
                    int b = Math.abs(p1[i] - p[i]) + Math.abs(p2[i] - p[i]);

                    //步骤五 ：通过嵌入的秘密信息位数 e 和 比特流的值 b。得到隐藏的像素值。

                    for (int k = 1; k <= e; k++) {
                        int remainder = b - (int) Math.pow(2, e - k);//对目标数字求余
                        if (remainder < 0) {
                            remainder = 0;
                        } else {
                            b = remainder;
                            remainder = 1;
                        }
                        pixelValues += remainder * Math.pow(2, bitNumber--); // 从小到大的计算2的幂次。
                        if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                            bitNumber = 7;
                            pw[theNumberPicture].setColor(hindHomej, hindHomei, Color.grayRgb(pixelValues));
                            pixelValues = 0;
                            hindHomej++;
                            if (hindHomej == ShadowImageWidth) {
                                hindHomei++;
                                hindHomej = 0;
                            }
                            if (hindHomei == ShadowImageHeight && hindHomej == 0) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag == false) break;
                }
            }

            flowPaneHFYuan.setAlignment(Pos.CENTER);
            ivsOKk.add(new ImageView());//创建一个imageview的对象。
            ivsOKk.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOKk.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOKk.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneHFYuan.getChildren().add(ivsOKk.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
        }
    }
}

