package javafxControllers.pictureProcess.others;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
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

public class ControllerRecoverImage {

    @FXML
    private Stage stage;

    @FXML
    private Button buttHuiSecret;

    @FXML
    private ProgressBar pbHF;

    @FXML
    private Button secretSave;

    @FXML
    private Button secretSaveYJ;

    @FXML
    private Button TrueHF;

    @FXML
    private FlowPane flowPaneHFYuan;

    @FXML
    private FlowPane flowPaneHF;

    Utils utils = new Utils();

    ScheduledService<Double> sche;

    public static ImageView HFYuan = new ImageView();

    public static Image image[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

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
        }
    }

    @FXML
    void tobuttHuiSecret(ActionEvent event) throws IOException { //恢复照片
        ivs.clear();
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

        image = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, image, ivs, fps, textimgNames, pr, flowPaneHF);
    }

    @FXML
    void HFTrue(ActionEvent event) throws IOException {
        if (ivs.isEmpty()) {
            return;
        }
        System.out.println("运行中。。。。。");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("提示框：");
        alert.headerTextProperty().set("点击成功，正在运行中。。。。。");
        alert.show();

        int kk = ivs.size();
        int ShadowImageWidth = (int) image[0].getWidth(); //影子图像的宽高。
        int ShadowImageHeight = (int) image[0].getHeight();
        int secretImageWidth = ShadowImageWidth; // 秘图的宽高。
        int secretImageHeight = ShadowImageHeight * kk;
        WritableImage wi = new WritableImage(secretImageWidth, secretImageHeight); //设置一个可以写入原图的空白区域。
        if (secretImageHeight / secretImageWidth > 3.0 / 4) {
            HFYuan.setFitHeight(180);
        } else HFYuan.setFitWidth(240);
        HFYuan.setPreserveRatio(true);
        HFYuan.setImage(wi);
        PixelWriter pw = wi.getPixelWriter();
        flowPaneHFYuan.getChildren().add(HFYuan);
        flowPaneHFYuan.setAlignment(Pos.CENTER);

        /*
                1. 获得用户选择的图片数量 R。
                2. 获得每一张影子图像的最后一位坐标像素值的值。（这是用多项式恢复系数要有的 ，这里需要一个循环记录操作的是第几张影子图像。）
                3. 依次从R张影子图像中依次取出坐标像素值。每一张取一个坐标像素值一共是R个。
                4. 将R个坐标像素值和各自影子图像隐藏的x值构成R对（x，y）值，带入拉格朗日插值函数。
                5. 得到确定的多项式，取得R个系数，即为秘图的R个颜色像素值。
         */

        int[] x = hindNum(kk, ShadowImageWidth, ShadowImageHeight);//存储每一张影子图像使用的x值
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
                                RecoverImage(kk,pw,ShadowImageWidth,ShadowImageHeight,x,secretImageWidth);
                                num++;
                                break;
                            default:
                                num++;
                        }
                        super.updateValue(value);
                        pbHF.setProgress(value);
                        if (value >= 1) {
                            sche.cancel();
                            alert.close();
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

    public static void RecoverImage(int kk, PixelWriter pw, int ShadowImageWidth, int ShadowImageHeight ,int[] x,int secretImageWidth) {
        boolean flag = true;
        int iSecretImage = 0; //遍历秘图坐标像素值的坐标值。
        int jSecretImage = 0;
        int iShadowImage = 0; //遍历影子图像坐标像素值的坐标值。
        int jShadowImage = 0;
        int mold = 251;
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
            int[] redSecretDecimal = Lagrange.getCoef(Lagrange.Lag(x, redShadowDecimal, mold), kk); // 用拉格朗日插值函数首先获得多项式，然后获得所有的系数值。
            int[] greedSecretDecimal = Lagrange.getCoef(Lagrange.Lag(x, greedShadowDecimal, mold), kk);
            int[] blueSecretDecimal = Lagrange.getCoef(Lagrange.Lag(x, blueShadowDecimal, mold), kk);

            for (int R = 0; R < kk; R++) { // 循环遍历获得系数值的数组。三个数组的依次各自取一个元素构成秘图的一个坐标像素值。
                pw.setColor(iSecretImage, jSecretImage, Color.rgb(redSecretDecimal[R], greedSecretDecimal[R], blueSecretDecimal[R]));
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

    //将影子图像中隐藏的x值，读取出来，并且存入到数组中。
    public static int[] hindNum(int kk, int width, int height) {
        int ran_split[] = new int[kk];
        for (int i = 0; i < kk; i++) {//读取每一张影子图片隐藏的x值。
            ran_split[i] = (pr[i].getArgb(width - 1, height - 1) >> 16) & 0xff;
        }
        return ran_split;
    }
}
