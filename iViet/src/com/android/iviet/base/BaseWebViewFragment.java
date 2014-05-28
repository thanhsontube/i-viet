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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.iviet.MsConst;
import com.android.iviet.R;
import com.android.iviet.dialog.AddPictureDialog;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

abstract public class BaseWebViewFragment extends Fragment implements OnBackPressListener, OnClickListener{

	static final String TAG = "BaseWebViewFragment";
	FilterLog log = new FilterLog(TAG);
	ViewGroup empty;
	protected WebView webview;
	protected ImageView mTop;
	protected ImageView mAddImage;
	URI mUri = null;
	protected MenuItem menuSend;
	protected MenuItem menuTemp;
	protected ActionBar actionBar;
	protected boolean isShowSendMenu;
	
	public interface Controller {
		void load(URI uri);

		boolean dispatchBackPress();
	}
	
	abstract protected String generateTitle();
	abstract protected boolean isShowSendMenuItem();
	abstract protected int isShowFastTop();
	abstract protected int isShowAddImage();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActivity().getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		ActionBarUtils.setTitle(actionBar, generateTitle());
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.webview_fragment, container, false);
		log.d("log>>>" + "onCreateView");
		mTop = (ImageView) view.findViewById(R.id.webview_img_go_top);
		mTop.setOnClickListener(this);
		
		mAddImage = (ImageView) view.findViewById(R.id.webview_img_add_picture);
		mAddImage.setOnClickListener(this);
		
		mTop.setVisibility(isShowFastTop());
		mAddImage.setVisibility(isShowAddImage());
		empty = (ViewGroup) view.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, empty, true);
		empty.findViewById(R.id.waiting_txt).setVisibility(View.GONE);
		webview = (WebView) view.findViewById(R.id.webview);

		webview.getSettings().setSupportZoom(false);
		webview.setVerticalScrollbarOverlay(true);
		webview.setHorizontalScrollBarEnabled(false);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				empty.setVisibility(View.GONE);
			}
		});
		
		if (getArguments() != null) {
			String url = getArguments().getString(MsConst.EXTRA_URL);
			log.d("log>>>" + "url:" + url);
			try {
	            mUri = new URI(url);
            } catch (URISyntaxException e) {
	            e.printStackTrace();
            }
		}
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
			if (webview != null && mUri != null) {
				webview.loadUrl(uri.toASCIIString());
			} else {
				log.e("log>>>" + "URI NULL");
			}
		}

		@Override
		public boolean dispatchBackPress() {
			if (webview.canGoBack()) {
				webview.goBack();
				// mAddImage.setVisibility(View.INVISIBLE);
				// mTop.setVisibility(View.VISIBLE);
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
		
		menuTemp = menu.add(Menu.NONE, 2, Menu.NONE, "");
		menuTemp.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuTemp.setIcon(R.drawable.shape_icon);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		log.d("log>>>" + "onPrepareOptionsMenu");
		menuSend.setVisible(isShowSendMenuItem());
		menuTemp.setVisible(!isShowSendMenuItem());
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
			Toast.makeText(getActivity(), "test menu", Toast.LENGTH_SHORT).show();
			break;
		case android.R.id.home:
			mController.dispatchBackPress();
			break;

		default:
		}
		getActivity().invalidateOptionsMenu();
		 return true;
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
