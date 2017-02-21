package jxnu.x3107.Fragment.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class HGridView extends GridView{

	public HGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public HGridView(Context context) {
		super(context);
	}
	
	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}
}
