package com.android.iviet.base;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.dialog.AddPictureDialog;
import com.android.iviet.dialog.ReportDialog;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class WebViewFragment extends Fragment implements OnBackPressListener, OnClickListener{

	private static final String TAG = "WebViewFragment";
	FilterLog log = new FilterLog(TAG);
	ViewGroup empty;
	WebView webview;
	private ImageView mTop;
	private ImageView mAddImage;
	URI mUri = null;
	private MenuItem menuSend;
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
		mTop = (ImageView) view.findViewById(R.id.webview_img_go_top);
		mTop.setOnClickListener(this);
		
		mAddImage = (ImageView) view.findViewById(R.id.webview_img_add_picture);
		mAddImage.setOnClickListener(this);
//		mAddImage.setVisibility(View.INVISIBLE);
		empty = (ViewGroup) view.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, empty, true);
		empty.findViewById(R.id.waiting_txt).setVisibility(View.GONE);
		webview = (WebView) view.findViewById(R.id.webview);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setSupportZoom(false);
		webview.setVerticalScrollbarOverlay(true);
		webview.setHorizontalScrollBarEnabled(false);
		webview.addJavascriptInterface(new AndroidBridge(), "Android");
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
			}
		}

		@Override
		public boolean dispatchBackPress() {
			if (webview.canGoBack()) {
				webview.goBack();
//				mAddImage.setVisibility(View.INVISIBLE);
//				mTop.setVisibility(View.VISIBLE);
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
		}
		getActivity().invalidateOptionsMenu();
		 return true;
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
			getActivity().invalidateOptionsMenu();
//			mAddImage.setVisibility(View.VISIBLE);
//			mTop.setVisibility(View.INVISIBLE);
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


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.webview_img_go_top:
			webview.scrollTo(0, 0);
			break;

		case R.id.webview_img_add_picture:

			DialogFragment f = new AddPictureDialog();
			getActivity().getSupportFragmentManager().beginTransaction().add(f, f.getClass().getName())
					.commitAllowingStateLoss();

			break;

		default:
			break;
		}

	}
}
