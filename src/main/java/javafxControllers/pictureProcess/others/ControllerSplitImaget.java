package javafxControllers.pictureProcess.others;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafxControllers.Main;
import secretSharing.Area;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerSplitImaget {
    @FXML
    private Stage stage;

    @FXML
    private TextField fieSceretR;

    @FXML
    private ProgressBar pbCF;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Text textRun;

    @FXML
    private TextField fieSceretN;

    @FXML
    private Button True;

    @FXML
    private Button buttonImageCLSave;

    @FXML
    private VBox updatePane;

    @FXML
    private FlowPane flowPaneYuan;

    @FXML
    private Button buttonImageCLSaveYJ;

    @FXML
    private Text textN;

    @FXML
    private Text textR;

    @FXML
    private Text textYZ;

    @FXML
    private Button buttChaiSecret;

    Utils utils = new Utils();

    Task copyWorker;

    ScheduledService<Double> sche;

    public static ImageView Yuan = new ImageView();//用于显示秘图的imageView的声明

    public static Image image; //用于存储秘图。

    public static ArrayList<ImageView> ivs = new ArrayList<ImageView>(); //用于显示影子图片的imageview的列表声明。

    @FXML
    void fieSceretNChange(ActionEvent event) { //控制n得文本输入内容的数据类型。
        fieSceretN.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fieSceretN.setTextFormatter(new TextFormatter<Integer>(new StringConverter<Integer>() {
                    @Override
                    public String toString(Integer object) {
                        return String.valueOf(object);
                    }

                    @Override
                    public Integer fromString(String string) {
                        int numN = Integer.valueOf(string);
                        return numN; //如果输入的不是一个整数类型，会报错。
                    }
                }));
                fieSceretN.commitValue();
            }
        });
    }

    @FXML
    void fieSceretRChange(ActionEvent event) {//控制r的文本输入的数据类型
        fieSceretR.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fieSceretR.setTextFormatter(new TextFormatter<Integer>(new StringConverter<Integer>() {
                    @Override
                    public String toString(Integer object) {
                        return String.valueOf(object);
                    }

                    @Override
                    public Integer fromString(String string) {
                        return Integer.valueOf(string); //如果输入的不是一个整数类型，会报错。
                    }
                }));
                fieSceretR.commitValue();
            }
        });
    }

    @FXML
    void toImageSave(ActionEvent event) throws IOException { //保存拆分好的图片。
        if (ivs.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        String fileName[] = new String[Integer.parseInt(fieSceretN.getText())];
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
    void toImageSaveYJ(ActionEvent event) throws IOException { //一键保存拆分好的图片在桌面上。
        if (ivs.isEmpty()) {
            return;
        }

        BufferedImage bi[] = new BufferedImage[Integer.parseInt(fieSceretN.getText())];
        String fileName[] = new String[Integer.parseInt(fieSceretN.getText())];
        for (int i = 0; i < fileName.length; i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
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
    void tobuttChaiSecret(ActionEvent event) throws IOException {//选择的图片
        ivs.clear();//清除imageview列表中的元素
        flowPane.getChildren().clear();//清除影子图像根节点的里面的子节点
        flowPaneYuan.getChildren().clear();//清除秘图图像的根节点的里面的子节点

        // 提示用户输入拆分秘图的R 和 N 值。
        if (fieSceretR.getText().trim().isEmpty() || fieSceretN.getText().trim().isEmpty() || Integer.parseInt(fieSceretN.getText()) <= Integer.parseInt(fieSceretR.getText())) {
            Stage stage1 = (Stage) buttChaiSecret.getScene().getWindow();
            try {
                utils.createTipsFrame(stage1,"请填写右下方：                 1.拆分的影子图像数量 n   2.恢复图像的门限值 r       注意： n > r",0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        FileChooser fc = null; //声明文件选的
        File file = null;//声明文件。

        //选择秘图。
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

        //将秘图放入imageview中显示。
        FileInputStream in = new FileInputStream(file);//创建一个输入文件流。
        image = new Image(in);//将文件，写入新建的image的对象。
        Yuan.setImage(image);//将image写入秘图的imageview中显示。
        if (image.getHeight() / image.getWidth() > 3.0 / 4) {
            Yuan.setFitHeight(180);
        } else Yuan.setFitWidth(240);
        Yuan.setPreserveRatio(true);//设置imageview的宽高按原比例来显示。
        flowPaneYuan.getChildren().add(Yuan);//将秘图的imageview放入根节点flowpane中布局。
        flowPaneYuan.setAlignment(Pos.CENTER);//将根节点中的子节点居中显示。

        in.close();//关闭流
    }

    @FXML
    void TrueCF(ActionEvent event) throws IOException {
        if (Yuan.getImage() == null) {//判断用户是否选择秘图。没有就直接结束，不进行下一步。
            return;
        }
//
//        Stage stage2 = (Stage) buttChaiSecret.getScene().getWindow();
//        try {
//            utils.createRunTipsFrame(stage2,"运行中，请稍等。。。");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println("运行中。。。。。");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.titleProperty().set("提示框：");
        alert.headerTextProperty().set("点击成功，正在运行中。。。。。");
        alert.show();

        int split_k = Integer.parseInt(fieSceretN.getText());
        int kk = Integer.parseInt(fieSceretR.getText());
        //获取像素值。

        int secretImageWidth = (int) image.getWidth(); //获取原图片的宽
        int secretImageHeight = (int) image.getHeight();//获取原图片的高
        int CFHeight = (secretImageHeight + (kk - secretImageHeight % kk) % kk) / kk; //影子图片的高度

        WritableImage wi[] = new WritableImage[split_k];
        PixelWriter pw[] = new PixelWriter[split_k];

        for (int i = 0; i < split_k; i++) {
            wi[i] = new WritableImage(secretImageWidth, CFHeight);
            ivs.add(new ImageView());
            ivs.get(i).setFitWidth(200);
            ivs.get(i).setPreserveRatio(true);
            ivs.get(i).setImage(wi[i]);//将创建的空白照片放在一个照片显示区域。
            pw[i] = wi[i].getPixelWriter();
            flowPane.getChildren().add(ivs.get(i));
        }

        /*
             1. 读取用户输入的R值，然后读取R个坐标像素值。（这里需要三个循环变量，两个是坐标值作为外层循环，一个是R作为内层循环。）
             2. 将R个像素值读取之后，用16，8，0三个作为颜色像素值的取值逻辑运算值（这里需要一个循环遍历，是incol的值的循环）
             3. 读取的颜色像素值存入一个列表中，用多项式运算得到新的颜色像素值。
         */
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
                                SplitImage(kk, secretImageWidth, secretImageHeight, pw, split_k, CFHeight);
                                num++;
                                break;
                            default:
                                num++;
                        }
                        super.updateValue(value);
                        pbCF.setProgress(value);
                        if (value >= 1) {
                            sche.cancel();
//                            stage2.hide();
                            alert.close();
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

    @FXML
    void splitProBar(ActionEvent event) {

    }

    public static void SplitImage(int kk, int secretImageWidth, int secretImageHeight, PixelWriter pw[], int split_k, int CFHeight) {
        boolean flag = true;
        int iSecretImage = 0; //遍历秘图坐标像素值的坐标值。
        int jSecretImage = 0;
        int iShadowImage = 0; //遍历影子图像坐标像素值的坐标值。
        int jShadowImage = 0;
        int mold = 251;
        PixelReader pixelReader = image.getPixelReader(); //读取原图的像素值
        while (flag) { //用于循环R和N的不断取值，当秘图像素值被取完就结束循环。
            ArrayList<Integer> redDecimal = new ArrayList<>(); //创建用于存储取出的坐标像素值的各自的颜色像素值。
            ArrayList<Integer> greedDecimal = new ArrayList<>();
            ArrayList<Integer> blueDecimal = new ArrayList<>();
            for (int R = 0; R < kk; R++) {// 取R个坐标像素值。
                int rgbValue = pixelReader.getArgb(iSecretImage, jSecretImage);//读取秘图的坐标像素值。
                for (int intcol = 16; intcol >= 0; intcol -= 8) {//读取一个坐标像素值的三个颜色像素值。
                    if (intcol == 16) {//判断进行一个像素值中的第几个rgb进行进制换算。
                        redDecimal.add(Area.Judgesub((rgbValue >> intcol) & 0xff, 0, mold));
                    } else if (intcol == 8) {
                        greedDecimal.add(Area.Judgesub((rgbValue >> intcol) & 0xff, 0, mold));
                    } else if (intcol == 0) {
                        blueDecimal.add(Area.Judgesub((rgbValue >> intcol) & 0xff, 0, mold));
                    }
                }
                iSecretImage++;
                if (iSecretImage == secretImageWidth) { //当一行坐标像素值被取完就换下一行。
                    jSecretImage++;
                    iSecretImage = 0;
                }
                if (jSecretImage == secretImageHeight) { // 如果当像素值都取完了，但是不足R个像素值，那么就补零作为多项式系数。
                    if (redDecimal.size() != 0 || redDecimal.size() != kk) {
                        int number = redDecimal.size(); //记录redDecimal的大小。
                        for (int supplyZero = 0; supplyZero < (kk - number); supplyZero++) {
                            redDecimal.add(0);
                            greedDecimal.add(0);
                            blueDecimal.add(0);
                        }
                    }
                    flag = false; //用于跳出while循环
                    break;//跳出R的循环。
                }
            }

            //各个颜色的R个多项式系数值已经取好，确定多项式进行带入值产生新的颜色像素值。
            for (int x = 101, numImage = 0; numImage < split_k; x = (x + 30) % 251, numImage++) { //用于带入多项式的x值，用于之后恢复时带入的x值，需要隐藏在影子图像中。
                int redDecimalLast = 0;
                int greedDecimalLast = 0;
                int blueDecimalLast = 0;
                for (int j = 0; j < kk; j++) {
                    redDecimalLast += (redDecimal.get(j) * Math.pow(x, j));
                    greedDecimalLast += (greedDecimal.get(j) * Math.pow(x, j));
                    blueDecimalLast += (blueDecimal.get(j) * Math.pow(x, j));
                }
                redDecimalLast %= mold; //将数值取模
                greedDecimalLast %= mold; //将数值取模
                blueDecimalLast %= mold; //将数值取模
                pw[numImage].setColor(iShadowImage, jShadowImage, Color.rgb(redDecimalLast, greedDecimalLast, blueDecimalLast));
                pw[numImage].setColor(secretImageWidth - 1, CFHeight - 1, Color.rgb(x, x, x));
            }
            iShadowImage++;
            if (iShadowImage == secretImageWidth) { //当一行坐标像素值被取完就换下一行。
                jShadowImage++;
                iShadowImage = 0;
            }
        }
    }

    public static boolean formatSystem() {
        return false;
    }
}

