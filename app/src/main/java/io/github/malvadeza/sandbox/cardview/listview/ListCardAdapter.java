package io.github.malvadeza.sandbox.cardview.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import io.github.malvadeza.sandbox.R;
import io.github.malvadeza.sandbox.cardview.ListElement;

public class ListCardAdapter extends ArrayAdapter<ListElement> {
    private LayoutInflater mInflater;

    public ListCardAdapter(Context context) {
        super(context, R.layout.card_item);

        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = mInflater.inflate(R.layout.card_item, parent, false);
        }

        TextView textOne = (TextView) view.findViewById(R.id.text_one);
        TextView textTwo = (TextView) view.findViewById(R.id.text_two);

        textOne.setText(getItem(position).pTextOne);
        textTwo.setText(getItem(position).pTextTwo);

        return view;
    }
}
