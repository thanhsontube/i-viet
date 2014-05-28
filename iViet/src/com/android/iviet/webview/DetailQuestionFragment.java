package com.android.iviet.webview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.android.iviet.base.BaseWebViewFragment;
import com.android.iviet.dialog.ReportDialog;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class DetailQuestionFragment extends BaseWebViewFragment {
	static final String TAG = "DetailQuestionFragment";
	FilterLog log = new FilterLog(TAG);
//	boolean isEnableSendMenu;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		ActionBarUtils.setTitle(actionBar, "Chi tiết câu hỏi");
		ActionBarUtils.hideChat(actionBar, true);
		ActionBarUtils.hideDot(actionBar, true);
		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new AndroidBridge(), "Android");
		isShowSendMenu = false;
		return view;
	}

	public class AndroidBridge {
		public AndroidBridge() {

		}

		@JavascriptInterface
		public void onDetail() {
			// menuSend.setVisible(true);
		}

		@JavascriptInterface
		public void onAnswer() {
			log.d("log>>>" + "onAnswer123");
//			getActivity().invalidateOptionsMenu();
			mAddImage.setVisibility(View.VISIBLE);
			mTop.setVisibility(View.GONE);
			ActionBarUtils.setTitle(actionBar, "Trả lời câu hỏi");
			isShowSendMenu = true;
			getActivity().invalidateOptionsMenu();
		}

		@JavascriptInterface
		public void onComment() {
			Toast.makeText(getActivity(), "onComment", Toast.LENGTH_SHORT).show();
			menuSend.setVisible(true);
			setHasOptionsMenu(true);
			webview.loadUrl("http://www.iviet.com/m/comments/q234?userToken=u10");

		}

		@JavascriptInterface
		public void onFollow() {
			Toast.makeText(getActivity(), "onFollow", Toast.LENGTH_SHORT).show();
		}

		@JavascriptInterface
		public void onError(String str) {
			DialogFragment f = new ReportDialog();
			getActivity().getSupportFragmentManager().beginTransaction().add(f, f.getClass().getName())
					.commitAllowingStateLoss();
		}

		@JavascriptInterface
		public void onShare(String str) {
			Toast.makeText(getActivity(), "onShare", Toast.LENGTH_SHORT).show();

		}
	}
	
	
}
