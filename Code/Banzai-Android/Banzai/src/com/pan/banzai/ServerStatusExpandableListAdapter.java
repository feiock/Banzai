package com.pan.banzai;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServerStatusExpandableListAdapter extends
		BaseExpandableListAdapter {
	public static final int sNumChildren = 1;
	public static final int sDonutInnerCircleRatio = 175;

	private ArrayList<ServerTierStatus> mTierStatuses;
	private Context mContext;

	public ServerStatusExpandableListAdapter(Context context,
			ArrayList<ServerTierStatus> tierStatuses) {
		mTierStatuses = tierStatuses;
		mContext = context;
	}

	@Override
	public int getGroupCount() {
		return mTierStatuses.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return sNumChildren;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mTierStatuses.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mTierStatuses.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// inflate the layout if the view doesn't exist already
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.list_group_server_status, parent, false);
		}
		// get the tier status
		ServerTierStatus tierStatus = (ServerTierStatus) getGroup(groupPosition);
		// set the header text to the tier name
		((TextView) convertView.findViewById(R.id.listGroupTextView))
				.setText(tierStatus.getTierName());

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// inflate the layout if the view doesn't exist yet
		View itemView = null;
		itemView = LayoutInflater.from(mContext).inflate(
				R.layout.list_item_server_status, parent, false);

		// get the tier status
		ServerTierStatus tierStatus = (ServerTierStatus) getChild(
				groupPosition, childPosition);

		// setup the donut graphs
		setUpDonut((DonutGraph) itemView.findViewById(R.id.cpuPieGraph),
				tierStatus.getCpuStatusPercent(), "CPU");
		setContainerListener(itemView.findViewById(R.id.cpuContainer), "CPU", 0);

		setUpDonut((DonutGraph) itemView.findViewById(R.id.ramPieGraph),
				tierStatus.getRamStatusPercent(), "RAM");
		setContainerListener(itemView.findViewById(R.id.ramContainer), "RAM", 1);

		setUpDonut((DonutGraph) itemView.findViewById(R.id.storagePieGraph),
				tierStatus.getStorageStatusPercent(), "Storage");
		setContainerListener(itemView.findViewById(R.id.storageContainer),
				"Storage", 2);

		addAverageQueueLengths(tierStatus.getAverageDiskQueueLengths(),
				(LinearLayout) itemView.findViewById(R.id.queueLengthContainer));

		return itemView;
	}

	/**
	 * Initializes a donut graph with only 2 values
	 * 
	 * @param pieGraph
	 *            The pieGraph on the layout
	 * @param value
	 *            Percentage out of 100 to show on graph
	 */
	private void setUpDonut(DonutGraph donut, float value, final String title) {
		// remove any existing slices
		donut.setPercentage(value);
	}

	private void setContainerListener(View container, final String title,
			final int type) {
		container.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = ((Activity) mContext).getFragmentManager();
				UtlizationFragment uFrag = new UtlizationFragment(title, type);
				fm.beginTransaction().replace(R.id.container, uFrag)
						.addToBackStack("frag").commit();
			}
		});
	}

	private void addAverageQueueLengths(ArrayList<Integer> lengths,
			ViewGroup container) {
		container.removeAllViews();
		// TODO replace i with actual disk number/ name
		for (int i = 0; i < lengths.size(); i++) {
			// TODO replace with custom view
			String message = container.getContext().getString(
					R.string.average_disk_queue_length, i, lengths.get(i));
			TextView lengthTextView = new TextView(container.getContext());
			lengthTextView.setText(message);
			lengthTextView.setTextColor(determineQueueLengthColor(lengths
					.get(i)));
			lengthTextView.setTextSize(14);
			container.addView(lengthTextView);
		}
	}

	private int determineQueueLengthColor(int value) {
		if (value >= DefaultValues.getQueueWaitLength()) {
			return mContext.getResources().getColor(R.color.critical);

		} else {
			return Color.BLACK;
		}
	}
}