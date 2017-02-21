package jxnu.x3107.Adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MerchandiseLeftFragmentAdapter  extends FragmentPagerAdapter{
	private List<Fragment> list;

	public MerchandiseLeftFragmentAdapter(FragmentManager fm) {
		super(fm);
		
	}

	public MerchandiseLeftFragmentAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		
		return list.isEmpty() ? 0 : list.size();
	}

}