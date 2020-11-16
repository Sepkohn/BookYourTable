package project.bookyourtable.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import project.bookyourtable.R;
import project.bookyourtable.database.entity.BookingEntity;
import project.bookyourtable.database.entity.TableEntity;
import project.bookyourtable.util.RecyclerViewItemClickListener;

public class BookingsRecyclerAdapter<T> extends RecyclerView.Adapter<BookingsRecyclerAdapter.ViewHolder> {

    private List<BookingEntity> mdata;

    public BookingsRecyclerAdapter(List<BookingEntity> mdata) {
        this.mdata = mdata;
    }

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


    public BookingsRecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /*Maintenant, il ne nous reste plus qu'à lier tout cela ensemble grâce à un Adapter. Ainsi, dans le package Views de notre application, nous allons créer un Adapter*/
    //VIA CA ON AJOUTE LES COMPORTEMENTS CLIC LONG ET CLICK A LA LISTE
    @Override
    public BookingsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //ici on décrit ou on insuffle les données
        TextView v = (TextView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.booking_recycler_view, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));         //Créer le layout recycleView pour l'affichage des tables
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {

        BookingEntity item = mdata.get(position);
        String text  = item.getName() + " at " + item.getTime() + " for " + item.getNumberPersons() + " persons " +"\n" +"at emplacement : " + item.getTableNumber();
        viewholder.textView.setText(text);

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


    /**
     * Define the datas to display in the Recycler view
     * @param data = list of components to display
     */
    public void setData(final List<BookingEntity> data) {
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
                        return (data.get(oldItemPosition)).getId().equals((data.get(newItemPosition)).getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (data instanceof BookingEntity) {
                        BookingEntity newTable = data.get(newItemPosition);
                        BookingEntity oldTable = data.get(newItemPosition);
                        return newTable.getId().equals(oldTable.getId())
                                && Objects.equals(newTable.getDate(), oldTable.getDate())
                                && Objects.equals(newTable.getName(), oldTable.getName())
                                && Objects.equals(newTable.getTelephoneNumber(), oldTable.getTelephoneNumber())
                                && Objects.equals(newTable.getMessage(), oldTable.getMessage())
                                && Objects.equals(newTable.getTableNumber(), oldTable.getTableNumber())
                                && Objects.equals(newTable.getNumberPersons(), oldTable.getNumberPersons())
                                && newTable.getTime()==(oldTable.getTime());
                    }
                    return false;
                }
            });
            mdata = data;
            result.dispatchUpdatesTo(this);
        }
    }
}

