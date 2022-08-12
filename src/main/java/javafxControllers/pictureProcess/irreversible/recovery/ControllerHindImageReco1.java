package javafxControllers.pictureProcess.irreversible.recovery;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import secretSharing.Area;
import secretSharing.Lagrange;
import utils.Utils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerHindImageReco1 {

    @FXML
    private Button buttHuiSecret;

    @FXML
    private Text textRun;

    @FXML
    private ProgressBar pbHF;

    @FXML
    private Button secretSave;

    @FXML
    private Text textYZ;

    @FXML
    private Button TrueHF;

    @FXML
    private FlowPane flowPaneHFYuan;

    @FXML
    private VBox updatePane;

    @FXML
    private FlowPane flowPaneHF;

    @FXML
    private Button secretSaveYJ;

    @FXML
    private ScrollPane ScrollYZ;

    Utils utils = new Utils();

    public static ImageView HFYuan = new ImageView();

    public static Image imageCarrier[] ;

    public static Image imageShadow[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsOK = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    ScheduledService<Double> sche;

    @FXML
    void HFtoImageSave(ActionEvent event) throws IOException {
        if (HFYuan.getImage() == null) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        String fileName = new String();
        StringBuffer sb = new StringBuffer(file.getAbsolutePath());
        sb.insert(sb.length() - 4, "_" + 1);
        fileName = sb.toString();
        ImageIO.write(SwingFXUtils.fromFXImage(HFYuan.getImage(), null), "png", new File(fileName));

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
        if (HFYuan.getImage() == null) {
            return;
        }

        String fileName = new String();
        StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
        sb.insert(sb.length() - 4, "_" + 1);
        fileName = sb.toString();
        ImageIO.write(SwingFXUtils.fromFXImage(HFYuan.getImage(), null), "png", new File(fileName));

        //调用提示框函数
        Stage stage1 = (Stage) secretSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
        } catch (IOException e) {
            e.printStackTrace();
        }    }

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
        flowPaneHFYuan.getChildren().clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivsZT.isEmpty()) {
            return;
        }

        System.out.println("运行中。。。。。");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("提示框：");
        alert.headerTextProperty().set("点击成功，正在运行中。。。。。");
        alert.show();

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
                                HindImageReco1(flowPaneHFYuan);
                                num++;
                                break;
                            default:
                                num++;
                        }
                        super.updateValue(value);
                        pbHF.setProgress(value);
                        if (value >= 1) {
                            sche.cancel();
                            alert.close();// 将运行提示框关闭。
                            Stage stage1 = (Stage) TrueHF.getScene().getWindow();
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

    public static void HindImageReco1(FlowPane flowPaneHFYuan) {
        int ShadowImageWidth = 0; //影子图像的宽高。
        int ShadowImageHeight = 0;
        int ran_split[] = new int[ivsZT.size()];
        for (int theNumberPicture = 0; theNumberPicture < ivsZT.size(); theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) imageCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            PixelReader pixelReaderCarrier = imageCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅载体图像的像素值。

            //获得载体图像中隐藏的恢复隐藏图的重要坐标信息。
            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier);
            ShadowImageWidth = recoverNecessaryInf[0];
            ShadowImageHeight = recoverNecessaryInf[1];


            //写入像素值。

            WritableImage wiShadow[] = new WritableImage[imageCarrier.length]; // 创建写入image的数组对象。
            wiShadow[theNumberPicture] = new WritableImage(recoverNecessaryInf[0], recoverNecessaryInf[1]);// 在空白区域中写入新的像素值。
            PixelWriter pwShadow[] = new PixelWriter[imageCarrier.length];//创建读取image的数组对象。
            pwShadow[theNumberPicture] = wiShadow[theNumberPicture].getPixelWriter();//调用图像写入函数。
            pr[theNumberPicture] = wiShadow[theNumberPicture].getPixelReader();

            //从头遍历载体图像隐藏的信息。
            int num = 0;
            int rgbNum = 0;
            int iHind = 0;//隐藏图像的坐标值坐标值。
            int jHind = 0;
            int[] rgbHind = new int[3];//用于存储隐藏图像的三个颜色像素值。
            boolean flag = true;
            for (int jCarrier = 0; jCarrier < heightCarrier; jCarrier++) {
                if (flag == false) break; //用于跳出第一次上一行循环。
                for (int iCarrier = 0; iCarrier < widthCarrier; iCarrier++) {
                    for (int intColorCarrier = 16; intColorCarrier >= 0; intColorCarrier -= 8) {
                        int DecimalCarrier = ((pixelReaderCarrier.getArgb(iCarrier, jCarrier)) >> intColorCarrier) & 0xff;//判断进行一个像素值中的第几个rgb进行进制换算。
                        rgbHind[rgbNum] = (rgbHind[rgbNum] << 1) | (DecimalCarrier & 1); // 累计一个隐藏图像的颜色像素值。
                        num++; // 计数从载体图像中出去的bit值的个数。
                        if (num % 8 == 0) rgbNum++;//当num为8的倍数那么就说明一个颜色的像素值取完。
                        if (rgbNum == 3) { //当rgbnum的值为3 那就说明一个坐标像素值够了。
                            pwShadow[theNumberPicture].setColor(iHind, jHind, Color.rgb(rgbHind[0], rgbHind[1], rgbHind[2])); // 写入隐藏有秘密的像素值替换原来的载体图像的值。
                            iHind++;
                            if (iHind == recoverNecessaryInf[0]) {
                                iHind = 0;
                                jHind++;
                            }
                            ran_split[theNumberPicture]= rgbHind[0];
                            rgbHind[0] = 0;
                            rgbHind[1] = 0;
                            rgbHind[2] = 0;
                            rgbNum = 0;
                        }
                    }
                    if (iCarrier == recoverNecessaryInf[3] && jCarrier == recoverNecessaryInf[2]) {
                        flag = false;
                        break;
                    }
                }
            }
        }

        int mold = 251;
        int kk = ivsZT.size();
        int secretImageWidth = ShadowImageWidth; // 秘图的宽高。
        int secretImageHeight = ShadowImageHeight * kk;
        WritableImage wi = new WritableImage(secretImageWidth, secretImageHeight); //设置一个可以写入原图的空白区域。
        if (secretImageHeight / secretImageWidth > 3.0 / 4) {
            HFYuan.setFitHeight(180);
        } else HFYuan.setFitWidth(240);
        HFYuan.setPreserveRatio(true);
        HFYuan.setImage(wi);
        PixelWriter pwHFYuan = wi.getPixelWriter();
        flowPaneHFYuan.getChildren().add(HFYuan);
        flowPaneHFYuan.setAlignment(Pos.CENTER);

        boolean flag = true;
        int iSecretImage = 0; //遍历秘图坐标像素值的坐标值。
        int jSecretImage = 0;
        int iShadowImage = 0; //遍历影子图像坐标像素值的坐标值。
        int jShadowImage = 0;
        while (flag) {
            int[] redShadowDecimal = new int[kk]; //创建用于存储影子图像取出的坐标像素值的各自的颜色像素值。
            int[] greedShadowDecimal = new int[kk];
            int[] blueShadowDecimal = new int[kk];
            for (int R = 0; R < kk; R++) {
                int rgbValue = pr[R].getArgb(iShadowImage, jShadowImage);//获得第R幅影子图像的坐标像素值。
                for (int intcol = 16; intcol >= 0; intcol -= 8) {//读取一个坐标像素值的三个颜色像素值。
                    if (intcol == 16) {//判断进行一个像素值中的第几个rgb进行进制换算。
                        redShadowDecimal[R] = Area.Judgesub((rgbValue >> intcol) & 0xff, 0, mold);
                    } else if (intcol == 8) {
                        greedShadowDecimal[R] = Area.Judgesub((rgbValue >> intcol) & 0xff, 0, mold);
                    } else if (intcol == 0) {
                        blueShadowDecimal[R] = Area.Judgesub((rgbValue >> intcol) & 0xff, 0, mold);
                    }
                }
            }
            //创建用于存储获得的秘图的坐标像素值的各自的颜色像素值。
            int[] redSecretDecimal = Lagrange.getCoef(Lagrange.Lag(ran_split, redShadowDecimal, mold), kk); // 用拉格朗日插值函数首先获得多项式，然后获得所有的系数值。
            int[] greedSecretDecimal = Lagrange.getCoef(Lagrange.Lag(ran_split, greedShadowDecimal, mold), kk);
            int[] blueSecretDecimal = Lagrange.getCoef(Lagrange.Lag(ran_split, blueShadowDecimal, mold), kk);

            for (int R = 0; R < kk; R++) { // 循环遍历获得系数值的数组。三个数组的依次各自取一个元素构成秘图的一个坐标像素值。
                pwHFYuan.setColor(iSecretImage, jSecretImage, Color.rgb(redSecretDecimal[R], greedSecretDecimal[R], blueSecretDecimal[R]));
                iSecretImage++;
                if (iSecretImage == secretImageWidth) { //当一行坐标像素值被取完就换下一行。
                    jSecretImage++;
                    iSecretImage = 0;
                }
            }

            iShadowImage++; //当每一张影子图像的同一个坐标像素值都被取出之后，就换下一个坐标像素值。
            if (iShadowImage == ShadowImageWidth) { //当一行坐标像素值被取完就换下一行。
                jShadowImage++;
                iShadowImage = 0;
            }
            if (jShadowImage == ShadowImageHeight) { // 如果当像素值都取完了，但是不足R个像素值，那么就补零作为多项式系数。
                break;//跳出R的循环。
            }
        }
    }
}



