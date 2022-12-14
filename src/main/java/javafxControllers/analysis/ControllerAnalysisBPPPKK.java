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

public class ControllerAnalysisBPPPKK {
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
        fps.clear();
        //???????????????????????????????????????????????????????????????????????????
//        if (ivs.isEmpty() || ivsZT.isEmpty()) {
//            return;
//        }

        for (int theNumberPicture = 0; theNumberPicture < imageOrigCarrier.length; theNumberPicture++) { //???????????????????????????????????????
            // ??????????????????????????????
            int widthCarrier = (int) imageOrigCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageOrigCarrier[theNumberPicture].getHeight();

            // ??????????????????
            PixelReader pixelReaderCarrier = imageOrigCarrier[theNumberPicture].getPixelReader(); //?????????numImage??????????????????????????????

            int b1 = 3; // ????????????????????????????????????
            int b2 = 3;
            int carrierHomei = 0;
            int carrierHomej = 0;

            //???????????????
            //????????? ??? ???????????????????????????,????????????????????????????????????
            boolean flag = true;
            int BPPnumber = 0;
            while (flag) {
                // ????????????????????????????????????
                int[] p = new int[b1 * b2];
                for (int b1b2 = 0; b1b2 < b1 * b2; b1b2++) {
                    int pixelValue = (pixelReaderCarrier.getArgb(carrierHomej++, carrierHomei)) & 0xff;
                    if (carrierHomej == widthCarrier) {
                        carrierHomej = 0;
                        carrierHomei++;
                    }
                    if (carrierHomei == heightCarrier) break;
                    if (pixelValue < 4 || pixelValue > 251) {
                        b1b2--;
                        continue;
                    }
                    p[b1b2] = pixelValue; // ?????????rgb?????????????????????
                }
                if (carrierHomei == heightCarrier) break;


                for (int i = 0; i < b1 * b2; i++) {
                    if (flag == false) break;
                    // ????????? ??? ????????????????????????????????????????????? ??????????????????3???
//                    int sum = 0; // ?????????3????????????????????????
//                    for (int j = 0; j < b1 * b2; j++) {
//                        if (j == i) continue;
//                        sum += p[j];
//                    }
//                    int m = Math.abs(p[i] - sum / (b1 * b2 - 1));  // ?????????3?????????????????? mi ??????
                    int max = 0;
                    for (int j = 1; j < b1 * b2; j++) {
                        if (j == i) continue;
                        if (max < Math.abs(p[i] - p[j])){
                            max = Math.abs(p[i] - p[j]);
                        }
                    }
                    int m = max;  // ?????????3?????????????????? mi ??????

                    //????????? ??? ???????????? mi ??????????????????????????? ei???
                    int e = 0; // ??????Java?????????log???0?????????????????????????????????????????? m = 0 ??????????????????????????? 1???
                    if (m == 1 || m == 0) {
                        e = 1; // ??????????????????bit???????????????
                    } else {
                        e = (int) (Math.log(m) / Math.log(2)); // ??????????????????bit???????????????
                    }
                    BPPnumber += e;
                }
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
