package com.iastate.eventXpert.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iastate.eventXpert.R;
import com.iastate.eventXpert.activities.SignInActivity;
import com.iastate.eventXpert.helpers.APIHelper;
import com.iastate.eventXpert.objects.Event;
import com.iastate.eventXpert.objects.UserEvent;

import java.util.ArrayList;

import static com.iastate.eventXpert.constants.UrlConstants.BASE_URL;
import static com.mikepenz.iconics.Iconics.getApplicationContext;

/**
 * The Array Adapter necessary for the EventRecyclerView.
 */
public class EventArrayAdapter extends RecyclerView.Adapter<EventArrayAdapter.ViewHolder> {

	/**
	 * ItemLayout of list
	 */
	private int listItemLayout;

	/**
	 * Hold items in eventList
	 */
	private ArrayList<Event> eventList;

	/**
	 * to allow clicking on events
	 */
	private OnEventListener eOnEventListener;

	/**
	 * Constructor of the class. Creates EventArray Adapter.
	 *
	 * @param layoutId        Id of layout
	 * @param itemList        list of items to put in layout
	 * @param onEventListener event listener
	 */
	public EventArrayAdapter(int layoutId, ArrayList<Event> itemList, OnEventListener onEventListener) {
		this.eOnEventListener = onEventListener;
		listItemLayout = layoutId;
		this.eventList = itemList;
	}

	/**
	 * Gets size of list
	 *
	 * @return size of list
	 */
	@Override
	public int getItemCount() {
		return eventList == null ? 0 : eventList.size();
	}

	/**
	 * Specifies the row layout file and click for each row.
	 *
	 * @param parent   parent (previous) ViewGroup
	 * @param viewType type of view
	 *
	 * @return ViewHolder of new
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
		ViewHolder myViewHolder = new ViewHolder(view, eOnEventListener);
		return myViewHolder;
	}

	/**
	 * Load data in each row element given a view and position in the list.
	 *
	 * @param holder       ViewHolder of component
	 * @param listPosition Position in list
	 */
	@Override
	public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
		TextView name = holder.name;
		TextView description = holder.description;
		TextView startDateTime = holder.startDateTime;
		TextView endDateTime = holder.endDateTime;
		TextView location = holder.location;

		UserEvent userEvent = getUserEventForEvent(eventList.get(listPosition));

		if (userEvent != null && userEvent.getIsAdmin()) {
			holder.itemView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
			holder.itemView.findViewById(R.id.header).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
			((TextView) holder.itemView.findViewById(R.id.description_label)).setTextColor(getApplicationContext().getResources().getColor(R.color.white));
			((TextView) holder.itemView.findViewById(R.id.end_date_label)).setTextColor(getApplicationContext().getResources().getColor(R.color.white));
			((TextView) holder.itemView.findViewById(R.id.location_label)).setTextColor(getApplicationContext().getResources().getColor(R.color.white));
			holder.description.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
			holder.startDateTime.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
			holder.startDateTime.setGravity(0x05); //right
			holder.endDateTime.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
			holder.location.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
			holder.name.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
		}

		name.setText(eventList.get(listPosition).getName());
		description.setText(eventList.get(listPosition).getDescription());
		startDateTime.setText(eventList.get(listPosition).getStartDateTime().toString().substring(0, 10));
		endDateTime.setText(eventList.get(listPosition).getEndDateTime().toString());
		location.setText(eventList.get(listPosition).getLocation());
	}

	private UserEvent getUserEventForEvent(Event event) {
		String url = BASE_URL + "/userEvents/users/" + SignInActivity.sessionUserId + "/events/" + event.getId();
		Object res = APIHelper.makeGETRequest(url);
		if (res != null) {
			return new UserEvent(res.toString());
		}
		return null;
	}

	/**
	 * Static inner class to initialize the views of rows
	 */
	static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView name;
		public TextView description;
		public TextView startDateTime;
		public TextView endDateTime;
		public TextView location;
		public View itemView;
		OnEventListener onEventListener;

		public ViewHolder(View itemView, OnEventListener onEventListener) {
			super(itemView);
			this.itemView = itemView;
			this.onEventListener = onEventListener;
			itemView.setOnClickListener(this);
			name = (TextView) itemView.findViewById(R.id.event_name);
			description = (TextView) itemView.findViewById(R.id.event_description);
			startDateTime = (TextView) itemView.findViewById(R.id.event_start_date);
			endDateTime = itemView.findViewById(R.id.event_end_time);
			location = itemView.findViewById(R.id.event_location);
		}

		@Override
		public void onClick(View view) {
			Log.d("onclick", "onClick " + getLayoutPosition() + " " + name.getText());
			onEventListener.onEventClick(getAdapterPosition());

		}
	}

	/**
	 * Finds the viewType of an item at a given position
	 *
	 * @return viewType at given position
	 */
	@Override
	public int getItemViewType(final int position) {
		return R.layout.cards_layout;
	}

	/**
	 * Listens for event and calls onEventClick with given position when event occurs.
	 */
	public interface OnEventListener {
		void onEventClick(int position);
	}
}