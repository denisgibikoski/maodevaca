package br.com.maodevaca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import br.com.maodevaca.dao.DataBaseHandler;
import br.com.maodevaca.model.Produto;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;

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

        prod.setValorUnitario( prod.getQtd() / prod.getValor() );
        banco.salvar(prod);

        makeText(this, "Sucesso", LENGTH_LONG).show();

    }

    public void btListarOnClick(View view) {

        Intent i = new Intent(this, ListarActivity.class);
        startActivity(i);

    }

    public void btIniciarBaseOnClick(View view) {
        banco.limparBase();
    }
}
