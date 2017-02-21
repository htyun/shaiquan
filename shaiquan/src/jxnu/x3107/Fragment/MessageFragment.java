package jxnu.x3107.Fragment;


import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.Fragment.View.RoundImageView;
import jxnu.x3107.SunGroup.AboutleftActivity;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.SunGroup.OtherActivity.MerchandiseleftActivity;
import jxnu.x3107.SunGroup.OtherActivity.PersonalleftActivity;
import jxnu.x3107.SunGroup.OtherActivity.SettingleftActivity;
import jxnu.x3107.utils.Utils;

public class MessageFragment extends Fragment implements OnClickListener{
	private View messageLayout;

	private RoundImageView leftHead;//头像
	private RelativeLayout Head;
	private View leftPersonal;
	private View leftMerchandise;
	private View leftSetting;
	private View leftAbout;
	private UrlService gurl;
	private User user;

	private SharedPreferences sPreferences;
	//	private Bitmap btPersonalHead;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.activity_main_layout_left, container,false);
		sPreferences = getActivity().getSharedPreferences(WelComeActivity.USERNAME,0);
		user = new User();
		gurl = new UrlService(getActivity());
		user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));
		initView();

		return messageLayout;
	}

	private void initView() {


		leftHead = (RoundImageView)messageLayout.findViewById(R.id.zdy_image_main);
		Head = (RelativeLayout) messageLayout.findViewById(R.id.iv_left_head);
		leftPersonal = messageLayout.findViewById(R.id.main_left_personal);
		leftMerchandise =messageLayout. findViewById(R.id.main_left_merchandise);
		leftSetting = messageLayout.findViewById(R.id.main_left_setting);
		leftAbout = messageLayout.findViewById(R.id.main_left_about);


		user = gurl.getUserInfoOther(sPreferences.getString("uN", ""));
		String imgUrl = UrlService.USERIMGURL + user.getUserLogo();
		/*	Picasso.with(getActivity())
		.load(imgUrl)
		.resize(500,500)
		.placeholder(R.drawable.ic_launcher)
		.into(leftHead);*/


		Picasso.with(getActivity())
		.load(imgUrl)
		.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//		.networkPolicy(NetworkPolicy.NO_CACHE)
		.into(leftHead);

		leftPersonal.setOnClickListener(this);
		leftMerchandise.setOnClickListener(this);
		leftSetting.setOnClickListener(this);
		leftAbout.setOnClickListener(this);
		Head.setOnClickListener(this);


	}

	private void setLeftSelect(int i) {
		switch (i) {
		case 1:
		case 2:
			
			if (null == (user.getStuNo()) || "".equals(user.getStuNo())) {
				Utils.showToast(getActivity(), "无法获取信息");
				return;
			}
			
			Intent intent1 = new Intent(getActivity(),PersonalleftActivity.class);
			startActivity(intent1);
			getActivity().finish();

			/*	// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} 
			System.gc();*/

			break;
		case 3:

			if (null == (user.getUserTel()) || "".equals(user.getUserTel())) {
				Utils.showToast(getActivity(), "请先绑定手机号码");
				return;
			}



			Intent intent3 = new Intent(getActivity(),MerchandiseleftActivity.class);
			startActivity(intent3);
			getActivity().finish();

			/*	// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} 
			System.gc();*/
			break;
		case 4:

			if (null == (user.getStuNo()) || "".equals(user.getStuNo())) {
				Utils.showToast(getActivity(), "无法获取信息");
				return;
			}

			Intent intent4 = new Intent(getActivity(),SettingleftActivity.class);
			startActivity(intent4);
			getActivity().finish();

			/*	// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} 
			System.gc();*/
			break;
		case 5:
			Intent intent5 = new Intent(getActivity(),AboutleftActivity.class);
			startActivity(intent5);
			getActivity().finish();

			/*// 先判断是否已经回收
			if(btPersonalHead != null && !btPersonalHead.isRecycled()){ 
				// 回收并且置为null
				btPersonalHead.recycle(); 
				btPersonalHead = null; 
			} 
			System.gc();*/
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_left_head:
			setLeftSelect(1);
			break;
		case R.id.main_left_personal:
			setLeftSelect(2);
			break;
		case R.id.main_left_merchandise:
			setLeftSelect(3);
			break;
		case R.id.main_left_setting:
			setLeftSelect(4);
			break;
		case R.id.main_left_about:

			setLeftSelect(5);

			break;

		default:
			break;
		}

	}

}
