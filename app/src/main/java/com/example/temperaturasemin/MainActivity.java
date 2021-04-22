package com.example.temperaturasemin;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothA2dp;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.bluetoothjhr.BluetoothJhr;

public class MainActivity extends AppCompatActivity {

    ListView listaDispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDispositivos = (ListView)findViewById((R.id.listaDospositivos));

        BluetoothJhr bluetoothJhr = new BluetoothJhr(this, listaDispositivos);
        bluetoothJhr.EncenderBluetooth();

        listaDispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Concectando con el dispositivo ...", Toast.LENGTH_LONG).show();
                bluetoothJhr.Disp_Seleccionado(view, position, VistaTemperatura.class);

            }
        });

    }




}