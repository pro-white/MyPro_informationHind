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

    public static Image image;

    public static Image imageColor[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsColor = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsGray = new ArrayList<ImageView>();

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
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"));
            listfile = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfile == null) {
                return;
            }
            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        imageColor = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, imageColor, ivsColor, fps, textimgNamesColor, pr, flowPaneColor);
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
            sb.insert(sb.length() - 4, "_" + i);
            fileName[i] = sb.toString();
            ImageIO.write(SwingFXUtils.fromFXImage(ivsGray.get(i).getImage(), null), "png", new File(fileName[i]));
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

        BufferedImage bi[] = new BufferedImage[listfile.size()];
        String fileName[] = new String[listfile.size()];
        for (int i = 0; i < listfile.size(); i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.jpg");
            sb.insert(sb.length() - 4, "_" + i);
            fileName[i] = sb.toString();
            bi[i] = SwingFXUtils.fromFXImage(ivsGray.get(i).getImage(), null);
            ImageIO.write(bi[i], "png", new File(fileName[i]));
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
//        ivsGray.clear();
//        flowPaneColor.getChildren().clear();
//        flowPaneGray.getChildren().clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivsColor.isEmpty()) {
            return;
        }
        int num = 0;
        for (int theNumberPicture = 0; theNumberPicture < imageColor.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得原载体图像的宽高。
            int widthColor = (int) imageColor[theNumberPicture].getWidth();
            int heightColor = (int) imageColor[theNumberPicture].getHeight();
            System.out.println("youmeyou");

            // 读取像素值。
            PixelReader pReaderColor = imageColor[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。

            //写入像素值。
            WritableImage wi[] = new WritableImage[imageColor.length]; // 创建写入image的数组对象。
            wi[theNumberPicture] = new WritableImage(widthColor, heightColor);
            PixelWriter pw[] = new PixelWriter[imageColor.length];//创建读取image的数组对象。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            int piexlGray = 0;
            for (int pixeli = 0; pixeli < heightColor; pixeli++) {
                for (int pixelj = 0; pixelj < widthColor; pixelj++) {
                    int piexlColorRed = (pReaderColor.getArgb(pixelj, pixeli) >> 16) & 0xff;
                    int piexlColorGreed = (pReaderColor.getArgb(pixelj, pixeli) >> 8) & 0xff;
                    int piexlColorBlue = pReaderColor.getArgb(pixelj, pixeli) & 0xff;

                    piexlGray = (piexlColorRed * 38 + piexlColorGreed * 75 + piexlColorBlue * 15) >> 7;
//                    pw[theNumberPicture].setColor(pixelj,pixeli, Color.rgb(piexlColorRed,piexlColorGreed,piexlColorBlue));
                    pw[theNumberPicture].setColor(pixelj, pixeli, Color.grayRgb(piexlGray));
                }
            }
            System.out.println("fadfas");
            ivsGray.add(new ImageView());//创建一个imageview的对象。
            ivsGray.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsGray.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsGray.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneGray.getChildren().add(ivsGray.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。\
        }
    }
}
