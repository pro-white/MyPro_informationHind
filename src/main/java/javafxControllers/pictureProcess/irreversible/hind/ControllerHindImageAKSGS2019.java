package javafxControllers.pictureProcess.irreversible.hind;

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

public class ControllerHindImageAKSGS2019 {
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
    void TrueHind(ActionEvent event) {
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

        for (int theNumberPicture = 0; theNumberPicture < imageHind.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得隐藏图片的宽高。
            int widthHind = (int) imageHind[theNumberPicture].getWidth();
            int heightHind = (int) imageHind[theNumberPicture].getHeight();
            System.out.println("影子图像的宽 = " + widthHind);
            System.out.println("影子图像的高 = " + heightHind);

            // 获得载体图像的宽高。
            int widthCarrier = (int) imageCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            PixelReader pixelReaderHind = imageHind[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。
            PixelReader pixelReaderCarrier = imageCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅载体图像的像素值。
            //写入像素值。

            //步骤一 ：将原载体图像复制两份
            WritableImage wi[] = new WritableImage[imageCarrier.length * 2]; // 创建写入image的数组对象。
            PixelWriter pw[] = new PixelWriter[imageCarrier.length * 2];//创建读取image的数组对象。

            wi[theNumberPicture] = new WritableImage(imageCarrier[theNumberPicture].getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

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
                // 首先将图像的像素分成1*2不重叠的像素块。
                int px = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomej = 0;
                    carrierHomei++;
                }
                int px1 = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomej = 0;
                    carrierHomei++;
                }
                //步骤一 ：计算两个像素值的差值 d。
                int d = Math.abs(px - px1);

                //步骤二 ：通过差值 d去查表获得嵌入的信息的位数。
                int n = TableOfDifferenceRanges(d);

                //步骤三 ：将n位二进制流转化位十进制数 ： dec ;以及获得rem1 和rem2.
                int dec1 = 0;// 是ei 位比特流的十进制值。
                for (int k = 1; k <= n; k++) {
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
                        hindHomej++;
                        if (hindHomej == widthHind) {
                            hindHomei++;
                            hindHomej = 0;
                        }
                    }
                    if (hindHomei == heightHind) {
                        flag = false;
                        break;
                    }
                    int bit = ((pixelReaderHind.getArgb(hindHomej, hindHomei) & 0xff) >>> bitNumber--) & 1; // 获得字节中具体下标的二进制值。
                    dec1 += bit * Math.pow(2, n - k);
                }
                int dec2 = 0;
                for (int k = 1; k <= n; k++) {
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
                        hindHomej++;
                        if (hindHomej == widthHind) {
                            hindHomei++;
                            hindHomej = 0;
                        }
                    }
                    if (hindHomei == heightHind) {
                        flag = false;
                        break;
                    }
                    int bit = ((pixelReaderHind.getArgb(hindHomej, hindHomei) & 0xff) >>> bitNumber--) & 1; // 获得字节中具体下标的二进制值。
                    dec2 += bit * Math.pow(2, n - k);
                }
                int rem1 = Math.floorMod(px, (int) Math.pow(2, n));
                int rem2 = Math.floorMod(px1, (int) Math.pow(2, n));

                //步骤四 ：将 rem 和 dec 进行计算得到x 和 y
                int x1 = rem1 - dec1;
                int x2 = dec1 - rem1;
                int y1 = rem2 - dec2;
                int y2 = dec2 - rem2;

                //步骤五 ：将获得的 x 和 y 带入公式得到px‘ 和 px1’
                int pxnew = pxpx1New1(dec1, rem1, x1, x2, n, px);
                int pxnew1 = pxpx1New1(dec2, rem2, y1, y2, n, px1);

                //步骤六 ：计算获得新像素的新差值 d‘，然后通过查表如果在不同的区域的对其进行不同的像素值的操作。
                int dNew = Math.abs(pxnew - pxnew1);
                int nnew = TableOfDifferenceRanges(dNew);

                int[] pixelGroup = new int[2];
                if (nnew == 2) {
                    pixelGroup = obtainLastPixel1(nnew, pxnew, pxnew1);
                } else if (nnew == 3) {
                    pixelGroup = obtainLastPixel2(nnew, pxnew, pxnew1);
                } else {
                    pixelGroup = obtainLastPixel3(nnew, pxnew, pxnew1);
                }
                //步骤七 ：如果出现溢出则执行公式。
                if (pixelGroup[0] < 0 | pixelGroup[0] > 255 | pixelGroup[1] < 0 | pixelGroup[1] > 255) {
                    pixelGroup = obtainLastPixelDisposeFOBP(nnew, pixelGroup);
                }
                int d1 = Math.abs(pixelGroup[0] - pixelGroup[1]);

                //步骤二 ：通过差值 d去查表获得嵌入的信息的位数。
                int n1 = TableOfDifferenceRanges(d1);
                int[] rl = RL(n);

                while (n != n1) {
                    if (pixelGroup[0] < pixelGroup[1] & d1 > rl[1]) {
                        pixelGroup[1] -= rl[2];
                    } else if (pixelGroup[0] < pixelGroup[1] & d1 < rl[0]) {
                        pixelGroup[1] += rl[2];
                    } else if (pixelGroup[0] > pixelGroup[1] & d1 > rl[1]) {
                        pixelGroup[0] -= rl[2];
                    } else {
                        pixelGroup[0] += rl[2];
                    }
                    n = n1;
                }
                if (pixelGroup[0] < 0 | pixelGroup[0] > 255 | pixelGroup[1] < 0 | pixelGroup[1] > 255) {
                    pixelGroup = obtainLastPixelDisposeFOBP(nnew, pixelGroup);
                }
                pw[theNumberPicture].setColor(carrierEndj, carrierEndi, Color.grayRgb(pixelGroup[0]));
                carrierEndj++;
                if (carrierEndj == widthCarrier) {
                    carrierEndj = 0;
                    carrierEndi++;
                }
                pw[theNumberPicture].setColor(carrierEndj, carrierEndi, Color.grayRgb(pixelGroup[1]));
                carrierEndj++;
                if (carrierEndj == widthCarrier) {
                    carrierEndj = 0;
                    carrierEndi++;
                }
                if (carrierEndi == heightCarrier) break;
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
            System.out.println("结束啦");
            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneOK.getChildren().add(ivsOK.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。\
        }
    }

    @FXML
    void HFHind(ActionEvent event) {
        int pixelValues = 0;
        for (int theNumberPicture = 0; theNumberPicture < ivsOK.size(); theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) ivsOK.get(theNumberPicture).getImage().getWidth();
            int heightCarrier = (int) ivsOK.get(theNumberPicture).getImage().getHeight();
            System.out.println("载体图像的宽 = " + widthCarrier);
            System.out.println("载体图像的高 = " + heightCarrier);

            // 步骤一 ：获得两幅载体图像，读取像素值。
            PixelReader pixelReaderCarrier = ivsOK.get(theNumberPicture).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。
            PixelReader pixelReaderHind = imageHind[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。

            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier);
            int ShadowImageWidth = recoverNecessaryInf[0]; //影子图像的宽高。
            int ShadowImageHeight = recoverNecessaryInf[1];
            System.out.println("恢复时重要的宽 = " + ShadowImageWidth);
            System.out.println("恢复时重要的高 = " + ShadowImageHeight);

            //写入像素值。
            WritableImage wi[] = new WritableImage[ivsOK.size()]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(ShadowImageWidth, ShadowImageHeight);// 在载体图像中写入新的像素值。
            PixelWriter pw[] = new PixelWriter[ivsOK.size()];//创建读取image的数组对象。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            int carrierHomei = 0;
            int carrierHomej = 0;
            int hindHomei = 0;
            int hindHomej = 0;
            int bitNumber = 7;// 二进制位数。
            boolean flag = true;

            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            while (flag) {
                if (carrierHomei == heightCarrier) break;
                // 首先将图像的像素分成1*2不重叠的像素块。
                int px = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomej = 0;
                    carrierHomei++;
                }
                int px1 = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomej = 0;
                    carrierHomei++;
                }
                //步骤一 ：计算两个像素值的差值 d.
                int d = Math.abs(px - px1);
                int n = TableOfDifferenceRanges(d);

                //步骤二 ：通过差值 d，判断在范围表中的位置，然后获得rem。
                int rem1 = Math.floorMod(px, (int) Math.pow(2, n));
                int rem2 = Math.floorMod(px1, (int) Math.pow(2, n));


                for (int k = 1; k <= n; k++) {
                    int remainder = rem1 - (int) Math.pow(2, n - k);//对目标数字求余
                    if (remainder < 0) {
                        remainder = 0;
                    } else {
                        rem1 = remainder;
                        remainder = 1;
                    }
//                System.out.println("remainder1 = " +remainder);
                    pixelValues += remainder * Math.pow(2, bitNumber--); // 从小到大的计算2的幂次。
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
                        pw[theNumberPicture].setColor(hindHomej, hindHomei, Color.grayRgb(pixelValues));
//                    System.out.println("pixelValues1 = " +pixelValues);
                        pixelValues = 0;
                        hindHomej++;
                        if (hindHomej == ShadowImageWidth) {
                            hindHomei++;
                            hindHomej = 0;
                        }
                        if (hindHomei == ShadowImageHeight) {
                            flag = false;
                            break;
                        }
                    }
                }
                for (int k = 1; k <= n; k++) {
                    int remainder = rem2 - (int) Math.pow(2, n - k);//对目标数字求余
                    if (remainder < 0) {
                        remainder = 0;
                    } else {
                        rem2 = remainder;
                        remainder = 1;
                    }
//                System.out.println("remainder2 = " +remainder);
                    pixelValues += remainder * Math.pow(2, bitNumber--); // 从小到大的计算2的幂次。
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
                        pw[theNumberPicture].setColor(hindHomej, hindHomei, Color.grayRgb(pixelValues));
//                    System.out.println("pixelValues2 = " +pixelValues);
                        pixelValues = 0;
                        hindHomej++;
                        if (hindHomej == ShadowImageWidth) {
                            hindHomei++;
                            hindHomej = 0;
                        }
                        if (hindHomei == ShadowImageHeight) {
                            flag = false;
                            break;
                        }
                    }
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
//
//    public static int TableOfDifferenceRanges(int difference) {
//        int n = 0; // 模函数的模a
//        //判断差值在哪一个范围内
//        if (difference >= 0 && difference <= 15) {
//            n = 2;
//        } else if (difference >= 16 && difference <= 31) {
//            n = 3;
//        } else if (difference >= 32 && difference <= 255) {
//            n = 4;
//        }
//        return n;
//    }
//
//    public static int[] TableOfDifferenceRangesGetNumber(int n, int px, int px1) {
//        int[] rem = new int[2];
//        int rem1 = 0;
//        int rem2 = 0;
//        if (n == 2) {
//            rem1 = Math.floorMod(px, 4);
//            rem2 = Math.floorMod(px1, 4);
//        } else if (n == 3) {
//            rem1 = Math.floorMod(px, 8);
//            rem2 = Math.floorMod(px1, 8);
//        } else if (n == 4) {
//            rem1 = Math.floorMod(px, 16);
//            rem2 = Math.floorMod(px1, 16);
//        }
//        rem[0] = rem1;
//        rem[1] = rem2;
//        return rem;
//    }
//
//    public static int pxpx1New1(int dec, int rem, int xy1, int xy2, int n, int px) {
//        int pxnew = 0;
//        int z = 0;
//        if (rem < dec && Math.abs(xy1) < Math.pow(2, n - 1)) {
//            pxnew = px - xy1;
//        } else if (rem > dec && Math.abs(xy2) < Math.pow(2, n - 1)) {
//            pxnew = px + xy2;
//        } else if (rem < dec && Math.abs(xy1) >= Math.pow(2, n - 1)) {
//            z = (int) Math.pow(2, n) + xy1;
//            pxnew = px - z;
//        } else if (rem > dec && Math.abs(xy2) >= Math.pow(2, n - 1)) {
//            z = (int) Math.pow(2, n) + xy2;
//            pxnew = px + z;
//        } else {
//            pxnew = px;
//        }
//        return pxnew;
//    }
//
//    public static int[] obtainLastPixel1(int n, int pxnew, int pxnew1) {
//        int[] pixelGroup = new int[2];
//        if (n == 2) {
//            pixelGroup[0] = pxnew;
//            pixelGroup[1] = pxnew1;
//        } else if (n == 3 && pxnew >= pxnew1) {
//            pixelGroup[0] = pxnew - (int) Math.pow(2, n);
//            pixelGroup[1] = pxnew1 + (int) Math.pow(2, n);
//        } else {
//            pixelGroup[0] = pxnew + (int) Math.pow(2, n);
//            pixelGroup[1] = pxnew1 - (int) Math.pow(2, n);
//        }
//        return pixelGroup;
//    }
//
//    public static int[] obtainLastPixel2(int n, int pxnew, int pxnew1) {
//        int[] pixelGroup = new int[2];
//        if (n == 3) {
//            pixelGroup[0] = pxnew;
//            pixelGroup[1] = pxnew1;
//        } else if ((n == 2 && pxnew >= pxnew1) || (n == 4 && pxnew < pxnew1)) {
//            pixelGroup[0] = pxnew + (int) Math.pow(2, n);
//            pixelGroup[1] = pxnew1 - (int) Math.pow(2, n);
//        } else if ((n == 2 && pxnew < pxnew1) || (n == 4 && pxnew >= pxnew1)) {
//            pixelGroup[0] = pxnew - (int) Math.pow(2, n);
//            pixelGroup[1] = pxnew1 + (int) Math.pow(2, n);
//        }
//        return pixelGroup;
//    }
//
//    public static int[] obtainLastPixel3(int n, int pxnew, int pxnew1) {
//        int[] pixelGroup = new int[2];
//        if (n == 4) {
//            pixelGroup[0] = pxnew;
//            pixelGroup[1] = pxnew1;
//        } else if (n == 2 || n == 3 && pxnew >= pxnew1) {
//            pixelGroup[0] = pxnew + (int) Math.pow(2, n);
//            pixelGroup[1] = pxnew1 - (int) Math.pow(2, n);
//        } else if (n == 2 || n == 3 && pxnew < pxnew1) {
//            pixelGroup[0] = pxnew - (int) Math.pow(2, n);
//            pixelGroup[1] = pxnew1 + (int) Math.pow(2, n);
//        }
//        return pixelGroup;
//    }
//
//    public static int[] obtainLastPixelDisposeFOBP(int n, int[] pixelGroup) {
//        int[] pixelGroupnew = new int[2];
//        if (pixelGroup[0] < 0 | pixelGroup[1] < 0) {
//            pixelGroupnew[0] = pixelGroup[0] + (int) Math.pow(2, n);
//            pixelGroupnew[1] = pixelGroup[1] + (int) Math.pow(2, n);
//        } else {
//            pixelGroupnew[0] = pixelGroup[0] - (int) Math.pow(2, n);
//            pixelGroupnew[1] = pixelGroup[1] - (int) Math.pow(2, n);
//        }
//        return pixelGroupnew;
//    }
//
//    public static int[] RL(int n) {
//        int[] rl = new int[3];
//        if (n == 2) {
//            rl[0] = 0;
//            rl[1] = 15;
//            rl[2] = 4;
//        } else if (n == 3) {
//            rl[0] = 16;
//            rl[1] = 31;
//            rl[2] = 8;
//        } else {
//            rl[0] = 32;
//            rl[1] = 255;
//            rl[2] = 16;
//        }
//        return rl;
//    }

    public static int TableOfDifferenceRanges(int difference) {
        int n = 0; // 模函数的模a
        //判断差值在哪一个范围内
        if (difference >= 0 & difference <= 15) {
            n = 2;
        } else if (difference >= 16 & difference <= 31) {
            n = 3;
        } else {
            n = 4;
        }
        return n;
    }

    public static int pxpx1New1(int dec, int rem, int xy1, int xy2, int n, int px) {
        int pxnew = 0;
        int z = 0;
        if (rem < dec & Math.abs(xy1) < Math.pow(2, n - 1)) {
            pxnew = px - xy1;
        } else if (rem > dec & Math.abs(xy2) < Math.pow(2, n - 1)) {
            pxnew = px + xy2;
        } else if (rem < dec & Math.abs(xy1) >= Math.pow(2, n - 1)) {
            z = (int) Math.pow(2, n) + xy1;
            pxnew = px - z;
        } else if (rem > dec & Math.abs(xy2) >= Math.pow(2, n - 1)) {
            z = (int) Math.pow(2, n) + xy2;
            pxnew = px + z;
        } else {
            pxnew = px;
        }
        return pxnew;
    }

    public static int[] obtainLastPixel1(int n, int pxnew, int pxnew1) {
        int[] pixelGroup = new int[2];
        if (n == 2) {
            pixelGroup[0] = pxnew;
            pixelGroup[1] = pxnew1;
        } else if (n == 3 & pxnew >= pxnew1) {
            pixelGroup[0] = pxnew - (int) Math.pow(2, n);
            pixelGroup[1] = pxnew1 + (int) Math.pow(2, n);
        } else {
            pixelGroup[0] = pxnew + (int) Math.pow(2, n);
            pixelGroup[1] = pxnew1 - (int) Math.pow(2, n);
        }
        return pixelGroup;
    }

    public static int[] obtainLastPixel2(int n, int pxnew, int pxnew1) {
        int[] pixelGroup = new int[2];
        if (n == 3) {
            pixelGroup[0] = pxnew;
            pixelGroup[1] = pxnew1;
        } else if ((n == 2 & pxnew >= pxnew1) || (n == 4 && pxnew < pxnew1)) {
            pixelGroup[0] = pxnew + (int) Math.pow(2, n);
            pixelGroup[1] = pxnew1 - (int) Math.pow(2, n);
        } else {
            pixelGroup[0] = pxnew - (int) Math.pow(2, n);
            pixelGroup[1] = pxnew1 + (int) Math.pow(2, n);
        }
        return pixelGroup;
    }

    public static int[] obtainLastPixel3(int n, int pxnew, int pxnew1) {
        int[] pixelGroup = new int[2];
        if (n == 4) {
            pixelGroup[0] = pxnew;
            pixelGroup[1] = pxnew1;
        } else if (n == 2 | n == 3 & pxnew >= pxnew1) {
            pixelGroup[0] = pxnew + (int) Math.pow(2, n);
            pixelGroup[1] = pxnew1 - (int) Math.pow(2, n);
        } else {
            pixelGroup[0] = pxnew - (int) Math.pow(2, n);
            pixelGroup[1] = pxnew1 + (int) Math.pow(2, n);
        }
        return pixelGroup;
    }

    public static int[] obtainLastPixelDisposeFOBP(int n, int[] pixelGroup) {
        int[] pixelGroupnew = new int[2];
        if (pixelGroup[0] < 0 | pixelGroup[1] < 0) {
            pixelGroupnew[0] = pixelGroup[0] + (int) Math.pow(2, n);
            pixelGroupnew[1] = pixelGroup[1] + (int) Math.pow(2, n);
        } else {
            pixelGroupnew[0] = pixelGroup[0] - (int) Math.pow(2, n);
            pixelGroupnew[1] = pixelGroup[1] - (int) Math.pow(2, n);
        }
        return pixelGroupnew;
    }

    public static int[] RL(int n) {
        int[] rl = new int[3];
        if (n == 2) {
            rl[0] = 0;
            rl[1] = 15;
            rl[2] = 4;
        } else if (n == 3) {
            rl[0] = 16;
            rl[1] = 31;
            rl[2] = 8;
        } else {
            rl[0] = 32;
            rl[1] = 255;
            rl[2] = 16;
        }
        return rl;
    }
}
