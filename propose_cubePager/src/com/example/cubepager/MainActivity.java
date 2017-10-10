package com.example.cubepager;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.markjmind.propose.MotionBuilder;
import com.markjmind.propose.MotionInitor;
import com.markjmind.propose.MotionListener;
import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	
	private FrameLayout pager_layout, main_layout, left_layout, right_layout;
	private LinearLayout left_img_lyt,main_img_lyt;
	float width, height;
	
	float density; 
	float margin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		width = Propose.getWindowWidth(this);
		height = Propose.getWindowHeight(this);
		density = Propose.getDensity(MainActivity.this);
		margin = 120f*density;
		
		pager_layout = (FrameLayout)findViewById(R.id.pager_layout);
		main_layout = (FrameLayout)findViewById(R.id.main_layout);
		left_layout = (FrameLayout)findViewById(R.id.left_layout);
		right_layout = (FrameLayout)findViewById(R.id.right_layout);
		left_img_lyt = (LinearLayout)findViewById(R.id.left_img_lyt);
		main_img_lyt = (LinearLayout)findViewById(R.id.main_img_lyt);
		
		
		main_layout.setPivotY(height/2);
		main_layout.setCameraDistance(width*20);
		
		right_layout.setPivotX(0);
		right_layout.setPivotY(height/2);
		right_layout.setCameraDistance(width*20);
		
		left_layout.setPivotX(width);
		left_layout.setPivotY(height/2);
		left_layout.setCameraDistance(width*20);
		
		
		
		/** right Menu animator**/
		ObjectAnimator frist_left_rot = ObjectAnimator.ofFloat(main_layout, View.ROTATION_Y, 0,-90);
		frist_left_rot.setDuration(1000);
		frist_left_rot.setInterpolator(null);
		ObjectAnimator frist_left_tran = ObjectAnimator.ofFloat(main_layout, View.TRANSLATION_X, 0,-width);
		frist_left_tran.setDuration(1000);
		frist_left_tran.setInterpolator(null);
		
		ObjectAnimator sec_left_rot = ObjectAnimator.ofFloat(right_layout, View.ROTATION_Y, 90,0);
		sec_left_rot.setDuration(1000);
		sec_left_rot.setInterpolator(null);
		ObjectAnimator sec_left_tran = ObjectAnimator.ofFloat(right_layout, View.TRANSLATION_X, width,0);
		sec_left_tran.setDuration(1000);
		sec_left_tran.setInterpolator(null);
		
		/** Left Menu animator**/
		ObjectAnimator frist_right_rot = ObjectAnimator.ofFloat(main_layout, View.ROTATION_Y, 0,48);
		frist_right_rot.setDuration(1000);
		frist_right_rot.setInterpolator(null);
		ObjectAnimator frist_right_tran = ObjectAnimator.ofFloat(main_layout, View.TRANSLATION_X, 0,width/2);
		frist_right_tran.setDuration(1000);
		frist_right_tran.setInterpolator(null);
		
		ObjectAnimator sec_right_rot = ObjectAnimator.ofFloat(left_layout, View.ROTATION_Y, 85,-5);
		sec_right_rot.setDuration(1000);
		sec_right_rot.setInterpolator(null);
		ObjectAnimator sec_right_tran = ObjectAnimator.ofFloat(left_layout, View.TRANSLATION_X, -width,-width/2);
		sec_right_tran.setDuration(1000);
		sec_right_tran.setInterpolator(null);
		
		
		/** set Motion **/
		Propose propose = new Propose(this);
		propose.motionLeft.play(frist_left_rot,(int)width/2).with(frist_left_tran)
		.with(sec_left_rot).with(sec_left_tran);
		propose.motionRight.play(frist_right_rot,(int)width/2).with(frist_right_tran)
		.with(sec_right_rot).with(sec_right_tran);
		
		propose.motionLeft.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {
				main_layout.setPivotX(width);
				left_layout.setVisibility(View.GONE);
				right_layout.setVisibility(View.VISIBLE);
				
			}
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {}
			public void onEnd(boolean isForward) {}
		});
		
		propose.motionRight.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {
				main_layout.setPivotX(0);
				left_layout.setVisibility(View.VISIBLE);
				right_layout.setVisibility(View.GONE);
				
			}
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {}
			public void onEnd(boolean isForward) {}
		});
		pager_layout.setOnTouchListener(propose);
		
		
		/** Left Image animation **/
		setMoveLyt(R.id.img_lyt1,0);
		setMoveLyt(R.id.img_lyt2,1);
		setMoveLyt(R.id.img_lyt3,2);
		setMoveLyt(R.id.img_lyt4,3);
	}	
	
	int count = 0;
	private void setMoveLyt(int id,final int index){
		BounceInterpolator inter = new BounceInterpolator();
		inter.getInterpolation(0.1f);
		final View main_img_lyt1 = main_img_lyt.findViewById(id);
		final View left_img_lyt1 = left_img_lyt.findViewById(id);
		left_img_lyt1.setTag(new ImageIndex(index));
		main_img_lyt1.setX(-width);
		ObjectAnimator leftX = ObjectAnimator.ofFloat(left_img_lyt1, View.TRANSLATION_X, 0,width);
		leftX.setDuration(1000);
		leftX.setInterpolator(inter);
		ObjectAnimator mainX = ObjectAnimator.ofFloat(main_img_lyt1, View.TRANSLATION_X, -width, 0);
		mainX.setDuration(1000);
		mainX.setInterpolator(inter);
		final ObjectAnimator leftY = ObjectAnimator.ofFloat(left_img_lyt1, View.TRANSLATION_Y, 
				0, 0);
		leftY.setDuration(1000);
		final ObjectAnimator mainY = ObjectAnimator.ofFloat(main_img_lyt1, View.TRANSLATION_Y, 
				0, 0);
		mainY.setDuration(1000);
		final Propose left_lyt1 = new Propose(this);
		final MotionBuilder leftBuilder = left_lyt1.motionRight.play(leftX,(int)width)
				.with(leftY);
		final Propose main_lyt1 = new Propose(this);
		final MotionBuilder mainBuilder = main_lyt1.motionRight.play(mainX,(int)width)
				.with(mainY);
		left_lyt1.motionRight.enableReverse(false);
		main_lyt1.motionRight.enableReverse(false);
		left_img_lyt1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				left_lyt1.onTouch(v, event);
				main_lyt1.onTouch(v, event);
				return true;
			}
		});
		main_img_lyt1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				left_lyt1.onTouch(v, event);
				main_lyt1.onTouch(v, event);
				return true;
			}
		});
		
		left_lyt1.motionRight.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {
				ImageIndex tag = (ImageIndex)left_img_lyt1.getTag();
				tag.y = left_img_lyt.getY();
				leftY.setFloatValues(margin*(tag.rightIndex-tag.leftIndex),-margin*(tag.leftIndex-count));
				mainY.setFloatValues(margin*(tag.rightIndex-tag.leftIndex),-margin*(tag.leftIndex-count));
			}
			
			@Override
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {
				float up = currDuration;
				if(up>300){
					up = 300;
				}
				for(int i=index+1;i<left_img_lyt.getChildCount();i++){
					final View leftChild = left_img_lyt.getChildAt(i);
					final View mainChild = main_img_lyt.getChildAt(i);
					ImageIndex tag = (ImageIndex)leftChild.getTag();
					if(tag.isLeft){
						leftChild.setY(-margin*(up/300f)+margin*(tag.rightIndex)+100f*density);
						mainChild.setY(-margin*(up/300f)+margin*(tag.rightIndex)+100f*density);
					}
				}
			}
			
			@Override
			public void onEnd(boolean isForward) {
				ImageIndex tag = (ImageIndex)left_img_lyt1.getTag();
				if(isForward){
					tag.isLeft = false;
					left_img_lyt1.setTag(tag);
					count++;
					for(int i=index+1;i<left_img_lyt.getChildCount();i++){
						final View leftChild = left_img_lyt.getChildAt(i);
						ImageIndex cTag = (ImageIndex)leftChild.getTag();
						cTag.rightIndex--;
						leftChild.setTag(cTag);
					}
				}
			}
		});
	}
	
	class ImageIndex{
		public boolean isLeft;
		public int leftIndex;
		public int rightIndex;
		public float y;
		public ImageIndex(int index){
			this.isLeft = true;
			this.leftIndex = index;
			this.rightIndex = index;
		}
	}
	
}
