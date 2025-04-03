package br.project.powerguard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DispositivosDetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_detalhes);

        // Obter dados do dispositivo
        Intent intent = getIntent();
        String deviceName = intent.getStringExtra("DEVICE_NAME");
        String status = intent.getStringExtra("STATUS");
        double consumption = intent.getDoubleExtra("CONSUMPTION", 0.0);
        String mac = intent.getStringExtra("MAC");
        String ip = intent.getStringExtra("IP");
        String type = intent.getStringExtra("TYPE");

        // Configurar views
        TextView tvDeviceName = findViewById(R.id.deviceName);
        TextView tvStatus = findViewById(R.id.deviceStatus);
        TextView tvConsumption = findViewById(R.id.deviceConsumption);
        TextView tvMac = findViewById(R.id.deviceMac);
        TextView tvIp = findViewById(R.id.deviceIp);
        TextView tvType = findViewById(R.id.deviceType);

        tvDeviceName.setText("Nome: " + deviceName);
        tvStatus.setText("Status: " + status);
        tvConsumption.setText(String.format("Consumo: %.1f kWh", consumption));
        tvMac.setText("MAC: " + mac);
        tvIp.setText("IP: " + ip);
        tvType.setText("Tipo: " + type);

        // Configurar botões
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());
    }
}