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

public class ControllerHindImageMyMDP1 {
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

    public static BufferedImage bufHindView[]; //用于显示隐藏图像。

    public static BufferedImage bufHindRead[]; //用于读取隐藏图像。

    public static BufferedImage bufCarrierView[]; //用于显示载体图像。

    public static BufferedImage bufCarrierRead[]; //用于显示载体图像。

    public static BufferedImage bufSave1[]; //用于写入秘密信息的载体图像以及保存下来。

    public static BufferedImage bufSave2[]; //用于写入秘密信息的载体图像以及保存下来。

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOK = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOKk = new ArrayList<ImageView>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    public static ArrayList<Text> textimgNamesZT = new ArrayList<Text>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Integer> sss = new ArrayList<Integer>();
    public static ArrayList<Integer> sss1 = new ArrayList<Integer>();
    public static ArrayList<Integer> sss2 = new ArrayList<Integer>();
    public static ArrayList<Integer> sss3= new ArrayList<Integer>();



//    List<File> listfile;

    ScheduledService<Double> sche;

    @FXML
    void HFtoImageSave(ActionEvent event) throws IOException {
        if (ivsOK.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave1(1);
        File file1 = Main.imageSave1(2);
        ArrayList<String> filename1 = new ArrayList<String>();
        ArrayList<String> filename2 = new ArrayList<String>();
        for (int i = 0; i < ivsOK.size() / 2; i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 5, "_" + i);
            filename1.add(sb.toString());
            StringBuffer sb1 = new StringBuffer(file1.getAbsolutePath());
            sb1.insert(sb1.length() - 5, "_" + i);
            filename2.add(sb1.toString());
            ImageIO.write(bufSave1[i], "tiff", new File(filename1.get(i)));
            ImageIO.write(bufSave2[i], "tiff", new File(filename2.get(i)));
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

        ArrayList<String> filename1 = new ArrayList<String>();
        ArrayList<String> filename2 = new ArrayList<String>();
        for (int i = 0; i < ivsOK.size() / 2; i++) {
            StringBuffer sb1 = new StringBuffer("C:/Users/ASUS/Desktop/默认1.tiff");
            sb1.insert(sb1.length() - 5, "_" + i);
            filename1.add(sb1.toString());
            StringBuffer sb2 = new StringBuffer("C:/Users/ASUS/Desktop/默认2.tiff");
            sb2.insert(sb2.length() - 5, "_" + i);
            filename2.add(sb2.toString());
            ImageIO.write(bufSave1[i], "tiff", new File(filename1.get(i)));
            ImageIO.write(bufSave2[i], "tiff", new File(filename2.get(i)));
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
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png", "*.tif", "*.tiff"));
            listfileYZ = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfileYZ == null) {
                return;
            }
            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        bufHindView = new BufferedImage[listfileYZ.size()];
        bufHindRead = new BufferedImage[listfileYZ.size()];
        pr = new PixelReader[listfileYZ.size()];
        //调用文件读取并显示函数
        Main.fileShow1(listfileYZ, bufHindRead, bufHindRead, bufHindRead, bufHindView, ivs, fps, textimgNames, pr, flowPaneYZ);
    }

    @FXML
    void buttZTChoose(ActionEvent event) throws IOException {
        fps.clear();
        ivsZT.clear();
        flowPaneZT.getChildren().clear();

        List<File> listfile = null;
        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            Stage stage = new Stage(); //创建一个场景
            FileChooser fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片多选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png", "*.tif", "*.tiff"));
            listfile = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfile == null) {
                return;
            }

            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        bufSave1 = new BufferedImage[listfile.size()];
        bufSave2 = new BufferedImage[listfile.size()];
        bufCarrierView = new BufferedImage[listfile.size()];
        bufCarrierRead = new BufferedImage[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow1(listfile, bufSave1, bufSave2, bufCarrierRead, bufCarrierView, ivsZT, fps, textimgNamesZT, pr, flowPaneZT);
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

        if (bufCarrierView.length != bufHindView.length) {//如果隐藏图像和载体图像的不是一对一的就直接结束运行。
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
        int num = 0;
        int numHind = 0;
        for (int theNumberPicture = 0; theNumberPicture < bufCarrierView.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得隐藏图片的宽高。
            int widthHind = (int) ivs.get(theNumberPicture).getImage().getWidth();
            int heightHind = (int) ivs.get(theNumberPicture).getImage().getHeight();

            // 获得载体图像的宽高。
            int widthCarrier = (int) ivsZT.get(theNumberPicture).getImage().getWidth();
            int heightCarrier = (int) ivsZT.get(theNumberPicture).getImage().getHeight();
            System.out.println("原载体图像的宽 = " + widthCarrier);
            System.out.println("原载体图像的高 = " + heightCarrier);

            //写入像素值。
            //步骤一 ：将原载体图像复制两份
            imageCarrierNumber1 = number++; // 复制的载体图像1
            imageCarrierNumber2 = number++; // 复制的载体图像2
            WritableImage wi[] = new WritableImage[bufCarrierView.length * 2]; // 创建写入image的数组对象。
            // 复制的载体图像1
//            wi[imageCarrierNumber1] = new WritableImage(ivsZT.get(theNumberPicture).getImage().getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
//            pw[imageCarrierNumber1] = wi[imageCarrierNumber1].getPixelWriter();//调用图像写入函数。
            // 复制的载体图像2
//            wi[imageCarrierNumber2] = new WritableImage(ivsZT.get(theNumberPicture).getImage().getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
//            pw[imageCarrierNumber2] = wi[imageCarrierNumber2].getPixelWriter();//调用图像写入函数。

            int carrierHomei = 0;
            int carrierHomej = 0;
            int carrierEndi = 0;
            int carrierEndj = 0;
            int hindHomei = 0;
            int hindHomej = 0;
            int bitNumber = 7;// 二进制位数。
            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            boolean flag = true;
            while (flag) {

                //步骤三 ：将像素对分别命名为：x，y。
                int x = (bufCarrierRead[theNumberPicture].getRGB(carrierHomej, carrierHomei)) & 0xff; // 将获得rgb值转为十进制。
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }
//                if (num < 10 && carrierHomei == 1 && carrierHomej >= 495) {
//                    System.out.println("x = " + x);
//                }

                int y = (bufCarrierRead[theNumberPicture].getRGB(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }
//                if (num < 10 && carrierHomei == 1 && carrierHomej >= 496) {
//                    System.out.println("y = " + y);
//                }

                //步骤四 ：计算两个像素对的差值。
                int d = Math.abs(x - y);

                //步骤五 ：通过差值d，查询差值范围表确定模函数的模a。
                int a = TableOfDifferenceRanges(d);
                int aa = (int) Math.ceil((a - 1) * 1.0 / 4);
                if (x < aa || x > (255 - aa) || y < aa || y > (255 - aa)) {
                    int colorValuex = x | x << 8 | x << 16;
                    bufSave1[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuex);
                    bufSave2[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuex);
                    carrierEndj++;
                    if (carrierEndj == widthCarrier) {
                        carrierEndj = 0;
                        carrierEndi++;
                    }
                    if (carrierEndi == heightCarrier) break;
                    int colorValuey = y | y << 8 | y << 16;
                    bufSave1[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuey);
                    bufSave2[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuey);
                    carrierEndj++;
                    if (carrierEndj == widthCarrier) {
                        carrierEndj = 0;
                        carrierEndi++;
                    }
                    if (carrierEndi == heightCarrier) break;
                    continue;
                }

                //步骤六 ：通过模函数的模a ，通过指数函数计算得到嵌入信息位数b。
                int b = (int) ((Math.log(a * a) / Math.log(2)));

//                if (num < 5 && carrierHomei == 1 && carrierHomej >= 496) {
//                    System.out.println("b = " + b);
//                    numHind++;
//                }

                //步骤七 ：已知嵌入信息数位数b，转化为十进制B。
                int B = 0;// 是ei 位比特流的十进制值。
                for (int k = 1; k <= b; k++) {
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
//                        if (numHind < 5 && numHind > 1) {
//                            System.out.println("hindPixel = " + ((bufHindRead[theNumberPicture].getRGB(hindHomej, hindHomei) & 0xff)));
//                        }
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
                    int bit = ((bufHindRead[theNumberPicture].getRGB(hindHomej, hindHomei) & 0xff) >>> bitNumber--) & 1; // 获得字节中具体下标的二进制值。
                    B += bit * Math.pow(2, b - k);
//                    if (numHind < 5 && numHind > 1) {
//                        System.out.println("循环 bitNumber = " + bitNumber);
//                        System.out.println("循环 hindPixel = " + ((bufHindRead[theNumberPicture].getRGB(hindHomej, hindHomei) & 0xff)));
//                        System.out.println("循环 bit = " + bit);
//                        System.out.println("循环 B = " + B);
//                    }
                }

                //步骤八 ：通过模函数公式改变原像素值对的值,得到像素对的修改量。
                int ci = 0;
                int cj = 0;
                boolean flag1 = true;
                for (int j = (-(a - 1)) / 2; j <= (a - 1) / 2; j++) {
                    if (flag1 == false) break;
                    for (int i = (-(a - 1)) / 2; i <= (a - 1) / 2; i++) {
                        if ((Math.floorMod(((x + i) + a * (y + j)), a * a) == B)) {
                            cj = j;
                            ci = i;
                            flag1 = false;
                            break;
                        }
                    }
                }
//                if (num < 5 && carrierHomei == 1 && carrierHomej >= 496) {
//                    System.out.println("bitNumber = " + bitNumber);
//                    System.out.println("hindPixel = " + ((bufHindRead[theNumberPicture].getRGB(hindHomej, hindHomei) & 0xff)));
//                    System.out.println("B = " + B);
//                    System.out.println("ci = " + ci);
//                    System.out.println("cj = " + cj);
//                }

                //步骤九 ：将得到的修改量使用平均法分别写入两幅载体图像中。
                int newx1 = x - (int) Math.floor((ci * 1.0) / 2.0);
                int newx2 = x + (int) Math.ceil((ci * 1.0) / 2.0);
                sss.add(newx1);
                sss1.add(newx2);
                int colorValuex1 = newx1 | newx1 << 8 | newx1 << 16;
                int colorValuex2 = newx2 | newx2 << 8 | newx2 << 16;
                bufSave1[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuex1);
                bufSave2[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuex2);
//                if (num < 10 && carrierHomei == 1 && carrierHomej >= 496) {
//                    System.out.println("newx1 = " + newx1);
//                    System.out.println("newx2 = " + newx2);
//                }
                carrierEndj++;
                if (carrierEndj == widthCarrier) {
                    carrierEndj = 0;
                    carrierEndi++;
                }
                if (carrierEndi == heightCarrier) break;
                int newy1 = y - (int) Math.floor((cj * 1.0) / 2.0);
                int newy2 = y + (int) Math.ceil((cj * 1.0) / 2.0);
                sss.add(newy1);
                sss1.add(newy2);

                int colorValuey1 = newy1 | newy1 << 8 | newy1 << 16;
                int colorValuey2 = newy2 | newy2 << 8 | newy2 << 16;
                bufSave1[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuey1);
                bufSave2[theNumberPicture].setRGB(carrierEndj, carrierEndi, colorValuey2);
//                if (num < 10 && carrierHomei == 1 && carrierHomej >= 496) {
//                    System.out.println("newy1 = " + newy1);
//                    System.out.println("newy2 = " + newy2);
//                    num++;
//                }
                carrierEndj++;
                if (carrierEndj == widthCarrier) {
                    carrierEndj = 0;
                    carrierEndi++;
                }
                if (carrierEndi == heightCarrier - 20) {
                    if (hindHomei < heightHind - 1) {
                        Stage stage1 = (Stage) TrueHind.getScene().getWindow();
                        try {
                            utils.createTipsFrame(stage1, "载体图像太小无法完全隐藏数据！", 0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    break;
                }
            }
//            //隐藏重要数据的方式：
////            HindInf(widthHind, heightHind, widthCarrier, heightCarrier, pixelReaderCarrier, pw);
//            ArrayList<Integer> recoverNecessaryInf = new ArrayList<Integer>();//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
//            recoverNecessaryInf.add(widthHind); // 1. 隐藏图像的宽
//            recoverNecessaryInf.add(heightHind); // 2. 隐藏图像的高
//            recoverNecessaryInf.add(carrierHomei); // 3. 载体图像结束位的y轴坐标值
//            recoverNecessaryInf.add(carrierHomej); // 4. 载体图像结束位的x轴坐标值。
//            recoverNecessaryInf.add(0); // 5.最后剩余的颜色的像素值个数。
//            int iCarrierLast = widthCarrier - 20;// 载体图像最后坐标像素值的x，y轴值。
//            int jCarrierLast = heightCarrier - 1;
//            int intColorCarrierLast = 16;
//            ArrayList<Integer> NumCarrierLast = new ArrayList<Integer>();//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
//            for (int theNumRecoverNecInf = 0; theNumRecoverNecInf < 5; theNumRecoverNecInf++) {//循环遍历列表recoverNecessaryInf中的元素。
//                for (int bit = 11; bit >= 0; bit--) {
//                    int b = (recoverNecessaryInf.get(theNumRecoverNecInf) >>> bit) & 1; // 与1&取最低位，并保证最低位为0或1
//                    int DecimalCarrierLast = ((pixelReaderCarrier.getArgb(iCarrierLast, jCarrierLast)) >> intColorCarrierLast) & 0xff;//判断进行一个像素值中的第几个rgb进行进制换算。
//                    NumCarrierLast.add((DecimalCarrierLast & 0xFFFFFFFE) | b);// 用逻辑运算，替换载体图像的最低有效位的值。
//                    intColorCarrierLast -= 8;
//                    if (NumCarrierLast.size() == 3) {
//                        pw[theNumberPicture].setColor(iCarrierLast, jCarrierLast, Color.rgb(NumCarrierLast.get(0), NumCarrierLast.get(1), NumCarrierLast.get(2))); // 写入隐藏有秘密的像素值替换原来的载体图像的值。
//                        iCarrierLast++;
//                        intColorCarrierLast = 16;
//                        NumCarrierLast.clear();
//                    }
//                }
//            }

            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(imageCarrierNumber1).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(imageCarrierNumber1).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(imageCarrierNumber1).setImage(SwingFXUtils.toFXImage(bufSave1[theNumberPicture], wi[theNumberPicture]));//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneOK.getChildren().add(ivsOK.get(imageCarrierNumber1));//将imageview的集合放入根节点中，显示出来。\
            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(imageCarrierNumber2).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(imageCarrierNumber2).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(imageCarrierNumber2).setImage(SwingFXUtils.toFXImage(bufSave2[theNumberPicture], wi[theNumberPicture]));//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
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
            System.out.println("载体图像的宽 = " + widthCarrier);
            System.out.println("载体图像的高 = " + heightCarrier);

            // 步骤一 ：获得两幅载体图像，读取像素值。
//            PixelReader pixelReaderCarrier1 = ivsOK.get(imageCarrierNumber1).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。
//            PixelReader pixelReaderCarrier2 = ivsOK.get(imageCarrierNumber2).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。
//            PixelReader pixelReaderHind = imageHind[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。

//            int[] recoverNecessaryInf = RecoverInf(widthCarrier, heightCarrier, pixelReaderCarrier1, pixelReaderCarrier2);//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
//            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier1);
//            int ShadowImageWidth = recoverNecessaryInf[0]; //影子图像的宽高。
//            int ShadowImageHeight = recoverNecessaryInf[1];
            int ShadowImageWidth = 128; //影子图像的宽高。
            int ShadowImageHeight = 128;
            System.out.println("恢复时重要的宽 = " + ShadowImageWidth);
            System.out.println("恢复时重要的高 = " + ShadowImageHeight);
            BufferedImage imaHindOri = new BufferedImage(ShadowImageWidth, ShadowImageHeight, BufferedImage.TYPE_INT_RGB);

            //写入像素值。
            WritableImage wi[] = new WritableImage[ivsOK.size() / 2]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(ShadowImageWidth, ShadowImageHeight);// 在载体图像中写入新的像素值。
//            PixelWriter pw[] = new PixelWriter[ivsOK.size() / 2];//创建读取image的数组对象。
//            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。
            int carrierHomei = 0;
            int carrierHomej = 0;
            int hindHomei = 0;
            int hindHomej = 0;
            int bitNumber = 7;// 二进制位数。
            int num = 0;
            boolean flag = true;
            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            while (flag) {
                //步骤三 ：将像素对分别命名为：x，y。
                if (carrierHomei == heightCarrier) break;
                int x1 = (bufSave1[theNumberPicture].getRGB(carrierHomej, carrierHomei)) & 0xff;
                int x2 = (bufSave2[theNumberPicture].getRGB(carrierHomej, carrierHomei)) & 0xff;
                if (x1 != sss.get(num) || x2 != sss1.get(num)) {

                    System.out.println("坐标x = " + carrierHomej);
                    System.out.println("坐标y = " + carrierHomei);
                    System.out.println("sssx1 = " + sss.get(num));
                    System.out.println("sssx2 = " + sss1.get(num));
                    System.out.println("x1 = " + x1);
                    System.out.println("x2 = " + x2);
                }
                num++;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }
                int y1 = (bufSave1[theNumberPicture].getRGB(carrierHomej, carrierHomei)) & 0xff;
                int y2 = (bufSave2[theNumberPicture].getRGB(carrierHomej, carrierHomei)) & 0xff;
                if (y1 != sss.get(num) || y2 != sss1.get(num)) {
                    System.out.println("sssy1 = " + sss.get(num));
                    System.out.println("sssy2 = " + sss1.get(num));
                    System.out.println("y1 = " + y1);
                    System.out.println("y2 = " + x2);
                }
                num++;

                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }

                //步骤四 ：计算像素对的修改量。
                int cx = x2 - x1;
                int cy = y2 - y1;

                //步骤五 ：使用平均法得到原像素值。
                int x = (x1 + x2) / 2;
                int y = (y1 + y2) / 2;
                if (x1 == x2 && y1 == y2) {
                    int d = Math.abs(x - y);
                    int a = TableOfDifferenceRanges(d);
                    int aa = (int) Math.ceil((a - 1) * 1.0 / 4);
                    if (x < aa || x > (255 - aa) || y < aa || y > (255 - aa)) {
                        continue;
                    }
                }

                //步骤六 ：计算原像素对的差值，然后查询差值范围表确定模函数的模a
                int d = Math.abs(x - y);
                int a = TableOfDifferenceRanges(d);
                int b = (int) (Math.log(a * a) / Math.log(2));

                //步骤七 ：计算得到嵌入信息之后的像素值，也就是f值
                int fx = x + cx;
                int fy = y + cy;

                //步骤八 ：将得到f值带入模函数公式得到嵌入信息的十进制B
                int B = Math.floorMod((fx + a * fy), a * a);

                //步骤九 ：将秘密信息B转化为b位二进制流。
                for (int k = 1; k <= b; k++) {
                    int remainder = B - (int) Math.pow(2, b - k);//对目标数字求余
                    if (remainder < 0) {
                        remainder = 0;
                    } else {
                        B = remainder;
                        remainder = 1;
                    }
                    pixelValues += remainder * Math.pow(2, bitNumber--); // 从小到大的计算2的幂次。
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
                        int colorValue = pixelValues | pixelValues << 8 | pixelValues << 16;
                        imaHindOri.setRGB(hindHomej, hindHomei, colorValue);
//                        if (pixelValues != (bufHindRead[theNumberPicture].getRGB(hindHomej, hindHomei) & 0xff) && num == 0) {
//                            System.out.println("载体图像x = " + carrierHomej);
//                            System.out.println("载体图像y = " + carrierHomei);
//                            num++;
//                        }
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
            flowPaneHFYuan.setAlignment(Pos.CENTER);
            ivsOKk.add(new ImageView());//创建一个imageview的对象。
            ivsOKk.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOKk.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOKk.get(theNumberPicture).setImage(SwingFXUtils.toFXImage(imaHindOri, wi[theNumberPicture]));//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneHFYuan.getChildren().add(ivsOKk.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
        }
    }

    public static int TableOfDifferenceRanges(int difference) {
        int a = 0; // 模函数的模a
        //判断差值在哪一个范围内
        if (difference >= 0 && difference <= 15) {
            a = 3;
        } else if (difference >= 16 && difference <= 31) {
            a = 5;
        } else if (difference >= 32 && difference <= 63) {
            a = 7;
        } else if (difference >= 64 && difference <= 127) {
            a = 11;
        } else if (difference >= 128 && difference <= 255) {
            a = 13;
        }
        return a;
    }

    public static void HindInf(int widthHind, int heightHind, int widthCarrier, int heightCarrier, PixelReader pixelReaderCarrier, PixelWriter pw[]) {
        int numinf = 0;
        int bitNumber = 14;
        int jCarrierLast = widthCarrier - 20;// 载体图像最后坐标像素值的x，y轴值。
        int iCarrierLast = heightCarrier - 1;
        int jCarrierend = widthCarrier - 20;// 载体图像最后坐标像素值的x，y轴值。
        int iCarrierend = heightCarrier - 1;
        int number = 0;
        int imageCarrierNumber1 = number++;
        int imageCarrierNumber2 = number++;
        ArrayList<Integer> recoverNecessaryInf = new ArrayList<Integer>();//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
        recoverNecessaryInf.add(widthHind);
        recoverNecessaryInf.add(heightHind);
        while (jCarrierLast != widthCarrier) {
            int x = (pixelReaderCarrier.getArgb(jCarrierLast, iCarrierLast)) & 0xff; // 将获得rgb值转为十进制。
            jCarrierLast++;
            int y = (pixelReaderCarrier.getArgb(jCarrierLast, iCarrierLast)) & 0xff;
            jCarrierLast++;

            int a = 3;
            int b = 3;
            int B = 0;// 是ei 位比特流的十进制值。
            for (int k = 1; k <= b; k++) {
                if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                    bitNumber = 14;
                    numinf++;
                }
                int bit = (recoverNecessaryInf.get(numinf) >>> bitNumber--) & 1; // 获得字节中具体下标的二进制值。
                B += bit * Math.pow(2, b - k);
            }

            //步骤八 ：通过模函数公式改变原像素值对的值,得到像素对的修改量。
            int ci = 0;
            int cj = 0;
            boolean flag1 = true;
            for (int j = (-(a - 1)) / 2; j <= (a - 1) / 2; j++) {
                if (flag1 == false) break;
                for (int i = (-(a - 1)) / 2; i <= (a - 1) / 2; i++) {
                    if ((Math.floorMod(((x + i) + a * (y + j)), a * a) == B)) {
                        cj = j;
                        ci = i;
                        flag1 = false;
                        break;
                    }
                }
            }
            //步骤九 ：将得到的修改量使用平均法分别写入两幅载体图像中。
            pw[imageCarrierNumber1].setColor(jCarrierend, iCarrierend, Color.grayRgb(x - (int) Math.floor((ci * 1.0) / 2.0)));
            pw[imageCarrierNumber2].setColor(jCarrierend, iCarrierend, Color.grayRgb(x + (int) Math.ceil((ci * 1.0) / 2.0)));
            jCarrierend++;
            pw[imageCarrierNumber1].setColor(jCarrierend, iCarrierend, Color.grayRgb(y - (int) Math.floor((cj * 1.0) / 2.0)));
            pw[imageCarrierNumber2].setColor(jCarrierend, iCarrierend, Color.grayRgb(y + (int) Math.ceil((cj * 1.0) / 2.0)));
            jCarrierend++;
        }
        System.out.println("隐藏时重要信息的宽 =" + recoverNecessaryInf.get(0));
        System.out.println("隐藏时重要信息的高 =" + recoverNecessaryInf.get(1));
    }

    public static int[] RecoverInf(int widthCarrier, int heightCarrier, PixelReader pixelReaderCarrier1, PixelReader pixelReaderCarrier2) {
        int bitNumber = 14;
        int Values = 0;
        int jCarrierLast = widthCarrier - 20;// 载体图像最后坐标像素值的x，y轴值。
        int iCarrierLast = heightCarrier - 1;
        int arrNum = 0;
        int[] recoverNecessaryInf = new int[2];//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
        //获得载体图像中隐藏的恢复隐藏图的重要坐标信息。
        while (jCarrierLast != widthCarrier) {
            //步骤三 ：将像素对分别命名为：x，y。
            int x1 = (pixelReaderCarrier1.getArgb(jCarrierLast, iCarrierLast)) & 0xff;
            int x2 = (pixelReaderCarrier2.getArgb(jCarrierLast, iCarrierLast)) & 0xff;
            jCarrierLast++;
            int y1 = (pixelReaderCarrier1.getArgb(jCarrierLast, iCarrierLast)) & 0xff;
            int y2 = (pixelReaderCarrier2.getArgb(jCarrierLast, iCarrierLast)) & 0xff;
            jCarrierLast++;

            //步骤四 ：计算像素对的修改量。
            int cx = x2 - x1;
            int cy = y2 - y1;

            //步骤五 ：使用平均法得到原像素值。
            int x = (x1 + x2) / 2;
            int y = (y1 + y2) / 2;

            int a = 3;
            int b = 3;
            int fx = x + cx;
            int fy = y + cy;
            int B = Math.floorMod((fx + a * fy), a * a);
            for (int k = 1; k <= b; k++) {
                int remainder = B - (int) Math.pow(2, b - k);//对目标数字求余
                if (remainder < 0) {
                    remainder = 0;
                } else {
                    B = remainder;
                    remainder = 1;
                }
                Values += remainder * Math.pow(2, bitNumber--); // 从小到大的计算2的幂次。
                if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                    bitNumber = 14;
                    recoverNecessaryInf[arrNum++] = Values;
                    Values = 0;
                }
            }
        }
        return recoverNecessaryInf;
    }
}
