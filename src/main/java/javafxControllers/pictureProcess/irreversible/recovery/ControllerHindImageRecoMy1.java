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
            utils.createTipsFrame(stage1,"已完成！",1);
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
            utils.createTipsFrame(stage1,"已完成！",1);
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
        System.out.println("好的");
    }

}
