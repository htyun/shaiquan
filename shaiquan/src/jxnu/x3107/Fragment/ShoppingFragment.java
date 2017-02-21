package jxnu.x3107.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.SGUrlService;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.SunGroup.OtherActivity.HomeGoodsGVActivity;
import jxnu.x3107.utils.Utils;

public class ShoppingFragment extends Fragment {

	private ListView lvCollection;
	private View shoppingLayout;
	private ImageView ivbianJi;
	private TextView tView;
	private int count;

	private SharedPreferences sPreferences;
	private SGUrlService sgUrlService;
	private UrlService urlService;
	private User user;
	private List<Goods> gList;
	private List<User> uList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		shoppingLayout = inflater.inflate(R.layout.shopping_layout, container,false);

		sPreferences = getActivity().getSharedPreferences(WelComeActivity.USERNAME,0);
		sgUrlService = new SGUrlService(getActivity());
		urlService = new UrlService(getActivity());

		count = 0;

		initView();

		return shoppingLayout;
	}

	private void initView() {

		lvCollection = (ListView) shoppingLayout.findViewById(R.id.shopping_news_head_lv);
		tView = (TextView) shoppingLayout.findViewById(R.id.tv_shopping_bianji);
		ivbianJi = (ImageView) shoppingLayout.findViewById(R.id.iv_shopping_bianji);

		gList = new ArrayList<Goods>();
		gList = sgUrlService.getGoodsTypeInfo(sPreferences.getString("uN", ""));


		if (gList == null) {
			Utils.showToast(getActivity(), "无法获得数据");
			GoodsAdapter shareAdapter = new GoodsAdapter(null,null);
			lvCollection.setAdapter(shareAdapter);
		}else{

			if (gList.size() == 0) {
				count++;
				ivbianJi.setEnabled(false);
			}

			uList = new ArrayList<User>();
			for(int i = 0; i < gList.size(); i++){
				user = new User();
				user = urlService.getUserInfoOther(gList.get(i).getReleaserID());
				uList.add(user);
			}



			ivbianJi.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					count++;
					if (count % 2 == 1) {
						tView.setText("编辑收藏");
						//						ItemOnLongClick1();
						lvCollectionOnItem2();


					}
					if (count % 2 == 0) {
						tView.setText("收藏");
						lvCollectionOnItem1();
					}

				}
			});




			GoodsAdapter shareAdapter = new GoodsAdapter(gList,uList);
			lvCollection.setAdapter(shareAdapter);
			lvCollection.setDivider(null);//消除listview横线

			if (count % 2 == 0) {
				tView.setText("收藏");
				lvCollectionOnItem1();
			}


		}

	}

	private void lvCollectionOnItem1() {
		lvCollection.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(),HomeGoodsGVActivity.class);
				intent.putExtra("int","hotGridView:"+arg2);
				intent.putExtra("goodsName", ""+gList.get(arg2).getGoodName());
				intent.putExtra("studentNumber", ""+gList.get(arg2).getReleaserID());
				intent.putExtra("releaseTime", ""+gList.get(arg2).getReleaseTime());
				startActivity(intent);
				getActivity().finish();
				Logger.d(arg2+"");
			}
		});
	}
	private void lvCollectionOnItem2() {
		lvCollection.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				if (sgUrlService.delGoodsType(gList.get(arg2).getReleaserID(), gList.get(arg2).getReleaseTime())) {
					Utils.showToast(getActivity(), "删除成功");
					initView();

				}else {
					Utils.showToast(getActivity(), "删除失败");
				}

			}
		});
	}


	/*private void ItemOnLongClick1() {
		lvCollection.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.add(0,0,0,"删除");

			}	
		});
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		int mListPos = info.position;
		switch (item.getItemId()) {
		case 0:


			if (sgUrlService.delGoodsType(gList.get(mListPos).getReleaserID(), gList.get(mListPos).getReleaseTime())) {
				Utils.showToast(getActivity(), "删除成功");
				initView();

			}else {
				Utils.showToast(getActivity(), "删除失败");

			}

			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}*/

	private class GoodsAdapter extends BaseAdapter{

		private List<Goods> gList  = new ArrayList<Goods>();
		private List<User> uLists = new ArrayList<User>();
		private LayoutInflater mInflater;
		public GoodsAdapter(List<Goods> gList,List<User> uList) {
			this.gList = gList;
			this.uLists = uList;
			mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {

			if (gList == null) {
				return 0;
			}else {
				return  gList.size();

			}

		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {



			ViewHolder holder = null;
			if (convertView == null) {

				//				user = new User();
				//				user = urlService.getUserInfoOther(gList.get(position).getReleaserID());

				convertView = mInflater.inflate(R.layout.myself_layout_list_share, null);
				holder = new ViewHolder();

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
				//				user = new User();
				//				user = urlService.getUserInfoOther(gList.get(position).getReleaserID());
				holder = (ViewHolder)convertView.getTag();
			}



			//用户头像


			Picasso.with(getActivity())
			.load(UrlService.USERIMGURL + uLists.get(position).getUserLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
			//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivUserPohto);

			//			Picasso.with(getActivity())
			//			.load()
			//			.placeholder(R.drawable.ic_launcher)
			//			.into(holder.ivUserPohto);

			//用户名
			holder.tvuserName.setText(uLists.get(position).getUserName());

			//用户发布商品信息
			holder.tvshare.setText(gList.get(position).getGoodIntroduction());

			//用户发布商品图片
			/*
			Picasso.with(getActivity())
			.load()
			.placeholder(R.drawable.ic_launcher)
			.into(holder.ivPohot);*/




			Picasso.with(getActivity())
			.load(UrlService.USERIMGURL + "goods/" + gList.get(position).getGoodLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
			//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivPohot);

			//显示时间
			holder.tvDate.setText(gList.get(position).getTime());

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
