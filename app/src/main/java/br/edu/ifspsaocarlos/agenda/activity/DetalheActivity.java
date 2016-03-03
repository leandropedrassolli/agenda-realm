package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.model.Telefone;
import io.realm.Realm;

public class DetalheActivity extends AppCompatActivity {
    
    private Contato c;
    //private ContatoDAO cDAO;
    
    Realm realm;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        realm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("contato")) {

            // Recebemos o parâmetro de outra intent e fazemos a consulta.
            String nome = getIntent().getStringExtra("contato");
            c = realm.where(Contato.class).equalTo("nome",nome).findFirst();

            EditText nameText = (EditText)findViewById(R.id.editText1);
            nameText.setText(c.getNome());

            EditText foneText = (EditText)findViewById(R.id.editText2);
            foneText.setText(c.getFones().first().getNumero());

            EditText emailText = (EditText)findViewById(R.id.editText3);
            emailText.setText(c.getEmail());

            int pos = c.getNome().indexOf(" ");
            if (pos==-1)
                pos = c.getNome().length();

            setTitle(c.getNome().substring(0,pos));
        }
        //cDAO = new ContatoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato")) {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                remover();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void remover() {
        //cDAO.deleteContact(c);
        realm.beginTransaction();
        c.removeFromRealm();
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    public void salvar()
    {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();

        realm.beginTransaction();

        if (c==null) {
            //c = new Contato();
            c = realm.createObject(Contato.class);
            c.setNome(name);
            Telefone t = realm.createObject(Telefone.class);
            t.setNumero(fone);
            c.getFones().add(t);
            c.setEmail(email);

            realm.commitTransaction();
            //cDAO.createContact(c);
            Toast.makeText(this, "Incluído com sucesso", Toast.LENGTH_SHORT).show();
        }
        else
        {
            c.setNome(name);
            c.getFones().get(0).setNumero(fone);
            c.setEmail(email);

            realm.commitTransaction();
            //cDAO.updateContact(c);
            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
        }

        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ao encerrar o app deve-se fechar o banco.
        realm.close();
    }

}
