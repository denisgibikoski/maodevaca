package br.com.maodevaca;
import br.com.maodevaca.dao.DataBaseHandler;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import br.com.maodevaca.model.Produto;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class ListarActivity extends AppCompatActivity {

    private ListView lvProdutos;

    private DataBaseHandler banco;

    private Cursor registros;

    private List<Produto> listaProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        banco = new DataBaseHandler(this);

        registros = banco.listar();

         lvProdutos = (ListView) findViewById(R.id.lista);

        String campos[] = { "nome" };
        int idTela = android.R.layout.simple_list_item_1;

        int camposTela[] = { android.R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, idTela, registros,campos, camposTela);

        lvProdutos.setAdapter(adapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void btMaisBaratoOnClick(View view) {

        listaProduto = new ArrayList<>();

        registros = banco.listar();

        this.getListaProduto();

        Produto aux = this.menorValor(listaProduto);

        String msg = "Nome : "+aux.getNome()+"Valor Unitario :"+aux.getValorUnitario();
        Log.i(null, msg );

        makeText(this, msg, LENGTH_LONG).show();

    }

    private Produto menorValor(List<Produto> listaProduto) {
        Produto aux = new Produto();
        for (int i = 0 ; i < listaProduto.size(); i ++){

            for(int j = 0; j < listaProduto.size(); j++){

                if ( listaProduto.get(i).getValorUnitario() < listaProduto.get(j).getValorUnitario()){
                    aux = listaProduto.get(i);
                }
            }
        }

        return aux;
    }

    public void getListaProduto() {

        while (registros.moveToNext()){

            String nome = registros.getString(registros.getColumnIndex("nome"));

            Double qtd = registros.getDouble(registros.getColumnIndex("qtd"));

            Double valor = registros.getDouble(registros.getColumnIndex("valor"));

            Produto produto =  new Produto();
            produto.setNome(nome);
            produto.setQtd(qtd);
            produto.setValor(valor);
            produto.setValorUnitario( ( valor  / qtd ) );

            listaProduto.add(produto);

        }
    }
}
