package com.pan.banzai;

import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OsUsageFragment extends Fragment {
	private UsagePieGraph mPieChart;
	private BanzaiLineGraph mLineChart;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_osusage, container,
				false);

		mPieChart = (UsagePieGraph) view.findViewById(R.id.os_current_pie_chart);
		setCurrentValues();

		mLineChart = (BanzaiLineGraph) view
				.findViewById(R.id.os_historic_line_chart);
		setHistoricData();

		return view;
	}

	// TODO get actual data
	private void setCurrentValues() {
		// TODO http request?

		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("Windows", 55f);
		map.put("Mac", 40f);
		map.put("Linux", 5f);

		mPieChart.setData(map);
	}

	// TODO get actual data
	private void setHistoricData() {
		// TODO http request?

		HashMap<String, Float[]> map = new HashMap<String, Float[]>();
		map.put("Windows", new Float[] { 60f, 58f, 55f });
		map.put("Mac", new Float[] { 30f, 32f, 40f });
		map.put("Linux", new Float[] { 10f, 10f, 5f });

		Date[] times = new Date[] { new Date(2015, 1, 10, 0, 30, 30),
				new Date(2015, 1, 10, 0, 35, 30),
				new Date(2015, 1, 10, 0, 40, 30) };

		mLineChart.setData(map, times);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
