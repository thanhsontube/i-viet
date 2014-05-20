package com.android.iviet.base;

import java.net.URI;
import java.net.URISyntaxException;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.iviet.R;
import com.android.iviet.utils.FilterLog;

public class WebViewFragment extends Fragment implements OnBackPressListener{

	private static final String TAG = "WebViewFragment";
	FilterLog log = new FilterLog(TAG);
	ViewGroup empty;
	WebView webview;
	URI mUri = null;
	
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
}
