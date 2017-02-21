package jxnu.x3107.SunGroup.OtherActivity.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * 购买商品
 * */

public class MerchandiseleftFragmentBuy extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity().getApplicationContext());
		textView.setText("MerchandiseleftFragmentBuy");
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}
}
