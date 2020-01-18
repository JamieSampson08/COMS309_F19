package com.iastate.eventXpert.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iastate.eventXpert.R;
import com.iastate.eventXpert.objects.Registrant;

import java.util.ArrayList;

/**
 * The array adapter necessary for the RegistrantInfoActivity RecyclerView
 */
public class RegistrantArrayAdapter extends RecyclerView.Adapter<RegistrantArrayAdapter.ViewHolder> {

	/**
	 * The id of the listItemLayout
	 */
	private int listItemLayout;

	/**
	 * A list of Registrants associated with this RegistrantRecyclerView
	 */
	private ArrayList<Registrant> registrantList;

	/**
	 * to allow clicking on events
	 */
	private OnEventListener uOnEventListener;

	/**
	 * Constructs a RegistrantArrayAdapter
	 *
	 * @param layoutId        the layoutId of the listItemLayout
	 * @param itemList        the list of Registrants to display
	 * @param onEventListener event listener
	 */
	public RegistrantArrayAdapter(int layoutId, ArrayList<Registrant> itemList, OnEventListener onEventListener) {
		this.uOnEventListener = onEventListener;
		listItemLayout = layoutId;
		this.registrantList = itemList;
	}

	/**
	 * returns number of registrants
	 *
	 * @return the number of registrants in the registrantList
	 */
	@Override
	public int getItemCount() {
		return registrantList == null ? 0 : registrantList.size();
	}

	/**
	 * Specify the layout and onCLick for each row
	 *
	 * @param parent   the parent of the returned ViewHoler
	 * @param viewType int viewType
	 *
	 * @return A ViewHolder with the result for this row
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
		ViewHolder myViewHolder = new ViewHolder(view, uOnEventListener);
		return myViewHolder;
	}

	/**
	 * Load data into each row element
	 *
	 * @param holder       the element for this row
	 * @param listPosition the position in the list
	 */
	@Override
	public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
		TextView name = holder.name;
		TextView email = holder.email;
		TextView isAdmin = holder.isAdmin;
		name.setText(registrantList.get(listPosition).getAssociatedUser().getFirstName() + " " + registrantList.get(listPosition).getAssociatedUser().getLastName());
		email.setText(registrantList.get(listPosition).getAssociatedUser().getEmail());
		isAdmin.setText((registrantList.get(listPosition).getAssociatedUserEvent().getIsAdmin() ? "true" : "false"));
	}

	/**
	 * Static inner class to initialize views in each row
	 */
	static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		/**
		 * The name of the registrant
		 */
		public TextView name;

		/**
		 * The email of the registrant
		 */
		public TextView email;

		/**
		 * Whether or not the registrant isAdmin
		 */
		public TextView isAdmin;

		OnEventListener onEventListener;

		/**
		 * Constructor for viewHolder
		 *
		 * @param itemView the view of the parent
		 */
		public ViewHolder(View itemView, OnEventListener onEventListener) {
			super(itemView);
			this.onEventListener = onEventListener;
			itemView.setOnClickListener(this);
			name = itemView.findViewById(R.id.registrant_name);
			email = itemView.findViewById(R.id.registrant_email);
			isAdmin = itemView.findViewById(R.id.registrant_is_admin);
		}

		/**
		 * The action that occurs when the item is clicked
		 */
		@Override
		public void onClick(View view) {
			Log.d("onclick", "onClick " + getLayoutPosition() + " " + name.getText());
			onEventListener.onEventClick(getAdapterPosition());
		}
	}

	/**
	 * Returns the item view type for a given position
	 *
	 * @param position of an item in the row
	 *
	 * @return R.layout.registrant_card_layout (the type for all elements)
	 */
	@Override
	public int getItemViewType(final int position) {
		return R.layout.registrant_cards_layout;
	}

	/**
	 * Listens for event and calls onEventClick with given position when event occurs.
	 */
	public interface OnEventListener {
		void onEventClick(int position);
	}
}