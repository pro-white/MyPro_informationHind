package javafxControllers.pictureProcess.reversible.hind;

import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

public class ControllerHindImageaSSVT2019 {
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
    private TextField textFieldPassword;

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

    public static ArrayList<ImageView> ivsOKk = new ArrayList<ImageView>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    public static ArrayList<Text> textimgNamesZT = new ArrayList<Text>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Integer> passwordInf = new ArrayList<>();

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
        passwordInf.clear();

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
            System.out.println("原载体图像的宽 = " + widthCarrier);
            System.out.println("原载体图像的高 = " + heightCarrier);

            // 读取像素值。
            PixelReader pixelReaderHind = imageHind[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。
            PixelReader pixelReaderCarrier = imageCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅载体图像的像素值。
            //写入像素值。

            //步骤一 ：将原载体图像复制两份
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

            int carrierHomei = 0;
            int carrierHomej = 0;
            int carrierEndi = 0;
            int carrierEndj = 0;
            int hindHomei = 0;
            int hindHomej = 0;
            int bitNumber = 7;// 二进制位数。
            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            if (widthHind * heightHind * 8 * 1.0/ 3 > widthCarrier * heightCarrier - 20){
                Stage stage1 = (Stage) TrueHind.getScene().getWindow();
                try {
                    utils.createTipsFrame(stage1, "载体图像容量不够\n 请重新选择。！", 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            boolean flag = true;
            while (flag) {
                // 首先将图像的像素分成1*2不重叠的像素块。
                int px = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomej = 0;
                    carrierHomei++;
                }

                if (px == 0 | px == 255) {
                    pw[imageCarrierNumber1].setColor(carrierEndj, carrierEndi, Color.grayRgb(px));
                    pw[imageCarrierNumber2].setColor(carrierEndj, carrierEndi, Color.grayRgb(px));
                    carrierEndj++;
                    if (carrierEndj == widthCarrier) {
                        carrierEndj = 0;
                        carrierEndi++;
                    }
                    continue;
                }
                int n = 3; //一次嵌入秘密信息比特流的信息流位数。

                //步骤二 ：将n位二进制流转化位十进制数 ： dec ;以及获得rem1 和rem2.
                int b = 0;// 是ei 位比特流的十进制值。
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
                    b += bit * Math.pow(2, n - k);
                }

                //判断嵌入的秘密信息为奇数还是偶数，以此来判断秘密是多少。
                if (b % 2 == 0) {
                    passwordInf.add(0);
//                    String sBPP = String.valueOf(0);
//                    textFieldPassword.appendText(sBPP);
                } else {
                    passwordInf.add(1);
//                    String sBPP = String.valueOf(1);
//                    textFieldPassword.appendText(sBPP);
                }

                //步骤三 ：使用公式得到差值d
                int d = b - (int) Math.pow(2, n -1);
                int[] s = ss(d);//通过查表得到两幅载体图像的一对像素值的变化值 s’ 和 s‘’

                //步骤四 ：得到新的像素值
                int pxNew = px + s[0];
                int px1New = px + s[1];

                //步骤七 ：如果出现溢出则执行公式。

                pw[imageCarrierNumber1].setColor(carrierEndj, carrierEndi, Color.grayRgb(pxNew));
                pw[imageCarrierNumber2].setColor(carrierEndj, carrierEndi, Color.grayRgb(px1New));
                carrierEndj++;
                if (carrierEndj == widthCarrier) {
                    carrierEndj = 0;
                    carrierEndi++;
                }
                if (carrierEndi == heightCarrier) break;
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
            System.out.println("结束啦");
//            for (int i =0 ;i < password.size() ; i++){
//                String sBPP = String.valueOf(password.get(i));
//                textFieldPassword.appendText(sBPP);
//            }
//            System.out.println("结束啦222");

            //隐藏重要数据的方式：
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
            System.out.println("载体图像的宽 = " + widthCarrier);
            System.out.println("载体图像的高 = " + heightCarrier);

            // 步骤一 ：获得两幅载体图像，读取像素值。
            imageCarrierNumber1 = number++; // 复制的载体图像1
            imageCarrierNumber2 = number++; // 复制的载体图像2
            PixelReader pixelReaderCarrier1 = ivsOK.get(imageCarrierNumber1).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。
            PixelReader pixelReaderCarrier2 = ivsOK.get(imageCarrierNumber2).getImage().getPixelReader(); //读取第numImage幅载体图像的像素值。

            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier1);
            int ShadowImageWidth = recoverNecessaryInf[0]; //影子图像的宽高。
            int ShadowImageHeight = recoverNecessaryInf[1];
            System.out.println("恢复时重要的宽 = " + ShadowImageWidth);
            System.out.println("恢复时重要的高 = " + ShadowImageHeight);

            //写入像素值。
            WritableImage wi[] = new WritableImage[ivsOK.size() / 2]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(ShadowImageWidth, ShadowImageHeight);// 在载体图像中写入新的像素值。
            PixelWriter pw[] = new PixelWriter[ivsOK.size() / 2];//创建读取image的数组对象。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            int carrierHomei = 0;
            int carrierHomej = 0;
            int hindHomei = 0;
            int hindHomej = 0;
            int bitNumber = 7;// 二进制位数。

            boolean flag = true;
            int numberPassword = 0;
            int n = 3;
            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            while (flag) {
                //步骤一 ：将像素对分别命名为：x，y。
                if (carrierHomei == heightCarrier) break;
                int px1 = (pixelReaderCarrier1.getArgb(carrierHomej, carrierHomei)) & 0xff;
                int px2 = (pixelReaderCarrier2.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }

                //步骤二 ：进行平均值求得原始像素值
                int px = (int) Math.ceil((px1 + px2) * 1.0 / 2);

                if (px == 0 | px == 255) {
                    continue;
                }

                //步骤三 ：计算像素对的修改量。
                int s1 = px1 - px;
                int s2 = px2 - px;
//                int password = Integer.parseInt(textFieldPassword.getText(numberPassword, ++numberPassword));
                int password = passwordInf.get(numberPassword++);

                //步骤四 ：通过查表以及password得到差值d
                int d = dd(s1, s2, password);
                int b = d + (int) Math.pow(2, 2);
                //步骤五：获得b转换为3位比特流。

                //步骤九 ：将秘密信息B转化为b位二进制流。
                for (int k = 1; k <= n; k++) {
                    int remainder = b - (int) Math.pow(2, n - k);//对目标数字求余
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
            flowPaneHFYuan.setAlignment(Pos.CENTER);
            ivsOKk.add(new ImageView());//创建一个imageview的对象。
            ivsOKk.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOKk.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOKk.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneHFYuan.getChildren().add(ivsOKk.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
        }
    }

    public static int[] ss(int d) {
        int[] s = new int[2];
        if (d == -4 | d == -3) {
            s[0] = -1;
            s[1] = 1;
        } else if (d == -2 | d == 1) {
            s[0] = 0;
            s[1] = -1;
        } else if (d == 0 | d == -1) {
            s[0] = 0;
            s[1] = 0;
        } else if (d == 2) {
            s[0] = -1;
            s[1] = 0;
        } else if (d == 3 | d == 4) {
            s[0] = 1;
            s[1] = -1;
        } else {
            System.out.println("这这这？？？");
        }
        return s;
    }

    public static int dd(int s1, int s2, int password) {
        int d = 0;
        if (s1 == -1 & s2 == 1 & password == 0) {
            d = -4;
        } else if (s1 == 0 & s2 == -1 & password == 0) {
            d = -2;
        } else if (s1 == 0 & s2 == 0 & password == 0) {
            d = 0;
        } else if (s1 == -1 & s2 == 0 & password == 0) {
            d = 2;
        } else if (s1 == 1 & s2 == -1 & password == 0) {
            d = 4;
        } else if (s1 == -1 & s2 == 1 & password == 1) {
            d = -3;
        } else if (s1 == 0 & s2 == 0 & password == 1) {
            d = -1;
        } else if (s1 == 0 & s2 == -1 & password == 1) {
            d = 1;
        } else if (s1 == 1 & s2 == -1 & password == 1) {
            d = 3;
        }else {
            System.out.println("怎么会没有呢？");
        }
        return d;
    }
}
