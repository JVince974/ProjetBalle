package com.example.vincent.projetballe.controller;import android.app.Activity;import android.content.Intent;import android.hardware.Sensor;import android.hardware.SensorEvent;import android.hardware.SensorEventListener;import android.hardware.SensorManager;import android.os.Bundle;import android.os.CountDownTimer;import android.util.Log;import android.widget.TextView;import com.example.vincent.projetballe.R;import com.example.vincent.projetballe.bibliotheque.MyMediaPlayer;import com.example.vincent.projetballe.model.GameObject.lesBalles.CatchBalle;import com.example.vincent.projetballe.model.GameObject.lesBalles.EnnemyBalle;import com.example.vincent.projetballe.model.GameObject.lesBalles.UserBalle;import com.example.vincent.projetballe.model.GameObject.lesBonus.BonusMalus;import com.example.vincent.projetballe.model.GameObject.lesBonus.Malus;import com.example.vincent.projetballe.model.GameObject.lesBonus.Bonus;import com.example.vincent.projetballe.view.AnimateGameView;import java.util.ArrayList;/** * Cette classe gère le déplacement de la balle de l'utilisateur à l'aide de l'accéléromètre * Elle instancie toutes les balles * Elle gére toutes les interaction de la balle avec les autres objets et l'environnement * ex : collision avec une autre balle, prendre un bonus, etc. */public class GameActivity extends Activity implements SensorEventListener {    public static final String MY_INTENT_EXTRA_SCORE = "score"; // Variable intent pour récupérer le score    private static final String TAG = "GameActivity";    // les views    private AnimateGameView mAnimateGameView;    private TextView tvScore;    private TextView tvTime;    private CountDownTimer mCountDownTimer; // chronometer    // Accéléromètre    private SensorManager mSensorManager;    private Sensor mAccelerometer;    private MyMediaPlayer mMediaPlayer; // pour jouer la musique de fond    private boolean started = false; // start/resume    // taille de l'écran    private int viewWidth;    private int viewHeight;    // tous les objets du jeu    private UserBalle mUserBalle;    private ArrayList<EnnemyBalle> mEnnemyBalleArrayList;    private CatchBalle mCatchBalle;    private BonusMalus mBonus;    private int score = 0;    private int life = 3;    private int numberEnnemyBalle = 3;    private float iaBallSpeed = 1;    /**     * Récupérer les vues     * Instancier toutes les variables     */    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_game);        // récupérer la vue        mAnimateGameView = (AnimateGameView) findViewById(R.id.animate_game_view);        tvScore = (TextView) findViewById(R.id.tv_score);        tvTime = (TextView) findViewById(R.id.tv_time);        displayScore();        // le sensor        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);        mMediaPlayer = new MyMediaPlayer(this, R.raw.rayman_ost);        // lister tous les sensors du téléphone//        List<Sensor> sensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);//        for (Sensor s : sensorsList) {//            Log.d("SensorAvailable", "" + s.toString());//        }    }    /**     * Détruire le jeu pour la partie suivante     */    @Override    protected void onDestroy() {        Log.d(TAG, "onDestroy() called");        super.onDestroy();        gameDestroy();    }    /**     * Analyse chaque interaction de la balle avec l'environnement     * ex : collision avec une autre balle, récupération d'un bonus     */    private void onUserBalleMovedListener() {        // vérifier les collisions avec chaque balles ennemies        // si touché, la partie est terminée        if(mUserBalle.getCanDie() == true) {            for (EnnemyBalle ennemyBalle : mEnnemyBalleArrayList) {                if (mUserBalle.touched(ennemyBalle)) {                    Log.d(TAG, "onUserBalleMovedListener: UserBalle touched EnnemyBalle");                    // Envoyer le score au main Activity                    Intent intent = new Intent(this, MainActivity.class);                    intent.putExtra(MY_INTENT_EXTRA_SCORE, score);                    if (score > 0)                        setResult(Activity.RESULT_OK, intent);                    return;                }            }        }        // lorsqu'on touche la balle a attraper        // augmente le score et augmenter la vitesse des balles        if (mUserBalle.touched(mCatchBalle)) {            Log.d(TAG, "onUserBalleMovedListener: UserBalle touched CatchBalle");            score += mCatchBalle.getScore();            displayScore();            // Détruire la catch balle et faire réapparaitre une plus tard            new Thread(new Runnable() {                @Override                public void run() {                    iaBallSpeed *= 1.1;                    mCatchBalle.stop();                    mCatchBalle.join();                    mCatchBalle.setPosX(viewWidth + 1000);//                    try {//                        Thread.sleep(3000);//                    } catch (InterruptedException e) {//                        e.printStackTrace();//                    }                    mCatchBalle = CatchBalle.randomCatchBalle(GameActivity.this, mCatchBalle.getRadius());                    mCatchBalle.start();                }            }).start();        }        // Quand on touche un bonus, cacher le bonus        if (mBonus != null) {            if (mUserBalle.touched(mBonus)) {                Log.d(TAG, "onUserBalleMovedListener: UserBalle touched Bonus");                mBonus.move(viewWidth + 1000, viewHeight + 1000);                displayCronometer(mBonus.getDuration());                mBonus.start(); // TODO: 30/10/2017 reactiver            }        }    }    // Récupérer la taille de la view et instancier la GameThread    @Override    public void onWindowFocusChanged(boolean hasFocus) {        Log.d(TAG, "onWindowFocusChanged() called with: hasFocus = [" + hasFocus + "]");        super.onWindowFocusChanged(hasFocus);        if (hasFocus) {            if (!started) {                gameStart();                started = true;            } else {                gameResume();            }        } else {            gamePause();        }    }    /**     * Récupérer la taille de la view     * Instancier la balle utilisateur     * Instancier les balles ia     * Instancier la balle à attraper     * Lancer le controleur de jeu,     * Lancer l'accelerometre     * Lancer la musique     */    private void gameStart() {        Log.d(TAG, "Starting game...");        viewWidth = mAnimateGameView.getWidth();        viewHeight = mAnimateGameView.getHeight();        Log.d("WindowsSize", "Width=[" + viewWidth + "], Height=[" + viewHeight + "]");        // Instancier toutes les balles        mUserBalle = new UserBalle(viewWidth / 2, viewHeight / 2, (int) (viewWidth * 4.63 / 100), viewWidth, viewHeight); // positionner la balle utilisateur au milieu de l'écran        mEnnemyBalleArrayList = new ArrayList<>();        while (mEnnemyBalleArrayList.size() < numberEnnemyBalle) { // ajouter des balles ia            EnnemyBalle ennemyBalle = EnnemyBalle.randomEnnmyBalle(this, mUserBalle.getRadius());            mEnnemyBalleArrayList.add(ennemyBalle);            ennemyBalle.start();        }        mCatchBalle = CatchBalle.randomCatchBalle(this, mUserBalle.getRadius());        mCatchBalle.start();        // TODO: 30/10/2017 toDelete        debug();        mAnimateGameView.setGameActivity(this); // permet a la vue de récupérer tous les objets de la gameActivity pour dessiner        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);        mMediaPlayer.start();    }    /**     * Mettre en pause toutes les balles     * Continuer l'accéléromètre et relancer les balles IA     * Remettre la musique     */    private void gameResume() {        Log.d(TAG, "Resuming game...");        // resume toutes les balles        for (EnnemyBalle ennemyBalle : mEnnemyBalleArrayList) {            ennemyBalle.resume();        }        mCatchBalle.resume();        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);        mMediaPlayer.resume();    }    /**     * Mettre en pause l'accéléromètre et les balles IA     * mettre en pause la musique     */    private void gamePause() {        Log.d(TAG, "Pausing game...");        // mettre en pause toutes les balles        for (EnnemyBalle ennemyBalle : mEnnemyBalleArrayList) {            ennemyBalle.pause();        }        mCatchBalle.pause();        mSensorManager.unregisterListener(this);        mMediaPlayer.pause();    }    /**     * Arreter tous les threads, la musique et l'accéléromètre     */    private void gameDestroy() {        Log.d(TAG, "Destroying game...");        // mettre en pause toutes les balles        for (EnnemyBalle ennemyBalle : mEnnemyBalleArrayList) {            ennemyBalle.stop();            ennemyBalle.join();        }        mCatchBalle.stop();        mCatchBalle.join();        mSensorManager.unregisterListener(this);        mMediaPlayer.stop();    }    /**     * Afficher le score     */    public void displayScore() {        tvScore.setText(getResources().getString(R.string.score) + " : " + this.score);    }    /**     * Afficher le décompte d'un chronomère pour les bonus     */    public void displayCronometer(final long time) {        // Créer le timer        mCountDownTimer = new CountDownTimer(time, 1000) {            @Override            public void onTick(long millisUntilFinished) {                int seconds = (int) (millisUntilFinished / 1000);                tvTime.setText(String.format("%02d", seconds));            }            @Override            public void onFinish() {                tvTime.setText("");            }        }.start();    }    /**     * Bouger la balle avec l'accéléromètre     */    @Override    public void onSensorChanged(SensorEvent event) {//        Log.v(TAG, "onSensorChanged:  x=" + event.values[0] + ", y=" + event.values[1]);        if (mUserBalle != null) {            mUserBalle.move((int) (mUserBalle.getPosX() - event.values[0] * mUserBalle.getSpeed()), (int) (mUserBalle.getPosY() + event.values[1] * mUserBalle.getSpeed()));            onUserBalleMovedListener();        } else {            Log.w(TAG, "onSensorChanged: UserBall est null, vérifié que vous avez récupérer depuis le GameThread :: mUserBall = mGameThread.getUserBall() dans la méthode onWindowsHasFocus()");        }    }    @Override    public void onAccuracyChanged(Sensor sensor, int accuracy) {    }    // Getters    public int getViewWidth() {        return viewWidth;    }    public int getViewHeight() {        return viewHeight;    }    public UserBalle getUserBalle() {        return mUserBalle;    }    public ArrayList<EnnemyBalle> getEnnemyBalleArrayList() {        return mEnnemyBalleArrayList;    }    public CatchBalle getCatchBalle() {        return mCatchBalle;    }    public BonusMalus getBonus() {        return mBonus;    }    public float getIaBallSpeed() {        return iaBallSpeed;    }    public void setIaBallSpeed(float iaBallSpeed) {        this.iaBallSpeed = iaBallSpeed;    }    /**     * DEBUG     */    private void debug() {        mBonus = Bonus.debugWhichBonus(this, Bonus.BONUS_INVINCIBILITY);        //mBonus = Malus.debugWhichMalus(this, Malus.MALUS_DIVISION); // exemple//        mBonus.start();    }}