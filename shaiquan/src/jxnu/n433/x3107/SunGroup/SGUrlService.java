package jxnu.n433.x3107.SunGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.StrictMode;
import jxnu.x3107.Fragment.View.Logger;

@SuppressWarnings("deprecation")
public class SGUrlService {
	public User user;
	public String result = "";//服务器返回的值
	public Context mContext;

	public SGUrlService(Context mContext) {
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

	//收藏
	public boolean addGoodsType(String userID,String releaserID,String releaserTime){
		boolean bl = false;

		String strUrl = UrlService.URL + "addGoodsType.action?" + "userID=" + userID + "&releaserID=" + releaserID + "&releaserTime=" + releaserTime;
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strUrl);
		try {
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "");

			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String strJson = EntityUtils.toString(entity,"utf-8");
					if (strJson != null) {
						JSONObject jsonObject = new JSONObject(strJson);
						result = jsonObject.getString("addGoodsType").toString();
						if(result.equals("Y")){
							bl = true;
						}
						if(result.equals("N")){
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

	//得到用户收藏
	public List<Goods> getGoodsTypeInfo(String userID){
		List<Goods> gList = null;

		String strSql = UrlService.URL + "getGoodsTypeInfo.action?" + "userID=" + userID;
		System.out.println(strSql);

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strSql);

		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "getGoodsTypeInfo");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray;
						try {
							jsonArray = jsonObject.getJSONArray("getGoodsTypeInfo");
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
							Logger.e("getGoodsTypeInfo" + gList.size());
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

	//删除收藏   delete from goodsTypeTable where releaserID='123' and releaseTime='160605172911'
	public boolean delGoodsType(String releaserID,String releaserTime){
		boolean bl = false;

		String strUrl = UrlService.URL + "delGoodsType.action?" + "releaserID=" + releaserID + "&releaserTime=" + releaserTime;
		Logger.e(strUrl);
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strUrl);
		try {
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "");

			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String strJson = EntityUtils.toString(entity,"utf-8");
					if (strJson != null) {
						JSONObject jsonObject = new JSONObject(strJson);
						result = jsonObject.getString("delGoodsType").toString();
						if(result.equals("Y")){
							bl = true;
						}
						if(result.equals("N")){
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

	//添加关注
	public boolean addFollow(String userID,String releaserID){

		boolean bl = false;

		String strUrl = UrlService.URL + "addFollow.action?" + "userID=" + userID + "&releaserID=" + releaserID;
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strUrl);
		try {
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String strJson = EntityUtils.toString(entity,"utf-8");
					if (strJson != null) {
						JSONObject jsonObject = new JSONObject(strJson);
						result = jsonObject.getString("addFollow").toString();
						if(result.equals("Y")){
							bl = true;
						}
						if(result.equals("N")){
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

	//取消关注
	public boolean delFollow(String releaserID){

		boolean bl = false;

		String strUrl = UrlService.URL + "delFollow.action?" + "releaserID=" + releaserID;
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strUrl);
		try {
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String strJson = EntityUtils.toString(entity,"utf-8");
					if (strJson != null) {
						JSONObject jsonObject = new JSONObject(strJson);
						result = jsonObject.getString("delFollow").toString();
						if(result.equals("Y")){
							bl = true;
						}
						if(result.equals("N")){
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

	//判断是否已经关注
	public List<Follow> getFollow(String userID){
		List<Follow> fList = null;

		String strSql = UrlService.URL + "getFollow.action?" + "userID=" + userID;
		System.out.println(strSql);

		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strSql);

		try {

			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "getFollow");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					String strJson = EntityUtils.toString(entity,"utf-8");
					if(strJson != null){
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray;
						try {
							jsonArray = jsonObject.getJSONArray("getFollow");
							fList = new ArrayList<Follow>();
							for(int i = 0; i < jsonArray.length(); i++){
								JSONObject jObject = (JSONObject) jsonArray.get(i);
								Follow follow = new Follow();
								follow.setUserID(jObject.getString("userID"));
								follow.setReleaserID(jObject.getString("releaserID"));
								fList.add(follow);


							}
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

		return fList;
	}

	//获得关注人的商品
	public List<Goods> getFollowGoods(String userID){

		List<Goods> gList = null;

		String strUrl = UrlService.URL + "getFollowGoods.action?" +"userID=" + userID;
		HttpClient hClient = new DefaultHttpClient();

		hClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
		hClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		
		HttpPost post = new HttpPost(strUrl);
		try {
			HttpResponse response = hClient.execute(post);
			Logger.e(response.getStatusLine().getStatusCode() + "");
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String strJson = EntityUtils.toString(entity,"utf-8");
					if (strJson != null) {
						JSONObject jsonObject = new JSONObject(strJson);
						JSONArray jsonArray = jsonObject.getJSONArray("getFollowGoods");
						gList = new ArrayList<Goods>();
						for (int i = 0; i < jsonArray.length(); i++) {
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
						Logger.e("getGoodsTypeInfo" + gList.size());
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

		return gList;
	}


}
