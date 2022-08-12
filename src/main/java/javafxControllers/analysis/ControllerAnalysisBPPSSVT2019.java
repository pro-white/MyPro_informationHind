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

public class ControllerAnalysisBPPSSVT2019 {
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

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    List<File> listfile;

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
        fps.clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
//        if (ivs.isEmpty() || ivsZT.isEmpty()) {
//            return;
//        }

        for (int theNumberPicture = 0; theNumberPicture < imageOrigCarrier.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) imageOrigCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageOrigCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            PixelReader pixelReaderCarrier = imageOrigCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅载体图像的像素值。

            int carrierHomei = 0;
            int carrierHomej = 0;

            //嵌入信息。
            //步骤一 ： 确定矩阵分块的大小,然后将其放入二维矩阵中。
            boolean flag = true;
            int BPPnumber = 0;
            while (flag) {
                // 将载体图像的像素值分块。
                int px = (pixelReaderCarrier.getArgb(carrierHomej, carrierHomei)) & 0xff;
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomej = 0;
                    carrierHomei++;
                }

                if (px == 0 | px == 255) {
                    continue;
                }
                int n = 3; //一次嵌入秘密信息比特流的信息流位数。
                BPPnumber += n;
                if (carrierHomei == heightCarrier) break;
            }
            double bpp = BPPnumber * 1.0 / (widthCarrier * heightCarrier);
            java.text.DecimalFormat dF = new java.text.DecimalFormat("0.00");
            String sBPP = String.valueOf(dF.format(bpp));
            String sCapacity = String.valueOf(BPPnumber);
            textAreaPSNRData.appendText(sBPP + "\n");
            textAreaPSNRData.appendText(sCapacity + "\n");
        }
    }
}
