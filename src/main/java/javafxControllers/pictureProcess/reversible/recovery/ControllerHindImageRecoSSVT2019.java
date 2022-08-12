package javafxControllers.pictureProcess.reversible.recovery;

import javafx.concurrent.ScheduledService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
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

public class ControllerHindImageRecoSSVT2019 {
    @FXML
    private ScrollPane SPaneContainSecretCret;

    @FXML
    private Button carrierSaveYJ;

    @FXML
    private Button buttHuiSecret;

    @FXML
    private ScrollPane SPaneOrigCarrier;

    @FXML
    private Text textRun;

    @FXML
    private Text TextoriginalCarrier;

    @FXML
    private ProgressBar pbHF;

    @FXML
    private Text TextContainSecretCarrier;

    @FXML
    private FlowPane flowPaneHFYuan;

    @FXML
    private VBox updatePane;

    @FXML
    private FlowPane flowPaneHF;

    @FXML
    private Button carrierSave;

    @FXML
    private Button secretSave;

    @FXML
    private FlowPane flowPaneOrigCarrier;

    @FXML
    private Button TrueHF;

    @FXML
    private Button secretSaveYJ;

    Utils utils = new Utils();

    public static ImageView HFYuan = new ImageView();

    public static Image imageCarrier[];

    public static Image imageShadow[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsOK = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsOKcarrier = new ArrayList<ImageView>();

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNames = new ArrayList<Text>();

    ScheduledService<Double> sche;

    @FXML
    void HFtoImageSave(ActionEvent event) throws IOException {
        if (ivsOK.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOK.size(); i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            ImageIO.write(SwingFXUtils.fromFXImage(ivsOK.get(i).getImage(), null), "png", new File(filename.get(i)));
        }

        //调用提示框函数
        Stage stage1 = (Stage) secretSave.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HFtoImageSaveYJ(ActionEvent event) throws IOException {
        if (ivsOK.isEmpty()) {
            return;
        }

        ArrayList<BufferedImage> bi = new ArrayList<BufferedImage>();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOK.size(); i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            bi.add(SwingFXUtils.fromFXImage(ivsOK.get(i).getImage(), null));
            ImageIO.write(bi.get(i), "png", new File(filename.get(i)));
        }

        Stage stage1 = (Stage) secretSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void HFcarrierSaveYJ(ActionEvent event) throws IOException {
        if (ivsOKcarrier.isEmpty()) {
            return;
        }

        ArrayList<BufferedImage> bi = new ArrayList<BufferedImage>();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOKcarrier.size(); i++) {
            StringBuffer sb = new StringBuffer("C:/Users/ASUS/Desktop/默认.png");
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            bi.add(SwingFXUtils.fromFXImage(ivsOKcarrier.get(i).getImage(), null));
            ImageIO.write(bi.get(i), "png", new File(filename.get(i)));
        }

        Stage stage1 = (Stage) carrierSaveYJ.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HFcarrierSave(ActionEvent event) throws IOException {
        if (ivsOKcarrier.isEmpty()) {
            return;
        }

        //调用文件保存函数
        File file = Main.imageSave();
        ArrayList<String> filename = new ArrayList<String>();
        for (int i = 0; i < ivsOKcarrier.size(); i++) {
            StringBuffer sb = new StringBuffer(file.getAbsolutePath());
            sb.insert(sb.length() - 4, "_" + i);
            filename.add(sb.toString());
            ImageIO.write(SwingFXUtils.fromFXImage(ivsOKcarrier.get(i).getImage(), null), "png", new File(filename.get(i)));
        }

        //调用提示框函数
        Stage stage1 = (Stage) carrierSave.getScene().getWindow();
        try {
            utils.createTipsFrame(stage1,"保存成功！",1);
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
        fps.clear();
        ivsOK.clear();
        ivsOKcarrier.clear();
        flowPaneHFYuan.getChildren().clear();
        //判断隐藏图片和载体图片是否已经选择，为空则不执行。
        if (ivsZT.isEmpty()) {
            return;
        }

        int imageCarrierNumber2 = 0;
        int imageCarrierNumber1 = 0;
        int number = 0;
        int pixelValues = 0;
        for (int theNumberPicture = 0; theNumberPicture < imageCarrier.length / 2; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得载体图像的宽高。
            int widthCarrier = (int) imageCarrier[theNumberPicture].getWidth();
            int heightCarrier = (int) imageCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            imageCarrierNumber1 = number++; // 复制的载体图像1
            imageCarrierNumber2 = number++; // 复制的载体图像2
            PixelReader pixelReaderCarrier1 = imageCarrier[imageCarrierNumber1].getPixelReader(); //读取第numImage幅载体图像的像素值。
            PixelReader pixelReaderCarrier2 = imageCarrier[imageCarrierNumber2].getPixelReader(); //读取第numImage幅载体图像的像素值。

            //获得载体图像中隐藏的恢复隐藏图的重要坐标信息。
//            int[] recoverNecessaryInf = ControllerHindImageMyMDP1.RecoverInf(widthCarrier, heightCarrier, pixelReaderCarrier1 ,pixelReaderCarrier2);
            int[] recoverNecessaryInf = Main.recoverNecessaryInf(widthCarrier, heightCarrier, pixelReaderCarrier1);
            int ShadowImageWidth = recoverNecessaryInf[0]; //影子图像的宽高。
            int ShadowImageHeight = recoverNecessaryInf[1];

            //写入像素值。
            WritableImage wi[] = new WritableImage[imageCarrier.length]; // 创建写入image的数组对象。
            PixelWriter pw[] = new PixelWriter[imageCarrier.length];//创建读取image的数组对象。

            //恢复的秘密图像像素的写入函数
            wi[theNumberPicture] = new WritableImage(recoverNecessaryInf[0], recoverNecessaryInf[1]);// 在载体图像中写入新的像素值。
            pw[theNumberPicture] = wi[theNumberPicture].getPixelWriter();//调用图像写入函数。

            //恢复的原载体图像像素的写入函数
            wi[theNumberPicture + 1] = new WritableImage(pixelReaderCarrier1, widthCarrier, heightCarrier);// 在载体图像中写入新的像素值。
            pw[theNumberPicture + 1] = wi[theNumberPicture + 1].getPixelWriter();//调用图像写入函数。

            int carrierHomei = 0;
            int carrierHomej = 0;
            int hindHomei = 0;
            int hindHomej = 0;
            int bitNumber = 7;// 二进制位数。

            //提取与恢复
            //获得两幅载体图像
            boolean flag = true;
            //步骤二 ：循环遍历图像的像素值，将图像分成1*2的非重叠块。
            while (flag) {
                //步骤三 ：将像素对分别命名为：x，y。
                if (carrierHomei == heightCarrier) break;
                int x1 = (pixelReaderCarrier1.getArgb(carrierHomej, carrierHomei)) & 0xff;
                int x2 = (pixelReaderCarrier2.getArgb(carrierHomej, carrierHomei)) & 0xff;
                pw[theNumberPicture + 1].setColor(carrierHomej, carrierHomei, Color.grayRgb((x1 + x2) / 2));

                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }

                int y1 = (pixelReaderCarrier1.getArgb(carrierHomej, carrierHomei)) & 0xff;
                int y2 = (pixelReaderCarrier2.getArgb(carrierHomej, carrierHomei)) & 0xff;
                pw[theNumberPicture + 1].setColor(carrierHomej, carrierHomei, Color.grayRgb((y1 + y2) / 2));
                pw[theNumberPicture + 1].setColor(carrierHomej, carrierHomei, Color.grayRgb((x1 + x2) / 2));
                carrierHomej++;
                if (carrierHomej == widthCarrier) {
                    carrierHomei++;
                    carrierHomej = 0;
                }

                //步骤四 ：计算像素对的修改量。
                int cx = x2 - x1;
                int cy = y2 - y1;

                //步骤五 ：使用平均法得到原像素值。
                int x = (x1 + x2) / 2;
                int y = (y1 + y2) / 2;
                if (x1 == x2 && y1 == y2) {
                    int d = Math.abs(x - y);
                    int a = TableOfDifferenceRanges(d);
                    int aa = (int) Math.ceil((a - 1) * 1.0 / 4);
                    if (x < aa || x > (255 - aa) || y < aa || y > (255 - aa)) {
                        continue;
                    }
                }

                //步骤六 ：计算原像素对的差值，然后查询差值范围表确定模函数的模a
                int d = Math.abs(x - y);
                int a = TableOfDifferenceRanges(d);
                int b = (int) (Math.log(a * a) / Math.log(2));

                //步骤七 ：计算得到嵌入信息之后的像素值，也就是f值
                int fx = x + cx;
                int fy = y + cy;

                //步骤八 ：将得到f值带入模函数公式得到嵌入信息的十进制B
                int B = Math.floorMod((fx + a * fy), a * a);

                //步骤九 ：将秘密信息B转化为b位二进制流。
                for (int k = 1; k <= b; k++) {
                    int remainder = B - (int) Math.pow(2, b - k);//对目标数字求余
                    if (remainder < 0) {
                        remainder = 0;
                    } else {
                        B = remainder;
                        remainder = 1;
                    }
                    pixelValues += remainder * Math.pow(2, bitNumber--); // 从小到大的计算2的幂次。
                    if (bitNumber < 0) { // 如果嵌入的数据bit位超过 8位，那么需要换下一个隐藏像素值。
                        bitNumber = 7;
                        pw[theNumberPicture].setColor(hindHomej, hindHomei, Color.grayRgb(pixelValues));
                        pixelValues = 0;
                        hindHomej++;
                        if (hindHomej == ShadowImageWidth) {
                            hindHomei++;
                            hindHomej = 0;
                        }
                        if (hindHomei == ShadowImageHeight && hindHomej == 0) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag == false) break;
            }

            //密图的显示框
            flowPaneHFYuan.setAlignment(Pos.CENTER);
            ivsOK.add(new ImageView());//创建一个imageview的对象。
            ivsOK.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOK.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOK.get(theNumberPicture).setImage(wi[theNumberPicture]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneHFYuan.getChildren().add(ivsOK.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
            //原在途图像的显示框
            ivsOKcarrier.add(new ImageView());//创建一个imageview的对象。
            ivsOKcarrier.get(theNumberPicture).setFitWidth(200);//设置imageview的对象的宽度。
            ivsOKcarrier.get(theNumberPicture).setPreserveRatio(true);//imageview的对象按原比例显示图片。
            ivsOKcarrier.get(theNumberPicture).setImage(wi[theNumberPicture+1]);//将创建的用于影子图像写入像素值的空白区域，放入imageview显示出来。
            flowPaneOrigCarrier.getChildren().add(ivsOKcarrier.get(theNumberPicture));//将imageview的集合放入根节点中，显示出来。
        }
    }
    public static int TableOfDifferenceRanges(int difference) {
        int a = 0; // 模函数的模a
        //判断差值在哪一个范围内
        if (difference >= 0 && difference <= 15) {
            a = 3;
        } else if (difference >= 16 && difference <= 31) {
            a = 5;
        } else if (difference >= 32 && difference <= 63) {
            a = 7;
        } else if (difference >= 64 && difference <= 127) {
            a = 11;
        } else if (difference >= 128 && difference <= 255) {
            a = 13;
        }
        return a;
    }
}
