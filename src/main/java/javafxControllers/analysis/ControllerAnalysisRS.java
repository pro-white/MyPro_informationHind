package javafxControllers.analysis;

import javafx.concurrent.ScheduledService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
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

public class ControllerAnalysisRS {

    @FXML
    private ProgressBar pbBF;

    @FXML
    private Text textRun;

    @FXML
    private Button True;

    @FXML
    private Text textSecretCarrier;

    @FXML
    private Text textSSIM;

    @FXML
    private FlowPane flowPaneSecretCarrier;

    @FXML
    private Button buttchooseSecretCarrier;

    @FXML
    private FlowPane flowPaneChart;

    Utils utils = new Utils();

    public static Image imageSecretCarrier[];

    public static FileInputStream in[];

    public static PixelReader pr[];

    public static ArrayList<ImageView> ivsZT = new ArrayList<ImageView>();

    public static ArrayList<FlowPane> fps = new ArrayList<FlowPane>();

    public static ArrayList<Text> textimgNamesSecretCarrier = new ArrayList<Text>();

    final NumberAxis xAxis = new NumberAxis(0, 100, 10);//定义一个x轴(数字横轴)

    final NumberAxis yAxis = new NumberAxis(0, 0.3, 0.03);//定义一个y轴(数字纵轴)

    final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);//定义一个折线图，将前文定义的x轴和y轴对象挂载上

    ScheduledService<Double> sche;

    @FXML
    void tobuttchooseSecretCarrier(ActionEvent event) throws IOException {
        ivsZT.clear();
        fps.clear();
        flowPaneSecretCarrier.getChildren().clear();

        List<File> listfile = null;
        AtomicBoolean flagtu = new AtomicBoolean(true);
        while (flagtu.get()) {
            Stage stage = new Stage(); //创建一个场景
            FileChooser fc = new FileChooser(); //创建一个file的对象
            fc.setTitle("图片多选选择");//为打开文件右上角的窗口命名。
            fc.setInitialDirectory(new File("C:"));//这是指定打开文件的路径
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png", "*.tiff"));
            listfile = fc.showOpenMultipleDialog(stage); //多选文件，返回的是一个列表
            if (listfile == null) {
                return;
            }
            //调用提示框函数
            Main.framePointReturn("确定是这些图片吗？", flagtu);
        }

        imageSecretCarrier = new Image[listfile.size()];
        in = new FileInputStream[listfile.size()];
        pr = new PixelReader[listfile.size()];
        //调用文件读取并显示函数
        Main.fileShow(listfile, in, imageSecretCarrier, ivsZT, fps, textimgNamesSecretCarrier, pr, flowPaneSecretCarrier);
    }

    @FXML
    void TrueAnalysisRS(ActionEvent event) {
        XYChart.Series<Number, Number> xyRm = new XYChart.Series<Number, Number>();//定义式创建一个数字序列
        XYChart.Series<Number, Number> xySm = new XYChart.Series<Number, Number>();//定义式创建一个数字序列
        XYChart.Series<Number, Number> xyR_m = new XYChart.Series<Number, Number>();//定义式创建一个数字序列
        XYChart.Series<Number, Number> xyS_m = new XYChart.Series<Number, Number>();//定义式创建一个数字序列
        xyRm.setName("Rm");//数字序列添加标题名称
        xySm.setName("Sm");//数字序列添加标题名称
        xyR_m.setName("R_m");//数字序列添加标题名称
        xyS_m.setName("S_m");//数字序列添加标题名称

        //设置掩码算子M
        int[] M1 = {1, 0, 1, 0};
        int[] M2 = {-1, 0, -1, 0};
        int num = 0;

        for (int theNumberPicture = 0; theNumberPicture < imageSecretCarrier.length; theNumberPicture++) { //按顺序获得用户选择的图像。
            // 获得原载体图像的宽高。
            int widthOrigCarrier = (int) imageSecretCarrier[theNumberPicture].getWidth();
            int heightOrigCarrier = (int) imageSecretCarrier[theNumberPicture].getHeight();

            // 读取像素值。
            PixelReader pReaderSecretCarrier = imageSecretCarrier[theNumberPicture].getPixelReader(); //读取第numImage幅隐藏图像的像素值。

            int carrierHomei = 0;
            int carrierHomej = 0;
            int n = 4;
            double Rm = 0;
            double Sm = 0;
            double R_m = 0;
            double S_m = 0;
            int regularNUmberm = 0;// 正则组
            int singularNumberm = 0;// 奇异组
            int regularNUmber_m = 0;// 正则组
            int singularNumber_m = 0;// 奇异组
            int area = widthOrigCarrier * heightOrigCarrier;

            int[] g = new int[n];
            boolean flag = true;
            while (flag) {
                if (carrierHomei == heightOrigCarrier) break;
                for (int i = 0; i < n; i++) {
                    g[i] = pReaderSecretCarrier.getArgb(carrierHomej, carrierHomei) & 0xff;
                    carrierHomej++;
                    if (carrierHomej == widthOrigCarrier) {
                        carrierHomej = 0;
                        carrierHomei++;
                    }
                    if (carrierHomei == heightOrigCarrier) break;
                }
                int[] FmReverse = Fm(g, M1, n); //执行反转函数1
                int[] F_mReverse = F_m(g, M2, n); // 执行反转函数2
                int f = fValue(g, n);//将反转函数得到值，进行f函数运算，得到相关性 ，G 属于 R -> f(F(G)) > f(G)
                int fm = fValue(FmReverse, n);
                if (fm > f) {
                    regularNUmberm++;
                } else if (fm < f) {
                    singularNumberm++;
                }
                int f_m = fValue(F_mReverse, n);
                if (f_m > f) {
                    regularNUmber_m++;
                } else if (f_m < f) {
                    singularNumber_m++;
                }
            }
            Rm = regularNUmberm * 1.0 / area;
            Sm = singularNumberm * 1.0 / area;
            R_m = regularNUmber_m * 1.0 / area;
            S_m = singularNumber_m * 1.0 / area;
            xyRm.getData().add(new XYChart.Data(num, Rm));
            xySm.getData().add(new XYChart.Data(num, Sm));
            xyR_m.getData().add(new XYChart.Data(num, R_m));
            xyS_m.getData().add(new XYChart.Data(num, S_m));
            System.out.println("Rm = " + Rm);
            System.out.println("Sm = " + Sm);
            System.out.println("R_m = " + R_m);
            System.out.println("S_m = " + S_m);
            num += 10;
        }

        lineChart.getData().add(xyRm);
        lineChart.getData().add(xySm);
        lineChart.getData().add(xyR_m);
        lineChart.getData().add(xyS_m);

//        xyRm.getData().forEach(new Consumer<XYChart.Data<Number, Number>>() {
//            @Override
//            public void accept(XYChart.Data<Number, Number> t) {
//                Tooltip tip = new Tooltip("(" + t.getXValue() + " , " + t.getYValue() + ")");
//                Tooltip.install(t.getNode(), tip);
//            }
//        });

        lineChart.setCreateSymbols(false);
        flowPaneChart.getChildren().add(lineChart);
    }

    public static int[] Fm(int[] arr, int[] m, int n) {
        int[] F = new int[n];
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0 && m[i] != 0) {
                F[i] = arr[i] + 1;
            } else if (arr[i] % 2 == 1 && m[i] != 0) {
                F[i] = arr[i] - 1;
            } else {
                F[i] = arr[i];
            }
        }
        return F;
    }

    public static int[] F_m(int[] arr, int[] m, int n) {
        int[] F = new int[n];
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 1 && m[i] != 0) {
                F[i] = arr[i] + 1;
            } else if (arr[i] % 2 == 0 && m[i] != 0) {
                F[i] = arr[i] - 1;
            } else {
                F[i] = arr[i];
            }
        }
        return F;
    }

    public static int fValue(int[] arr, int n) {
        int value = 0;
        for (int i = 0; i < n - 1; i++) {
            value += Math.abs(arr[i + 1] - arr[i]);
        }
        return value;
    }
}
