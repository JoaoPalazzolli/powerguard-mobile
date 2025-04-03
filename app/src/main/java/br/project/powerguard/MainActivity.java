package br.project.powerguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajusta o layout para considerar as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MainActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Pegando referência do botão de menu
        ImageButton btnMenu = findViewById(R.id.btn_menu);

        // Adicionando evento de clique no botão de menu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criando o menu popup
                PopupMenu popup = new PopupMenu(MainActivity.this, btnMenu);
                popup.getMenuInflater().inflate(R.menu.drawer_menu, popup.getMenu());

                // Adicionando evento de clique para os itens do menu
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.nav_dispositivos) {
                            Toast.makeText(MainActivity.this, "Abrindo Dispositivos...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DispositivosActivity.class);
                            startActivity(intent);
                            return true;
                        } else if (id == R.id.nav_relatorio) {
                            Toast.makeText(MainActivity.this, "Abrindo Relatório...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, RelatorioActivity.class);
                            startActivity(intent);
                            return true;
                        }

                        return false;
                    }
                });

                // Exibir o menu popup
                popup.show();
            }
        });
    }
}
