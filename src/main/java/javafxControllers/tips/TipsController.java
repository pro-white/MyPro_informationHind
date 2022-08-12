package javafxControllers.tips;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Utils;

public class TipsController {

    @FXML
    private Button confirmButton;
    private Utils utils;
    @FXML
    public void initialize(){
        utils = new Utils();
        //设置confirmButton在鼠标进入该图标区域改变形状为手型
        utils.setMouseEnteredHand(confirmButton);
        //设置registerShutImage在鼠标点击该图标区域关闭应用程序
        confirmButton.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标点击confirmButton，关闭tipsFrame！");
                // 刷新列表
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });
    }
}
