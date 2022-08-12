package javafxControllers.analysis;

import javafx.concurrent.ScheduledService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxControllers.Main;
import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerAnalysisSSIM {
    @FXML
    private ProgressBar pbBF;

    @FXML
    private Text textRun;

    @FXML
    private Text textOrigCarrier;

    @FXML
    private Button buttchooseOrigCarrier;

    @FXML
    private FlowPane flowPaneOrigCarrier;

    @FXML
    private TextArea textAreaSSIMData;

    @FXML
    private Button True;

    @FXML
    private Text textSecretCarrier;

    @FXML
    private Text textSSIM;

    @FXML
    private FlowPane flowPaneSecretCarrier;

    @FXML
    private Button buttchooseSecretCarrier;

    Utils utils = new Utils();

    public static Image imageSecretCarrier[];

    public static Image imageOrigCarrier[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNamesSecretCarrier = new ArrayList<Text>();

    public static ArrayList<Text> textimgNamesOrigCarrier = new ArrayList<Text>();

    ScheduledService<Double> sche;

    @FXML
    void tobuttchooseSecretCarrier(ActionEvent event) throws IOException {
        ivsZT.clear();
        fps.clear();
        flowPaneSecretCarrier.getChildren().clear();

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

        imageSecretCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, imageSecretCarrier, ivsZT, fps, textimgNamesSecretCarrier, pr, flowPaneSecretCarrier);
    }

    @FXML
    void tobuttchooseOrigCarrier(ActionEvent event) throws IOException {
        ivsZT.clear();
        fps.clear();
        flowPaneOrigCarrier.getChildren().clear();

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

        imageOrigCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, imageOrigCarrier, ivsZT, fps, textimgNamesOrigCarrier, pr, flowPaneOrigCarrier);
    }

    @FXML
    void TrueAnalysisSSIM(ActionEvent event) {
//        double[] ssim = new double[2];
        for (int theNumberPicture = 0; theNumberPicture < imageSecretCarrier.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得原载体图像的宽高。
            int widthOrigCarrier = (int) imageOrigCarrier[0].getWidth();
            int heightOrigCarrier = (int) imageOrigCarrier[0].getHeight();

            // 读取像素值。
            PixelReader pReaderSecretCarrier = imageSecretCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。
            PixelReader pReaderOrigCarrier = imageOrigCarrier[0].getPixelReader(); //读取第numImage幅载体图像的像素值。

            int R = heightOrigCarrier; // 图像的高。也就是欧多少行。
            int C = widthOrigCarrier; // 图像的宽，也就是有多少列。
            //常数：C1、C2、C3 （其中的bit为图像位数，假设图像为8位，L=255
            double k1 = 0.01;
            double k2 = 0.03;
            int L = 255;
            double c1 = Math.pow((k1 * L), 2);
            double c2 = Math.pow((k2 * L), 2);

            //SSIM中的子公式：均值。
            double Ux = 0;
            double Uy = 0;
            double sumU = 0;
            for (int pixeli = 0; pixeli < R; pixeli++) {
                for (int pixelj = 0; pixelj < C; pixelj++) {
                    int OrigCarrier = pReaderOrigCarrier.getArgb(pixelj, pixeli) & 0xff;
                    sumU += OrigCarrier;
                }
            }
            Ux = sumU / (R * C);
            Uy = Ux;

            //SSIM中的子公式：方差和标准差和两种图片的协方差计算。
            double sumQx = 0;
            double sumQy = 0;
            for (int pixeli = 0; pixeli < R; pixeli++) {
                for (int pixelj = 0; pixelj < C; pixelj++) {
                    int OrigCarrier = pReaderOrigCarrier.getArgb(pixelj, pixeli) & 0xff;
                    int secretCarrier = pReaderSecretCarrier.getArgb(pixelj, pixeli) & 0xff;
                    sumQx += Math.pow((OrigCarrier - Ux), 2);
                    sumQy += Math.pow((secretCarrier - Uy), 2);
                }
            }
            double Qx2 = sumQx / (R * C);
            double Qy2 = sumQy / (R * C);

            double sumQxy = 0;
            for (int pixeli = 0; pixeli < R; pixeli++) {
                for (int pixelj = 0; pixelj < C; pixelj++) {
                    int OrigCarrier = pReaderOrigCarrier.getArgb(pixelj, pixeli) & 0xff;
                    int secretCarrier = pReaderSecretCarrier.getArgb(pixelj, pixeli) & 0xff;
                    sumQxy += (OrigCarrier - Ux) * (secretCarrier - Uy);
                }
            }
            double Qxy = sumQxy / (R * C);

            //SSIM的计算结果
            //公式一：
            double s1 = (2 * Ux * Uy + c1) * (2 * Qxy + c2);
            double s2 = (Math.pow(Ux, 2) + Math.pow(Uy, 2) + c1) * (Qx2 + Qy2 + c2);
//            ssim[theNumberPicture] = s1 / s2;
            double ssim = s1 / s2;
            String s = String.valueOf(ssim);
            textAreaSSIMData.appendText(s + "\n");
        }
//        double sum = 0;
//        for (int i = 0; i < 2; i++) {
//            sum += ssim[i];
//        }
//        String s = String.valueOf(sum / 2);
//        textAreaSSIMData.appendText(s + "\n");
    }
}
