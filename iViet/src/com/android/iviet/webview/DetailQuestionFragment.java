package com.android.iviet.webview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.base.BaseWebViewFragment;
import com.android.iviet.dialog.ReportDialog;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class DetailQuestionFragment extends BaseWebViewFragment {
	static final String TAG = "DetailQuestionFragment";
	FilterLog log = new FilterLog(TAG);
	private boolean isShowSendMenu;
	private IDetailQuestionFragmentListener listener;
	
	public int iShowTop = View.VISIBLE;
	public int iAddImage = View.GONE;
	
	public boolean isShowSendMenu() {
		return isShowSendMenu;
	}

	public void setShowSendMenu(boolean isShowSendMenu) {
		this.isShowSendMenu = isShowSendMenu;
	}

	public interface IDetailQuestionFragmentListener {
		public void onDetailQuestionFragmentAnswer();
		public void onDetailQuestionFragmentBack();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof IDetailQuestionFragmentListener) {
			listener = (IDetailQuestionFragmentListener) activity;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
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
			if (listener != null) {
				listener.onDetailQuestionFragmentAnswer();
			}
//			mAddImage.setVisibility(View.VISIBLE);
//			mTop.setVisibility(View.GONE);
//			ActionBarUtils.setTitle(actionBar, "Trả lời câu hỏi");
//			isShowSendMenu = true;
//			
//			getActivity().invalidateOptionsMenu();
//			
//			getActivity().supportInvalidateOptionsMenu();
		}

		@JavascriptInterface
		public void onComment() {
			Toast.makeText(getActivity(), "onComment", Toast.LENGTH_SHORT).show();
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

	@Override
    protected String generateTitle() {
	    return "Chi tiết câu hỏi";
    }

	@Override
    protected boolean isShowSendMenuItem() {
	    return isShowSendMenu;
    }

	@Override
    protected int isShowFastTop() {
	    return iShowTop;
    }

	@Override
    protected int isShowAddImage() {
	    return iAddImage;
    }
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    // TODO Auto-generated method stub
	    super.onPrepareOptionsMenu(menu);
	    if (isShowAddImage() == View.VISIBLE) {
			ActionBarUtils.setTitle(actionBar, "Trả lời câu hỏi");
//			actionBar.setIcon(R.drawable.cancel);
//			actionBar.setDisplayHomeAsUpEnabled(false);
		} else {
			ActionBarUtils.setTitle(actionBar, "Chi tiết câu hỏi");
//			actionBar.setIcon(R.drawable.shape_icon);
//			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onBackPress() {
		listener.onDetailQuestionFragmentBack();
	    return super.onBackPress();
	}
	
	
}
