package io.github.malvadeza.sandbox.cardview.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.malvadeza.sandbox.R;
import io.github.malvadeza.sandbox.cardview.ListElement;

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private List<ListElement> list;

    ItemAdapter(@NonNull List<ListElement> list) {
        this.list = list;
    }

    @Override
    public ItemAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.textOne.setText(list.get(position).pTextOne);
        holder.textTwo.setText(list.get(position).pTextTwo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void addItem(@NonNull final ListElement element) {
        final int position = list.size();

        list.add(element);
        notifyItemInserted(position);
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textOne;
        private TextView textTwo;

        ItemHolder(View itemView) {
            super(itemView);
            textOne = (TextView) itemView.findViewById(R.id.text_one);
            textTwo = (TextView) itemView.findViewById(R.id.text_two);
        }
    }
}
