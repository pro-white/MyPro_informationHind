package utils;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Utils {
    double x1;
    double y1;
    double x_stage;
    double y_stage;

    //设置鼠标点击stage页面实现拖拽功能
    public void setMouseDragFun(Scene scene, Stage stage) {
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent m) {
                //计算
                stage.setX(x_stage + m.getScreenX() - x1);
                stage.setY(y_stage + m.getScreenY() - y1);
            }
        });
        scene.setOnDragEntered(null);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent m) {
                //按下鼠标后，记录当前鼠标的坐标
                x1 = m.getScreenX();
                y1 = m.getScreenY();
                x_stage = stage.getX();
                y_stage = stage.getY();
            }
        });
    }

    //创建提示框
    public Button createTipsFrame(Stage mainStage, String tips, int flag) throws IOException {
        //设置提示窗体Stage
        Stage tipsStage = new Stage();
        //加载提示页面中Scene的fxml文件资源
        Parent tipsRoot;
        Scene tipsScene;
        if (flag == 0) {
            tipsRoot = FXMLLoader.load(getClass().getResource("/javafx/tips/Tips.fxml"));
            tipsScene = new Scene(tipsRoot, 320, 190);
        } else if (flag == 1){
            tipsRoot = FXMLLoader.load(getClass().getResource("/javafx/tips/Tips.fxml"));
            tipsScene = new Scene(tipsRoot, 220, 130);
        }else {
            tipsRoot = FXMLLoader.load(getClass().getResource("/javafx/tips/Tips.fxml"));
            tipsScene = new Scene(tipsRoot, 220, 130);
        }
        Button confirmButton = (Button) tipsRoot.lookup("#confirmButton");

        tipsStage.setScene(tipsScene);
        tipsStage.setX(850);
        tipsStage.setY(420);
        //设置鼠标拖拽功能
        setMouseDragFun(tipsScene, tipsStage);
        //模态化窗口（使得注册窗口在登录窗口之上，当注册窗口关闭前，无法操作登录窗口）
        tipsStage.initOwner(mainStage);
        tipsStage.initModality(Modality.WINDOW_MODAL);  // 提示音+窗口闪动
        //隐藏stage顶部状态栏
        tipsStage.initStyle(StageStyle.UNDECORATED);
        ImageView tipsImage = (ImageView) tipsRoot.lookup("#tipsImage");
        if (flag == 0) {
            tipsImage.setImage(new Image("file:src/main/resources/images/error.png"));
            TextArea tipsText = (TextArea) tipsRoot.lookup("#tipsText");
            tipsText.setEditable(false);
            tipsText.setWrapText(true);
            tipsText.setText(tips);
        } else if (flag == 1){
            tipsImage.setImage(new Image("file:src/main/resources/images/ok.png"));
            Text tipsText = (Text) tipsRoot.lookup("#tipsText");
            tipsText.setText(tips);
        }else {
            tipsImage.setImage(new Image("file:src/main/resources/images/spinner.gif"));
            Text tipsText = (Text) tipsRoot.lookup("#tipsText");
            tipsText.setText(tips);
        }
//        tipsText.setEditable(false);
//        tipsText.setWrapText(true);
        tipsStage.show();
        return confirmButton;
    }

    public void createRunTipsFrame(Stage mainStage, String tips) throws IOException {
        //设置提示窗体Stage
        Stage tipsStage = new Stage();
        //加载提示页面中Scene的fxml文件资源
        Parent tipsRoot = FXMLLoader.load(getClass().getResource("/javafx/tips/runPrompt.fxml"));

        Scene tipsScene = new Scene(tipsRoot, 234, 135);
        tipsStage.setScene(tipsScene);
        tipsStage.setX(850);
        tipsStage.setY(420);
        //设置鼠标拖拽功能
        setMouseDragFun(tipsScene, tipsStage);
        //模态化窗口（使得注册窗口在登录窗口之上，当注册窗口关闭前，无法操作登录窗口）
        tipsStage.initOwner(mainStage);
        tipsStage.initModality(Modality.WINDOW_MODAL);  // 提示音+窗口闪动
        //隐藏stage顶部状态栏
        tipsStage.initStyle(StageStyle.UNDECORATED);
        ImageView tipsImage = (ImageView) tipsRoot.lookup("#tipsImage");

        tipsImage.setImage(new Image("file:src/main/resources/images/spinner.gif"));

        Text tipsText = (Text) tipsRoot.lookup("#tipsText");
//        tipsText.setEditable(false);
//        tipsText.setWrapText(true);
        tipsText.setText(tips);
        tipsStage.show();
    }

    //创建后台输出任务提示框，返回强制退出按钮，调用函数中添加相应事件
    public Button createTransTipsFrame(Stage mainStage, String tips) throws IOException {
        //设置提示窗体Stage
        Stage tipsStage = new Stage();
        //加载提示页面中Scene的fxml文件资源
        Parent tipsRoot = FXMLLoader.load(getClass().getResource("/javafx/tips/TipsFeedback.fxml"));
        Scene tipsScene = new Scene(tipsRoot, 260, 133);
        tipsStage.setScene(tipsScene);
        //设置鼠标拖拽功能
        setMouseDragFun(tipsScene,tipsStage);
        //模态化窗口（使得注册窗口在登录窗口之上，当注册窗口关闭前，无法操作登录窗口）
        tipsStage.initOwner(mainStage);
        tipsStage.initModality(Modality.WINDOW_MODAL);  // 提示音+窗口闪动
        //隐藏stage顶部状态栏
        tipsStage.initStyle(StageStyle.UNDECORATED);
        ImageView tipsImage = (ImageView) tipsRoot.lookup("#tipsImage");
        tipsImage.setImage(new Image("file:src/main/resources/images/help.png"));

        Text tipsText = (Text) tipsRoot.lookup("#tipsText");
        tipsText.setText(tips);
        tipsStage.show();
        return (Button) tipsRoot.lookup("#confirmButton");
    }

    public void setMouseEnteredHand(Control control) {
        //设置鼠标进入control区域变为手形
        control.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                control.setCursor(Cursor.HAND);
            }
        });
    }

    public void setMouseEnteredHand(Node node) {
        //设置鼠标进入node区域变为手形
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.setCursor(Cursor.HAND);
            }
        });
    }
}
