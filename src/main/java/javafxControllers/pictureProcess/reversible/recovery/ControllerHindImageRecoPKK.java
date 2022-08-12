package javafxControllers.pictureProcess.reversible.recovery;

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

public class ControllerHindImageRecoPKK {
    @FXML
    private ScrollPane SPaneContainSecretCret;

    @FXML
    private Button carrierSaveYJ;

    @FXML
    private Button buttHuiSecret;

    @FXML
    private ScrollPane SPaneOrigCarrier;

    @FXML
    private Text textRun;

    @FXML
    private Text TextoriginalCarrier;

    @FXML
    private ProgressBar pbHF;

    @FXML
    private Text TextContainSecretCarrier;

    @FXML
    private FlowPane flowPaneHFYuan;

    @FXML
    private VBox updatePane;

    @FXML
    private FlowPane flowPaneHF;

    @FXML
    private Button carrierSave;

    @FXML
    private Button secretSave;

    @FXML
    private FlowPane flowPaneOrigCarrier;

    @FXML
    private Button TrueHF;

    @FXML
    private Button secretSaveYJ;

    Utils utils = new Utils();

    public static ImageView HFYuan = new ImageView();

    public static Image imageCarrier[];

    public static Image imageShadow[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsOK = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOKcarrier = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

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
    void HFcarrierSaveYJ(ActionEvent event) throws IOException {
        if (ivsOKcarrier.isEmpty()) {
            return;
        }

        ArrayList<BufferedImage> bi = new ArrayList<BufferedImage>();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOKcarrier.size(); i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            bi.add(SwingFXUtils.fromFXImage(ivsOKcarrier.get(i).getImage(), null));
            ImageIO.write(bi.get(i), "png", new File(filename.get(i)));
        }

        Stage stage1 = (Stage) carrierSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1, "保存成功！", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HFcarrierSave(ActionEvent event) throws IOException {
        if (ivsOKcarrier.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOKcarrier.size(); i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            ImageIO.write(SwingFXUtils.fromFXImage(ivsOKcarrier.get(i).getImage(), null), "png", new File(filename.get(i)));
        }

        //调用提示框函数
        Stage stage1 = (Stage) carrierSave.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1, "保存成功！", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tobuttHuiSecret(ActionEvent event) throws IOException { //恢复照片
        ivsZT.clear();
        fps.clear();
        flowPaneHF.getChildren().clear();
        flowPaneHFYuan.getChildren().clear();

        List<File> listfile = null;
        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            Stage stage = new Stage(); //创建一个场景
            FileChooser fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片多选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"));
            listfile = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfile == null) {
                return;
            }
            //调用提示框函数
            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        imageCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, imageCarrier, ivsZT, fps, textimgNames, pr, flowPaneHF);
    }

    @FXML
    void HFTrue(ActionEvent event) {
        fps.clear();
        ivsOK.clear();
        ivsOKcarrier.clear();
        flowPaneHFYuan.getChildren().clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivsZT.isEmpty()) {
            return;
        }

        int imageCarrierNumber2 = 0;
        int imageCarrierNumber1 = 0;
        int number = 0;
        int pixelValues = 0;
        for (int theNumberPicture = 0; theNumberPicture < imageCarrier.length / 2; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) imageCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            imageCarrierNumber1 = number++; // 复制的载体图像1
            imageCarrierNumber2 = number++; // 复制的载体图像2
            PixelReader pixelReaderCarrier1 = imageCarrier[imageCarrierNumber1].getPixelReader(); //读取第numImage幅载体图像的像素值。
            PixelReader pixelReaderCarrier2 = imageCarrier[imageCarrierNumber2].getPixelReader(); //读取第numImage幅载体图像的像素值。

            //获得载体图像中隐藏的恢复隐藏图的重要坐标信息。
            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier1);
            int ShadowImageWidth = recoverNecessaryInf[0]; //影子图像的宽高。
            int ShadowImageHeight = recoverNecessaryInf[1];

            //写入像素值。
            WritableImage wi[] = new WritableImage[imageCarrier.length]; // 创建写入image的数组对象。
            PixelWriter pw[] = new PixelWriter[imageCarrier.length];//创建读取image的数组对象。

            //恢复的秘密图像像素的写入函数
            wi[theNumberPicture] = new WritableImage(recoverNecessaryInf[0], recoverNecessaryInf[1]);// 在载体图像中写入新的像素值。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            //恢复的原载体图像像素的写入函数
            wi[theNumberPicture + 1] = new WritableImage(pixelReaderCarrier1, widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            pw[theNumberPicture + 1] = wi[theNumberPicture + 1].getPixelWriter();//调用图像写入函数。

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
                    p2[b1b2] = (pixelReaderCarrier2.getArgb(carrierHomej, carrierHomei)) & 0xff;
                    //步骤三 ：通过两幅载体图像相同位置像素值进行平均值计算的原载体图像的像素值。
                    int pixelValue =  (int) Math.ceil((p1[b1b2] + p2[b1b2]) / 2.0);
                    pw[theNumberPicture + 1].setColor(carrierHomej++, carrierHomei, Color.grayRgb(p[b1b2]));
                    if (carrierHomej == widthCarrier) {
                        carrierHomej = 0;
                        carrierHomei++;
                    }
                    //得到原载体图像。
                    if (pixelValue < 4 || pixelValue > 251) {
                        b1b2--;
                        continue;
                    }
                    p[b1b2] = pixelValue;// ！！！！！！注意：这里向上取整，那么除数或者被除数一定要有一个是小数。
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
            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneHFYuan.getChildren().add(ivsOK.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
            //原在途图像的显示框
            ivsOKcarrier.add(new ImageView());//创建一个imageview的对象。
            ivsOKcarrier.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOKcarrier.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOKcarrier.get(theNumberPicture).setImage(wi[theNumberPicture+1]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneOrigCarrier.getChildren().add(ivsOKcarrier.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。。
        }
    }
}
