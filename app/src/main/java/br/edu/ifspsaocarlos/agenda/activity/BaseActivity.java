package br.edu.ifspsaocarlos.agenda.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.adapter.ContatoAdapter;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.Realm;
import io.realm.RealmResults;

public class BaseActivity extends AppCompatActivity {

    Realm realm;

    //protected ContatoDAO cDAO=new ContatoDAO(this);
    public ListView list;
    public ContatoAdapter adapter;
    protected SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2,
                                    long arg3) {
                Contato contact = (Contato) adapterView.getAdapter().getItem(arg2);
                Intent i = new Intent(getApplicationContext(), DetalheActivity.class);
                i.putExtra("contact", contact);
                startActivityForResult(i, 0);
            }

        });

        registerForContextMenu(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.pesqContato).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ContatoAdapter adapter = (ContatoAdapter)list.getAdapter();
        Contato contact = adapter.getItem(info.position);

        switch(item.getItemId()){
            case R.id.delete_item:
                //cDAO.deleteContact(contact);

                realm.beginTransaction();
                contact.removeFromRealm();
                realm.commitTransaction();

                Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
                buildListView();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    protected void buildListView() {
        //List<Contato> values = cDAO.buscaTodosContatos();
        //adapter = new ContatoAdapter(this, values);
        RealmResults<Contato> contatos = realm.where(Contato.class).findAll();
        final ContatoAdapter adapter = new ContatoAdapter(this, R.id.listView, contatos, true);
        list.setAdapter(adapter);

    }

    protected void buildSearchListView(String query) {
        //List<Contato> values = cDAO.buscaContato(query);
        //adapter= new ContatoAdapter(this, values);
        RealmResults<Contato> contatos = realm.where(Contato.class).contains("nome",query).findAll();
        final ContatoAdapter adapter = new ContatoAdapter(this, R.id.listView, contatos, true);
        list.setAdapter(adapter);

    }

}
