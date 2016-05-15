package co.alectronic.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void btnClickCurrencyConverter(View v ){

        EditText txtCurrency = (EditText) findViewById(R.id.txtCurrency);
        Double Money = Double.parseDouble(txtCurrency.getText().toString());
        Double MoneyConverted = MoneyConverted = getGBP2EURO(Money);

        Toast.makeText(getApplicationContext(),"£" + Money + " = €"+MoneyConverted ,Toast.LENGTH_SHORT).show();
    }

    private Double getGBP2EURO(Double gbp ){
        return Double.parseDouble(df.format(gbp * 1.27));
    }

    private Double getEUROGBP(Double euro ){
        return Double.parseDouble(df.format(euro * 0.79));
    }
}
