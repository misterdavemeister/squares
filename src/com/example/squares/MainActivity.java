package com.example.squares;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getSimpleName();
	private GameAi mGameAi = new GameAi();
	protected static int mDifficulty; // number of squares/buttons in GameActivity.
	private static Button mButtonOne;
	private static Button mButtonTwo;
	private static Button mButtonThree;
	private static Button mButtonFour;
	private static Button mButtonFive;
	private static Button mButtonSix;
	private static Button mButtonSeven;
	private static Button mButtonEight;
	private static Button mButtonNine;
	private static Button mButtonTen;
	private static Button mButtonEleven;
	private static Button mButtonTwelve;
	private static Button mButtonThirteen;
	private static Button mButtonFourteen;
	private static Button mButtonFifteen;
	private static Button mButtonSixteen;
	private static TextView mTextView;
	private static TextView mRoundView;
	public static Button mButtonStart;
	private List<Integer> mListOfPlayerButtonsPressed = new ArrayList<Integer>();
	private boolean mFirstRun;
	protected int mRoundNum;
	protected int mNumOfChances;
	private boolean playersTurn;
	protected MediaPlayer player;
/* Called when the activity is first created. */
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    Log.d(TAG, "1. onCreate started");
	    mButtonOne = (Button) findViewById(R.id.Button01);
	    mButtonTwo = (Button) findViewById(R.id.Button02);
	    mButtonThree = (Button) findViewById(R.id.Button03);
	    mButtonFour = (Button) findViewById(R.id.Button04);
	    mButtonFive = (Button) findViewById(R.id.Button05);
	    mButtonSix = (Button) findViewById(R.id.Button06);
	    mButtonSeven = (Button) findViewById(R.id.Button07);
	    mButtonEight = (Button) findViewById(R.id.Button08);
	    mButtonNine = (Button) findViewById(R.id.Button09);
	    mButtonTen = (Button) findViewById(R.id.Button10);
	    mButtonEleven = (Button) findViewById(R.id.Button11);
	    mButtonTwelve = (Button) findViewById(R.id.Button12);
	    mButtonThirteen = (Button) findViewById(R.id.Button13);
	    mButtonFourteen = (Button) findViewById(R.id.Button14);
	    mButtonFifteen = (Button) findViewById(R.id.Button15);
	    mButtonSixteen = (Button) findViewById(R.id.Button16);
	    mButtonStart = (Button) findViewById(R.id.StartButton);
	    mTextView = (TextView) findViewById(R.id.textView1);
	    mRoundView = (TextView) findViewById(R.id.textView2);
	    mDifficulty = 16; // TODO: make automatic and dynamic
	    mFirstRun = true; // for aiButtons (mButtonStart onClickListener)
	    final Button [] ButtonsOCLArray = getArray();
	    mNumOfChances = 3;
	    mRoundNum = 1;
	    mRoundView.setText("Round: " + mRoundNum);
 	    mTextView.setText("Chances: " + mNumOfChances);
	    mButtonStart.setVisibility(View.VISIBLE);
	    playersTurn = false;
	    
	    
	    
	// setOnClickListener for all buttons in array for user input
	    for (int i = 0; i < mDifficulty; i++) {
	    	 ButtonsOCLArray[i].setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
				  if (playersTurn) { // click NOT out of turn
					if (mGameAi.compareCount < mGameAi.mListOfAiButtonsToPress.size()) { // more buttons to press
						//compareCount is a count of the amount of AI buttons pressed. 
						if (mGameAi.comparePlayerAndAiButtons(v)) { // Correct button pressed
							//TODO: animateButtonsCorrect(v);
							animateButtons(v);
							playSound(v);
							if (mGameAi.compareCount == mGameAi.mListOfAiButtonsToPress.size()) { // No more buttons to press
								//everything done correctly, begin next round
								v.postDelayed(new Runnable () {
									public void run() {
										playersTurn = false;
										mRoundNum++;
										mRoundView.setText("Round: " + mRoundNum);
										mButtonStart.performClick();
									} // run
								}, 1500); // new runnable
							} // if 
						}// if
						//TODO:	else {
						//TODO: 	animateButtonsIncorrect(v);
						//TODO:	}
						else { // Incorrect button pressed
							playersTurn = false;
							mNumOfChances--;
							mTextView.setText("Chances: " + mNumOfChances); // if this is necessary, it needs more implemented:
							mRoundNum++; // TODO: make round remain the same, but this is not possible right now with the way the AIButtons are implemented
							mRoundView.setText("Round: " + mRoundNum);
							// TODO: repeat same Ai Buttons pressed
							// TODO: maybe a repeat button which also reduces mNumOfChances
							// TODO: gain chances after 10 rounds (maybe)
							v.postDelayed(new Runnable () {
								public void run() {
									mButtonStart.performClick(); // start AIbutton methods
								} // run
							}, 1500); // new runnable
						} // else
					}// if
					else { // No buttons to press
						playersTurn = false; //AI's turn
						v.postDelayed(new Runnable () {
							public void run() {
								mButtonStart.performClick(); // start AIbutton methods
							} // run
						}, 1500); // new runnable
					} // else
				  } // if (playersTurn)
				} // onclick 
			}); // ocl
	    } // for loop
	    
	    //start button clicked
	    mButtonStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				if (playersTurn) {
					playersTurn = false;
				}
				if (mNumOfChances > 0) {
				Log.d(TAG, "START BUTTON CLICKED!");
				if (mFirstRun) {
					mGameAi.setupAiButtons();	
					mFirstRun = false;
				}
				v.setVisibility(View.INVISIBLE);
				animateButtons(ButtonsOCLArray[mGameAi.getFirstButtonInList()]);
				playSound(ButtonsOCLArray[mGameAi.getFirstButtonInList()]);
				mGameAi.deleteFirstButtonInList();
				v.postDelayed(new Runnable() {
					public void run() {
						if (!mGameAi.buttonsListIsEmpty()) { // more clicks to animate
							v.performClick();
						}
						else { // done with list
							mFirstRun = true;
							playersTurn = true;
						}
					}
				}, 500);
				System.out.println("end of mButtonStart's onclicklistener");
			} // end if mNumOfChances
				else {
					resetGame();
				}
			}
	    });
	}
	    
	@Override
	public void onResume(){
		super.onResume();
		Button [] Buttons = getArray();
		System.out.println("(in onResume())buttons are" + Buttons);
		//if (player == null) {
		//	MediaPlayer player;
		//	player.start();
	//	}
			
	}
	
	public static Button[] getArray() {
		System.out.println("button array creating");
		Button CreateButtonArray[] = {mButtonOne, mButtonTwo, mButtonThree, mButtonFour,
		    	mButtonFive, mButtonSix, mButtonSeven, mButtonEight, mButtonNine, mButtonTen,
	    		mButtonEleven, mButtonTwelve, mButtonThirteen, mButtonFourteen, mButtonFifteen, mButtonSixteen};
		return CreateButtonArray;
	}
	
	public static void animateButtons(View v) {
		AlphaAnimation fadeInAnimation = new AlphaAnimation(0F, 1F);
    	fadeInAnimation.setDuration(300);
    	fadeInAnimation.setFillAfter(true);
    	v.startAnimation(fadeInAnimation);
    	System.out.println("buttons animated - (ANIMATEBUTTONS())");
	}
	
	private void playSound(View v) {
		MediaPlayer player = null;
		if ((v.getId() == R.id.Button01) || (v.getId() == R.id.Button02) || (v.getId() == R.id.Button03) || (v.getId() == R.id.Button04)) {
			player = MediaPlayer.create(this, R.raw.sound1);
		}
		else if ((v.getId() == R.id.Button05) || (v.getId() == R.id.Button06) || (v.getId() == R.id.Button07) || (v.getId() == R.id.Button08)) {
			player = MediaPlayer.create(this, R.raw.sound2);
		}
		else if ((v.getId() == R.id.Button09) || (v.getId() == R.id.Button10) || (v.getId() == R.id.Button11) || (v.getId() == R.id.Button12)) {
			player = MediaPlayer.create(this, R.raw.sound3);
		}
		else {
			player = MediaPlayer.create(this, R.raw.sound4);
		}
    	player.start();
    	player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
    }
	
	@Override
	public void onPause() {
		super.onPause();
		if (player != null) {
			player.stop();
			player.release();
		player = null;
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		if (player != null) {
			player.stop();
			player.release();
		player = null;
		}
	}
	public void resetGame() {
		mButtonStart.setVisibility(View.VISIBLE);
		mNumOfChances = 3;
		mRoundNum = 1;
		mGameAi.resetGameAi();
	    mDifficulty = 16; // TODO: make automatic and dynamic
	    mFirstRun = true; // for aiButtons (mButtonStart onClickListener)
	    mTextView.setText("Chances: " + mNumOfChances);
	    mRoundView.setText("Round: " + mRoundNum);
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
}
