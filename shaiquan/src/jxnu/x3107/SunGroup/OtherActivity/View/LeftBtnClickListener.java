package jxnu.x3107.SunGroup.OtherActivity.View;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class LeftBtnClickListener implements OnClickListener{

	private PersonalleftDialog dialog;
	private int n;
	@SuppressWarnings("unused")
	private Context mContext;

	public LeftBtnClickListener(Context mContext,PersonalleftDialog dialog, int n) {
		super();
		this.mContext = mContext;
		this.dialog = dialog;
		this.n = n;
	}

	@Override
	public void onClick(View v) {
//		Utils.showToast(mContext, "点中了左边的按钮!");

		switch (n) {
		case 1:
			dialog.dismiss();
			break;
		case 2:

			dialog.dismiss();
			break;
		case 3:
			dialog.dismiss();
			break;
		case 4:

			dialog.dismiss();
			break;
		case 5:
			dialog.dismiss();
			break;
		case 6:
			dialog.dismiss();
			break;
		case 7:
			dialog.dismiss();
			break;
		case 8:
			dialog.dismiss();
			break;
		case 9:
			dialog.dismiss();
			break;

		default:
			break;
		}

	}

}
