package br.com.maodevaca.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.maodevaca.model.Produto;

public class DataBaseHandler extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "produtos";
    private static final int VERSION = 1 ;

    private static final String TABLE_DESCRICAO = "descricao";
    private static final String TABLE_PRODUTO = "produto";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( " CREATE TABLE IF NOT EXISTS "+TABLE_DESCRICAO+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT) " );

        db.execSQL( " CREATE TABLE IF NOT EXISTS "+TABLE_PRODUTO+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, qtd TEXT,  valor TEXT ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DESCRICAO);
        onCreate(db);

    }

    public void limparBase(){
        SQLiteDatabase banco = this.getWritableDatabase();

        banco.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUTO);
        banco.execSQL( " CREATE TABLE IF NOT EXISTS "+TABLE_PRODUTO+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, qtd DECIMAL, valor DECIMAL ) " );
    }

    public void salvar(Produto prod) {
        SQLiteDatabase banco = this.getWritableDatabase();

        ContentValues registro = new ContentValues();

        ContentValues descricao = new ContentValues();

        descricao.put("descricao", prod.getNome());

        registro.put("nome", prod.getNome());
        registro.put("qtd", prod.getQtd());
        registro.put("valor", prod.getValor());

        banco.insert(TABLE_DESCRICAO, null, descricao);
        banco.insert(TABLE_PRODUTO, null, registro);

    }

    public Cursor listar() {
        SQLiteDatabase banco = this.getWritableDatabase();

        Cursor registros = banco.query(TABLE_PRODUTO, null, null, null,null,null,null);


        return registros;
    }


}
