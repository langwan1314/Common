package com.blue.leaves.util.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.blue.leaves.util.Utils;

/**
 * 
 * Android网络判断工具
 * 
 * @author  Tencent QQLive
 * @since   2011-10
 * @version 1.0.1
 * @description
 *          Tencent QQLive`s Introduction, version information, copyright information
 *          
 */
public class AndroidNetworkUtils {

	/**
	 * 
	 * 判断网络是否有效
	 * 
	 * @param context
	 * 		    上下文对象
	 * 
	 * @return boolean
	 *         -- TRUE  有效
	 *         -- FALSE 无效
	 *         
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			if (manager == null) {
				return false;
			}
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo == null || !networkinfo.isAvailable()) {
				return false;
			}

			if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isNetworkAvailable() {
		boolean ret = true;
		Context context = Utils.getAppContext();
		if (context != null && !AndroidNetworkUtils.isNetworkAvailable(context)) {
			ret = false;
		}
		return ret;
	}
	
	/**
	 * wifi网络条件下
	 * @param context
	 * @return
	 */
	public static boolean isWifiNetwork(Context context) {
		final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = connMgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].isConnected() == false)
					continue;
				if (info[i].getTypeName().equals("WIFI") || info[i].getTypeName().equals("USBNET")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 非wifi网络条件下
	 * @param context
	 * @return
	 */
	public static boolean isNotWifiNetwork(Activity context) {
		final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = connMgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].isConnected() == false)
					continue;
				if (info[i].getTypeName().equals("WIFI") || info[i].getTypeName().equals("USBNET")) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 返回当前是否连接到移动网络
	 *
	 * @param context
	 * @return true如果当前正连接在移动网络；false如果无网络或者连接的不是移动网络
	 */
	public static boolean isMobileNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo != null && networkinfo.isConnected()) {
				switch (networkinfo.getType()) {
				case ConnectivityManager.TYPE_MOBILE:
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isNetworkTypeWIFI(int networkType) {
		switch (networkType) {
		case ConnectivityManager.TYPE_WIFI:
		case ConnectivityManager.TYPE_WIMAX:
			return true;
		default:
			return false;
		}
	}

	public static boolean isNetworkTypeMobile(int networkType) {
		switch (networkType) {
		case ConnectivityManager.TYPE_MOBILE:
		case ConnectivityManager.TYPE_MOBILE_MMS:
		case ConnectivityManager.TYPE_MOBILE_SUPL:
		case ConnectivityManager.TYPE_MOBILE_DUN:
		case ConnectivityManager.TYPE_MOBILE_HIPRI:
			return true;
		default:
			return false;
		}
	}
	

	/**
	 * 返回当前连接的是什么网络，2G,3G,4G,WIFI等
	 *
	 * @param context
	 * @return NETWORK_CLASS
	 */
	public static int getNetworkClass(Context context) {
		int ret=NETWORK_CLASS_UNKNOWN;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager != null) {
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo != null && networkinfo.isConnected()) {
				switch (networkinfo.getType()) {
					case ConnectivityManager.TYPE_MOBILE:
						int subType=networkinfo.getSubtype();
						ret=getNetworkClass(subType);
						break;
				}
			}
		}
		return ret;
	}
	
	private static int getNetworkClass(int networkType) {
		switch (networkType) {
			case NETWORK_TYPE_GPRS:
			case NETWORK_TYPE_EDGE:
			case NETWORK_TYPE_CDMA:
			case NETWORK_TYPE_1xRTT:
			case NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2_G;
			case NETWORK_TYPE_UMTS:
			case NETWORK_TYPE_EVDO_0:
			case NETWORK_TYPE_EVDO_A:
			case NETWORK_TYPE_HSDPA:
			case NETWORK_TYPE_HSUPA:
			case NETWORK_TYPE_HSPA:
			case NETWORK_TYPE_EVDO_B:
			case NETWORK_TYPE_EHRPD:
			case NETWORK_TYPE_HSPAP:
				return NETWORK_CLASS_3_G;
			case NETWORK_TYPE_LTE:
				return NETWORK_CLASS_4_G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}
	
	public static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. */
	public static final int NETWORK_CLASS_2_G = 2;
	/** Class of broadly defined "3G" networks.  */
	public static final int NETWORK_CLASS_3_G = 3;
	/** Class of broadly defined "4G" networks. */
	public static final int NETWORK_CLASS_4_G = 4;
	
	
	/** Current network is GPRS */
	private static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	private static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	private static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B*/
	private static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0*/
	private static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A*/
	private static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT*/
	private static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	private static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	private static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	private static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	private static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B*/
	private static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	private static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	private static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	private static final int NETWORK_TYPE_HSPAP = 15;
}
