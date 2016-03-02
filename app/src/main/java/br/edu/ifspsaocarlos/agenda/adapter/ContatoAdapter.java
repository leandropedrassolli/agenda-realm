package br.edu.ifspsaocarlos.agenda.adapter;



import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.ListAdapter;
import android.widget.TextView;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ContatoAdapter extends RealmBaseAdapter<Contato> implements ListAdapter {

    private LayoutInflater inflater;

    // Construtor de acordo com herança.
    public ContatoAdapter(Context context, int resId,
                          RealmResults<Contato> realmResults,
                          boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contato_celula, null);
            holder = new ViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.nome);
            holder.telefone = (TextView) convertView.findViewById(R.id.fone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // faz consulta no banco.
        Contato c = realmResults.get(position);
        holder.nome.setText(c.getNome());
        holder.telefone.setText(c.getFones().first().getNumero());
        return convertView;
    }

    static class ViewHolder {
        public TextView nome;
        public TextView telefone;
    }

    // Referencia os resultados das consulta.
    public RealmResults<Contato> getRealmResults() {
        return realmResults;
    }

}


