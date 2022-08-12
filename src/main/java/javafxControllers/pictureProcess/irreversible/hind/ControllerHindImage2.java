package javafxControllers.pictureProcess.irreversible.hind;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
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

public class ControllerHindImage2 {

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

    Utils utils = new Utils();

    @FXML
    private Button secretSaveYJ;

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
            utils.createTipsFrame(stage1,"保存成功！",1);
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
            utils.createTipsFrame(stage1,"保存成功！",1);
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
                utils.createTipsFrame(stage1,"隐藏图像和载体图像的数量不相等！\n 请重新选择。",1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        /*
          α = 2：
            L : 载体图像总的可以影藏信息的byte像素值
            l : 影子图像需要影藏的bit值。
            t : 隐藏bit值每一组的个数。
                1. 首先要确认t字节是多少：就必须要求 总的可影藏信息颜色像素值 l * t  + t * 20 <= ( L * α）；
                    那么就是 t = （L * α） / （l + 20）
                    例如：t = 3；那么就是3个byte为一组，3个中选择一个作为隐藏信息的byte位。
                2. 确定byte[i]的位置，用一个密钥产生一个随机序列k[0]、k[1]、k[2] ....。用于确定隐藏信息在一组byte中哪一个byte[i]中影藏。
                   byte[i] = int(i * t) + k[i] mod (int((i + 1) * t) - int(i * t)   (0 < i <= l)
                   注意：这里就先指定的位置，不随机。定为每一组的第一个位置。
                3. 确定bit[i]的位置，用 bit[i] = k[i] * mod 3  (0 < i <= l)
                   注意：这里就先指定的位置，不随机，定位最后两位最低有效值。
                4. 用定义好的m0＝ x0 ⊕ x2，m1＝ x1 ⊕ x2 ，两个式子的关系来隐藏信息。
         */

        sche = new ScheduledService<Double>() {
            double i = 0;
            int num = 1;

            @Override
            protected Task<Double> createTask() {
                Task<Double> task = new Task<Double>() {
                    @Override
                    protected Double call() throws Exception {
                        if (i < 0.7) {
                            return i = i + 0.2;
                        } else {
                            return i = 1;
                        }
                    }

                    protected void updateValue(Double value) {
                        switch (num) {
                            case 1:
                                num++;
                                break;
                            case 2:
                                num++;
                                break;
                            case 3:
                                HindImage2(flowPaneOK , TrueHind);
                                num++;
                                break;
                            default:
                                num++;
                        }
                        super.updateValue(value);
                        pbHind.setProgress(value);
                        if (value >= 1) {
                            sche.cancel();
                            alert.close();// 将运行提示框关闭。
                            Stage stage1 = (Stage) TrueHind.getScene().getWindow();
                            try {
                                utils.createTipsFrame(stage1,"已完成！",1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("运行结束！");
                        }
                    }
                };
                return task;
            }
        };

        sche.setDelay(Duration.millis(0));
        sche.setPeriod(Duration.millis(100));
        sche.start();
    }

    public static void HindImage2(FlowPane flowPaneOK ,Button TrueHind) {
        Utils utils = new Utils();

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
            WritableImage wi[] = new WritableImage[imageCarrier.length]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(imageCarrier[theNumberPicture].getPixelReader(), widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            PixelWriter pw[] = new PixelWriter[imageCarrier.length];//创建读取image的数组对象。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            int intColorCarrier = 16;//存储载体图像的获得不同颜色的rgb值的逻辑运算值。（从前面的颜色开始隐藏信息【red（16）  greed（8）  bule（0）】）
            ArrayList<Integer> rgbNumCarrierAfter = new ArrayList<Integer>();//用于存储隐藏了信息的三个rgb值。

            int a = 2; // 一个byte影藏a个bit值
            int l = 1;//记录影藏的第几个bit值
            int kByte = 0; // 充当随机序列的数字。
            int kBit = 0; //当a = 1时才会用到。也就是一个byte影藏一个bit隐藏信息。
            int iCarrier = 0;//依次读取载体图像的像素值的坐标值。
            int jCarrier = 0;
            int t = (widthCarrier * heightCarrier * a) / ((widthHind * heightHind) * 8 + 20); // 隐藏bit值每一组的个数。
            if (t > 0) {
                for (int iHind = 0; iHind < heightHind; iHind++) {//依次读取隐藏图像的像素值。
                    for (int jHind = 0; jHind < widthHind; jHind++) {
                        for (int intColorHind = 16; intColorHind >= 0; intColorHind -= 8) {//对一个坐标像素值，循环三次获得三个颜色各自的像素值。
                            int rgbDecimalHind = ((pixelReaderHind.getArgb(jHind, iHind)) >> intColorHind) & 0xff; //判断进行一个像素值中的第几个rgb进行进制换算。
                            for (int bit = 7; bit >= 0; ) {
                                int s0 = (rgbDecimalHind >>> bit--) & 1; // 与1&取最低位，并保证最低位为0或1(得到隐藏byte对应的bit值。）
                                int s1 = (rgbDecimalHind >>> bit--) & 1;
                                int rgbDecimalCarrier = ((pixelReaderCarrier.getArgb(iCarrier, jCarrier)) >> intColorCarrier) & 0xff;//判断进行一个像素值中的第几个rgb进行进制换算。
                                int x0 = (rgbDecimalCarrier >>> 0) & 1; // 获得载体图像的最后三位bit值。
                                int x1 = (rgbDecimalCarrier >>> 1) & 1;
                                int x2 = (rgbDecimalCarrier >>> 2) & 1;
                                int m0 = x0 ^ x2; // 使用设计好的关系式。
                                int m1 = x1 ^ x2;
                                if (m0 == s0 && m1 == s1) { // 判断关系式的关系。
                                    rgbNumCarrierAfter.add(rgbDecimalCarrier);// 用逻辑运算，替换载体图像的最低有效位的值。
                                } else if (m0 != s0 && m1 == s1) {
                                    if (x0 == 1) {
                                        rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFE));
                                    } else rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFE) | 1);
                                    rgbNumCarrierAfter.add(rgbDecimalCarrier);
                                } else if (m0 == s0 && m1 != s1) {
                                    if (x1 == 1) {
                                        rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFD));
                                    } else rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFD) | 2);
                                    rgbNumCarrierAfter.add(rgbDecimalCarrier);
                                } else if (m0 != s0 && m1 != s1) {
                                    if (x2 == 1) {
                                        rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFB));
                                    } else rgbDecimalCarrier = ((rgbDecimalCarrier & 0xFFFFFFFB) | 4);
                                    rgbNumCarrierAfter.add(rgbDecimalCarrier);
                                }
//                                if (kBit == 0 && 。。。){  这是用于a = 1时，用于随机选择byte的最后三位bit隐藏信息。
                                intColorCarrier -= 8;//换下一个坐标像素值里面的下一个颜色像素值。
                                if (rgbNumCarrierAfter.size() == 3) {//当存储装有隐藏信息的载体像素值够三个（也就是一个坐标像素值），就写入原载体图像，替换原像素值。
                                    pw[theNumberPicture].setColor(iCarrier, jCarrier, Color.rgb(rgbNumCarrierAfter.get(0), rgbNumCarrierAfter.get(1), rgbNumCarrierAfter.get(2))); // 写入隐藏有秘密的像素值替换原来的载体图像的值。
                                    intColorCarrier = 16;//将载体图像（换下一个坐标像素值里面的下一个颜色像素值）的变量重置。
                                    rgbNumCarrierAfter.clear();//清空用于存储隐藏了信息的三个rgb值的列表。
                                    l++;
                                    iCarrier = (t * (l - 1)) % widthCarrier + kByte; //通过记录该隐藏第几个bit值来确定载体图像的坐标像素值。
                                    jCarrier = (t * (l - 1)) / widthCarrier;
                                }
                            }
                        }
                    }
                }
                if (rgbNumCarrierAfter.size() != 0) {//如果最后没有凑够三个颜色的像素值（也就是一个坐标像素值），那么数组也不会被清空。
                    if (rgbNumCarrierAfter.size() == 1) {
                        pw[theNumberPicture].setColor(jCarrier, iCarrier, Color.rgb(rgbNumCarrierAfter.get(0), ((pixelReaderCarrier.getArgb(jCarrier, iCarrier)) >> 8) & 0xff, ((pixelReaderCarrier.getArgb(jCarrier, iCarrier)) & 0xff))); // 写入隐藏有秘密的像素值替换原来的载体图像的值。
                        System.out.println("size = " + rgbNumCarrierAfter.size());
                    } else if (rgbNumCarrierAfter.size() == 2) {
                        pw[theNumberPicture].setColor(jCarrier, iCarrier, Color.rgb(rgbNumCarrierAfter.get(0), rgbNumCarrierAfter.get(1), ((pixelReaderCarrier.getArgb(jCarrier, iCarrier)) & 0xff))); // 写入隐藏有秘密的像素值替换原来的载体图像的值。
                        System.out.println("size = " + rgbNumCarrierAfter.size());
                    }
                }
                ArrayList<Integer> recoverNecessaryInf = new ArrayList<Integer>();//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
                recoverNecessaryInf.add(widthHind); // 1. 隐藏图像的宽
                recoverNecessaryInf.add(heightHind); // 2. 隐藏图像的高
                recoverNecessaryInf.add(iCarrier); // 3. 载体图像结束位的y轴坐标值
                recoverNecessaryInf.add(jCarrier); // 4. 载体图像结束位的x轴坐标值。
                recoverNecessaryInf.add(rgbNumCarrierAfter.size()); // 5.最后剩余的颜色的像素值个数。

                for (int i = 0; i < 5; i++) {
                    System.out.println(recoverNecessaryInf.get(i));// 3. 载体图像结束位的y轴坐标值);
                }
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
                ivsOK.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
                ivsOK.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
                ivsOK.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
                flowPaneOK.getChildren().add(ivsOK.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
            } else {
                Stage stage1 = (Stage) TrueHind.getScene().getWindow();
                try {
                    utils.createTipsFrame(stage1,"第" + (theNumberPicture + 1) + "张载体图像的像素值不够！\n 请重新选择。",1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
