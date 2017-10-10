package com.example.graphtest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.markjmind.mobile.api.android.ui.drawgraph.GraphBoard;
import com.markjmind.mobile.api.android.ui.drawgraph.HBackLineSkin;
import com.markjmind.mobile.api.android.ui.drawgraph.LineGraphSkin;
import com.markjmind.mobile.api.android.ui.drawgraph.PieGraph;
import com.markjmind.mobile.api.android.ui.drawgraph.RectGraphSkin;
import com.markjmind.mobile.api.android.ui.drawgraph.SimpleStyle;
import com.markjmind.mobile.api.android.ui.drawgraph.TextArraySkin;
import com.markjmind.mobile.api.android.ui.drawgraph.VBackLineSkin;
import com.markjmind.propose.Propose;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196f3")));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }
        
        
        private PieGraph pieGraph1;
        private GraphBoard graphBoard1; 			//그래프를 그려줄 board
    	private VBackLineSkin vback; 		//배경 세로줄
    	private HBackLineSkin hback; 		//배경 가로줄
    	private RectGraphSkin rectSkin; 	//막대그래프
    	private LineGraphSkin lineSkin; 	//라인그래프1
    	private LineGraphSkin lineSkin2; 	//라인그래프2
    	private TextArraySkin tSkin; 		//하단 텍스트 정의
    	
    	private ViewGroup top_lyt,graph_lyt,left_touch,right_touch;
    	
    	private GraphBoard bottom_graph;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            bottom_graph = (GraphBoard)rootView.findViewById(R.id.bottom_graph);
            pieGraph1 = (PieGraph)rootView.findViewById(R.id.pieGraph1);
            graphBoard1 = (GraphBoard)rootView.findViewById(R.id.graphBoard1);
            float density = Propose.getDensity(getActivity());
            float height = Propose.getWindowHeight(getActivity());
            float width = Propose.getWindowWidth(getActivity());
            top_lyt = (ViewGroup)rootView.findViewById(R.id.top_lyt);
            graph_lyt = (ViewGroup)rootView.findViewById(R.id.graph_lyt);
            left_touch = (ViewGroup)rootView.findViewById(R.id.left_touch);
            right_touch = (ViewGroup)rootView.findViewById(R.id.right_touch);
            
            initPie();
            initBoard();
            initLine();
            
            ObjectAnimator slideAnim = ObjectAnimator.ofFloat(top_lyt, View.TRANSLATION_Y, 0,height/2-70*density);
            slideAnim.setDuration(1000);
            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(graph_lyt, View.ALPHA, 0.0f, 0.2f,0.5f,1.0f);
            alphaAnim.setDuration(1000);
            
            Propose propose = new Propose(rootView.getContext());
            propose.motionDown.play(slideAnim,(int)height/3).with(alphaAnim);
            top_lyt.setOnTouchListener(propose);
            
            
            ValueAnimator backAnim = ValueAnimator.ofFloat(0f, 50f);
			backAnim.setDuration(1000);
			backAnim.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float percent = (Float)animation.getAnimatedValue();
					if(percent<10){
						hback.setShow(false);
						vback.setShow(false);
						lineSkin.setShow(false);
						lineSkin2.setShow(false);
					}
					else if(10f<=percent && percent<20){
						hback.setShow(true);
						vback.setShow(false);
						lineSkin.setShow(false);
						lineSkin2.setShow(false);
						
					}else if(20f<=percent && percent<30){
						hback.setShow(true);
						vback.setShow(true);
						lineSkin.setShow(false);
						lineSkin2.setShow(false);
					}else if(30f<=percent && percent<40){
						hback.setShow(true);
						vback.setShow(true);
						lineSkin.setShow(true);
						lineSkin2.setShow(false);
					}else{
						hback.setShow(true);
						vback.setShow(true);
						lineSkin.setShow(true);
						lineSkin2.setShow(true);
					}
					graphBoard1.invalidate();
				}
			});
			
			Propose linePro = new Propose(rootView.getContext());
			linePro.motionRight.play(backAnim,height/2);
			linePro.motionRight.enableFling(false).enableTabUp(false);
            left_touch.setOnTouchListener(linePro);
            
            ValueAnimator rectAnim = ValueAnimator.ofFloat(0f, 1.0f);
    		rectAnim.setDuration(1000);
    		rectAnim.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float percent = (Float)animation.getAnimatedValue();
					graphBoard1.setDrawPercent(percent);
					graphBoard1.invalidate();
				}
			});
			
			ValueAnimator pieAnim = ValueAnimator.ofFloat(0f, 1.0f);
    		pieAnim.setDuration(1000);
    		pieAnim.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float percent = (Float)animation.getAnimatedValue();
					pieGraph1.setDrawPercent(percent);
					pieGraph1.invalidate();
				}
			});
    		
    		Propose graphPro = new Propose(rootView.getContext());
			graphPro.motionUp.play(pieAnim,height/3).with(rectAnim);
            right_touch.setOnTouchListener(graphPro);
    		
    		
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
        
        /**
    	 * 원그래프 영역 초기화
    	 */
    	public void initPie(){	
    		//테스트
    		pieGraph1.setRadius(15);							//pie 두께 20
    		pieGraph1.setBackgroundColor(Color.parseColor("#bdbdbd"));			//pie 배경색 지정
    		pieGraph1.add(50, Color.parseColor("#027ac7"));	
    		pieGraph1.add(30, Color.parseColor("#8bc34a"));	
    		pieGraph1.add(60, Color.parseColor("#4caf50"));	
    		pieGraph1.add(90, Color.parseColor("#009688"));	
    		pieGraph1.add(120,Color.parseColor("#673ab7"));
    		pieGraph1.add(70, Color.parseColor("#acba39"));		//70도만큼 노란색으로 그리기
    		pieGraph1.setDrawPercent(0f);
    	}
    	/**
    	 * 막대그래프 영역 초기화
    	 */
    	public void initBoard(){
    		rectSkin = new RectGraphSkin(5); //막대그래프 최대값 5로 설정
    		lineSkin = new LineGraphSkin(5); //라인그래프2 최대값 5로 설정
    		lineSkin2 = new LineGraphSkin(5); //라인그래프2 최대값 5로 설정
    		vback = new VBackLineSkin(7); //배경 세로줄 7줄로 설정
    		hback = new HBackLineSkin(5); //배경 가로줄 5줄로 설정
    		tSkin = new TextArraySkin(7); //그래프 하단에 나타낼 text 7개로 지정
    		
    		//막대그래프 정의
    		int[] rm = {1,1,2,3,4,5,4};  
//    		rectSkin.addAll(rm, RectGraphSkin.getSimpleStyle(Color.parseColor("#aa027ac7"), 40));
    		rectSkin.add(2, RectGraphSkin.getSimpleStyle(Color.parseColor("#aa027ac7"), 40));
    		rectSkin.add(1, RectGraphSkin.getSimpleStyle(Color.parseColor("#aaacba39"), 40));
    		rectSkin.add(2, RectGraphSkin.getSimpleStyle(Color.parseColor("#aa4caf50"), 40));
    		rectSkin.add(3, RectGraphSkin.getSimpleStyle(Color.parseColor("#aa8bc34a"), 40));
    		rectSkin.add(4, RectGraphSkin.getSimpleStyle(Color.parseColor("#aa009688"), 40));
    		rectSkin.add(5, RectGraphSkin.getSimpleStyle(Color.parseColor("#aa673ab7"), 40));
    		rectSkin.add(4, RectGraphSkin.getSimpleStyle(Color.parseColor("#aae91e63"), 40));
    		
    		
    		//라인그래프1 정의
    		int[] lm = {0,2,2,1,5,3,2};
    		//꼭지점 동그라미 정의
    		SimpleStyle circle = LineGraphSkin.getCircleSimpleStyle(Color.parseColor("#ffffff"), 4, 4);
    		lineSkin.addAll(lm, LineGraphSkin.getLineSimpleStyle(Color.parseColor("#ffffff"), 4), circle);
    		
    		//라인그래프2 정의
    		int[] lm2 = {2,1,2,3,2,4,3};  
    		lineSkin2.addAll(lm2, LineGraphSkin.getLineSimpleStyle(Color.RED, 4));
    		
    		//하단 텍스트 정의
    		String[] array = {"S1","S2","S3","S4","S5","S6","AVR"};
    		tSkin.addAll(array, TextArraySkin.getSimpleStyle(Color.parseColor("#607d8b"), 30, 5));
    		
    		//가로줄 정의
    		SimpleStyle hbackStyle = HBackLineSkin.getSimpleStyle(Color.parseColor("#8097a2"), 1);
    		hback.addAll(hbackStyle);
    		
    		//세로줄 정의
    		SimpleStyle vbackStyle = new SimpleStyle(Color.parseColor("#8097a2"), 1, 0, 0, SimpleStyle.Align.CENTER);
    		vback.addAll(vbackStyle);
    		
    		//기준선 추가(주석을 제거하면 기준선이 추가됨)
    		hbackStyle.setColor(Color.parseColor("#77607d8b")).setWidth(6);
    		hback.add(3, hbackStyle);
    		
    		graphBoard1.addSkin(rectSkin);	//막대그래프 추가
    		graphBoard1.addSkin(lineSkin); 	//라인그래프1 추가
    		graphBoard1.addSkin(lineSkin2);	//라인그래프2 추가
    		graphBoard1.addSkin(tSkin);		//하단 텍스트 추가
    		graphBoard1.addSkin(vback);		//세로줄 추가
    		graphBoard1.addSkin(hback);		//가로줄 추가
    		graphBoard1.setDrawPercent(0f);
    		hback.setShow(false);
			vback.setShow(false);
			lineSkin.setShow(false);
			lineSkin2.setShow(false);
    		
    	}
    	
    	
    	
    	/**
    	 * 선그래프 영역 초기화
    	 */
    	public void initLine(){
    		//세로줄 정의
    		VBackLineSkin vback = new VBackLineSkin(24);
    		SimpleStyle vbackStyle = new SimpleStyle(Color.parseColor("#D9D8E7"), 1, 0, 0, SimpleStyle.Align.CENTER);
    		vback.addAll(vbackStyle);
    		
    		//가로줄 정의
    		HBackLineSkin hback = new HBackLineSkin(3);
    		SimpleStyle hbackStyle = HBackLineSkin.getSimpleStyle(Color.parseColor("#027ac7"), 1);
    		hback.addAll(hbackStyle);
    		
    		//라인그래프1 정의
    		LineGraphSkin lineSkin = new LineGraphSkin(300);
    		int[] lm = {150,140,130,120,110,100,88,76,30,35,90,120,110,150,200,230,200,160,180,190,210,220,230,230,220,230,240,240,290,150,110,90,30,60,50,40,110,120,210,10,110,110,220,210,230,190,170,140,160,130,110,170,110,160,200,160,140,130,100,90,80,70,60,30,12,8,10,10,20,6,30,40,4,0,0,22,2,6,2,12,6,18,86,8,4,4,0,6,0,6,12,0,0,26,0,0,0,0,0,0,0,2,0,4,4,48,2,58,0,4,0,6,16,0,8,8,22,10,4,4,22,26,172,170,160,26,20,12,78,24,40,46,56,32,50,74,62,0,14,72,12,0,6,8,18,4,40,42,2,20,4,10,8,20,184,462,462,508,211,248,26,8,24,70,0,14,18,6,114,6,26,78,96,34,20,14,74,36,40,50,60,90,100,150,160,170,180,190,220,290,200,210,260,210,250,230,250,230,220,210,220,30,90,100,10,160,60,140,130,190,130,120,100,60,30,50,90,130,150,100,70,50,30,20,10,90,70,60,50,40,30,20,20,30,40,50,60,10,70,80,90,80,70,60,50,60,50,74,26,10,20,10,6,30,40,45,50,55,60,70,80,90,100,105,110,115,120,125,120,130,140,150,148,150,160,165};
    		lineSkin.addAll(lm, LineGraphSkin.getLineSimpleStyle(Color.parseColor("#8ecd00"), 4));
    		
    		//하단 텍스트 정의
    		TextArraySkin tSkin = new TextArraySkin(15);
    		String[] array = new String[15];
    		for(int i=0;i<array.length;i++){
    			array[i]=""+(i+1);
    		}
    		tSkin.addAll(array, TextArraySkin.getSimpleStyle(Color.BLACK, 30,5));
    		
    		bottom_graph.addSkin(vback);		//세로줄 추가
    		bottom_graph.addSkin(hback);		//가로줄 추가
    		bottom_graph.addSkin(lineSkin); 	//라인그래프1 추가
    		bottom_graph.addSkin(tSkin);		//하단 텍스트 추가
    	}
    	
    	
    }

}
