package com.example.temperaturasemin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.bluetoothjhr.BluetoothJhr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
                                guardar();
                            }
                        }
                    });
                }
            }
        }).start();

        String[] archivos = fileList();
        if(Validar(archivos, "temperaturas.txt")){
            String texto = "";
            try{
                InputStreamReader archivo = new InputStreamReader(openFileInput("temperaturas.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();

                while(linea != null){
                    texto = texto + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();

            }catch (IOException e){
                Toast.makeText(VistaTemperatura.this, "error de lectura...", Toast.LENGTH_LONG).show();
            }
        }

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

    private boolean Validar(String[] archivos, String buscarArchivo){
        for (int f=0; f<archivos.length; f++){
            if(buscarArchivo.equals(archivos[f])){
                return true;
            }
        }
        return false;
    }

    public void guardar(){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("temperaturas.txt", Activity.MODE_PRIVATE));
            archivo.write(data.getText().toString());
            archivo.flush();
            archivo.close();
        }catch (IOException e){
            Toast.makeText(VistaTemperatura.this, "error de escritura...", Toast.LENGTH_LONG).show();
        }
        finish();
    }

}