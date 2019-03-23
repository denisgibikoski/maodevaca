package br.com.maodevaca;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.com.maodevaca.dao.DataBaseHandler;
import br.com.maodevaca.model.Produto;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etQtd;
    private EditText etValor;

    private DataBaseHandler banco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etNome = findViewById( R.id.etProduto);
        etQtd = findViewById( R.id.etQtd);
        etValor = findViewById( R.id.etValor);

        banco = new DataBaseHandler(this);

    }

    public void btIncluirOnClick(View view) {

        Produto prod = new Produto();

        prod.setNome(etNome.getText().toString());
        prod.setQtd(Double.parseDouble(etQtd.getText().toString()));
        prod.setValor(Double.parseDouble(etValor.getText().toString()));

        banco.salvar(prod);

        Toast.makeText(this, "Sucesso", Toast.LENGTH_LONG).show();

    }

    public void btListarOnClick(View view) {

        Cursor registros = banco.listar();

        ListView lvCadastro = new ListView(this);

        String campos[] = { "nome" };
        int idTela = android.R.layout.simple_list_item_1;

        int camposTela[] = { android.R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, idTela, registros,campos, camposTela);

        lvCadastro.setAdapter(adapter);

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        alerta.setTitle("Registro");
        alerta.setCancelable(false);
        alerta.setPositiveButton("O Mais Barato", null);
        alerta.setView(lvCadastro);
        alerta.show();


    }

    public void btIniciarBaseOnClick(View view) {
        banco.limparBase();
    }
}
