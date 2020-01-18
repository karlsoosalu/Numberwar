import javafx.stage.Stage;

public class Medium extends Easy {
    private String mänguvooruTulemus;
    private int summa=40;
    @Override
    public void mängi(Stage peaLava){
        Easy keskmine=new Easy(summa,"keskmine");
        keskmine.mängi(peaLava);
    }
    @Override
    public String getMänguvooruTulemus() {
        return mänguvooruTulemus;
    }
}