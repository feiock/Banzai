package com.pan.banzai;

import java.util.ArrayList;
import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class DashboardFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_dashboard, container,
				false);

		// add the adapter to the expandable list view
		ExpandableListView serverStatusList = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		serverStatusList.setAdapter(new ServerStatusExpandableListAdapter(
				getActivity(), getData()));

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private ArrayList<ServerTierStatus> getData() {
		ArrayList<ServerTierStatus> statuses = new ArrayList<ServerTierStatus>();

		
		ArrayList<Integer> empty = new ArrayList<Integer>();
		
		Random r = new Random();
		
		statuses.add(new ServerTierStatus("App Tier", showRandomInteger(90, 100, r), showRandomInteger(70, 80, r), showRandomInteger(40, 60, r), empty));
		statuses.add(new ServerTierStatus("Web Tier", showRandomInteger(80, 90, r), showRandomInteger(60, 75, r), showRandomInteger(90, 100, r), empty));
		statuses.add(new ServerTierStatus("Data Tier", showRandomInteger(30, 40, r), showRandomInteger(40, 50, r), showRandomInteger(40, 50, r), empty));

		return statuses;
	}
	
	public static int showRandomInteger(int aStart, int aEnd, Random aRandom){
	    if (aStart > aEnd) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = (long)aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);    
	    return randomNumber;
	  }
}