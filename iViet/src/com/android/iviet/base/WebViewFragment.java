package com.android.iviet.base;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class WebViewFragment extends Fragment implements OnBackPressListener{

	private static final String TAG = "WebViewFragment";
	FilterLog log = new FilterLog(TAG);
	ViewGroup empty;
	WebView webview;
	URI mUri = null;
	private MenuItem menuSend;
	private MenuItem menuAddPicture;
	ActionBar actionBar;
	
	public static WebViewFragment newInstance (String url) {
		WebViewFragment f = new WebViewFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		f.setArguments(bundle);
		return f;
	}

	public interface Controller {
		void load(URI uri);

		boolean dispatchBackPress();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			String sUrl = bundle.getString("url");
			try {
				mUri = new URI(sUrl);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		actionBar = getActivity().getActionBar();
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.webview_fragment, container, false);
		empty = (ViewGroup) view.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, empty, true);
		webview = (WebView) view.findViewById(R.id.webview);
		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(false);
		webview.setVerticalScrollbarOverlay(true);
		webview.setHorizontalScrollBarEnabled(false);
		webview.addJavascriptInterface(new AndroidBridge(), "Android");
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
				return false;
			}
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
//				empty.setVisibility(View.VISIBLE);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				empty.setVisibility(View.GONE);
			}
		});
		if (mUri != null) {
			mController.load(mUri);
		}
		return view;
	}
	
	private final Controller mController = new Controller() {
		@Override
		public void load(URI uri) {
			mUri = uri;
			if (webview != null) {
				webview.loadUrl(uri.toASCIIString());
			}
		}

		@Override
		public boolean dispatchBackPress() {
			if (webview.canGoBack()) {
				webview.goBack();
				return true;
			}
			return false;
		}
	};

	@Override
	public boolean onBackPress() {
		return mController.dispatchBackPress();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		log.d("log>>>" + "onCreateOptionsMenu");
		menuSend = menu.add(Menu.NONE, 1, Menu.NONE, "Send"); 
		menuSend.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuSend.setIcon(R.drawable.answer);
		
		menuAddPicture = menu.add(Menu.NONE, 2, Menu.NONE, "Chèn Ảnh");
		menuAddPicture.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuAddPicture.setIcon(R.drawable.add_picture);
		
		ActionBarUtils.setTitle(actionBar, "Trả lời câu hỏi");
		ActionBarUtils.hideChat(actionBar, true);
		ActionBarUtils.hideDot(actionBar, true);
		
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    log.d("log>>>" + "onPrepareOptionsMenu");
//	    menuSend.setVisible(false);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		log.d("log>>>" + "onOptionsItemSelected");
		switch (item.getItemId()) {
		case 1:
			Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();;
			break;
		case 2:
			

		default:
			break;
		}
//		getActivity().invalidateOptionsMenu();
		return true;
//	    return super.onOptionsItemSelected(item);
	}
	
	class AndroidBridge {
		public AndroidBridge() {
			
		}
		
		public void onDetail() {
//			menuSend.setVisible(true);
		}
		
		public void onAnswer() {
			log.d("log>>>" + "onAnswer");
			menuSend.setVisible(true);
			menuAddPicture.setVisible(true);
		}
	}
}
