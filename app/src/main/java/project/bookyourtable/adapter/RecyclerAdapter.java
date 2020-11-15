package project.bookyourtable.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;
import project.bookyourtable.R;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.RecyclerViewItemClickListener;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<TableEntity> mdata;
    private RecyclerViewItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }


    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

  /** The new ViewHolder will be used to display items of the adapter using onBindViewHolder(ViewHolder, int, List).
   * Since it will be re-used to display different items in the data set, it is a good idea to cache references to sub views of the View to avoid
   * unnecessary View.findViewById(int) calls*/
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//ici on décrit ou on insuffle les données
        TextView v = (TextView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    /**
     * */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {

        TableEntity item = mdata.get(position);
        viewholder.textView.setText("T" + item.getLocation() + "(p: " +item.getPersonNumber() +")");

        boolean availability = item.getAvailability();
        if(availability==false){
            viewholder.textView.setTextColor(Color.parseColor("#cccccc"));
        }
    }

    /**
     * This method return size of a data list and notify adapter numbers rows which can contain RecyclerView
     * */
    @Override
    public int getItemCount() {
        if (mdata != null) {
            return mdata.size();
        } else {
            return 0;
        }
    }


    public void setData(final List<TableEntity> data) {
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

