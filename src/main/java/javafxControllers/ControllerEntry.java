package javafxControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ControllerEntry {
    @FXML
    private Stage stage;

    @FXML
    private ImageView entryclose;

    @FXML
    private AnchorPane MainAnOther;

    @FXML
    private AnchorPane MainRootAn;

    @FXML
    private Text textGN;

    @FXML
    public void initialize() throws FileNotFoundException {
        entryclose.setImage(new Image("file:src/main/resources/images/shut.png"));

//        FileInputStream in = new FileInputStream("file:@../../resources/images/hintImage.png");
//        Image im = new Image(in);
//        hintimage.setImage(im);

        //设置closeImage在鼠标进入该图标区域改变形状为手型
        entryclose.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                entryclose.setCursor(Cursor.HAND);
            }
        });

        //设置closeImage在鼠标点击该图标区域关闭应用程序
        entryclose.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标点击closeImage，关闭程序！");
                stage = (Stage) entryclose.getScene().getWindow();
                stage.close();
            }
        });
    }

    @FXML
    void enterCFsecretJM(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/others/secretSplitImaget.fxml", "【 拆分秘图（Shamir）】");
    }

    @FXML
    void enterCFYUV(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/others/Z_ImageSplit_YUV.fxml", "【 图像拆分成YUV 】");
    }

    @FXML
    void enterHFsecretJM(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/others/secretRecoverImage.fxml", "【 恢复秘图（（Shamir）未可视化）】");
    }

    @FXML
    void enterHindImageReco1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/recovery/secretHindImageReco1.fxml", "【 恢复秘图（LSB_原1）】");
    }

    @FXML
    void enterHindImageReco2(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/recovery/secretHindImageReco2.fxml", "【 恢复秘图（LSB_改2）】");
    }

    @FXML
    void enterHindImageRecoPKK(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/recovery/secretHindImageRecoPKK.fxml", "【 恢复秘图（PKK）】");
    }

    @FXML
    void enterHindImageRecoTJC2015(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/recovery/secretHindImageRecoTJC2015.fxml", "【 恢复秘图（TJC2015）】");
    }
    @FXML
    void enterHindImageRecoSSVT2019(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/recovery/secretHindImageRecoASSVT2019.fxml", "【 恢复秘图（SSVT2019）】");
    }

    @FXML
    void enterHindImageRecoMy1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/recovery/secretHindImageRecoMy1.fxml", "【 恢复秘图（My1）】");
    }

    @FXML
    void enterHindImageRecoMyMDP1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/recovery/secretHindImageRecoMyMDP1.fxml", "【 恢复秘图（MyMDP1）】");
    }

    @FXML
    void enterHindImageRecoAK2019(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/recovery/secretHindImageRecoAKSGS2019.fxml", "【 恢复秘图（MDPAKSGS2019）】");
    }

    @FXML
    void enterHindImageRecoAK20192(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/recovery/secretHindImageRecoAKSGS20192.fxml", "【 恢复秘图（MDPAKSGS20192）】");
    }

    @FXML
    void enterSceretHindImage1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/hind/secretHindImage1.fxml", "【 图像隐藏（LSB_原1）】");
    }

    @FXML
    void enterSceretHindImage2(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/hind/secretHindImage2.fxml", "【 图像隐藏（LSB_改2）】");
    }

    @FXML
    void enterSceretHindImagePKK(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/hind/secretHindImagePKK.fxml", "【 图像隐藏（PKK）】");
    }

    @FXML
    void enterSceretHindImageTJC2015(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/hind/secretHindImageaTJC2015.fxml", "【 图像隐藏（TJC2015）】");
    }

    @FXML
    void enterSceretHindImageSSVT2019(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/hind/secretHindImageaSSVT2019.fxml", "【 图像隐藏（SSVT2019）】");
    }

    @FXML
    void enterSceretHindImageMy1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/hind/secretHindImageMy1.fxml", "【 图像隐藏（My1） 】");
    }

    @FXML
    void enterSceretHindImageMyMDP1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/hind/secretHindImageMyMDP1.fxml", "【 我的差分和模函数1】");
    }


    @FXML
    void enterSceretHindImagePMAT2018(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/reversible/hind/secretHindImagePMTA2018.fxml", "【 PMTA2018】");
    }


    @FXML
    void enterSceretHindImageAKSGS2019(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/hind/secretHindImageAKSGS2019.fxml", "【 图像隐藏（MDPAKSGS2019） 】");
    }

    @FXML
    void enterSceretHindImageAKSGS20192(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/irreversible/hind/secretHindImageAKSGS20192.fxml", "【 图像隐藏（MDPAKSGS20192） 】");
    }

    @FXML
    void enterSceretBF(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/others//secret8bit_2value.fxml", "【 灰度图拆八 】");
    }

    @FXML
    void enterColorToGray(ActionEvent event) throws IOException {
        Interfacejump("/javafx/pictureProcess/others/otherColorToGray.fxml", "【 彩色转灰度图像 】");
    }

    @FXML
    void enterAnalysisPSNR(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/analysisPSNR.fxml", "【 PSNR分析 】");

    }
    @FXML
    void enterAnalysisSSIM(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/analysisSSIM.fxml", "【 SSIM分析 】");
    }

    @FXML
    void enterAnalysisRS(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/analysisRS.fxml", "【 RS分析 】");
    }

    @FXML
    void enterAnalysisBPPPKK(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPPKK.fxml", "【 BPP(KHJung)】");
    }

    @FXML
    void enterAnalysisBPPMyMDP1(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPMyMDP1.fxml", "【 BPP(MyMDP1) 】");
    }

    @FXML
    void enterAnalysisBPPMDPAK2019(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPAKSGS2019.fxml", "【 BPP(AKSGS2019) 】");
    }

    @FXML
    void enterAnalysisBPPMDPAK20192(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPAKSGS20192.fxml", "【 BPP(AKSGS20192) 】");
    }
    @FXML
    void enterAnalysisBPPTJC2015(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPTJC2015.fxml", "【 BPP(TJC2015) 】");
    }

    @FXML
    void enterAnalysisBPPSSVT2019(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPSSVT2019.fxml", "【 BPP(PMAT2018) 】");
    }

    @FXML
    void enterAnalysisBPPPMTA2018(ActionEvent event) throws IOException {
        Interfacejump("/javafx/analysis/AnalysisBPPPMTA2018.fxml", "【 BPP(SSVT2019) 】");
    }



    @FXML
    void ButtHome(ActionEvent event) throws IOException {
        textGN.setText("【 主页面 】");
        MainRootAn.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource("/javaFX/Entry.fxml"));
        MainRootAn.getChildren().add(root);
    }

    @FXML
    void ButtSave(ActionEvent event) {

    }

    @FXML
    void ButtUpDate(ActionEvent event) {

    }

    @FXML
    void ButtAbout(ActionEvent event) {

    }

    @FXML
    void ButtSteg(ActionEvent event) {

    }

    //界面跳转函数
    public void Interfacejump(String s ,String text) throws IOException {
        textGN.setText(text);
        MainAnOther.getChildren().clear();
        Parent root = FXMLLoader.load(getClass().getResource(s));
        MainAnOther.getChildren().add(root);
    }
}
