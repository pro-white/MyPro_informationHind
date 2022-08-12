package javafxControllers.pictureProcess.others;


import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafxControllers.Main;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller8bit_2value {
    @FXML
    private ProgressBar pbBF;

    @FXML
    private Button buttonImageCLSaveYJ;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Button True;

    @FXML
    private Button buttonImageCLSave;

    @FXML
    private FlowPane flowPaneYuan;

    @FXML
    private Button buttChaiSecret;

    @FXML
    private Stage stage;

    Utils utils = new Utils();

    ScheduledService<Double> sche;

    public static ImageView Yuan = new ImageView();

    public static Image image;

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>();

    @FXML
    void toImageSave(ActionEvent event) throws IOException { //图像的保存
        if (ivs.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        String fileName[] = new String[1];
        for (int i = 0; i < fileName.length; i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 4, "_" + i);
            fileName[i] = sb.toString();
            ImageIO.write(SwingFXUtils.fromFXImage(ivs.get(i).getImage(), null), "png", new File(fileName[i]));
        }

        //调用提示框函数
        Stage stage1 = (Stage) buttonImageCLSave.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toImageSaveYJ(ActionEvent event) throws IOException { //一键图像的保存
        if (ivs.isEmpty()) {
            return;
        }

        BufferedImage bi[] = new BufferedImage[8];
        String fileName[] = new String[8];
        for (int i = 0; i < fileName.length; i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.jpg");
            sb.insert(sb.length() - 4, "_" + i);
            fileName[i] = sb.toString();
            bi[i] = SwingFXUtils.fromFXImage(ivs.get(i).getImage(), null);
            ImageIO.write(bi[i], "png", new File(fileName[i]));
        }

        Stage stage1 = (Stage) buttonImageCLSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tobuttChaiSecret(ActionEvent event) throws IOException { //图像的拆分
        ivs.clear();//清除imageview列表中的元素
        flowPane.getChildren().clear();
        flowPaneYuan.getChildren().clear();

        FileChooser fc = null;
        File file = null;

        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            stage = new Stage(); //创建一个窗口
            fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片单选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop\\image"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"));
            file = fc.showOpenDialog(stage);//会打开文件窗口，返回一个文件的绝对路径 注意：这一句一定是放在最后。

            if (file == null) { //如果返回的文件不为空，那才打印文件的绝对路径
                return;
            }
            //进行一个判断，是否确认选的图片。
            Main.framePointReturn("确定是此图片吗？", flagtu);
        }

        //将选择的图片放入imageview中显示。
        FileInputStream in = new FileInputStream(file);
        image = new Image(in);
        Yuan.setImage(image);
        Yuan.setFitWidth(240);
        Yuan.setPreserveRatio(true);
        flowPaneYuan.getChildren().add(Yuan);
        flowPaneYuan.setAlignment(Pos.CENTER);

        in.close();
    }

    @FXML
    void TrueCF(ActionEvent event) { //确定程序的启动
        if (Yuan.getImage() == null) {
            return;
        }

        int split_k = 8;

        int width = (int) image.getWidth(); //获取原图片的宽
        int height = (int) image.getHeight();//获取原图片的高
        WritableImage wi[] = new WritableImage[split_k];
        PixelWriter pw[] = new PixelWriter[split_k];

        for (int i = 0; i < split_k; i++) {
            wi[i] = new WritableImage(width, height);
            ivs.add(new ImageView());
            ivs.get(i).setFitWidth(200);
            ivs.get(i).setPreserveRatio(true);
            ivs.get(i).setImage(wi[i]);//将创建的空白照片放在一个照片显示区域。
            pw[i] = wi[i].getPixelWriter();
            flowPane.getChildren().add(ivs.get(i));
        }


//        PixelReader pixelReader = image.getPixelReader(); //读取原图的像素值
//        int value = 0;
//        int rgbDecimal = 0;
//        int b = 0;
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                value = pixelReader.getArgb(j, i);//获取对应坐标的像素值。
//                rgbDecimal = value & 0xff; // 将获得rgb值转为十进制。
//                pw[0].setColor(j, i, Color.grayRgb(rgbDecimal));
//            }
//        }


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
                                bitYuan(image, height, width, pw);
                                num++;
                                break;
                            default:
                                num++;
                        }
                        super.updateValue(value);
                        pbBF.setProgress(value);
                        if (value >= 1) {
                            sche.cancel();
                            Stage stage1 = (Stage) buttChaiSecret.getScene().getWindow();
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

    public static void bitYuan(Image image, int height, int width, PixelWriter pw[]) {
        PixelReader pixelReader = image.getPixelReader(); //读取原图的像素值
        int value = 0;
        int rgbDecimal = 0;
        int b = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                value = pixelReader.getArgb(j, i);//获取对应坐标的像素值。
                rgbDecimal = value & 0xff; // 将获得rgb值转为十进制。
                for (int bit = 0; bit <= 7; bit++) {
                    b = (rgbDecimal >>> bit) & 1; // 与1&取最低位，并保证最低位为0或1
                    if (b == 0) {
                        pw[bit].setColor(j, i, Color.grayRgb(0));
                    } else {
                        pw[bit].setColor(j, i, Color.grayRgb((int) Math.pow(2, bit)));
                    }
                }
            }
        }
    }
}
