package jxnu.x3107.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import jxnu.n433.x3107.SunGroup.Goods;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.x3107.utils.Utils;

public class GirdViewAdapter extends BaseAdapter{


	//首页商品显示网格布局


	private int colors[] = new int[] {
			Color.rgb(189, 202, 188),
			Color.rgb(222, 203, 161),
			Color.rgb(244, 107, 65)
	};


	private Context mContext;
	private List<Goods> gList= new ArrayList<Goods>();;

	public GirdViewAdapter(Context context,List<Goods> gList) {
		this.mContext = context;
		this.gList = gList;
		//		goodsDHelper = new GoodsDataHelper(mContext);


		//		mSelfListing = goodsDHelper.getGoodsInfoList();
	}

	@Override
	public int getCount() {

		if (gList == null) {
			return 0;
		}else{

			if (gList.size()<6) {
				return gList.size();
			}else {
				return 6;
			}
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

		HotGriViewHolder holder = null;
		if (convertView == null) {
			Random random = new Random();
			int n = random.nextInt(4);
			if (n % 2 == 0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.homepage_layout_main_adapter_commodity_view_left,
						null
						);
			} else {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.homepage_layout_main_adapter_commodity_view_right,
						null
						);
			}
			holder = new HotGriViewHolder(convertView,position,gList);
			Random random2 = new Random();
			int n2 = random2.nextInt(3);
			holder.getBgLayout().setBackgroundColor(colors[n2]);
			/*
			try {
				Bitmap btUP = BitmapFactory.decodeFile(mSelfListing.get(position).getGoodsImages());//从Sd中找头像，转换成Bitmap
				if(btUP!=null){
					@SuppressWarnings("deprecation")
					Drawable drawable = new BitmapDrawable(btUP);//转换成drawable
					holder.getImageView().setImageDrawable(drawable);
				}else{
			 *//**
			 *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
			 * 
			 *//*

					//				Utils.showToast(getActivity(), "没有图片");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			String imgUrl = UrlService.USERIMGURL + "goods/" + gList.get(position).getGoodLogo();
			//			Picasso.with(mContext).load( ).placeholder(R.drawable.ic_launcher).into(holder.getImageView());

			Picasso.with(mContext)
			.load(imgUrl)
			.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
			//	         .networkPolicy(NetworkPolicy.NO_CACHE)
			.into(holder.getImageView());

			holder.getText();
			holder.getShopName();
			convertView.setTag(holder);
		}else {
			holder = (HotGriViewHolder) convertView.getTag();
		}

		return convertView;
	}

	private class HotGriViewHolder implements OnClickListener{

		private ImageView imageView;
		private TextView shopName;
		private TextView text;
		private View view;
		private int position;
		//		private GoodsDataHelper gDHelper;
		private List<Goods> selfListing;

		private RelativeLayout bgLayout;

		private HotGriViewHolder(View view,int position,List<Goods> selfListing){
			this.view = view;
			this.position = position;
			this.selfListing = selfListing;

			selfListing = new ArrayList<Goods>();
			//			gDHelper = new GoodsDataHelper(mContext);
			//			selfListing = gDHelper.getGoodsInfoList();
		}

		public ImageView getImageView() {

			if (imageView == null) {
				imageView = (ImageView) view.findViewById(R.id.commodity_name);

			}

			return imageView;
		}

		public TextView getShopName() {

			if (shopName == null) {
				shopName = (TextView) view.findViewById(R.id.commodity_shopname);
				shopName.setText(selfListing.get(position).getGoodName()+"");
			}

			return shopName;
		}

		public TextView getText() {

			if (text == null) {
				text = (TextView) view.findViewById(R.id.commodity_text);
				text.setText(selfListing.get(position).getGoodIntroduction());
			}

			return text;
		}

		public RelativeLayout getBgLayout() {

			if (bgLayout == null) {
				bgLayout = (RelativeLayout) view.findViewById(R.id.commodity_bg);
				//				bgLayout.setOnClickListener(this);

			}

			return bgLayout;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.commodity_bg:
				Utils.showToast(mContext, shopName.getText().toString().trim()+"");
				break;

			default:
				break;
			}
		}


	}

}
