package javafxControllers.pictureProcess.others;

import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class Z_ImageSplit_YUVController {
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
            utils.createTipsFrame(stage1, "保存成功！", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toImageSaveYJ(ActionEvent event) throws IOException { //一键图像的保存
        if (ivs.isEmpty()) {
            return;
        }

        BufferedImage bi[] = new BufferedImage[1];
        String fileName[] = new String[1];
        for (int i = 0; i < fileName.length; i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
            sb.insert(sb.length() - 4, "_" + i);
            fileName[i] = sb.toString();
            bi[i] = SwingFXUtils.fromFXImage(ivs.get(i).getImage(), null);
            ImageIO.write(bi[i], "png", new File(fileName[i]));
        }

        Stage stage1 = (Stage) buttonImageCLSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1, "保存成功！", 1);
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
//        Stage stage2 = (Stage) buttChaiSecret.getScene().getWindow();
//        try {
//            utils.createRunTipsFrame(stage2,"请稍等。。。");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        int split_k = 1;

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

        /*
                    1.将图像分解为8*8的图像块
                    2.将表示像素的RGB系统转换成YUV系统
                    3.然后从左至右，从上至下对每个图像块做DCT变换，舍弃高频分量，保留低频分量
                    4.对余下的图像块进行量化压缩，由压缩后的数据所组成的图像大大缩减了存储空间
                    5.解压缩时对每个图像块做DCT反转换（IDCT），然后重建一幅完整的图像
         */

        double[][] redLast = new double[width][height]; // 将原图的各个的RGB值转换为DCT系数
        double[][] greedLast = new double[width][height];
        double[][] buleLast = new double[width][height];
        PixelReader pixelReader = image.getPixelReader(); //读取原图的像素值
        int value = 0;
        int y = 0;
        int Cb = 0;
        int Cr = 0;
        int b = 0;

        //将原图进行分块，分成8*8大小的。 （分割之前先将图像处理成宽高都可以被8整除。）
        int waitDicedWidth = 0; // 原图被补足成8的倍数的宽高。
        int waitDicedHeight = 0;
        if (width % 8 != 0) { // 将原图的宽处理成可以被8整除的数据（用的是补足法）
            waitDicedWidth = width + (8 - width % 8);
        } else {
            waitDicedWidth = width;
        }

        if (height % 8 != 0) { // 将原图的高处理成可以被8整除的数据（用的是补足法）
            waitDicedHeight = height + (8 - height % 8);
        } else {
            waitDicedHeight = height;
        }

        int blocki = 0;
        int blockj = 0;
        boolean flag = true;
        while (flag) {// 用于循环整个图像
            int[][] redYuv = new int[width][height]; //存储原图的各个RGB值处理成YUV
            int[][] greedYuv = new int[width][height];
            int[][] blueYuv = new int[width][height];

            // 如何循环遍历每一块分块？
            for (; blockj < waitDicedHeight; ) {
                for (; blocki < waitDicedWidth; blocki++) {
                    value = pixelReader.getArgb(blockj, blocki);//获取对应坐标的像素值。
                    // 将RGB值处理成YUV值的函数。
                    // ?????
//                    Y =  0.299*R + 0.587*G + 0.114*B;

//                    U = -0.169*R - 0.331*G + 0.5  *B ;

//                    V =  0.5  *R - 0.419*G - 0.081*B;

                    blocki++;
                    if (blocki % 8 == 0) { // 因为是先调用在++所以，blocki不需要加一之后去取除8的余数。
                        blockj++;
                        blocki -= 8;
                    }
                    if (blockj % 8 == 0){
                        blockj -= 8;
                        
                    }
                }
            }

            if (blocki == waitDicedHeight && blockj == waitDicedWidth) {// 如果切分到了最后一块处理之后，就跳出循环。
                flag = false;
            }
        }
        while ((blocki + 1) % 8 != 0) {

        }


//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                value = pixelReader.getArgb(i, j);//获取对应坐标的像素值。
////                y = (77 * ((value >> 16) & 0xff) + 150 * ((value >> 8) & 0xff) + 29 * (value & 0xff)) >> 8;
////                pw[0].setColor(j, i, Color.rgb(y, 128, 128));
////                Cb = ((-44 * ((value >> 16) & 0xff) - 87 * ((value >> 8) & 0xff) + 131 * (value & 0xff)) >> 8) + 128;
////                pw[1].setColor(j, i, Color.rgb(0, Cb, 0));
////                Cr = ((131 * ((value >> 16) & 0xff) - 110 * ((value >> 8) & 0xff) - 21 * (value & 0xff)) >> 8) + 128;
////                pw[2].setColor(j, i, Color.rgb(0, 0, Cr));
//                red[i][j] = (value >> 16) & 0xff;
//                System.out.println(red[i][j]);
//                greed[i][j] = (value >> 8) & 0xff;
//                blue[i][j] = value & 0xff;
//            }
//            System.out.println();
//        }
    }
}
