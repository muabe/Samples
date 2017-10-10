package com.example.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.markjmind.propose.JwAnimatorListener;
import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;


public class MainActivity extends Activity {

	float width;
	View touch_lyt, lyt1,lyt2,lyt3,lyt4;
	Propose propose;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        width = 200*Propose.getDensity(this);
        float ds = (width*100);
        touch_lyt = findViewById(R.id.touch_lyt);
        lyt1 = findViewById(R.id.lyt1);
        lyt1.setCameraDistance(ds);
        lyt2 = findViewById(R.id.lyt2);
        lyt2.setCameraDistance(ds);
        lyt3 = findViewById(R.id.lyt3);
        lyt3.setCameraDistance(ds);
        lyt4 = findViewById(R.id.lyt4);
        lyt4.setCameraDistance(ds);
        
        Log.e("c","car:"+Propose.getCameraDistanceY(this)+ " = "+ds);
        
        
        ObjectAnimator rot1 = ObjectAnimator.ofFloat(lyt1, View.ROTATION_Y, 0,90);
        rot1.setDuration(1000);
        rot1.setInterpolator(null);
        
        rot1.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float x = width*((float)animation.getCurrentPlayTime())/1000f;
//				lyt1.setPivotX(width/2-x/2);
				lyt1.setPivotX(0);
				
			}
		});
        ObjectAnimator tran1 = ObjectAnimator.ofFloat(lyt1, View.TRANSLATION_X, 0,width);
        tran1.setDuration(1000);
        tran1.setInterpolator(null);
        
        ObjectAnimator rot2 = ObjectAnimator.ofFloat(lyt2, View.ROTATION_Y, 270, 360);
        rot2.setDuration(1000);
        rot2.setInterpolator(null);
        rot2.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float x = width*((float)animation.getCurrentPlayTime())/1000f;
//				lyt2.setPivotX(width-x/2);
				lyt2.setPivotX(width);
			}
		});
        ObjectAnimator tran2 = ObjectAnimator.ofFloat(lyt2, View.TRANSLATION_X, -width,0);
        tran2.setDuration(1000);
        tran2.setInterpolator(null);
        
        
        
        ObjectAnimator rot3 = ObjectAnimator.ofFloat(lyt3, View.ROTATION_X, 270, 360);
        rot3.setDuration(1000);
        rot3.setInterpolator(null);
        lyt3.setPivotX(width/2);
        ObjectAnimator tran3 = ObjectAnimator.ofFloat(lyt3, View.TRANSLATION_Y, width,0);
        tran3.setDuration(1000);
        tran3.setInterpolator(null);
        
        ObjectAnimator rot4 = ObjectAnimator.ofFloat(lyt4, View.ROTATION_X, 90, 0);
        rot4.setDuration(1000);
        rot4.setInterpolator(null);
        lyt4.setPivotX(width/2);
        ObjectAnimator tran4 = ObjectAnimator.ofFloat(lyt4, View.TRANSLATION_Y, -width,0);
        tran4.setDuration(1000);
        tran4.setInterpolator(null);
        
        
        
        ObjectAnimator up1 = ObjectAnimator.ofFloat(lyt1, View.ROTATION_X, 0, 90);
        up1.setDuration(1000);
        up1.setInterpolator(null);
        lyt1.setPivotX(width/2);
        ObjectAnimator upTran1 = ObjectAnimator.ofFloat(lyt1, View.TRANSLATION_Y, 0,-width);
        upTran1.setDuration(1000);
        upTran1.setInterpolator(null);
        
        ObjectAnimator up2 = ObjectAnimator.ofFloat(lyt2, View.ROTATION_X, 0, 90);
        up2.setDuration(1000);
        up2.setInterpolator(null);
        lyt2.setPivotX(width/2);
        ObjectAnimator upTran2 = ObjectAnimator.ofFloat(lyt2, View.TRANSLATION_Y, 0,-width);
        upTran2.setDuration(1000);
        upTran2.setInterpolator(null);
        
        ObjectAnimator down1 = ObjectAnimator.ofFloat(lyt1, View.ROTATION_X, 0, -90);
        down1.setDuration(1000);
        down1.setInterpolator(null);
        lyt1.setPivotX(width/2);
        ObjectAnimator downTran1 = ObjectAnimator.ofFloat(lyt1, View.TRANSLATION_Y, 0,width);
        downTran1.setDuration(1000);
        downTran1.setInterpolator(null);
        
        ObjectAnimator down2 = ObjectAnimator.ofFloat(lyt2, View.ROTATION_X, 0, -90);
        down2.setDuration(1000);
        down2.setInterpolator(null);
        lyt2.setPivotX(width/2);
        ObjectAnimator downTran2 = ObjectAnimator.ofFloat(lyt2, View.TRANSLATION_Y, 0,width);
        downTran2.setDuration(1000);
        downTran2.setInterpolator(null);
        
        
        lyt2.setX(-width);
        lyt2.setRotationY(270);
        
        lyt3.setY(-width);
        lyt3.setRotationX(270);
        
        lyt4.setY(0);
        lyt4.setRotationX(-90);
        
        lyt1.setPivotY(width/2);
        lyt2.setPivotY(width/2);
        lyt3.setPivotX(width/2);
        lyt4.setPivotX(width/2);
        
        lyt4.setPivotY(width);
        
        
        ObjectAnimator upRight1 = ObjectAnimator.ofFloat(lyt3, View.ROTATION, 0, 90);
        upRight1.setDuration(1000);
        upRight1.setInterpolator(null);
        lyt3.setPivotX(width/2);
        ObjectAnimator upRightTran1 = ObjectAnimator.ofFloat(lyt3, View.TRANSLATION_X, 0,width);
        upRightTran1.setDuration(1000);
        upRightTran1.setInterpolator(null);
        
        ObjectAnimator downRight1 = ObjectAnimator.ofFloat(lyt4, View.ROTATION, 0, -90);
        downRight1.setDuration(1000);
        downRight1.setInterpolator(null);
        lyt4.setPivotX(width/2);
        ObjectAnimator downRightTran1 = ObjectAnimator.ofFloat(lyt4, View.TRANSLATION_X, 0,width);
        downRightTran1.setDuration(1000);
        downRightTran1.setInterpolator(null);
        
        propose = new Propose(this);
        propose.motionRight.play(rot1).with(tran1).with(rot2).with(tran2)
        .with(upRight1).with(upRightTran1)
        .with(downRight1).with(downRightTran1);
        propose.motionUp.play(rot3).with(tran3).with(up1).with(upTran1).with(up2).with(upTran2);
        propose.motionDown.play(rot4).with(tran4).with(down1).with(downTran1).with(down2).with(downTran2);
        
        propose.setMotionDistanceAll((int)width/2);
        
        rot1.addListener(new JwAnimatorListener() {
			@Override
			public void onStart(Animator animation) {
				lyt3.setPivotX(0);
				lyt4.setPivotX(0);
			}
			@Override
			public void onReverseStart(Animator animation) {
			}
			@Override
			public void onReverseEnd(Animator animation) {
			}
			@Override
			public void onEnd(Animator animation) {
			}
		});
        rot3.addListener(new JwAnimatorListener() {
			@Override
			public void onStart(Animator animation) {
				lyt1.setPivotY(width);
				lyt2.setPivotY(width);
			}
			@Override
			public void onReverseStart(Animator animation) {
			}
			@Override
			public void onReverseEnd(Animator animation) {
			}
			@Override
			public void onEnd(Animator animation) {
			}
		});
        
        rot4.addListener(new JwAnimatorListener() {
			@Override
			public void onStart(Animator animation) {
				lyt1.setPivotY(0);
				lyt2.setPivotY(0);
			}
			@Override
			public void onReverseStart(Animator animation) {
			}
			@Override
			public void onReverseEnd(Animator animation) {
			}
			@Override
			public void onEnd(Animator animation) {
			}
		});
        
        touch_lyt.setOnTouchListener(propose);
        
    }


}
