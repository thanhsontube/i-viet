package com.android.iviet.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.user.base.OverScrollView;
import com.android.iviet.utils.AlertDialogUtils;
import com.android.iviet.utils.AnimationUtils;
import com.android.iviet.utils.AnimationUtils.OnAnimationCallBack;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link ProfileFragment.OnProfileFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link ProfileFragment#newInstance}
 * factory method to create an instance of this fragment.
 * 
 */
public class ProfileFragment extends BaseFragment implements OnClickListener {
	private View mViewFooter;
	private OverScrollView mViewRoot;
	private OnProfileFragmentInteractionListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mViewRoot = (OverScrollView) inflater.inflate(R.layout.fragment_profile, container, false);
		((ImageView) mViewRoot.findViewWithTag("avatar")).setOnClickListener(this);
		((ImageButton) mViewRoot.findViewById(R.id.profile_btn_answer)).setOnClickListener(this);
		((ImageButton) mViewRoot.findViewById(R.id.profile_btn_favorite)).setOnClickListener(this);
		((ImageButton) mViewRoot.findViewById(R.id.profile_btn_question)).setOnClickListener(this);
		((ImageButton) mViewRoot.findViewById(R.id.profile_btn_timeline)).setOnClickListener(this);
		((Button) mViewRoot.findViewById(R.id.profile_btn_back_to_close)).setOnClickListener(this);
		((Button) mViewRoot.findViewById(R.id.profile_btn_edit)).setOnClickListener(this);
		mViewFooter = mViewRoot.findViewById(R.id.profile_v_footer);
		mViewRoot.setSmoothScrollingEnabled(true);
		return mViewRoot;
	}

	@Override
	public String generateTitle() {
		return "Thông tin";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.profile_btn_timeline:
			if (mViewFooter.getVisibility() == View.VISIBLE) {
				goneFooter();
			} else {
				visibleFooter();
			}
			break;
		case R.id.profile_btn_back_to_close:
			goneFooter();
			break;
		case R.id.profile_btn_edit:
			if (mListener != null) {
				mListener.onProfileFragmentInteraction(R.id.profile_btn_edit);
			}

			break;
		case R.id.profile_img_avatar:
			View contentView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.profile_change_avatar, null);
			final AlertDialog dialog = AlertDialogUtils.getIOSDialog(getActivity(), contentView, null);
			dialog.show();
			Button cancel = (Button) contentView.findViewById(R.id.profile_avatar_btn_cancel);
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			Button camera = (Button) contentView.findViewById(R.id.profile_avatar_btn_camera);
			camera.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivity(intent);
				}
			});
			Button lib = (Button) contentView.findViewById(R.id.profile_avatar_btn_library);
			lib.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivity(i);
				}
			});
			break;
		default:
			break;
		}

	}

	private void visibleFooter() {
		AnimationUtils.visibleTranslateAnimation(mViewFooter, 0, 0, mViewFooter.getHeight(), 0,
				new OnAnimationCallBack() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						mViewRoot.fullScroll(ScrollView.FOCUS_DOWN);
					}
				});
	}

	private void goneFooter() {
		AnimationUtils.goneTranslateAnimation(mViewFooter, 0, 0, 0, mViewFooter.getHeight(), new OnAnimationCallBack() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mViewRoot.fullScroll(ScrollView.FOCUS_UP);
			}
		});
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(int id) {
		if (mListener != null) {
			mListener.onProfileFragmentInteraction(id);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnProfileFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnProfileFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onProfileFragmentInteraction(int id);
	}

}
