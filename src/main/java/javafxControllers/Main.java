package javafxControllers;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafxControllers.pictureProcess.others.ControllerSplitImaget;
import secretSharing.Area;
import utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

//    public static Stage stage;

    Utils utils;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(); //创建一个FXML的对象
        fxmlLoader.setLocation(getClass().getResource("/javafx/Entry.fxml"));//通过FXML的对象来实现调用文件fxml
        Parent root = fxmlLoader. load();//创建一个根节点的对象，通过在FXML的对象。
        Scene scene = new Scene(root); //将parent根节点放入到场景里面。

        ImageView miniImage = (ImageView) root.lookup("#minimizeImage");
        utils = new Utils();

        utils.setMouseEnteredHand(miniImage);
        miniImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标点击refreshMemberImage！");
                Stage mainStage = (Stage) root.getScene().getWindow();
                mainStage.setIconified(true);

            }
        });
        //设置鼠标点击stage页面实现拖拽功能
        utils.setMouseDragFun(scene, stage);

        stage.setScene(scene); //将场景放入窗口

        stage.initStyle(StageStyle.UNDECORATED);//隐藏stage顶部状态栏

        stage.getIcons().add(new Image("file:src/main/resources/images/logo.png"));//窗口的logo图标。
        stage.setResizable(false);//控制窗口的大小，不能改变。
        stage.setTitle("打开"); //设置窗口的名称
        stage.show();//将窗口显示出来。
    }
    //冒泡排序
    public static ArrayList<ArrayList<Integer>> BubbleSort(ArrayList<ArrayList<Integer>> key) {
        for (int i = 0; i < key.size() - 1; i++) {//外层循环控制排序趟数
            for (int j = 0; j < key.size() - 1 - i; j++) {//内层循环控制每一趟排序多少次
                if (key.get(j).get(0) > key.get(j + 1).get(0)) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp = key.get(j);
                    key.set(j, key.get(j + 1));
                    key.set(j + 1, temp);
                }
            }
        }
        return key;
    }

    //随机产生密钥。
    public static int[] ran_key(int key[], int split_k) {
        int index = 0;
        ArrayList keynum = new ArrayList();
        for (int j = 0; j < split_k * 4; j++) {
            keynum.add(j);
        }
        System.out.print("key = ");
        Random random = new Random();
        for (int i = 0; i < split_k * 3; i++) {
            index = random.nextInt(keynum.size());
            key[i] = (int) keynum.get(index);//将rgb值存储在a[]数组里面，后面会作为多项式的系数。
            System.out.print(key[i] + " ");
            keynum.remove(index);//删除已经被使用的rgb值
        }
        System.out.println();
        return key;
    }



    //可以返回的提示框
    public static void framePointReturn(String s, AtomicBoolean flagtu) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set("确定是此图片吗？");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                flagtu.set(ControllerSplitImaget.formatSystem());
            }
        });
    }

    //文件保存
    public static File imageSave() {
        Stage stage1 = new Stage();
        FileChooser fc = new FileChooser();
        fc.setTitle("文件保存");
        fc.setInitialFileName("图");
        fc.setInitialDirectory(new File("C:\\Users\\ASUS\\Desktop"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("文件类型", "*.jpg")
        );

        File file = fc.showSaveDialog(stage1);
        if (file == null) {
            return null;
        }

        return file;
    }

    //文件读取并显示
    public static void fileShow(List<File> listfile, FileInputStream in[], Image image[], ArrayList<ImageView> ivs, ArrayList<FlowPane> fps, ArrayList<Text> textimgNames, PixelReader pr[], FlowPane flowPane) throws IOException {
        for (int i = 0; i < listfile.size(); i++) {
            in[i] = new FileInputStream(listfile.get(i));
            image[i] = new Image(in[i]);
            ivs.add(new ImageView());
            ivs.get(i).setFitWidth(200);
            ivs.get(i).setPreserveRatio(true);
            ivs.get(i).setImage(image[i]);//将创建的空白照片放在一个照片显示区域。
            fps.add(new FlowPane());
            textimgNames.add(new Text());
            pr[i] = image[i].getPixelReader();
            fps.get(i).setHgap(10);
            fps.get(i).setVgap(10);
            fps.get(i).setMaxWidth(200);
            StringBuffer sb = new StringBuffer(listfile.get(i).getAbsolutePath());
            sb.reverse();
            StringBuffer sb1 = new StringBuffer(sb.substring(0, sb.indexOf("\\")));
            textimgNames.get(i).setText(sb1.reverse().toString());
            fps.get(i).getChildren().addAll(ivs.get(i), textimgNames.get(i));
            flowPane.getChildren().add(fps.get(i));

            in[i].close();
        }
    }

//    //文件读取并显示
//    public static void fileShow1(List<File> listfile, FileImageInputStream in[], Image image[], ArrayList<ImageView> ivs, ArrayList<FlowPane> fps, ArrayList<Text> textimgNames, PixelReader pr[], FlowPane flowPane) throws IOException {
//        for (int i = 0; i < listfile.size(); i++) {
//            ImageReader reader = ImageIO.getImageReadersByFormatName("tif").next();
//            FileImageInputStream inputStream = new FileImageInputStream(listfile.get(i));
//
//            reader.setInput(inputStream);
//            System.out.println("shiyishi = " +reader.setInput(inputStream));
//            in[i] = new FileImageInputStream(listfile.get(i));;
////            in[i] = new FileInputStream(listfile.get(i));
//            image[i] = new Image(String.valueOf(in[i]));
//            ivs.add(new ImageView());
//            ivs.get(i).setFitWidth(200);
//            ivs.get(i).setPreserveRatio(true);
//            ivs.get(i).setImage(image[i]);//将创建的空白照片放在一个照片显示区域。
//            fps.add(new FlowPane());
//            textimgNames.add(new Text());
//            pr[i] = image[i].getPixelReader();
//            fps.get(i).setHgap(10);
//            fps.get(i).setVgap(10);
//            fps.get(i).setMaxWidth(200);
//            StringBuffer sb = new StringBuffer(listfile.get(i).getAbsolutePath());
//            sb.reverse();
//            StringBuffer sb1 = new StringBuffer(sb.substring(0, sb.indexOf("\\")));
//            textimgNames.get(i).setText(sb1.reverse().toString());
//            fps.get(i).getChildren().addAll(ivs.get(i), textimgNames.get(i));
//            flowPane.getChildren().add(fps.get(i));
//
//            in[i].close();
//        }
//    }

    //隐藏恢复需要的重要信息
    public static int[] recoverNecessaryInf(int widthCarrier, int heightCarrier, PixelReader pixelReaderCarrier) {
        int[] recoverNecessaryInf = new int[5];//用于存储恢复隐藏图像需要使用的信息（如：隐藏图像的原宽高，载体图像隐藏结束的位置）
        int numRecoverNecessaryInf = 0;
        long num = 0;

        //先获得每一张载体图像中最后隐藏的重要恢复信息。
        for (int recoverInfNum = widthCarrier - 20; recoverInfNum < widthCarrier; recoverInfNum++) { //遍历最后一行的最后20列的坐标像素值。
            for (int intColorCarrierLast = 16; intColorCarrierLast >= 0; intColorCarrierLast -= 8) {
                int DecimalCarrierLast = ((pixelReaderCarrier.getArgb(recoverInfNum, heightCarrier - 1)) >> intColorCarrierLast) & 0xff;//判断进行一个像素值中的第几个rgb进行进制换算。
                    /*
                              // 从bit中还原int(文本的字节数组长度)
                              // 1. (valueCarrierRGB & 1)取valueCarrierRGB的最低一比特位
                              // 2. recoverNecessaryInf[numRecoverNecessaryInf] << 1 左移
                              // 3. (recoverNecessaryInf[numRecoverNecessaryInf] << 1) | (valueCarrierRGB & 1) -> 把取出的每个bit通过|操作，累加到recoverNecessaryInf[num]
                     */
                recoverNecessaryInf[numRecoverNecessaryInf] = (recoverNecessaryInf[numRecoverNecessaryInf] << 1) | (DecimalCarrierLast & 1);
                num++;
            }
            if (num % 12 == 0) {
                numRecoverNecessaryInf++;
            }
        }
        return recoverNecessaryInf;
    }

    //读取图片的像素并且将对应的颜色的rgb值存入列表中
    public static ArrayList<Integer> getArgbMy(int height, int width, PixelReader pixelReader, int mold, int intcol) {
        int value = 0;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                value = pixelReader.getArgb(j, i);//获取对应坐标的像素值。imageSecretText.image.getPixelReader()
                if (intcol == 16) {
                    arr.add(Area.Judgesub((value >> intcol) & 0xff, 0, mold));
                } else if (intcol == 8) {
                    arr.add(Area.Judgesub((value >> intcol) & 0xff, 0, mold));
                } else {
                    arr.add(Area.Judgesub((value >> intcol) & 0xff, 0, mold));
                }
            }
        }
        return arr;
    }

    //十进制转为二进制
    public static int decimalToBinary(int a) {// a 为定义一个变量并赋给他一个十进制的值。
        int remainder;//定义一个变量用于存储余数
        int sum = 0;//定义一个变量用于存放和
        int k = 1;//定义一个变量控制位数
        while (a != 0) {
            remainder = a % 2;//对目标数字求余
            a /= 2;//对目标数字求商
            sum = sum + remainder * k;//求和
            k *= 10;//改变位数
        }
        return sum;
    }

    //将整数的每一位值，都单个的存入列表中（例如：整数：245，那么就是{2，4，5}
    public static ArrayList<Integer> intTurnArr(int Decimal) {
        ArrayList<Integer> Binary = new ArrayList<Integer>();//存储处理的每个像素值中的每一个rgb值。
        ArrayList<Integer> BinaryNew = new ArrayList<Integer>();//存储处理的每个像素值中的每一个rgb值。
        String s = String.valueOf(Decimal);
        int len = s.length();
        int m = 1;
        for (int i = 0; i < len; i++) {
            Binary.add(Decimal / m % 10);
            m *= 10;
        }
        for (int i = 0; i < len; i++) {
            BinaryNew.add(Binary.get(len - (i + 1)));
        }
        while (BinaryNew .size() != 8) {
            BinaryNew.add(0, 0);
        }
        Binary.clear();
        for (int i = 0 ; i < BinaryNew.size() ; i ++){
            Binary.add(BinaryNew.get(7 - i));
        }
        return Binary;
    }
}
