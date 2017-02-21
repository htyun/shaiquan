package jxnu.x3107.Adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.x3107.SunGroup.LocatorActivity;
import jxnu.x3107.bean.LocatorAnDSeek;

public class LocatorRecordAdapter extends BaseAdapter{
	private ArrayList<LocatorAnDSeek> listLocatorRecord;// 所有的Item
	private ArrayList<LocatorAnDSeek> mObject;// 过滤后的item

	private Context mContext;

	private final Object mLock = new Object();
	private int mMaxMatch = 10;// 最多显示多少个选项,负数表示全部
	private OnClickListener mOnClickListener;
	private SharedPreferences sPreferences;

	public LocatorRecordAdapter(Context context,int maxMatch,OnClickListener onClickListener) {
		this.mContext = context;
		this.mMaxMatch = maxMatch;
		this.mOnClickListener = onClickListener;

		initRecordHistory();//历史

		mObject = listLocatorRecord;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == mObject ? 0 :mObject.size();
	}

	@Override
	public Object getItem(int position) {

		return null == mObject ? 0 : mObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		AutoHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.locator_seek, parent,false);

			holder = new AutoHolder();
			holder.content = (TextView) convertView.findViewById(R.id.auto_content);
			holder.addButton = (TextView) convertView.findViewById(R.id.auto_add);
			holder.autoImage = (TextView) convertView.findViewById(R.id.auto_image);

			convertView.setTag(holder);

		}else {
			holder = (AutoHolder) convertView.getTag();
		}


		LocatorAnDSeek lAnds = mObject.get(position);
		holder.content.setText(lAnds.getLocator());
		holder.addButton.setTag(lAnds);
		holder.addButton.setOnClickListener(mOnClickListener);

		return convertView;

	}
	
	/**
	 * 读取历史搜索记录
	 */
	public  void initRecordHistory() {

		sPreferences = mContext.getSharedPreferences(LocatorActivity.LOCATOR_SEEK_T,0);
		
		String longhistory = sPreferences.getString(LocatorActivity.LOCATOR_SEEK_T, "");
		String hisArrays[] = longhistory.split(",");
		listLocatorRecord = new ArrayList<LocatorAnDSeek>();
		if (hisArrays.length == 1) {
			return;
		}
		for(int i = 0;i < hisArrays.length; i++){
			listLocatorRecord.add(new LocatorAnDSeek().setLocator(hisArrays[i]));
		}
	}
	public  void initRecordHistory(int n) {
		if (n == 1) {
			listLocatorRecord.clear();
			sPreferences.edit().clear().commit();
			

		}
		notifyDataSetChanged();
	}
	
	/**
	 * 匹配过滤搜索内容
	 * 
	 * @param prefix
	 *            输入框中输入的内容
	 */
	
	@SuppressLint("DefaultLocale")
	public void performFiltering(CharSequence pSequence){
		
		if (pSequence == null || pSequence.length() == 0) {//搜索框内容为空的时候显示所有历史记录
			
			
			
			synchronized (mLock) {
				mObject = listLocatorRecord;
			}
			
		}else {
			String prefixString = pSequence.toString().toLowerCase();
			int count = listLocatorRecord.size();
			ArrayList<LocatorAnDSeek> newValues = new ArrayList<LocatorAnDSeek>(
					count);
			for (int i = 0; i < count; i++) {
				final String value = listLocatorRecord.get(i).getLocator();
				final String valueText = value.toLowerCase();
				if (valueText.contains(prefixString)) {

				}
				if (valueText.startsWith(prefixString)) {
					newValues.add(new LocatorAnDSeek().setLocator(valueText));
				} else {
					final String[] words = valueText.split(" ");
					final int wordCount = words.length;
					for (int k = 0; k < wordCount; k++) {
						if (words[k].contains(prefixString)) {//startsWith判断开头字符串是不是匹配
							newValues.add(new LocatorAnDSeek()
									.setLocator(value));
							break;
						}
					}
				}
				if (mMaxMatch > 0) {
					if (newValues.size() > mMaxMatch - 1) {
						break;
					}
				}
			}
			mObject = newValues;
		}
		notifyDataSetChanged();
	}

	private class AutoHolder {
		TextView content;
		TextView addButton;
		@SuppressWarnings("unused")
		TextView autoImage;
	}
}
