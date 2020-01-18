

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage peaLava) throws Exception {

        Group juur=new Group();
        VBox vBox=new VBox();
        Label pealkiri = new Label("NUMBRISÕDA!");
        pealkiri.setFont(new Font(28));
        pealkiri.setTextFill(Color.BLUE);
        pealkiri.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                pealkiri.setScaleX(1.5);
                pealkiri.setScaleY(1.5);
            }
        });

        pealkiri.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                pealkiri.setScaleX(1);
                pealkiri.setScaleY(1);
            }
        });

        Text sissejuhatus=new Text("Käesolevas mängus võistled sa arvutiga.\nSul on maksimaalselt 5 käiku, mille jooksul saad pakkuda arve 0-10.\n" +
                "Juhul kui sisestatud arv on väiksem kui arvuti oma, oled sa koheselt kaotanud,\nkui sinu arv on suurem, siis saad punkte, seda rohkem, mida lähemal su arv arvuti arvule oli.\n" +
                "Arvuti sisestab alati juhusliku täisarvu vahemikus nullist kümneni.");
        sissejuhatus.setFont(new Font(20));
        sissejuhatus.setTextAlignment(TextAlignment.CENTER);

        Label küsimus=new Label("Millise raskusastmega soovite mängida?");
        küsimus.setFont(new Font(18));
        küsimus.setTextFill(Color.PURPLE);

        HBox hBox=new HBox();
        Button nuppKerge=new Button("Kerge");
        Button nuppKeskmine=new Button("Keskmine");
        Button nuppRaske=new Button("Raske");
        hBox.getChildren().addAll(nuppKerge,nuppKeskmine,nuppRaske);
        hBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(pealkiri, sissejuhatus,küsimus,hBox);
        juur.getChildren().add(vBox);

        nuppKerge.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Easy mäng=new Easy();
                mäng.mängi(peaLava);
            }
        });

        nuppKeskmine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Easy mäng=new Medium();
                mäng.mängi(peaLava);
            }
        });

        nuppRaske.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Easy mäng=new Hard();
                mäng.mängi(peaLava);
            }
        });


        Scene stseen=new Scene(juur,850,300, Color.GREY);
        sissejuhatus.wrappingWidthProperty().bind(stseen.widthProperty().subtract(15));
        vBox.prefWidthProperty().bind(stseen.widthProperty().multiply(0.80));
        vBox.prefHeightProperty().bind(stseen.heightProperty().multiply(0.80));
        peaLava.setScene(stseen);
        peaLava.setTitle("Numbrisõda");




        stseen.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double laius=newValue.doubleValue();
                double kõrgus=stseen.getHeight();
                sissejuhatus.setFont(new Font(laius/50+kõrgus/50));
                küsimus.setFont(new Font(laius/50+kõrgus/50));
                pealkiri.setFont(new Font(laius/30+kõrgus/30));


            }
        });

        stseen.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double laius=stseen.getWidth();
                double kõrgus=newValue.doubleValue();
                sissejuhatus.setFont(new Font(laius/50+kõrgus/50));
                küsimus.setFont(new Font(laius/50+kõrgus/50));
                pealkiri.setFont(new Font(laius/30+kõrgus/30));



            }
        });

        peaLava.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
