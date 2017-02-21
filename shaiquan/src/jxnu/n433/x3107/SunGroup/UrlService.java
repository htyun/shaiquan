package jxnu.n433.x3107.SunGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import jxnu.x3107.Fragment.View.Logger;
import jxnu.x3107.utils.Utils;

@SuppressWarnings("deprecation")
public class UrlService {
	public static final String URL = "http://115.159.27.243:8056/ShaiquanSer/";
	//	public static final String URL = "http://10.100.5.149/ShaiquanSer/";
	public static final String USERIMGURL = "http://115.159.27.243:8056/images/";
	public User user;
	private String result = "";//服务器返回的值
	private Context mContext;

	public UrlService(Context mContext) {
		this.mContext = mContext;

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork()
				.penaltyLog()
				.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects()
				.penaltyLog()
				.penaltyDeath()
				.build());

	}

	//登录
	public  String logToService(String etUserName, String etUserPw) {

		String bl = "";

		String strSql = UrlService.URL + "userLog.action?" + "userName=" + etUserName + "&userPw=" + etUserPw;
		Logger.e(strSql);

		try {
			HttpClient hClient = new DefaultHttpClient();


			HttpParams params = hClient.getParams();
			if(params==null){
				params = new BasicHttpParams();
			}
			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strSql);
			HttpResponse response = hClient.execute(post);

			Logger.e(response.getStatusLine().getStatusCode() + "");

			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				String strJson = EntityUtils.toString(entity,"utf-8");
				if(strJson != null){
					try {
						JSONObject jsonObject = new JSONObject(strJson);
						result = jsonObject.getString("logResult").toString();

						if(result.equals("userNameY")){
							Logger.e("登录成功");
							bl = "userNameY";
						}
						if(result.equals("stuNoY")){
							Logger.e("登录成功");
							bl = "stuNoY";
						}
						if(result.equals("N")){
							Utils.showToast(mContext, "用户名或密码错误");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

			}

		} catch (ClientProtocolException  e) {
			e.printStackTrace();

		}catch (IOException e) {
			e.printStackTrace();
		}

		return bl;
	}


	//注册
	public boolean regToServer(String userName, String userPw, String schoolNumber, String schoolPw, String schoolName) {


		boolean bl = false;
		String strUrl = "";
		strUrl = UrlService.URL + "userReg.action?" + "userName=" + userName + "&userPw=" + userPw
				+ "&stuNo=" + schoolNumber + "&schoolPw=" + schoolPw
				+ "&schoolName=" + schoolName;
		Logger.e(strUrl+"");

		try {
			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode()+"");
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String strJson = EntityUtils.toString(entity,"utf-8");
				if (strJson != null) {
					try {
						JSONObject jsonObject = new JSONObject(strJson);
						result = jsonObject.getString("regResult").toString();
						if(result.equals("Y")){
							bl = true;
						}
						if(result.equals("CF")){
							Utils.showToast(mContext, "该用户名: \""+userName+"\" 已经注册");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}else {
				result = "N";//无网络
				Logger.e(result);
			}
		} catch (ClientProtocolException  e) {
			e.printStackTrace();

		}catch (IOException e) {
			e.printStackTrace();
		}

		return bl;
	}



	//获取服务器user信息
	public User getUserInfo(String userName){
		String strUrl = UrlService.URL + "userInfo?" + "userName=" + userName;
		user = new User();
		try{
			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);
			HttpResponse response = hClient.execute(post);

			Logger.e(response.getStatusLine().getStatusCode()+"");

			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray = jsonObject.getJSONArray("info");
						System.out.println(jsonArray.toString());

						JSONObject jsonObj = (JSONObject)jsonArray.get(0);

						System.out.println(jsonObj.getString("userName"));



						user.setUserLogo(jsonObj.getString("userLogo"));
						user.setUserName(jsonObj.getString("userName"));
						user.setUserSex(jsonObj.getString("userSex"));
						user.setUserCity(jsonObj.getString("userCity"));
						user.setIntroduction(jsonObj.getString("introduction"));
						user.setStuName(jsonObj.getString("stuName"));
						user.setBirth(jsonObj.getString("birth"));
						user.setQq(jsonObj.getString("qq"));
						user.setUserEmail(jsonObj.getString("userEmail"));
						user.setUserTel(jsonObj.getString("userTel"));
						user.setStuNo(jsonObj.getString("stuNo"));

						Logger.e("stuNo:" + user.getStuNo());
						Logger.e("stuNo:" + user.getUserLogo());

					}
				}
			}

		}catch (ClientProtocolException  e) {
			e.printStackTrace();

		}catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		//		System.out.println("userName" + user.getUserName());
		return user;

	}
	//获取服务器user信息
	public User getUserInfoOther(String stuNo){
		String strUrl = UrlService.URL + "userInfoOther.action?" + "stuNo=" + stuNo;
		user = new User();
		try{
			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);
			HttpResponse response = hClient.execute(post);

			Logger.e(response.getStatusLine().getStatusCode()+"");

			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray = jsonObject.getJSONArray("infoOther");
						System.out.println(jsonArray.toString());

						JSONObject jsonObj = (JSONObject)jsonArray.get(0);

						System.out.println(jsonObj.getString("userName"));



						user.setUserLogo(jsonObj.getString("userLogo"));
						user.setUserName(jsonObj.getString("userName"));
						user.setUserSex(jsonObj.getString("userSex"));
						user.setUserCity(jsonObj.getString("userCity"));
						user.setIntroduction(jsonObj.getString("introduction"));
						user.setStuName(jsonObj.getString("stuName"));
						user.setBirth(jsonObj.getString("birth"));
						user.setQq(jsonObj.getString("qq"));
						user.setUserEmail(jsonObj.getString("userEmail"));
						user.setUserTel(jsonObj.getString("userTel"));
						user.setStuNo(jsonObj.getString("stuNo"));

						Logger.e("stuNo:" + user.getStuNo());
						Logger.e("stuNo:" + user.getUserLogo());

					}
				}
			}

		}catch (ClientProtocolException  e) {
			e.printStackTrace();

		}catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		//		System.out.println("userName" + user.getUserName());
		return user;

	}



	//修改user信息
	public boolean alterUserInfo(String userName, int n ,String userInfo){
		String strUrl = UrlService.URL + "alUserInfo.action?" + "userInfo=" + userInfo + "&n=" + n + "&stuNo=" + userName;

		boolean bl = false;

		try {

			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode()+"");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						String result = jsonObject.getString("alResult").toString();
						if(result.equals("Y")){
							Utils.showToast(mContext, "修改成功");
							bl = true;
						}
						if(result.equals("N")){
							Utils.showToast(mContext, "修改失败");
							bl = false;
						}
					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bl;
	}

	//修改密码
	public boolean alPassword(int unTel,String stuNo,String userPw,String schoolPw){
		boolean bl = false;

		String strUrl = UrlService.URL + "alPassword.action?" + "unTel=" + unTel + "&stuNo=" + stuNo + "&userPw=" + userPw + "&schoolPw=" + schoolPw;
		try {

			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode()+"");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						String result = jsonObject.getString("actalResult").toString();
						if(result.equals("Y")){
							Utils.showToast(mContext, "修改成功");
							bl = true;
						}
						if(result.equals("N")){
							Utils.showToast(mContext, "修改失败");
						}
					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return bl;
	}

	//上传头像
	public boolean uploadImg(String userName,Bitmap bitmap){
		boolean bl = false;
		String strUrl = UrlService.URL + "uploadUserLogo.action?" + "stuNo=" + userName ;

		try {

			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);


			ByteArrayOutputStream baOS = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baOS);
			InputStream is = new ByteArrayInputStream(baOS.toByteArray());
			InputStreamEntity entity = new InputStreamEntity(is, is.available());
			post.setEntity(entity);

			baOS.flush();
			baOS.close();
			is.close();

			HttpResponse response = hClient.execute(post);

			if(response.getStatusLine().getStatusCode() == 200){
				bl = true;

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bl;
	}

	/*
	//下载头像
	public Bitmap dowImg(String userName){
		Bitmap bitmap = null;
		String strUrl = "";

		user = new User();
		user =getUserInfo(userName);
		strUrl = "http://localhost/images/" + user.getUserLogo();


		//httpGet连接对象  
		HttpGet httpRequest = new HttpGet(strUrl);  
		//取得HttpClient 对象  
		HttpClient httpclient = new DefaultHttpClient();  
		try {  
			//请求httpClient ，取得HttpRestponse  
			HttpResponse httpResponse = httpclient.execute(httpRequest);  
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
				//取得相关信息 取得HttpEntiy  
				HttpEntity httpEntity = httpResponse.getEntity();  
				//获得一个输入流  
				InputStream is = httpEntity.getContent();  
				bitmap = BitmapFactory.decodeStream(is);  
				is.close();  
			}  

		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  

		return bitmap;
	}
	 */

	//商品信息获取
	public List<Goods> getGoodsInfo(){
		Goods goods ;
		List<Goods> gList = null ;
		String strUrl = UrlService.URL + "allGoodsInfo.action";

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);
		try {


			HttpResponse response = hClient.execute(post);

			Logger.e(response.getStatusLine().getStatusCode() + "getGoodsInfo");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8"); 
					if(strJson != null){

						try {
							JSONObject jsonObject = new JSONObject(strJson);
							JSONArray jsonArray = jsonObject.getJSONArray("allGoodsInfo");
							gList = new ArrayList<Goods>();
							for(int i = 0; i < jsonArray.length(); i++){


								JSONObject jObject = (JSONObject) jsonArray.get(i);


								goods = new Goods();
								goods.setGoodID(jObject.getInt("goodID"));
								goods.setGoodName(jObject.getString("goodName"));
								goods.setGoodLogo(jObject.getString("goodLogo"));
								goods.setGoodIntroduction(jObject.getString("goodIntroduction"));
								goods.setReleaseTime(jObject.getString("releaseTime"));
								goods.setFromWhere(jObject.getString("fromWhere"));
								goods.setGoodState(jObject.getInt("goodState"));
								goods.setPrice(jObject.getDouble("price"));
								goods.setFromSchoolID(jObject.getString("fromSchoolID"));
								goods.setReleaserID(jObject.getString("releaserID"));
								goods.setGoodClassify(jObject.getString("goodClassify"));
								goods.setTime(jObject.getString("time"));

								gList.add(goods);

							}
							Logger.e("gList"+gList.size());
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return gList;
	}

	//获取具体商品信息
	public Goods getOlGoodsInfo(String goodName,String releaserID ,String releaseTime){

		Goods goods = null;
		String strUrl = UrlService.URL + "goodsOlInfo.action?" + "goodName=" + goodName + "&releaserID=" + releaserID + "&releaseTime=" + releaseTime;
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);
		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "goodsOlInfo");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8"); 
					if(strJson != null){

						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray = jsonObject.getJSONArray("goodsOlInfo");
						JSONObject jObject = (JSONObject) jsonArray.get(0);

						goods = new Goods();
						goods.setGoodName(jObject.getString("goodName"));
						goods.setGoodLogo(jObject.getString("goodLogo"));
						goods.setGoodIntroduction(jObject.getString("goodIntroduction"));
						goods.setReleaseTime(jObject.getString("releaseTime"));
						goods.setFromWhere(jObject.getString("fromWhere"));
						goods.setGoodState(jObject.getInt("goodState"));
						goods.setPrice(jObject.getDouble("price"));
						goods.setReleaserID(jObject.getString("releaserID"));
						goods.setGoodClassify(jObject.getString("goodClassify"));
						goods.setTime(jObject.getString("time"));

						Logger.e("goodName" + goods.getGoodName());

					}

				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return goods;

	}
	//获取个人商品信息
	public List<Goods> getIDGoodsInfo(String releaserID){
		List<Goods> gList = null;

		String strUrl = UrlService.URL + "goodsIDInfo.action?" + "releaserID=" + releaserID;

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);

		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "getIDGoodsInfo");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray;
						try {
							jsonArray = jsonObject.getJSONArray("goodsIDInfo");
							gList = new ArrayList<Goods>();

							for(int i = 0; i < jsonArray.length(); i++){
								JSONObject jObject = (JSONObject) jsonArray.get(i);
								Goods goods = new Goods();

								goods.setGoodID(jObject.getInt("goodID"));
								goods.setGoodName(jObject.getString("goodName"));
								goods.setGoodLogo(jObject.getString("goodLogo"));
								goods.setGoodIntroduction(jObject.getString("goodIntroduction"));
								goods.setReleaseTime(jObject.getString("releaseTime"));
								goods.setFromWhere(jObject.getString("fromWhere"));
								goods.setGoodState(jObject.getInt("goodState"));
								goods.setPrice(jObject.getDouble("price"));
								goods.setFromSchoolID(jObject.getString("fromSchoolID"));
								goods.setReleaserID(jObject.getString("releaserID"));
								goods.setGoodClassify(jObject.getString("goodClassify"));
								goods.setTime(jObject.getString("time"));
								gList.add(goods);


							}
							Logger.e("goodsIDInfo" + gList.size());
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		return gList;
	}
	//获取分类商品信息
	public List<Goods> getClassGoodsInfo(String goodClassify){
		List<Goods> gList = null;

		String strUrl = UrlService.URL + "getClassGoodsInfo.action?" + "goodClassify=" + goodClassify;

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);

		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "getClassGoodsInfo");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray;
						try {
							jsonArray = jsonObject.getJSONArray("getClassGoodsInfo");
							gList = new ArrayList<Goods>();

							for(int i = 0; i < jsonArray.length(); i++){
								JSONObject jObject = (JSONObject) jsonArray.get(i);
								Goods goods = new Goods();

								goods.setGoodID(jObject.getInt("goodID"));
								goods.setGoodName(jObject.getString("goodName"));
								goods.setGoodLogo(jObject.getString("goodLogo"));
								goods.setGoodIntroduction(jObject.getString("goodIntroduction"));
								goods.setReleaseTime(jObject.getString("releaseTime"));
								goods.setFromWhere(jObject.getString("fromWhere"));
								goods.setGoodState(jObject.getInt("goodState"));
								goods.setPrice(jObject.getDouble("price"));
								goods.setFromSchoolID(jObject.getString("fromSchoolID"));
								goods.setReleaserID(jObject.getString("releaserID"));
								goods.setGoodClassify(jObject.getString("goodClassify"));
								goods.setTime(jObject.getString("time"));
								gList.add(goods);


							}
							Logger.e("goodsIDInfo" + gList.size());
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		return gList;
	}

	//获得搜索商品
	//	"select * from goodsTable where goodName like '%"+goods.getGoodName()+"%'"
	public List<Goods> getSeekGoodsInfo(String goodName){ 
		List<Goods > gList = null;

		String strSql = UrlService.URL + "getSeekGoodsInfo.action?" + "goodName=" + goodName;

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strSql);

		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "getSeekGoodsInfo");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray;
						try {
							jsonArray = jsonObject.getJSONArray("getSeekGoodsInfo");
							gList = new ArrayList<Goods>();

							for(int i = 0; i < jsonArray.length(); i++){
								JSONObject jObject = (JSONObject) jsonArray.get(i);
								Goods goods = new Goods();

								goods.setGoodID(jObject.getInt("goodID"));
								goods.setGoodName(jObject.getString("goodName"));
								goods.setGoodLogo(jObject.getString("goodLogo"));
								goods.setGoodIntroduction(jObject.getString("goodIntroduction"));
								goods.setReleaseTime(jObject.getString("releaseTime"));
								goods.setFromWhere(jObject.getString("fromWhere"));
								goods.setGoodState(jObject.getInt("goodState"));
								goods.setPrice(jObject.getDouble("price"));
								goods.setFromSchoolID(jObject.getString("fromSchoolID"));
								goods.setReleaserID(jObject.getString("releaserID"));
								goods.setGoodClassify(jObject.getString("goodClassify"));
								goods.setTime(jObject.getString("time"));
								gList.add(goods);


							}
							Logger.e("goodsIDInfo" + gList.size());
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		return gList;
	}

	//上传商品
	public boolean uploadGoodsInfo(Goods goods){
		boolean bl = false;

		String strUrl = UrlService.URL + "uploadGoodsInfo.action?"
				+ "goodName=" + goods.getGoodName().trim()
				+ "&goodIntroduction=" + goods.getGoodIntroduction().trim()
				+ "&releaseTime=" + goods.getReleaseTime().trim()
				+ "&fromWhere=" + goods.getFromWhere().trim()
				+ "&goodState=" + goods.getGoodState()
				+ "&price=" + goods.getPrice()
				+ "&releaserID=" + goods.getReleaserID().trim()
				+ "&goodClassify=" + goods.getGoodClassify()
				+ "&time=" + goods.getTime();
		Logger.e(strUrl);
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);
		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode()+"");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						String result = jsonObject.getString("uploadGoodsInfoResult").toString();
						if(result.equals("Y")){
							Utils.showToast(mContext, "上传成功");
							bl = true;
						}
						if(result.equals("N")){
							Utils.showToast(mContext, "上传失败");
						}
					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return bl;
	}

	//上传商品图片
	public boolean upGoodsLogo(Goods goods,Bitmap bitmap){
		boolean bl = false;
		String strUrl = UrlService.URL + "upGoodsLogo.action?"
				+ "releaseTime=" + goods.getReleaseTime().trim() 
				+ "&releaserID=" + goods.getReleaserID().trim();

		try {

			HttpClient hClient = new DefaultHttpClient();

			hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
			hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

			HttpPost post = new HttpPost(strUrl);


			ByteArrayOutputStream baOS = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baOS);
			InputStream is = new ByteArrayInputStream(baOS.toByteArray());
			InputStreamEntity entity = new InputStreamEntity(is, is.available());
			post.setEntity(entity);

			baOS.flush();
			baOS.close();
			is.close();

			HttpResponse response = hClient.execute(post);



			if(response.getStatusLine().getStatusCode() == 200){
				bl = true;

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bl;
	}

	//修改商品信息
	public boolean altIDGoodsInfo(Goods goods,String releaserID,String releaseTime){
		boolean bl = false;

		String strUrl = UrlService.URL + "altIDGoodsInfo.action?" 
				+ "releaserID=" + releaserID 
				+ "&releaseTime=" + releaseTime
				+ "&goodName=" + goods.getGoodName().trim()
				+ "&goodIntroduction=" + goods.getGoodIntroduction().trim()
				+ "&price=" + goods.getPrice()
				+ "&goodClassify=" + goods.getGoodClassify();
		Logger.e(strUrl);

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);
		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode()+"");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						String result = jsonObject.getString("altIDGoodsInfoResult").toString();
						if(result.equals("Y")){
							Utils.showToast(mContext, "修改成功");
							bl = true;
						}
						if(result.equals("N")){
							Utils.showToast(mContext, "修改失败");
						}
					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		return bl;
	}



	//删除商品
	public boolean delIDGoodsInfo(String releaserID,String releaseTime){

		boolean bl = false;
		String strUrl = UrlService.URL + "delIDGoodsInfo.action?" 
				+ "releaserID=" + releaserID 
				+ "&releaseTime=" + releaseTime;


		Logger.e(strUrl);

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);

		HttpPost post = new HttpPost(strUrl);
		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode()+"");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						String result = jsonObject.getString("delIDGoodsInfoResult").toString();
						if(result.equals("Y")){
							Utils.showToast(mContext, "删除成功");
							bl = true;
						}
						if(result.equals("N")){
							Utils.showToast(mContext, "删除失败");
						}
					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		return bl;

	}

}
