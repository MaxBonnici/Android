package com.Max.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Traduction> tradList;

    public HistoryAdapter(Context context, ArrayList<Traduction> tradList) {
        this.context = context;
        this.tradList = tradList;
    }

    @Override
    public int getCount() {
        return tradList.size();
    }

    @Override
    public Object getItem(int position) {
        return tradList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_history, parent, false);
        }

        TextView textSource = view.findViewById(R.id.textSource);
        TextView textTranslated = view.findViewById(R.id.textFinal);

        Traduction trad = tradList.get(position);
        textSource.setText(trad.getSourceText());
        textTranslated.setText(trad.getTranslatedText());

        return view;
    }
}
