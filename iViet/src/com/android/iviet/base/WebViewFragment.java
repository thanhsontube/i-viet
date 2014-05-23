package com.android.iviet.base;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class WebViewFragment extends Fragment implements OnBackPressListener {

	private static final String TAG = "WebViewFragment";
	FilterLog log = new FilterLog(TAG);
	ViewGroup empty;
	WebView webview;
	URI mUri = null;
	private MenuItem menuSend;
	private MenuItem menuAddPicture;
	ActionBar actionBar;
	boolean isShowSendMenu;

	public static WebViewFragment newInstance(String url) {
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
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		// actionBar.setd
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.webview_fragment, container, false);
		empty = (ViewGroup) view.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, empty, true);
		empty.findViewById(R.id.waiting_txt).setVisibility(View.GONE);
		webview = (WebView) view.findViewById(R.id.webview);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(false);
		webview.setVerticalScrollbarOverlay(true);
		webview.setHorizontalScrollBarEnabled(false);
		webview.addJavascriptInterface(new AndroidBridge(), "Android");
//		webview.getSettings().setDomStorageEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
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
		public void load(final URI uri) {
			log.d("log>>>" + "load:" + uri.toASCIIString());
			mUri = uri;
			if (webview != null) {
				webview.loadUrl(uri.toASCIIString());
//				Handler handler = new Handler();
//				handler.post(new Runnable() {
//					
//					@Override
//					public void run() {
//					}
//				});
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
		// menuAddPicture.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuAddPicture.setIcon(R.drawable.add_picture);

		ActionBarUtils.setTitle(actionBar, "Trả lời câu hỏi");
		ActionBarUtils.hideChat(actionBar, true);
		ActionBarUtils.hideDot(actionBar, true);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		log.d("log>>>" + "onPrepareOptionsMenu");
		menuSend.setVisible(isShowSendMenu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		log.d("log>>>" + "onOptionsItemSelected");
		switch (item.getItemId()) {
		case 1:
			Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
			;
			break;
		case 2:
			
		case android.R.id.home:
			mController.dispatchBackPress();
			break;

		default:
//			return mController.dispatchBackPress();
////			if (!mController.dispatchBackPress()) {
////				getActivity().getSupportFragmentManager().popBackStack();
////				ActionBarUtils.hideChat(actionBar, false);
////				ActionBarUtils.hideDot(actionBar, false);
////				return true;
////			} else {
////				return true;
////			}
////			return onBackPress();
//			// break;
		}
		getActivity().invalidateOptionsMenu();
		 return true;
//		return super.onOptionsItemSelected(item);
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
			log.d("log>>>" + "onAnswer");
			isShowSendMenu = true;
			// menuSend.setVisible(true);
			getActivity().invalidateOptionsMenu();
		}

		@JavascriptInterface
		public void onComment() {
			Toast.makeText(getActivity(), "onComment", Toast.LENGTH_SHORT).show();
//			try {
//	            mController.load(new URI("http://www.iviet.com/m/comments/q234?userToken=u10"));
//            } catch (URISyntaxException e) {
//	            log.d("log>>>" + "error loadL:" + e.toString());
//            }
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
			Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
		}

		@JavascriptInterface
		public void onShare(String str) {
			Toast.makeText(getActivity(), "onShare", Toast.LENGTH_SHORT).show();

		}
	}
}
