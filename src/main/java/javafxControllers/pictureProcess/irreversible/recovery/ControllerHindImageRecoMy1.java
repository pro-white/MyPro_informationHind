package javafxControllers.pictureProcess.irreversible.recovery;

import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxControllers.Main;
import utils.Utils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerHindImageRecoMy1 {
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

    public static Image imageCarrier[];

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

        //????????????????????????
        File file = Main.imageSave();
        String fileName = new String();
        StringBuffer sb = new StringBuffer(file.getAbsolutePath());
        sb.insert(sb.length() - 4, "_" + 1);
        fileName = sb.toString();
        ImageIO.write(SwingFXUtils.fromFXImage(HFYuan.getImage(), null), "png", new File(fileName));

        //?????????????????????
        Stage stage1 = (Stage) secretSave.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"????????????",1);
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
        StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/??????.png");
        sb.insert(sb.length() - 4, "_" + 1);
        fileName = sb.toString();
        ImageIO.write(SwingFXUtils.fromFXImage(HFYuan.getImage(), null), "png", new File(fileName));

        //?????????????????????
        Stage stage1 = (Stage) secretSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"????????????",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tobuttHuiSecret(ActionEvent event) throws IOException { //????????????
        ivsZT.clear();
        fps.clear();
        flowPaneHF.getChildren().clear();
        flowPaneHFYuan.getChildren().clear();

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

        imageCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //?????????????????????????????????
        Main.fileShow(listfile, in, imageCarrier, ivsZT, fps, textimgNames, pr, flowPaneHF);
    }


    @FXML
    void HFTrue(ActionEvent event) {
        System.out.println("??????");
    }

}
