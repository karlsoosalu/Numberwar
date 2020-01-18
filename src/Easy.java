
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;

public class Easy {
    public Easy() {
    }

    public Easy(int summa, String tase) {
        this.summa = summa;
        this.tase = tase;
        järelSumma=new Text("("+summa+")");
    } // konstruktor

    // isendiväljad
    private String mänguvooruTulemus;
    int sisestatuteSumma=0;
    int käikeTehtud=0;
    int skoor=0;
    static int kergeRekord=0;
    static int keskmineRekord=0;
    static int raskeRekord=0;
    private Text reks;
    private int summa=1000;
    private Text järelSumma=new Text();
    private String tase="kerge";

    public void mängi(Stage peaLava) {
        mänguvoor(peaLava);
    }

    public void mänguvoor(Stage peaLava)  {
        Stage teineLava=new Stage(); // uue lava loomine
        Group juur2=new Group(); // uue juure loomine
        Scene stseen2=new Scene(juur2,300,350); // uue stseeni loomine
        teineLava.setMinHeight(300); // lava kõrguse määramine
        teineLava.setMinWidth(220); // lava laiuse määramime
        teineLava.setScene(stseen2); // lavale stseeni lisamine
        teineLava.setTitle("Numbrisõda"); // lava tiitelribale teksti panemine

        VBox vBox=new VBox();

        //rekordite faili kirjutamine
        try(BufferedReader puhvrilugeja = new BufferedReader(new InputStreamReader(new FileInputStream("rekordid.txt"), "UTF-8"))){
            kergeRekord=Integer.parseInt(puhvrilugeja.readLine());
            keskmineRekord=Integer.parseInt(puhvrilugeja.readLine());
            raskeRekord=Integer.parseInt(puhvrilugeja.readLine());
        } catch (IOException e) {
            kergeRekord=0;
            keskmineRekord=0;
            raskeRekord=0;
        }

        if (tase.equals("keskmine")){
            reks=new Text("Rekord: " + keskmineRekord);
        }
        else if (tase.equals("raske")){
            reks=new Text("Rekord: " + raskeRekord);
        }
        else {
            reks=new Text("Rekord: " + kergeRekord);
        } // rekordi loomine erinevatele tasemetele

        reks.setX(stseen2.getWidth()-65); // rekordi x koordinaadi määramine
        reks.setY(stseen2.getHeight()-10); // rekordi y koordinaadi määramine
        juur2.getChildren().add(reks); // rekordi lisamine juurele

        Text teadeKeskmine = new Text("Teie viie mänguvooru sisestatud numbrite summa peab olema alla 40."); // teate loomine keskmise taseme jaoks
        teadeKeskmine.setWrappingWidth(stseen2.getWidth());
        teadeKeskmine.setTextAlignment(TextAlignment.CENTER); // keskele joondamine
        teadeKeskmine.setY(reks.getY()+200); // y koordinaadi määramine

        Text teadeRaske = new Text("Teie viie mänguvooru sisestatud numbrite summa peab olema alla 30."); // teate loomine raske taseme jaoks
        teadeRaske.setWrappingWidth(stseen2.getWidth());
        teadeRaske.setTextAlignment(TextAlignment.CENTER); // keskele joondamine

        Text küsimus=new Text("Teie lahingnumber:"); // teksti loomine
        küsimus.setFont(new Font(13)); // suuruse määramine
        TextField number= new TextField(); // tekstivälja number loomine
        HBox hBox=new HBox();
        Button sisestus=new Button("Sisesta"); // nupu "sisesta" loomine
        hBox.getChildren().addAll(number,sisestus,järelSumma); // alluvate lisamine

        if (tase.equals("keskmine")){
            vBox.getChildren().addAll(teadeKeskmine,küsimus,hBox);
        } // keskmise taseme valiku korral lisatakse need alluvad
        else if (tase.equals("raske")){
            vBox.getChildren().addAll(teadeRaske,küsimus,hBox);
        } // raske taseme valiku korral need alluvad
        else {
            vBox.getChildren().addAll(küsimus,hBox);
        } // kerge taseme korral

        sisestus.setOnMouseClicked(new EventHandler<MouseEvent>() { // tegevused, mis hakkavad toimuma, kui on sisestatud sobiv number ja klikatud nuppu "sisesta"
            @Override
            public void handle(MouseEvent event){
                try{
                    int lahingNumber=Integer.parseInt(number.getText()); // täisarvuks muutmine
                    int arv=(int)(Math.random()*11); // suvalise numbri genereerimine nullist kümneni
                    Integer w=arv;
                    if (lahingNumber < 11 && lahingNumber > - 1){ // tingimuse kontroll
                        Text tekst=new Text("Arvuti lahingnumber: "); // teksti loomine
                        Text arvt=new Text(w.toString()); // arvuti lahingnumbri moodustamine
                        HBox hBox1=new HBox(tekst,arvt);
                        vBox.getChildren().add(hBox1); // alluvate lisamine
                        Text vastus=new Text("");
                        vBox.getChildren().add(vastus); // alluvate lisamine
                        if (summa < 50) {
                            summa=summa-lahingNumber; // järelejäänud summa arvutamine
                            hBox.getChildren().remove(järelSumma); // vana väärtuse eemaldamine
                            järelSumma=new Text("("+summa+")"); // uue loomine
                            hBox.getChildren().add(järelSumma); // uue väärtuse lisamine
                        }
                        if (arv>lahingNumber){
                            vastus.setText("AI KURJA, arvuti kavaldas su üle."); // vastus juhul, kui mängija number on väiksem kui arvuti genereeritud
                            stseen2.setFill(Color.RED); // värvi valimine
                        }else {
                            vastus.setText("Tubli! Teenisid " + (10 - (lahingNumber - arv)) + " punkti."); // kui mängija sisestatud number on võrdne v suurem, siis kiidetakse mängijat
                            skoor += 10 - (lahingNumber - arv); // skoor arvutamine
                            käikeTehtud++; // käikude loendus
                        }
                        if(käikeTehtud==5&&summa>=0){
                            mänguvooruTulemus="Tubli! Teenisid " + (10 - (lahingNumber - arv)) + " punkti.\nPalju õnne,sa võitsid! Su lõppskooriks jäi " + skoor + "."; // vastus võiduka mängu korral
                            vastus.setText(mänguvooruTulemus); // teksti lisamine
                            stseen2.setFill(Color.GREEN); // stseeni taustvärvi määramine
                            if (tase.equals("keskmine")&&skoor>keskmineRekord){
                                reks=new Text("Rekord: " + keskmineRekord);
                                keskmineRekord=skoor;
                            }
                            else if (tase.equals("raske")&&skoor>raskeRekord){
                                reks=new Text("Rekord: " + raskeRekord);
                                raskeRekord=skoor;
                            }
                            else if (skoor>kergeRekord){
                                reks=new Text("Rekord: " + kergeRekord);
                                kergeRekord=skoor;
                            }

                            //kirjutame rekordid faili
                            try (BufferedWriter kirjutaja = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("rekordid.txt")))) {
                                Integer kergeint=kergeRekord;
                                kirjutaja.write(kergeint.toString());
                                kirjutaja.newLine();
                                Integer keskmineint=keskmineRekord;
                                kirjutaja.write(keskmineint.toString());
                                kirjutaja.newLine();
                                Integer raskeint=raskeRekord;
                                kirjutaja.write(raskeint.toString());
                            } catch (IOException ex) {
                            }
                        }

                        if (käikeTehtud==5&&summa<0){
                            mänguvooruTulemus="Tubli! Teenisid " + (10 - (lahingNumber - arv)) + " punkti.\nAI KURJA, numbrite summa jäi "+(0-summa)+" võrra alla 40ne"; // vastus allajäämise puhul
                            vastus.setText(mänguvooruTulemus); // teksti lisamine
                            stseen2.setFill(Color.RED); // stseeni taustvärvi määramine
                        }
                        if (arv>lahingNumber||käikeTehtud==5) { // tegevused mängu lõpu korral
                            hBox.getChildren().remove(sisestus); // sisesta nupu eemaldamine
                            HBox nupud = new HBox(); // uute nuppude loomine
                            vBox.getChildren().add(nupud); // alluvate lisamine
                            Button tagasi = new Button("Tagasi"); // tagasi nupu loomine
                            nupud.getChildren().add(tagasi); // alluva lisamine
                            tagasi.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    peaLava.show();
                                    teineLava.close();
                                }
                            }); // tagasi valiku korral suletakse mängulava ja avatakse avaekraan

                            Button uus = new Button("Uus mäng"); // nupp uue mängu jaoks
                            nupud.getChildren().add(uus); // alluva lisamine
                            uus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    teineLava.close(); // mängu sulgemine
                                    Easy mäng = new Easy(); // uue mängu loomine
                                    if (tase.equals("keskmine")){mäng=new Medium();} // keskmise taseme korral
                                    else if (tase.equals("raske")){mäng=new Hard();} // raske taseme korral
                                    mäng.mängi(peaLava); // meetodisse minek
                                }
                            });
                        }
                    }
                    else { // kui kasutaja ei sisesta sobivat numbrit
                        Text vastus =new Text("Palun sisestage number 0-10!"); // teksti loomine
                        vastus.setFill(Color.RED); // teksti värv
                        vBox.getChildren().add(vastus); // alluva lisamine
                    }
                }catch (NumberFormatException e){ // kui kasutaja ei sisesta numbrit
                    System.out.println("Palun sisestage number 0-10!");
                    Text vastus =new Text("Palun sisestage number 0-10!"); // teksti loomine
                    vastus.setFill(Color.RED); // värvi määramine
                    vBox.getChildren().add(vastus); // alluva lisamine
                }
            }
        });
        juur2.getChildren().addAll(vBox); // kõikide alluvate lisamine
        peaLava.close(); // algekraani sulgemine
        teineLava.show(); // mänguekraani avamine

        stseen2.widthProperty().addListener(new ChangeListener<Number>() {
            @Override // mänguekraani laiuse muutmine
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double laius=newValue.doubleValue(); // laiuse määramine
                double kõrgus=stseen2.getHeight(); // kõrguse määramine
                reks.setX(stseen2.getWidth()-65); // rekordi asukoha määramine
                reks.setY(stseen2.getHeight()-10); // rekord asukoha määramine

                küsimus.setFont(new Font(laius/50+kõrgus/50)); // küsimuse suuruse muutumine
            }
        });

        stseen2.heightProperty().addListener(new ChangeListener<Number>() {
            @Override // mänguekraani kõrguse muutmine
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // suuruste määramine
                double laius=stseen2.getWidth();
                double kõrgus=newValue.doubleValue();
                küsimus.setFont(new Font(laius/50+kõrgus/50));
                teadeKeskmine.setFont(new Font(laius/50+kõrgus/50));
                teadeRaske.setFont(new Font(laius/50+kõrgus/50));
                teadeKeskmine.setWrappingWidth(laius);
                teadeRaske.setWrappingWidth(laius);
            }
        });
    }
    public String getMänguvooruTulemus() { // get-meetod
        return mänguvooruTulemus;
    }

    public int getSisestatuteSumma() { // get-meetod
        return sisestatuteSumma;
    }
}