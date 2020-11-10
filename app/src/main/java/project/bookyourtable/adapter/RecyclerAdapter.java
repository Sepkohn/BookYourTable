package project.bookyourtable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

    public RecyclerAdapter(List<TableEntity> mdata) {
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


    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /*Maintenant, il ne nous reste plus qu'à lier tout cela ensemble grâce à un Adapter. Ainsi, dans le package Views de notre application, nous allons créer un Adapter*/
    //VIA CA ON AJOUTE LES COMPORTEMENTS CLIC LONG ET CLICK A LA LISTE
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//ici on décrit ou on insuffle les données
        TextView v = (TextView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);

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

        TableEntity item = mdata.get(position);
        //viewholder.textView.setText("Table id: " + item.getId() + "(Personnes: " +item.getPersonNumber()+ ", Position: " + item.getLocation() + ".");
        viewholder.textView.setText("Table n°: " + item.getLocation() + "(P.: " +item.getPersonNumber() +")");

        long id = item.getId();
        int nbPerson = item.getPersonNumber();
        int numLocation = item.getLocation();

        //ceci est à utiliser seulement quand on passe dans des objets présents dans le layout voir vidéo et la méthode setData s'y rapportant pour l'exemple
        //viewholder.setData(id,nbPerson,numlocation);
    }
//voir méthode onBindViewHolder pour la raison
//    public void setData(long id, int nbPerson, int numLocation){
//        this.id=id;
//        this.nbPerson=nbPerson;
//        this.numLocation=numLocation;
//    }

    /*Cette méthode permet de retourner la taille de notre liste d'objet, et ainsi indiquer à l'Adapter le nombre de lignes que peut contenir la RecyclerView.*/
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

