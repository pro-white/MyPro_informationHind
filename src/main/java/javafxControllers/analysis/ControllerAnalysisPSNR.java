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

public class ControllerAnalysisPSNR {
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
    private TextArea textAreaPSNRData;

    @FXML
    private Button True;

    @FXML
    private Text textSecretCarrier;

    @FXML
    private FlowPane flowPaneSecretCarrier;

    @FXML
    private Button buttchooseSecretCarrier;

    @FXML
    private Text textPSNR;

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
            Stage stage = new Stage(); //??????????????????
            FileChooser fc = new FileChooser(); //????????????file?????????
            fc.setTitle("??????????????????");//??????????????????????????????????????????
            fc.setInitialDirectory(new File("C:"));//?????????????????????????????????
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("????????????", "*.jpg", "*.png"));
            listfile = fc.showOpenMultipleDialog(stage); //???????????????????????????????????????
            if (listfile == null) {
                return;
            }
            //?????????????????????
            Main.framePointReturn("???????????????????????????", flagtu);
        }

        imageSecretCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //?????????????????????????????????
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
            Stage stage = new Stage(); //??????????????????
            FileChooser fc = new FileChooser(); //????????????file?????????
            fc.setTitle("??????????????????");//??????????????????????????????????????????
            fc.setInitialDirectory(new File("C:"));//?????????????????????????????????
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("????????????", "*.jpg", "*.png"));
            listfile = fc.showOpenMultipleDialog(stage); //???????????????????????????????????????
            if (listfile == null) {
                return;
            }
            //?????????????????????
            Main.framePointReturn("???????????????????????????", flagtu);
        }

        imageOrigCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //?????????????????????????????????
        Main.fileShow(listfile, in, imageOrigCarrier, ivsZT, fps, textimgNamesOrigCarrier, pr, flowPaneOrigCarrier);
    }

    @FXML
    void TrueAnalysisPSNR(ActionEvent event) {

//        double[] psnr = new double[2];
        for (int theNumberPicture = 0; theNumberPicture < imageSecretCarrier.length; theNumberPicture++) { //???????????????????????????????????????
            // ?????????????????????????????????
            int widthOrigCarrier = (int) imageOrigCarrier[0].getWidth();
            int heightOrigCarrier = (int) imageOrigCarrier[0].getHeight();

            // ??????????????????
            PixelReader pReaderSecretCarrier = imageSecretCarrier[theNumberPicture].getPixelReader(); //?????????numImage??????????????????????????????
            PixelReader pReaderOrigCarrier = imageOrigCarrier[0].getPixelReader(); //?????????numImage??????????????????????????????

            int m = widthOrigCarrier; // ????????????
            int n = heightOrigCarrier; // ????????????

            double sum = 0;// ???????????????????????????
            for (int pixeli = 0; pixeli < n; pixeli++) {
                for (int pixelj = 0; pixelj < m; pixelj++) {
                    int secretCarrier = pReaderSecretCarrier.getArgb(pixelj, pixeli) & 0xff;
                    int OrigCarrier = pReaderOrigCarrier.getArgb(pixelj, pixeli) & 0xff;
                    sum += Math.pow((secretCarrier - OrigCarrier), 2);
                }
            }

            double mse = 1.0 / (m * n) * sum;
            double psnr= 10 * Math.log10((255 * 255) / mse);
            String s = String.valueOf(psnr);
            textAreaPSNRData.appendText(s + "\n");
        }
//        double sum = 0;
//        for (int i = 0; i < 2; i++) {
//            sum += psnr[i];
//        }
//        String s = String.valueOf(sum / 2);
//        textAreaPSNRData.appendText(s + "\n");
    }
}
