package jxnu.x3107.SunGroup.OtherActivity;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import jxnu.n433.x3107.SunGroup.R;
import jxnu.n433.x3107.SunGroup.UrlService;
import jxnu.n433.x3107.SunGroup.User;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.SunGroup.WelComeActivity;
import jxnu.x3107.utils.Utils;

import java.io.File;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;

public class PictureHeadActivity extends Activity implements OnClickListener{

	private ImageView ivHead;//头像显示
	private Button btnTakephoto;//拍照
	private Button btnPhotos;//相册
	private Button btnOff;//取消
	public  Bitmap head;//头像Bitmap
	public  static final String PATH="/sdcard/SunGroupHead/";//sd路径

	private Button btnconfirm;//确定
	private ImageView ivPictureBack;
	private Dialog dialog;

	private SharedPreferences sPreferences;
	public static String userName;
	private User user;
	private UrlService urlService;
	/*	private List<UserInfo> userInfoList;
	private UserInfoDataHelper userInfoDHelper;
	private GoodsDataHelper goodsDHelper;
	private List<MySelfListing> mslList;*/
//	private Bitmap bt;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_picture_head);

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME, 0);
		userName = sPreferences.getString("uN", "")+"";

		user = new User();
		urlService = new UrlService(getApplicationContext());
		user = urlService.getUserInfoOther(userName);

		initBack();//返回



		initView();//初始化控件

	}

	private void initBack() {

		ivPictureBack = (ImageView) findViewById(R.id.picture_head_iv_back);
		ivPictureBack.setOnClickListener(this);

	}



	private void initView() {
		//初始化控件

		btnconfirm = (Button) findViewById(R.id.picture_btn_confirm);

		ivHead = (ImageView) findViewById(R.id.picture_iv_head);



		btnconfirm.setOnClickListener(this);

		ivHead.setOnClickListener(this);



//		try {
//			bt = BitmapFactory.decodeFile(PATH + "head"+".png");
//			if(bt!=null){
//				@SuppressWarnings("deprecation")
//				Drawable drawable = new BitmapDrawable(bt);//转换成drawable
//				ivHead.setImageDrawable(drawable);
//			}else{
				//**
				//如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
				user = urlService.getUserInfoOther(sPreferences.getString("uN", ""));
				String imgUrl = UrlService.USERIMGURL + user.getUserLogo();
				System.out.println(imgUrl);

				Picasso.with(getApplicationContext())
				.load(imgUrl)
				.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//		         .networkPolicy(NetworkPolicy.NO_CACHE)
				.into(ivHead);

				/*Picasso.with(getApplicationContext())
				.load(imgUrl)
				.resize(500,500)
				.placeholder(R.drawable.beautiful)
				.into(ivHead);
				*/

				//*
//			}
		/*} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}*/
	}

	@SuppressWarnings("deprecation")
	private void showSelectDialog() {//dialog选择
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);

		btnPhotos = (Button) view.findViewById(R.id.picture_btn_photos);
		btnTakephoto = (Button) view.findViewById(R.id.picture_btn_takephoto);
		btnOff =(Button) view.findViewById(R.id.picture_btn_off);
		btnPhotos.setOnClickListener(this);
		btnTakephoto.setOnClickListener(this);
		btnOff.setOnClickListener(this);

		dialog = new Dialog(this,R.style.transparentFrameWindowStyle);
		dialog.setContentView(view,new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getWidth();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				cropPhoto(data.getData());//裁剪图片
				Logger.e( "stuNo:img:"+user.getStuNo());
			}
			break;
		case 2:
			if (resultCode == RESULT_OK) {
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/head_"+user.getStuNo()+".png");
				Logger.e( "stuNo:img:"+"/head_"+user.getStuNo()+".png");
				cropPhoto(Uri.fromFile(temp));//裁剪图片
			}
			break;
		case 3:
			if (data != null) {
				Bundle extras = data.getExtras();
				head = extras.getParcelable("data");
				if(head!=null){
					//
					//  上传服务器代码

					if(urlService.uploadImg(userName,head)){
						Utils.showToast(getApplicationContext(), "上传成功");
					}

					//
					//					setPicToView(head);//保存在SD卡中
//					ivHead.setImageBitmap(head);//用ImageView显示出来
					
					user = urlService.getUserInfoOther(sPreferences.getString("uN", ""));
					String imgUrl = UrlService.USERIMGURL + user.getUserLogo();
					System.out.println(imgUrl);

					Picasso.with(getApplicationContext())
					.load(imgUrl)
					.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
//			         .networkPolicy(NetworkPolicy.NO_CACHE)
					.into(ivHead);
					
					head.recycle();
					head = null;
					System.gc();
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	};

	/**
	 * 调用系统的裁剪
	 * @param uri
	 */
	public void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	/*	private void setPicToView(Bitmap mBitmap) {
		String sdStatus = Environment.getExternalStorageState();  
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
			return;  
		}  
		FileOutputStream b = null;
		File file = new File(PATH);
		file.mkdirs();// 创建文件夹
		String fileName =PATH + "head"+strSNumber+".png";//图片名字
		try {
			b = new FileOutputStream(fileName);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭流
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	 */
	//点击

	@Override
	public void onClick(View v) {

		sPreferences = getSharedPreferences(WelComeActivity.USERNAME, 0);

		switch (v.getId()) {
		case R.id.picture_iv_head:
			showSelectDialog();
			break;

		case R.id.picture_btn_photos://从相册里面取照片
			Intent intent1 = new Intent(Intent.ACTION_PICK, null);
			intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent1, 1);



			break;
		case R.id.picture_btn_takephoto://调用相机拍照
			Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
					"head_"+user.getStuNo()+".png")));
			Logger.e( "stuNo:img:"+"head_"+user.getStuNo()+".png");
			startActivityForResult(intent2, 2);//采用ForResult打开
			break;
		case R.id.picture_head_iv_back:
			Intent intent4 = new Intent(PictureHeadActivity.this,PersonalleftActivity.class);
			startActivity(intent4);
			finish();
			break;

		case R.id.picture_btn_confirm:

			/*	userInfoList = new ArrayList<UserInfo>();
			userInfoDHelper = new UserInfoDataHelper(getApplicationContext());
			userInfoList = userInfoDHelper.getUserInfoList();
			for(int i = 0; i < userInfoList.size(); i++){
				if (userInfoList.get(i).getStudentNumber().equals(sPreferences.getString("uN",""))) {
					userInfoDHelper.updateUserInfo(PATH + "head"+strSNumber+".png", strSNumber, 11);
				}
			}

			mslList = new ArrayList<MySelfListing>();
			goodsDHelper = new GoodsDataHelper(getApplicationContext());
			mslList = goodsDHelper.getGoodsInfoList(strSNumber,1);

			if (mslList.size() != 0) {
				for(int i = 0; i < mslList.size(); i++){
					if (mslList.get(i).getStudentNumber().equals(sPreferences.getString("uN",""))) {
						goodsDHelper.updateGoods(PATH + "head"+strSNumber+".png", null, strSNumber, 8);
					}
				}
			}*/




			Intent intent3 = new Intent(PictureHeadActivity.this,PersonalleftActivity.class);
			startActivity(intent3);
			finish();
			break;

		case R.id.picture_btn_off:

			dialog.dismiss();

			break;

		default:

			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent3 = new Intent(PictureHeadActivity.this,PersonalleftActivity.class);
			startActivity(intent3);
			finish();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}
