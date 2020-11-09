package project.bookyourtable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.RecyclerViewItemClickListener;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mdata;
    private RecyclerViewItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    //VIA CA ON AJOUTE LES COMPORTEMENTS CLIC LONG ET CLICK A LA LISTE
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);

        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));         //Créer le layout recycleView pour l'affichage des tables
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mdata.get(position);
        holder.textView.setText(item.getClass()+"TOTO sa");
    }

    @Override
    public int getItemCount() {
        if (mdata != null) {
            return mdata.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mdata == null) {
            mdata = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mdata.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (data instanceof TableEntity) {
                        return ((TableEntity) data.get(oldItemPosition)).getId().equals(((TableEntity) data.get(newItemPosition)).getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (data instanceof TableEntity) {
                        TableEntity newTable = (TableEntity) data.get(newItemPosition);
                        TableEntity oldTable = (TableEntity) data.get(newItemPosition);
                        return newTable.getId().equals(oldTable.getId())
                                && Objects.equals(newTable.getPersonNumber(), oldTable.getPersonNumber())
                                && Objects.equals(newTable.getAvailability(), oldTable.getAvailability())
                                && newTable.getLocation()==(oldTable.getLocation());
                    }
                    return false;
                }
            });
            mdata = data;
            result.dispatchUpdatesTo(this);
        }
    }
}

