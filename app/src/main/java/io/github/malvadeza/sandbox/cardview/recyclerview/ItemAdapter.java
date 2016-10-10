package io.github.malvadeza.sandbox.cardview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.malvadeza.sandbox.R;
import io.github.malvadeza.sandbox.cardview.ListElement;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private LayoutInflater mInflater;
    private List<ListElement> mList;

    public ItemAdapter(List<ListElement> list) {
        mList = list;
    }

    @Override
    public ItemAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.textOne.setText(mList.get(position).pTextOne);
        holder.textTwo.setText(mList.get(position).pTextTwo);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textOne;
        private TextView textTwo;

        public ItemHolder(View itemView) {
            super(itemView);
            textOne = (TextView) itemView.findViewById(R.id.text_one);
            textTwo = (TextView) itemView.findViewById(R.id.text_two);
        }
    }
}
