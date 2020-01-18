

import javafx.stage.Stage;

public class Hard extends Easy {
    private String mänguvooruTulemus;
    private int summa=30;
    @Override
    public void mängi(Stage peaLava){
        Easy raske=new Easy(summa,"raske");
        raske.mängi(peaLava);
    }
    @Override
    public String getMänguvooruTulemus() {
        return mänguvooruTulemus;
    }
}