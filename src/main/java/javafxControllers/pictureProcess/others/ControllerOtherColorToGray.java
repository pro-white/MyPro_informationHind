package javafxControllers.pictureProcess.others;

import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafxControllers.Main;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerOtherColorToGray {
    @FXML
    private Button buttchooseColor;

    @FXML
    private ProgressBar pbBF;

    @FXML
    private FlowPane flowPaneGray;

    @FXML
    private Text textRun;

    @FXML
    private Button secretSave;

    @FXML
    private Text textGray;

    @FXML
    private Button True;

    @FXML
    private Text textColor;

    @FXML
    private FlowPane flowPaneColor;

    @FXML
    private Button secretSaveYJ;

    Utils utils = new Utils();

    ScheduledService<Double> sche;

    public static BufferedImage bufOriginalView[]; //用于存放首先选择的图像的路径位置转化成bufferedimage流。

    public static BufferedImage bufSave[]; //用于存放首先选择的图像的路径位置转化成bufferedimage流。

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsColor = new ArrayList<ImageView>(); //用于放图彩色图像显示。

    public static ArrayList<ImageView> ivsGray = new ArrayList<ImageView>(); // 用于放灰色图像显示。

    public static ArrayList<Text> textimgNamesColor = new ArrayList<Text>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    List<File> listfile;

    @FXML
    void tobuttchooseColor(ActionEvent event) throws IOException {
        fps.clear();
        ivsColor.clear();
        flowPaneColor.getChildren().clear();

        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            Stage stage = new Stage(); //创建一个场景
            FileChooser fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片多选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png", "*.tif", "*.tiff"));
            listfile = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfile == null) {
                return;
            }
            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        bufSave = new BufferedImage[listfile.size()];
        bufOriginalView = new BufferedImage[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow1(listfile, bufSave, bufOriginalView, ivsColor, fps, textimgNamesColor, pr, flowPaneColor);
    }

    @FXML
    void toImageSave(ActionEvent event) throws IOException { //图像的保存
        if (ivsGray.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        String fileName[] = new String[listfile.size()];
        for (int i = 0; i < listfile.size(); i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 5, "_" + i);
            fileName[i] = sb.toString();
            ImageIO.write(bufSave[i], "tiff", new File(fileName[i]));
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
    void toImageSaveYJ(ActionEvent event) throws IOException { //一键图像的保存
        if (ivsGray.isEmpty()) {
            return;
        }

        String fileName[] = new String[listfile.size()];
        for (int i = 0; i < listfile.size(); i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.tiff");
            sb.insert(sb.length() - 5, "_" + i);
            fileName[i] = sb.toString();
            ImageIO.write(bufSave[i], "tiff", new File(fileName[i]));
        }

        Stage stage1 = (Stage) secretSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1, "保存成功！", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void TrueColorToGray(ActionEvent event) {
        fps.clear();

        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivsColor.isEmpty()) {
            return;
        }
        for (int theNumberPicture = 0; theNumberPicture < bufOriginalView.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得原载体图像的宽高。
            int widthColor = (int) ivsColor.get(theNumberPicture).getImage().getWidth();
            int heightColor = (int) ivsColor.get(theNumberPicture).getImage().getHeight();
            bufSave[theNumberPicture] = average(bufSave[theNumberPicture]);
            //写入像素值。
            WritableImage wi[] = new WritableImage[bufOriginalView.length]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(widthColor, heightColor);

            for (int pixeli = 0; pixeli < heightColor; pixeli++) {
                for (int pixelj = 0; pixelj < widthColor; pixelj++) {
                    int piexl = bufSave[theNumberPicture].getRGB(pixeli, pixelj);
                    int piexlColorRed = (piexl >> 16) & 0xff;
                    int piexlColorGreed = (piexl >> 8) & 0xff;
                    int piexlColorBlue = piexl & 0xff;
                    int colorValue = piexlColorBlue | piexlColorGreed << 8 | piexlColorRed << 16;
                    bufSave[theNumberPicture].setRGB(pixeli, pixelj, colorValue);
                }
            }
            ivsGray.add(new ImageView());//创建一个imageview的对象。
            ivsGray.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsGray.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsGray.get(theNumberPicture).setImage(SwingFXUtils.toFXImage(bufSave[theNumberPicture], wi[theNumberPicture]));//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneGray.getChildren().add(ivsGray.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。\
        }
    }

    public static BufferedImage average(BufferedImage images) {
        BufferedImage average = new BufferedImage(images.getWidth(), images.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = average.getRaster().createCompatibleWritableRaster();
        for (int k = 0; k < images.getHeight(); ++k) {
            for (int j = 0; j < images.getWidth(); ++j) {
                float sum = 0.0f;
                    sum = sum + images.getRaster().getSample(j, k, 0);
                raster.setSample(j, k, 0, Math.round(sum));
            }
        }
        average.setData(raster);
        return average;
    }
}
