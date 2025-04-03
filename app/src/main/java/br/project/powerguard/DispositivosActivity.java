package br.project.powerguard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DispositivosActivity extends AppCompatActivity {

    private TextView acStatus, lightStatus, pcStatus, fridgeStatus, totalConsumption;
    private Switch acSwitch, lightSwitch, pcSwitch, fridgeSwitch;
    private LinearLayout acCard, lightCard, pcCard, fridgeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        // Inicializar views
        acStatus = findViewById(R.id.acStatus);
        lightStatus = findViewById(R.id.lightStatus);
        pcStatus = findViewById(R.id.pcStatus);
        fridgeStatus = findViewById(R.id.fridgeStatus);
        totalConsumption = findViewById(R.id.totalConsumption);

        acSwitch = findViewById(R.id.acSwitch);
        lightSwitch = findViewById(R.id.lightSwitch);
        pcSwitch = findViewById(R.id.pcSwitch);
        fridgeSwitch = findViewById(R.id.fridgeSwitch);

        // Inicializar cards
        acCard = findViewById(R.id.acCard);
        lightCard = findViewById(R.id.lightCard);
        pcCard = findViewById(R.id.pcCard);
        fridgeCard = findViewById(R.id.fridgeCard);

        // Configurar listeners para os switches
        acSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateDeviceStatus(acStatus, "Ar Condicionado - Sala 1", isChecked, 1.5));

        lightSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateDeviceStatus(lightStatus, "Iluminação - Corredor", isChecked, 0.5));

        pcSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateDeviceStatus(pcStatus, "Computador - Escritório", isChecked, 0.1));

        fridgeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateDeviceStatus(fridgeStatus, "Geladeira - Cozinha", isChecked, 0.8));

        // Configurar listeners para os cards
        acCard.setOnClickListener(v -> openDeviceDetails(
                "Ar Condicionado - Sala 1",
                acSwitch.isChecked() ? "Ligado" : "Desligado",
                1.5,
                "00:1B:44:11:3A:B7",
                "192.168.1.100",
                "Ar Condicionado"
        ));

        lightCard.setOnClickListener(v -> openDeviceDetails(
                "Iluminação - Corredor",
                lightSwitch.isChecked() ? "Ligado" : "Desligado",
                0.5,
                "00:1B:44:11:3A:B8",
                "192.168.1.101",
                "Iluminação"
        ));

        pcCard.setOnClickListener(v -> openDeviceDetails(
                "Computador - Escritório",
                pcSwitch.isChecked() ? "Ligado" : "Desligado",
                0.1,
                "00:1B:44:11:3A:B9",
                "192.168.1.102",
                "Computador"
        ));

        fridgeCard.setOnClickListener(v -> openDeviceDetails(
                "Geladeira - Cozinha",
                fridgeSwitch.isChecked() ? "Ligado" : "Desligado",
                0.8,
                "00:1B:44:11:3A:C0",
                "192.168.1.103",
                "Geladeira"
        ));
    }

    private void updateDeviceStatus(TextView statusView, String deviceName, boolean isOn, double consumption) {
        String status = isOn ? "Ligado" : "Desligado";
        double currentConsumption = isOn ? consumption : 0.0;

        String statusText = deviceName + " - Status: " + status + " - Consumo: " + currentConsumption + " kWh";
        statusView.setText(statusText);

        updateTotalConsumption();
    }

    private void updateTotalConsumption() {
        double total = 0;
        if (acSwitch.isChecked()) total += 1.5;
        if (lightSwitch.isChecked()) total += 0.5;
        if (pcSwitch.isChecked()) total += 0.1;
        if (fridgeSwitch.isChecked()) total += 0.8;
        totalConsumption.setText(String.format("Consumo Total: %.1f kWh", total));
    }

    private void openDeviceDetails(String name, String status, double consumption,
                                   String mac, String ip, String type) {
        Intent intent = new Intent(this, DispositivosDetalhesActivity.class);
        intent.putExtra("DEVICE_NAME", name);
        intent.putExtra("STATUS", status);
        intent.putExtra("CONSUMPTION", consumption);
        intent.putExtra("MAC", mac);
        intent.putExtra("IP", ip);
        intent.putExtra("TYPE", type);
        startActivity(intent);
    }
}