package jxnu.x3107.SunGroup.OtherActivity.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.SGUrlService;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.SunGroup.OtherActivity.MerchandiseleftIssueActivity;
import jxnu.x3107.utils.Utils;


/*
 * 发布商品
 * */

public class MerchandiseleftFragmentIssue extends Fragment{

	private View merIssue;
	/*	private List<MySelfListing> mLists;
	private GoodsDataHelper goodsDHelper;*/

	private ListView lvIssue;

	private SharedPreferences sPreferences;
	private List<Goods> gList;
	private UrlService urlService;
	private User user ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		merIssue = inflater.inflate(R.layout.activity_merchandise_left_fragment_issue, container,false);
		sPreferences = getActivity().getSharedPreferences(WelComeActivity.USERNAME,0);
		gList = new ArrayList<Goods>();
		user = new User();
		urlService = new UrlService(getActivity());
		user = urlService.getUserInfoOther(sPreferences.getString("uN", ""));
		gList = urlService.getIDGoodsInfo(user.getStuNo());

		initView();

		return merIssue;


	}

	private void initView() {




		lvIssue = (ListView) merIssue.findViewById(R.id.merchandise_issue_news_head_lv);
		gList = new ArrayList<Goods>();
		user = new User();
		urlService = new UrlService(getActivity());
		user = urlService.getUserInfoOther(sPreferences.getString("uN", ""));
		gList = urlService.getIDGoodsInfo(user.getStuNo());

		if (gList == null) {
			Utils.showToast(getActivity(), "无法获得数据");
		}else{

			GoodsAdapter goodsAdapter = new GoodsAdapter(gList);

			Logger.d(gList.size()+"gList");
			lvIssue.setAdapter(goodsAdapter);

			ItemOnLongClick1();
		}
	}


	private void ItemOnLongClick1() {
		lvIssue.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.add(0,0,0,"编辑");
				menu.add(0,1,0,"删除");

			}	
		});
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		int mListPos = info.position;
		//		MID = (int) info.id;
		switch (item.getItemId()) {
		case 0:

			Utils.showToast(getActivity(), "编辑");



			Intent intent = new Intent(getActivity(),MerchandiseleftIssueActivity.class);

			String goodsNameBJ = gList.get(mListPos).getGoodName()+"";
			String studentNumberBJ = gList.get(mListPos).getReleaserID()+"";

			intent.putExtra("menu", "编辑");
			intent.putExtra("studentNumber", studentNumberBJ);
			intent.putExtra("goodsName", goodsNameBJ);
			intent.putExtra("releaseTime", gList.get(mListPos).getReleaseTime()+"");
			execCommand(gList.get(mListPos).getGoodLogo()+"");

			startActivity(intent);


			break;
		case 1:

			Utils.showToast(getActivity(), "删除"+mListPos);


			if(urlService.delIDGoodsInfo( gList.get(mListPos).getReleaserID()+"", gList.get(mListPos).getReleaseTime()+"") ){
				SGUrlService sgUrlService = new SGUrlService(getActivity());
				sgUrlService.delGoodsType(gList.get(mListPos).getReleaserID(), gList.get(mListPos).getReleaseTime());
				initView();

			}


			//			execCommand(mLists.get(mListPos).+"");删除图片




			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}
	public void execCommand(String path){
		if (!TextUtils.isEmpty(path)) {
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			File file = new File(path);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			getActivity().sendBroadcast(intent);
			file.delete();                                          
		}
	}

	private class GoodsAdapter extends BaseAdapter{

		private List<Goods> gList  = new ArrayList<Goods>();
		private LayoutInflater mInflater;
		private int size;
		public GoodsAdapter(List<Goods> gList) {
			this.gList = gList;
			size = gList.size();
			mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return size;
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
				holder = (ViewHolder)convertView.getTag();
			}


			//用户头像

			String imgUrl = UrlService.USERIMGURL + user.getUserLogo();
			System.out.println(imgUrl);

			/*try{
				Picasso.with(getActivity())
				.load(imgUrl)
				.placeholder(R.drawable.ic_launcher)
				.into(holder.ivUserPohto);
			}catch(Exception exception){
				exception.printStackTrace();
			}*/


			Picasso.with(getActivity())
			.load(imgUrl)
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivUserPohto);

			//用户名
			holder.tvuserName.setText(user.getUserName());

			//用户发布商品信息
			holder.tvshare.setText(gList.get(position).getGoodIntroduction());


			//用户发布商品图片
			/*
			try{
				Picasso.with(getActivity())
				.load(UrlService.USERIMGURL + "goods/" + gList.get(position).getGoodLogo())
				.placeholder(R.drawable.ic_launcher)
				.into(holder.ivPohot);
			}catch(Exception exception){
				exception.printStackTrace();
			}
			 */

			Picasso.with(getActivity())
			.load(UrlService.USERIMGURL + "goods/" + gList.get(position).getGoodLogo())
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.ivPohot);


			//显示时间
			holder.tvDate.setText(gList.get(position).getTime()+"");

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

	public  void initClean() {

		lvIssue.removeAllViews();

	}
}
