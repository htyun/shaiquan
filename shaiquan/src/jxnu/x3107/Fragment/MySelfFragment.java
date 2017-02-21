package jxnu.x3107.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.SGUrlService;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.SunGroup.OtherActivity.HomeGoodsGVActivity;
import jxnu.x3107.SunGroup.OtherActivity.UserIntroActivity;
import jxnu.x3107.utils.Utils;

public class MySelfFragment extends Fragment{

	private View myselfLayout;

	/*	private RelativeLayout Head;
	private ImageView myselfHead;
	private TextView myselfUserName;*/
	private ListView myselfListing;


	private SharedPreferences sPreferences;
	private User user;
	private UrlService urlService;

	private SGUrlService sgUrlService;
	private List<Goods> gList;
	private List<User> uList;




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		myselfLayout = inflater.inflate(R.layout.myself_layout, container,false);

		sPreferences = getActivity().getSharedPreferences(WelComeActivity.USERNAME,0);
		urlService = new UrlService(getActivity());
		sgUrlService = new SGUrlService(getActivity());

		initView();


		initListing();


		return myselfLayout;
	}



	private void initView() {
		myselfListing = (ListView) myselfLayout.findViewById(R.id.myself_news_head_lv);

	}



	private void initListing() {


		gList = new ArrayList<Goods>();
		gList = sgUrlService.getFollowGoods(sPreferences.getString("uN", ""));


		if (gList == null) {
			Utils.showToast(getActivity(), "无法获得数据");
			ShareAdapter shareAdapter = new ShareAdapter(null,null);
			myselfListing.setAdapter(shareAdapter);
		}else{

			uList = new ArrayList<User>();
			for(int i = 0; i < gList.size(); i++){
				user = new User();
				user = urlService.getUserInfoOther(gList.get(i).getReleaserID());
				uList.add(user);
			}

			ShareAdapter shareAdapter = new ShareAdapter(gList,uList);
			myselfListing.setAdapter(shareAdapter);
			myselfListing.setDivider(null);//消除listview横线
			//		myselfListing.setEnabled(false);
			ItemOnLongClick1();
		}
	}




	private void ItemOnLongClick1() {
		myselfListing.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.add(0,0,0,"查看个人信息");
				menu.add(0,1,0,"查看商品信息");
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		int mListPos = info.position;

		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent(getActivity(),UserIntroActivity.class);
			intent.putExtra("followstuNo",gList.get(mListPos).getReleaserID());
			startActivity(intent);
			getActivity().finish();
			break;
		case 1:

			Intent intent1 = new Intent(getActivity(),HomeGoodsGVActivity.class);
			intent1.putExtra("goodsName", ""+gList.get(mListPos).getGoodName());
			intent1.putExtra("studentNumber", ""+gList.get(mListPos).getReleaserID());
			intent1.putExtra("releaseTime", ""+gList.get(mListPos).getReleaseTime());
			startActivity(intent1);
			getActivity().finish();
			Logger.d(gList+"");


			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	/*
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

//		case R.id.myself_news_username:
		case R.id.myself_news_head_image:

			Intent intentHead = new Intent(getActivity(),PersonalleftActivity.class);
			startActivity(intentHead);
			getActivity().finish();

			break;

		default:
			break;
		}
	}
	 */





	class ShareAdapter extends BaseAdapter {

		private List<Goods> mList  = new ArrayList<Goods>();
		private List<User> uLists = new ArrayList<User>();

		public ShareAdapter(List<Goods> mList,List<User> uList) {
			this.mList = mList;
			this.uLists = uList;
		}

		@Override
		public int getCount() {
			if (mList == null) {
				return 0;
			}else {
				
				return mList.size();
			}
			
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView( final int position, View convertView, ViewGroup parent) {



			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.myself_layout_list_share, null);


				holder.ivUserPohto = (ImageView) convertView.findViewById(R.id.myself_news_userPhoto);
				holder.tvuserName = (TextView) convertView.findViewById(R.id.myself_news_share_user_pohot);
				holder.tvDate = (TextView) convertView.findViewById(R.id.myself_news_date);
				holder.tvDetailed  = (TextView) convertView.findViewById(R.id.myself_news_detailed);
				holder.tvshare = (TextView) convertView.findViewById(R.id.myself_news_share_text);
				holder.ivPohot = (ImageView) convertView.findViewById(R.id.myself_news_share_pohot);
				holder.ivLike  = (ImageView) convertView.findViewById(R.id.myself_news_reply_icon);

				convertView.setTag(holder);
			}
			else{


				holder = (ViewHolder)convertView.getTag();
			}


			/*			Picasso.with(getActivity())
			.load()
			.placeholder(R.drawable.ic_launcher)
			.into(holder.ivUserPohto);*/



			Picasso.with(getActivity())
			.load(UrlService.USERIMGURL + uLists.get(position).getUserLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivUserPohto);

			//用户名

			holder.tvuserName.setText(uLists.get(position).getUserName());

			//用户发布商品信息
			holder.tvshare.setText(mList.get(position).getGoodIntroduction());

			//用户发布商品图片


			/*			Picasso.with(getActivity())
			.load(UrlService.USERIMGURL + "goods/" + mList.get(position).getGoodLogo())
			.placeholder(R.drawable.ic_launcher)
			.into(holder.ivPohot);*/

			Picasso.with(getActivity())
			.load(UrlService.USERIMGURL + "goods/" + mList.get(position).getGoodLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivPohot);


			//显示时间
			holder.tvDate.setText(mList.get(position).getTime()+"");


			//查看商品详情的textview

			holder.tvDetailed.setText("");





			return convertView;
		}



	}

	static class ViewHolder { 
		ImageView ivUserPohto;
		TextView tvuserName;
		TextView tvshare;
		ImageView ivPohot;
		TextView tvDate;
		TextView tvDetailed;
		TextView tvLikenum;
		ImageView ivLike;
	}


}
