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

public class ControllerAnalysisBPPMyMDP1 {
    @FXML
    private TextArea textAreaPSNRData;

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
    private Button True;

    @FXML
    private Text textPSNR;

    Utils utils = new Utils();

    public static Image imageOrigCarrier[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNamesOrigCarrier = new ArrayList<Text>();

    ScheduledService<Double> sche;

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
    void TrueAnalysisPSNR(ActionEvent event) {
        for (int theNumberPicture = 0; theNumberPicture < imageOrigCarrier.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) imageOrigCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageOrigCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            PixelReader pixelReaderCarrier = imageOrigCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅载体图像的像素值。
            int carrierHomei = 0;
            int carrierHomej = 0;
            int BPPnumber = 0;
            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            boolean flag = true;
            while (flag) {

                if (carrierHomei == heightCarrier) break;
                //步骤三 ：将像素对分别命名为：x，y。
                int x = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff; // 将获得rgb值转为十进制。
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }
                if (carrierHomei == heightCarrier) break;
                int y = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }

                //步骤四 ：计算两个像素对的差值。
                int d = Math.abs(x - y);

                //步骤五 ：通过差值d，查询差值范围表确定模函数的模a。
                int a = TableOfDifferenceRanges(d);
                int aa = (int) Math.ceil((a - 1) * 1.0 / 4);
                if (x < aa || x > (255 - aa) || y < aa || y > (255 - aa)) continue;

                //步骤六 ：通过模函数的模a ，通过指数函数计算得到嵌入信息位数b。
                int b = (int) ((Math.log(a * a) / Math.log(2)));
                BPPnumber += b;
            }
            double bpp = BPPnumber * 1.0 / (widthCarrier * heightCarrier);
            java.text.DecimalFormat dF = new java.text.DecimalFormat("0.00");
            String sBPP = String.valueOf(dF.format(bpp));
            String sCapacity = String.valueOf(BPPnumber);
            textAreaPSNRData.appendText(sBPP + "\n");
            textAreaPSNRData.appendText(sCapacity + "\n");
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
}
