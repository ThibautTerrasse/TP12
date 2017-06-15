package fr.formation.tp12;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.formation.tp12.database.modele.User;

/**
 * Created by admin on 15/06/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    /**
     * List items
     */
    private List<User> items;
    /**
     * the resource id of item Layout
     */
    private int itemLayout;

    /**
     * Constructor RecyclerSimpleViewAdapter
     * @param items : the list items

     */
    public RecyclerViewAdapter(List<User> items, int layout) {
        this.items = items;
        this.itemLayout = layout;
    }

    /**
     * Create View Holder by Type
     * @param parent, the view parent
     * @param viewType : the type of View
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get inflater and get view by resource id itemLayout
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        // return ViewHolder with View
        return new ViewHolder(v);
    }

    /**
     * Get the size of items in adapter
     * @return the size of items in adapter
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
    /**
     * Bind View Holder with Items
     * @param holder: the view holder
     * @param position : the current position
     */
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        // find item by position
        User item = items.get(position);
        // save information in holder, we have one type in this adapter
        holder.mContentView .setText(item.toString());
        holder.itemView.setTag(item);
        /*if ((position % 2) == 0) {
            holder.itemView.setBackgroundResource(R.color.color1);
        } else {
            holder.itemView.setBackgroundResource(R.color.color2);
        }*/

        ((ViewHolder) holder).mContentView.setText(items.get(position).getNom());
    }
    /**
     *
     * @author florian
     * Class viewHolder
     * Hold an textView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // TextViex

        public final TextView mContentView;
        public final View mView;
        /**
         * Constructor ViewHolder
         * @param itemView: the itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            // link primaryText
            mView = itemView;
            mContentView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
