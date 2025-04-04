package br.project.powerguard;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

public class RelatorioActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        // Inicializar views
        TextView tvEconomia = findViewById(R.id.tvEconomia);
        TextView tvConsumoTotal = findViewById(R.id.tvConsumoTotal);
        TextView tvDispositivosLigados = findViewById(R.id.tvDispositivosLigados);
        TextView tvDispositivosDesligados = findViewById(R.id.tvDispositivosDesligados);
        Button btnGerarRelatorio = findViewById(R.id.btnGerarRelatorio);

        // Configurar dados (pode ser substituído por dados reais)
        String economia = "R$ 150,00";
        String consumoTotal = "300 kWh";
        String dispositivosLigados = "3";
        String dispositivosDesligados = "1";

        tvEconomia.setText(economia);
        tvConsumoTotal.setText(consumoTotal);
        tvDispositivosLigados.setText(dispositivosLigados);
        tvDispositivosDesligados.setText(dispositivosDesligados);

        // Listener do botão Gerar Relatório
        btnGerarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar permissão de armazenamento
                if (checkPermission()) {
                    // Gerar PDF
                    PDFGenerator pdfGenerator = new PDFGenerator(
                            RelatorioActivity.this,
                            economia,
                            consumoTotal,
                            dispositivosLigados,
                            dispositivosDesligados
                    );
                    pdfGenerator.generatePDF();
                } else {
                    requestPermission();
                }
            }
        });
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ - Verifica se tem permissão especial
            return Environment.isExternalStorageManager();
        } else {
            // Android 10 ou inferior - Verifica permissão tradicional
            int result = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ - Abre tela de configurações para conceder permissão
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        } else {
            // Android 10 ou inferior - Solicita permissão normalmente
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão concedida. Clique novamente para gerar o PDF.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão negada. Não é possível salvar o PDF.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}