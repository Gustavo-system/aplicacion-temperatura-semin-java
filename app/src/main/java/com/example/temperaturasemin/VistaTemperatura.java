package com.example.temperaturasemin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.bluetoothjhr.BluetoothJhr;

public class VistaTemperatura extends AppCompatActivity {

    BluetoothJhr bluetoothJhr2;
    TextView data;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_temperatura);

        bluetoothJhr2 = new BluetoothJhr(MainActivity.class, this);
        bluetoothJhr2.ConectaBluetooth();

        data = (TextView) findViewById((R.id.temperatura));
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VistaTemperatura.this, "Loading ...", Toast.LENGTH_LONG).show();
            }

        });

        new Thread(new Runnable(){
            @Override
            public void run(){
                while (true){
                    Delay();

                    data.post(new Runnable() {
                        @Override
                        public void run() {
                            if( bluetoothJhr2.Rx() != null && bluetoothJhr2.Rx() != "null" && !bluetoothJhr2.Rx().equalsIgnoreCase("null") && bluetoothJhr2.Rx() != ""){
                                String dato = bluetoothJhr2.Rx();
                                data.setText(dato);
                                bluetoothJhr2.ResetearRx();
                            }
                        }
                    });
                }
            }
        }).start();


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        bluetoothJhr2.CierraConexion();
    }

    private void Delay(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}