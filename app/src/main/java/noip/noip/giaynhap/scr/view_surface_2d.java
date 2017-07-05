package noip.noip.giaynhap.scr;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.io.FileNotFoundException;
import java.util.*;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.DisplayMetrics;
import android.graphics.*;
import java.io.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.lang.Math;
import  android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;
/**
 * Created by giaynhap on 07/10/2016.
 */
public class view_surface_2d extends SurfaceView {
    Typeface tf;
    Typeface tf2;
    Typeface tf3;
    public SurfaceHolder holder;
    public float player_rotate = 45.0f;
    public float player_size = 45.0f;
    public float start_are_size = 60;
    private int start_effmo1 = 1;
    public boolean is_click_start = false;
    public int[] ball_pos = new int[]{0, 0};
    public int[] root_pos = new int[]{0, 0};
    public float game_r_size = 0.0f;
    public boolean player_alive=true;
    public boolean game_pause=false;
    public float[] ball_vec = new float[]{1.0f, 1.0f};
    public boolean is_draw_scoreeff = false;
    public int player_score=0;
    game_loop gLoop;
    public boolean is_add_score =false;
    public long _timeshow_end = System.currentTimeMillis();
    public boolean isGuiIngame = false;
    public Bitmap bitmap_Playbut;
    public Bitmap bitmap_restart;
    public Bitmap bitmap_exit;
    public Bitmap bitmap_overgui;
    public int [] scrSize = new int [2];
    public int  HightScore=0;
    public int Scale_first_show =0;
    MediaPlayer snd_boom;
    MediaPlayer snd_add_score;
    MediaPlayer snd_end;
    MediaPlayer snd_start;
    MediaPlayer snd_click;
    MediaPlayer snd_background;
    MediaPlayer snd_eff_t1;
    MediaPlayer snd_eff_over;
    MediaPlayer snd_eff_newhight;
    MediaPlayer snd_eff_lost;
    public boolean is_super_sh = false;
    public int int_player_mhar=0;
    public int isGame=2;
    public void game_restart()
    {
        EffStart_main =root_pos[0]  ;
        snd_end.seekTo(0);
        snd_end.start();
        addScoreOver=0;
        player_rotate = (float)Math.random()*360.0f;
        player_size = 45.0f;
        start_are_size = 60;
        start_effmo1 = 1;

        ball_pos = new int[]{0, 0};

        player_score=0;
        int_player_mhar=0;
        Scale_first_show=200;
        float rand1 = (float)Math.random();
        float rand2 = (float)Math.random();
        if (rand1>0.5) rand1= 1;
        else rand1 =-1;
        if (rand2>0.5) rand2= 1;
        else rand2 =-1;
        ball_vec = new float[]{(float)Math.random()*rand1, 0.0f};
        ball_vec[1] = (float)(1.0 - Math.abs(ball_vec[0]))*rand2;effsize_1=0.0f;
        is_click_start = false;
        player_alive=true;
        game_pause=false;
    }


    public void game2_restart()
    {
        issound4 = false;
        EffStart_main =root_pos[0]  ;
        snd_end.seekTo(0);
        snd_end.start();
        addScoreOver=0;
        player_vec1=0.0f;
        start_are_size = 60;
        start_effmo1 = 1;
        player_ac=1;
        player_score=0;
        int_player_mhar=0;
        Scale_first_show=200;
        player_speed=5.0f;
        is_click_start = false;
        player_alive=true;
        game_pause=false;

         /* GAME 2 */     reset_allent();
    }

    public view_surface_2d(Context context)  {


        super(context);
        //setRequestedOrientation

         snd_boom=MediaPlayer.create(context, R.raw.boom);
         snd_add_score=MediaPlayer.create(context, R.raw.mp3_476475);
         snd_end=MediaPlayer.create(context, R.raw.mp3_455279);
         snd_start=MediaPlayer.create(context, R.raw.mp3_455289);
        snd_click =MediaPlayer.create(context, R.raw.mp3_455671);
        snd_background =MediaPlayer.create(context, R.raw.backgroundsound);
         snd_eff_t1 = MediaPlayer.create(context, R.raw.tick_1);
         snd_eff_over = MediaPlayer.create(context, R.raw.gameover);
         snd_eff_newhight= MediaPlayer.create(context, R.raw.newhight);
         snd_eff_lost= MediaPlayer.create(context, R.raw.lost);
        snd_eff_over.setVolume(2.0f,2.0f);

        snd_background.start();
        snd_background.setLooping(true);
        snd_background.setVolume(0.2f,0.2f);
        snd_start.seekTo(0);
        snd_start.start();
        bitmap_Playbut=BitmapFactory.decodeResource(getResources(), R.drawable.mainplaybutton);
       // bitmap_restart=BitmapFactory.decodeResource(getResources(), R.drawable.brestart);
       // bitmap_exit=BitmapFactory.decodeResource(getResources(), R.drawable.bexit);
        bitmap_overgui =BitmapFactory.decodeResource(getResources(), R.drawable.gui_1);
        gLoop = new game_loop();
        gLoop.start();
        holder = getHolder();

            HightScore = Read_Score(context);

        tf =Typeface.createFromAsset(context.getAssets(),"Fonts/34_5214_COLLEGE.ttf");
        tf2 = Typeface.createFromAsset(context.getAssets(),"Fonts/font.TTF");
        tf3 = Typeface.createFromAsset(context.getAssets(),"Fonts/HEXAGON_.TTF");
        game_restart();

        holder.addCallback(new SurfaceHolder.Callback() {
            DisplayMetrics metrics = new DisplayMetrics();

            @SuppressLint("WrongCall")
            @Override

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Canvas canvas = holder.lockCanvas(null);
                onDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;

                game_pause = true;

            }

        });

    }


    boolean IsRuning = true;

    public class game_loop implements Runnable {

        Thread game_thread = null;

        game_loop() {


        }

        public void run() {
            Canvas canvas = null;
            while (IsRuning) {
          //      System.out.println("OUT: "+HightScore);
                while(game_pause) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }

                if (holder.getSurface().isValid()) {

                    try {
                        canvas = holder.lockCanvas(null);
                        root_pos = new int[]{canvas.getWidth() / 2, canvas.getHeight() / 2};
                        scrSize = new int []{canvas.getWidth() , canvas.getHeight() };
                        if (game_r_size <= 5.0)
                            game_r_size = canvas.getHeight() / 2.2f;
                        if (isGuiIngame == true)
                        {
                        if (isGame==1)
                       draw_game(canvas);
                        else if (isGame ==2)
                        DrawGame2(canvas);
                        //return;
                            }
                        else
                            draw_main(canvas);
                    } finally {
                        if (canvas != null)
                            holder.unlockCanvasAndPost(canvas);
                    }
                }

            }
        }

        public void start() {
            if (game_thread != null) return;
            game_thread = new Thread(this, "");
            game_thread.start();
            IsRuning = true;
        }

        public void stop() {
            IsRuning = false;

        }
    }





    private boolean check_x1(float[] p_pos, float[] p_pos2) {
        if (ball_pos[0]+15 > p_pos[0] && ball_pos[0]-15 < p_pos2[0]) return true;
        return false;
    }

    private boolean check_x2(float[] p_pos, float[] p_pos2) {
        if (ball_pos[0]-15 < p_pos[0] && ball_pos[0]+15 > p_pos2[0]) return true;
        return false;
    }

    private boolean check_y1(float[] p_pos, float[] p_pos2) {
        if (ball_pos[1]+15 >= p_pos[1] && ball_pos[1]-15 <= p_pos2[1]) return true;
        return false;
    }

    private boolean check_y2(float[] p_pos, float[] p_pos2) {
        if (ball_pos[1]-15 <= p_pos[1] && ball_pos[1] +15 >= p_pos2[1]) return true;
        return false;
    }

    private int check_touch_player() {
        float[] p_pos = new float[]{(float) (game_r_size * Math.cos(((float) player_rotate) / 180.0f * Math.PI)), (float) (game_r_size * Math.sin(player_rotate / 180.0f * Math.PI))};
        float[] p_pos2 = new float[]{(float) (game_r_size * Math.cos((float) (player_rotate + player_size) / 180.0f * Math.PI)), (float) (game_r_size * Math.sin((player_rotate + player_size) / 180.0f * Math.PI))};
        //   &&
        if (math_calc_dist(new float[]{0.0f, 0.0f}, new float[]{ball_pos[0], ball_pos[1]}) >= game_r_size - 40) {


            if (check_x1(p_pos, p_pos2) && check_y1(p_pos, p_pos2)) return 1;
            if (check_x1(p_pos, p_pos2) && check_y2(p_pos, p_pos2)) return 1;
            if (check_x2(p_pos, p_pos2) && check_y1(p_pos, p_pos2)) return 1;
            if (check_x2(p_pos, p_pos2) && check_y2(p_pos, p_pos2)) return 1;
            return 2;
        }
        //System.out.println("  " + ball_pos[0] + "  " + p_pos[0] + "");
        return -1;
    }

    public double math_calc_dist(float[] p1, float[] p2) {
        return Math.sqrt((p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]));
    }
    public boolean is_hightScore =false;
    private int mod(int x, int y)
    {
        int result = x % y;
        return result < 0? result + y : result;
    }
    public void func_calc_game() {
        if (player_alive==true) {
            ball_vec[0] += ball_vec[0] / 50.0f;
            ball_vec[1] += ball_vec[1] / 50.0f;
            ball_pos[0] += ball_vec[0];
            ball_pos[1] += ball_vec[1];
            int status1 = check_touch_player();
            if (is_add_score==true) return;
            if (status1 == 1) {


                if (is_super_sh == true )
                {

                    set_die();

                }
                else {
                    ball_vec[0] = -1 * (float) Math.cos(((float) (player_rotate+player_size/2)) / 180.0f * Math.PI) * (2 + azimut / 180+int_player_mhar);
                    ball_vec[1] = -1 * (float) Math.sin(((float) (player_rotate+player_size/2)) / 180.0f * Math.PI) * (2 + azimut / 180+int_player_mhar);

                    set_add_next();
                }
                is_super_sh = false;
                if (player_score>2)
                if (Math.floor(Math.random()*8) ==5 ) is_super_sh = true;

              if (mod(player_score,10)==0)
                    int_player_mhar+=1;

                snd_add_score.seekTo(0);
                snd_add_score.start();
            } else if (status1 == 2) {

                if (is_super_sh == true ) {
                    set_add_next();
                    ball_vec[0] *= -1 ;
                    ball_vec[1] *= -1 ;

                }
                else
                    set_die();
                is_super_sh = false;
            }
        }
        //  else if (status1==2)  System.out.println("false   ");

    }
    public void set_add_next()
    {
       is_draw_scoreeff = true;
        is_add_score = true;
        eff_data_score1 =0.0f;
        player_score+=1;
        Scale_first_show=80;
    }
    public void set_die()
    {
        reset_effect_die();
        issound3 = false;
        issound4 = false;
        Scale_first_show=200;
        _timeshow_end = System.currentTimeMillis();
        player_alive = false;
        is_hightScore = false;
        snd_boom.seekTo(0);
        snd_boom.start();
        game_over_gui_eff = 800;
        if (HightScore < player_score && player_score >1) {
            HightScore = player_score;
            is_hightScore = true;
            Write_Score(player_score, super.getContext());

        }
    }
    public float azimut;
    public float eff_data_score1 =0.0f;

    public void draw_eff_score(Canvas canvas)
    {

        Paint myPaint = new Paint();
        myPaint.setColor(Color.parseColor("#b56969"));
        myPaint.setStrokeWidth(40);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setAlpha(255-(int)eff_data_score1);
        canvas.drawCircle(root_pos[0], root_pos[1], (int) game_r_size+eff_data_score1, myPaint);
        eff_data_score1+=10;
        if (eff_data_score1>255)
        {
            eff_data_score1 =0.0f;
            is_draw_scoreeff=false;
            is_add_score = false;
        }
    }
    public int addScoreOver=0;
    private float tex_touch_eff =0.0f;
    private float tex_score_eff =0.0f;
    private int EffStart_main =0;
    private float effsize_1=0.0f;
    public void draw_main(Canvas canvas) {


        if (EffStart_main>0) EffStart_main-=20;
        if (EffStart_main<0) EffStart_main=0;
        canvas.drawColor(Color.parseColor("#EBE3CE"));
        Paint textPaint = new Paint(Color.RED);
        textPaint.setTextSize(180);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Style.FILL);
        textPaint.setTypeface(tf2);
        textPaint.setColor(Color.parseColor("#D47464"));
        canvas.drawText("Circle&Loop", root_pos[0] + EffStart_main,  400 , textPaint);
        Paint authorpaint = new Paint(Color.RED);
        authorpaint.setTextSize(30);
        authorpaint.setTextAlign(Paint.Align.LEFT);
        authorpaint.setStyle(Style.FILL);
        //textPaint.setTypeface(tf);
        authorpaint.setTypeface(tf3);
        authorpaint.setColor(Color.parseColor("#D47464"));
        canvas.drawText("AUTHOR:GIAY NHAP", (int)((float)EffStart_main/(float)root_pos[0]*50),  50 , authorpaint);


        Rect grect = new Rect();
        grect.left = 0;
        grect.top = 0;
        grect.right = bitmap_Playbut.getWidth();
        grect.bottom = bitmap_Playbut.getHeight();
      //  Rect drawrect = new Rect();
      //  drawrect.left= root_pos[0]-100;
      //  drawrect.right= root_pos[0]-100+200;
      //  drawrect.top =  root_pos[1]+100;
     //   drawrect.bottom =  root_pos[1]+100+200;
      //  canvas.drawBitmap(bitmap_Playbut, grect, drawrect,null);
        effsize_1+=0.1;
        textPaint.setTextSize(100+(int)(Math.cos(effsize_1)*10.0f));
        canvas.drawText("GAME 1 ", root_pos[0]-200,  root_pos[1]+200 , textPaint);

        canvas.drawText("GAME 2 ", root_pos[0]+200,  root_pos[1]+200 , textPaint);

    }
    public int game_over_gui_eff =0;
    public boolean issound4 =false;
    public void  DrawMenuGame(Canvas canvas)
    {
        if (game_over_gui_eff>0) game_over_gui_eff-=40;
        if (game_over_gui_eff<0) game_over_gui_eff=0;



        Rect grect = new Rect();


        DrawImage( canvas,bitmap_overgui , root_pos[0]-400 , root_pos[1]-400,800-game_over_gui_eff,800,null);



        Paint text_hi = new Paint(Color.RED);
        text_hi.setTextSize(50);
        text_hi.setTextAlign(Paint.Align.CENTER);
        text_hi.setStyle(Style.FILL);
        text_hi.setColor(Color.WHITE);
        text_hi.setTypeface(tf);
        if (issound3==false)
        {
            issound3 = true;
            snd_end.seekTo(0);
            snd_end.start();




        }
        if (game_over_gui_eff == 0) {

            if (addScoreOver<root_pos[0])
                addScoreOver += 10 + addScoreOver / 10;
            if ( issound4 == false)

            {
                issound4 = true;
                if (is_hightScore==true )
                {

                snd_eff_newhight.seekTo(0);
                snd_eff_newhight.start();
                 }
                else
                {
                    snd_eff_over.seekTo(0);
                    snd_eff_over.start();
                }
            }

            if (is_hightScore == true) {
                canvas.drawText("New Hight Score !!!", addScoreOver, root_pos[1] + 100, text_hi);
            } else
                canvas.drawText("Hight Score:" + HightScore, addScoreOver, root_pos[1] + 100, text_hi);
            canvas.drawText("Your Score:" + player_score, root_pos[0]*2-addScoreOver, root_pos[1] , text_hi);
        }

        //DrawImage( canvas,bitmap_restart ,root_pos[0]-85 , root_pos[1]+195,70,70,null);
      //  DrawImage( canvas,bitmap_exit , root_pos[0]+20,  root_pos[1]+195,70,70,null);



    }
    public void DrawImage(Canvas canvas,Bitmap bitmap,int x,int y,int width,int height,Paint paint)
    {
        Rect grect = new Rect();
        grect.left = 0;
        grect.top = 0;
        grect.right = bitmap.getWidth();
        grect.bottom = bitmap.getHeight();
        Rect drawrect = new Rect();



        drawrect.left= x;
        drawrect.right= x+width;
        drawrect.top =  y;
        drawrect.bottom =  y+height;
        canvas.drawBitmap(bitmap, grect, drawrect,paint);

    }
    boolean issound3=false;


    public void draw_game(Canvas canvas) {



        if (Scale_first_show>0)Scale_first_show-=5+Scale_first_show/50.0f;
        else Scale_first_show=0;
        canvas.drawColor(Color.parseColor("#EBE3CE"));

        Paint textPaint = new Paint(Color.RED);
        textPaint.setTextSize(180+((float)Scale_first_show)/20.0f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Style.FILL);
        textPaint.setTypeface(tf2);
        textPaint.setColor(Color.parseColor("#808080"));
        canvas.drawText(player_score +"", root_pos[0],  root_pos[1]+40 , textPaint);
        Paint myPaint = new Paint();
        myPaint.setColor(Color.parseColor("#D47464"));
            myPaint.setStrokeWidth(40);
        myPaint.setStyle(Paint.Style.STROKE);

        Paint paint2 = new Paint();
        paint2.setColor(Color.parseColor("#F6D262"));
        paint2.setStrokeWidth(30);
        paint2.setStyle(Paint.Style.STROKE);

        Paint paint3 = new Paint(Color.parseColor("#14FF00"));
        paint3.setStyle(Style.FILL);
        paint3.setColor(Color.parseColor("#FF851B"));
        if (is_draw_scoreeff==true)
        {

            draw_eff_score( canvas);
        }

        if (Math.abs(azimut) > 2.0f) player_rotate += azimut / 7.0f;
        canvas.drawCircle(root_pos[0], root_pos[1], (int) game_r_size+Scale_first_show, myPaint);
        gnexDrawArc(canvas, root_pos[0], root_pos[1], (int) game_r_size +Scale_first_show-20, (int) (player_rotate), (int) player_size, paint2);
        if (is_click_start == false) {
            Paint paint4 = new Paint();

            paint4.setStrokeWidth(2);
            paint4.setStyle(Style.STROKE);
            paint4.setColor(Color.parseColor("#AAAAAA"));
            paint4.setPathEffect(new CornerPathEffect(1));
            paint4.setAlpha(180);

            Path mPath;
            DashPathEffect mDashPathEffect;

            float[] intervals = new float[]{start_are_size / 4 - 10, 10.0f};
            float phase = 0;
            mPath = new Path();
            mDashPathEffect = new DashPathEffect(intervals, phase);
            paint4.setPathEffect(mDashPathEffect);
            paint4.setStyle(Style.FILL);

            paint4.setStrokeWidth(6);
            paint4.setAntiAlias(true);

            canvas.drawCircle((int) (canvas.getWidth() / 2), (int) (canvas.getHeight() / 2), start_are_size+Scale_first_show, paint4);
            start_are_size += 2 * start_effmo1;

            Paint text_over = new Paint(Color.RED);
            text_over.setTextSize(70+((float)Scale_first_show)/20.0f);

            text_over.setTextAlign(Paint.Align.CENTER);
            text_over.setStyle(Style.FILL);
            text_over.setColor(Color.WHITE);
            tex_touch_eff+=0.05;
            canvas.drawText("Touch To Start", root_pos[0]+(int)(Math.cos(tex_touch_eff)*10), 200+(int)(Math.sin(tex_touch_eff)*10), text_over);

            if (start_are_size > 120.0f || start_are_size < 50.0f) start_effmo1 *= -1;
        } else func_calc_game();
        if (player_alive==true) {
            if (is_super_sh==true ) paint3.setColor(Color.parseColor("#2B2B2B"));
            canvas.drawCircle(root_pos[0] + ball_pos[0], root_pos[1] + ball_pos[1], 20+Scale_first_show, paint3);
            tex_score_eff+=0.05;
        }
        else {
            if (System.currentTimeMillis() -_timeshow_end > 1500)
            {


                DrawMenuGame(canvas);

            }
            else  if (System.currentTimeMillis() -_timeshow_end > 1000) {

            }
            else if (System.currentTimeMillis() -_timeshow_end <500) {
                float cur = System.currentTimeMillis() - _timeshow_end;
                Paint paint4 = new Paint();
                paint4.setStyle(Style.FILL);
                paint4.setColor(Color.WHITE);
                paint4.setAlpha(255 - (int) (cur / 500.0f * 255.0f));
                canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), paint4);
                tex_touch_eff =0.0f;

            }
            Draweffect_die(canvas);
        }
    }
    int num_ent=5;
    float player_effect = 0.0f;
    public float [] player_post = new float[]{ 0.0f,0.0f};
    public float player_vec1=0.0f;
    public  int player_ac=1;

    float [][]g2pos_ent = new float[num_ent][];
    float [][]g2vec_ent = new float[num_ent][];
    float []  player_effect_p = new float[]{0.0f,9999.0f};
    int []  gtype_ent = new int[num_ent];
    float ent_size = 50;
    public void reset_allent()
    {
    //    g2pos_ent = new float[10][];
        for (int i=0;i<num_ent;i++) {

            g2pos_ent[i] = new float[]{0.0f, -200.0f};
            g2vec_ent[i] = new float[]{0.0f,1.0f};
            gtype_ent[i] = 0;
        }
        for (int i=0;i<num_ent;i++) {

            reset_ent(i);
        }
        reset_effect_die();

    }
    public int current_reset=0;
    public void reset_ent(int ident)
    {
        int last = current_reset;
        if (last<0) {
            if (g2pos_ent[num_ent-1][1] > -200.0f) last = num_ent-1;


        }
        gtype_ent[ident]=0;
        if (Math.floor(Math.random()*25)==5) {
            gtype_ent[ident]=1;

        }
        if ( gtype_ent[ident] !=0)
            g2pos_ent[ident][1] = g2pos_ent[last][1]-200.0f-(float)Math.random()*200.0f ;
            else
         g2pos_ent[ident][1] = g2pos_ent[last][1]-300.0f-(float)Math.random()*300.0f ;
        int als1 = -1;
        if (Math.random()<0.5) als1=1;
        g2pos_ent[ident][0] =(float)Math.random()*280*als1;
        g2vec_ent[ident] = new float[]{0.0f,1.0f};

        if (Math.floor(Math.random()*8)==5)
        {
            g2vec_ent[ident] = new float[]{(float)Math.random()*1.0f,(float)Math.random()};
            if (g2vec_ent[ident][1]<0.6f) g2vec_ent[ident][1]+=0.4f;
        }


        current_reset = ident;

    }
    public float gent_speed=5.0f;
    public float player_speed=5.0f;
    public void calc_ent_sts()
    {
        player_post[1] = scrSize[1]-100;
        for (int i=0;i<num_ent;i++)
        {
            g2pos_ent[i][1]+=g2vec_ent[i][1]*(gent_speed+player_speed);
            g2pos_ent[i][0]+=g2vec_ent[i][0]*(gent_speed+player_speed);
            if ( g2pos_ent[i][1]  >scrSize[1]+100) {
                if (player_alive == true) {
                    player_score += 1;
                    if (mod(player_score,10)==0) player_speed+=1;
                    if (player_speed>20) player_speed=20;
                }
                reset_ent(i);
            }

            if (  g2pos_ent[i][0]<=-280|| g2pos_ent[i][0]>=+280) {
                g2vec_ent[i][0] *= -1;
                if (  g2pos_ent[i][0]<=-280) g2pos_ent[i][0] = -270;
                if (  g2pos_ent[i][0]>=280) g2pos_ent[i][0] = 270;
            }

            for (int j=0;j<num_ent;j++)
            {
                if (j!=i)
                {
                    if (math_calc_dist(g2pos_ent[i],g2pos_ent[j])<ent_size*2) {
                        g2vec_ent[i][0]= (g2vec_ent[i][0]+ g2vec_ent[j][0])/2;
                        g2vec_ent[j][0]=( g2vec_ent[j][0]+g2vec_ent[i][0])/2;
                        if (g2vec_ent[j][0]>1.0f||g2vec_ent[j][0]<1.0) g2vec_ent[j][0]=1.0f;
                        if (g2vec_ent[i][0]>1.0f||g2vec_ent[i][0]<1.0) g2vec_ent[i][0]=1.0f;
                    }

                }

            }

            if (player_alive == true)
            if (math_calc_dist(g2pos_ent[i],player_post)<ent_size+20)
            {
                if (gtype_ent [i]==0) {
                    if (player_ac <= 0) {
                        player_alive = false;
                        _timeshow_end = System.currentTimeMillis();
                        is_hightScore = false;
                        if (HightScore < player_score) {
                            HightScore = player_score;
                            is_hightScore = true;

                            Write_Score(player_score, super.getContext());
                        }
                        snd_boom.seekTo(0);
                        snd_boom.start();
                        snd_boom.seekTo(0);
                        snd_boom.start();
                    } else {


                        effentpaint.setColor(Color.parseColor("#B7695C"));
                          is_eff =true;
                        effkillpos= new int[]{(int)g2pos_ent[i][0],(int)g2pos_ent[i][1]};
                        effkilszie =0;
                        player_ac -= 1;
                        snd_click.seekTo(0);
                        snd_click.start();

                        reset_ent(i);

                    }
                }else if (gtype_ent[i]==1)
                {
                    player_ac+=1;
                    effentpaint.setColor(Color.parseColor("#51A39D"));
                     is_eff =true;
                    effkillpos= new int[]{(int)g2pos_ent[i][0],(int)g2pos_ent[i][1]};
                    effkilszie =0;
                    snd_eff_t1.seekTo(0);
                    snd_eff_t1.start();
                    reset_ent(i);
                }

            }
        }

    }
     Paint effentpaint = new Paint();
     int effkilszie = 0;
     boolean is_eff =false;
     int[] effkillpos= new int[2];

    public void effkillent(Canvas canvas)
    {
        if (is_eff==false) return;

        effentpaint.setStyle(Style.STROKE);
        effentpaint.setStrokeWidth(30);



        if (effkilszie<255) effkilszie+=10;
        if (effkilszie>255)  {
            is_eff = false;
            effkilszie=0;
            return;
        }

        effentpaint.setAlpha(255-effkilszie);
        canvas.drawCircle(root_pos[0]+effkillpos[0], effkillpos[1],effkilszie+50, effentpaint);
    }

    public void DrawGame2(Canvas canvas)
    {

        canvas.drawColor(Color.parseColor("#7B8D8E"));

        Paint paint3 = new Paint();
        paint3.setStyle(Style.FILL);
        paint3.setColor(Color.parseColor("#F2EDD8"));
        canvas.drawRect(root_pos[0]-340,
                0,
                root_pos[0]+340,
                scrSize[1],paint3);
        calc_ent_sts();
        Paint text1 = new Paint();
        text1.setTextSize(50);
        text1.setTextAlign(Paint.Align.CENTER);
        text1.setStyle(Style.FILL);
        text1.setTypeface(tf);
        text1.setColor(Color.parseColor("#ffffff"));

        Paint text2 = new Paint();
        text2.setTextSize(40);
        text2.setTextAlign(Paint.Align.LEFT);
        text2.setStyle(Style.FILL);
        text2.setTypeface(tf);
        text2.setColor(Color.parseColor("#808080"));
        canvas.drawText("AC: "+player_ac,root_pos[0]-330,40, text2);
        canvas.drawText("Score: "+player_score,root_pos[0]-330,80, text2);
        Paint pfire = new Paint();
        pfire.setColor(Color.parseColor("#D47464"));
        pfire.setStrokeWidth(50);
        pfire.setStyle(Paint.Style.STROKE);


        for (int i=0;i<num_ent;i++)
        {
            if (gtype_ent[i]==0) {
                paint3.setColor(Color.parseColor("#B7695C"));

                canvas.drawCircle(root_pos[0] + g2pos_ent[i][0], g2pos_ent[i][1], (int) ent_size, paint3);
            }
            else if (gtype_ent[i]==1)
            {
                paint3.setColor(Color.parseColor("#51A39D"));

                canvas.drawCircle(root_pos[0] + g2pos_ent[i][0], g2pos_ent[i][1], (int) ent_size, paint3);


                canvas.drawText("AC", root_pos[0] + g2pos_ent[i][0], g2pos_ent[i][1]+20, text1);
            }
            else if (gtype_ent[i]==2)
            {
                paint3.setColor(Color.parseColor("#CDBB79"));

                canvas.drawCircle(root_pos[0] + g2pos_ent[i][0], g2pos_ent[i][1], (int) ent_size, paint3);


                canvas.drawText("AR", root_pos[0] + g2pos_ent[i][0], g2pos_ent[i][1]+20, text1);
            }
        }

        if (player_alive==true) {
            int effsize = (int)(Math.cos(player_effect)*190);


                if ( player_post[0]>-310&&player_post[0]<+310) {
                    if (Math.abs(azimut) > 2.0f) {

                    player_vec1+= azimut / 20.0f;
                    }
                }
                else {

                    player_vec1 *= -1;


            }
            if (player_vec1>10.0||player_vec1<-10.0)
                player_vec1 = player_vec1/Math.abs(player_vec1)*10.0f;


                    player_post[0] -= player_vec1;
            player_effect_p[0]=player_post[0];
            player_effect_p[1]+=10;
            if (player_effect_p[1] >scrSize[1]) player_effect_p[1] = player_post[1];

            Paint myPaint = new Paint();
            myPaint.setColor(Color.parseColor("#D47464"));
            myPaint.setStrokeWidth(20);
            myPaint.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(root_pos[0]+(int)player_post[0], scrSize[1]-100,20, myPaint);
            myPaint.setStrokeWidth(20);
            myPaint.setColor(Color.parseColor("#F1A94E"));


            gnexDrawArc(canvas, root_pos[0]+(int)player_post[0],(int) player_post[1] ,20, 90+effsize, 50, myPaint);
            player_effect+=0.1f;

            myPaint.setAlpha(255-(int)((player_effect_p[1]-(scrSize[1]-200))/200.0f*200));
            canvas.drawCircle(root_pos[0]+ player_effect_p[0],  player_effect_p[1],20, myPaint);


        }
        else
        {
            if (player_speed>0) player_speed-=0.5;
            Draweffect_die2(canvas);
            if (System.currentTimeMillis() -_timeshow_end > 1500)
            {


                DrawMenuGame(canvas);

            }
            else if (System.currentTimeMillis() -_timeshow_end <500) {
                float cur = System.currentTimeMillis() - _timeshow_end;
                Paint paint4 = new Paint();
                paint4.setStyle(Style.FILL);
                paint4.setColor(Color.WHITE);
                paint4.setAlpha(255 - (int) (cur / 500.0f * 255.0f));
                canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), paint4);
                tex_touch_eff =0.0f;

            }
        }
        effkillent( canvas);

    }






    private float [][]effect_die_d = new float[36][];
    public void reset_effect_die()
    {
        for (int i=0;i<36;i++)
        {
            effect_die_d[i]=new float[]{(float)Math.random()*50,(float)Math.random()*50};
        }

    }

    public void Draweffect_die(Canvas canvas)
    {
        Paint paint3 = new Paint();
        paint3.setStyle(Style.FILL);
        paint3.setColor(Color.parseColor("#F1A94E"));
        for (int i=0;i<36;i++)
        {
            effect_die_d[i][0]+=Math.cos(((float)(i*10))/180.0f*Math.PI)*3+effect_die_d[i][0]/100;
            effect_die_d[i][1]+=Math.sin(((float)(i*10))/180.0f*Math.PI)*3+effect_die_d[i][1]/100;
            canvas.drawRect(root_pos[0]+ ball_pos[0]+effect_die_d[i][0]+0.0f,
                    root_pos[1]+ball_pos[1]+effect_die_d[i][1]+0.0f,
                    root_pos[0]+effect_die_d[i][0]+ball_pos[0]+10.0f,
                    root_pos[1]+effect_die_d[i][1]+ball_pos[1]+10.0f,paint3);
        }
    }

    public void Draweffect_die2(Canvas canvas)
    {
        Paint paint3 = new Paint();
        paint3.setStyle(Style.FILL);
        paint3.setColor(Color.parseColor("#E45641"));
        for (int i=0;i<36;i++)
        {
            effect_die_d[i][0]+=Math.cos(((float)(i*10))/180.0f*Math.PI)*3+effect_die_d[i][0]/100;
            effect_die_d[i][1]+=Math.sin(((float)(i*10))/180.0f*Math.PI)*3+effect_die_d[i][1]/100;
            canvas.drawRect(root_pos[0]+ player_post[0]+effect_die_d[i][0]+0.0f,
                    player_post[1]+effect_die_d[i][1]+0.0f,
                    root_pos[0]+effect_die_d[i][0]+player_post[0]+10.0f,
                    effect_die_d[i][1]+player_post[1]+10.0f,paint3);
        }
    }


    public void gnexDrawArc(Canvas canvas, int x, int y, int radius, int sangle, int swangle, Paint paint) {

        RectF oval = new RectF();
        float left = x - radius;
        float top = y - radius;
        float right = x + radius;
        float bottom = y + radius;
        oval.set(left, top, right, bottom);
        canvas.drawArc(oval, sangle, swangle, false, paint);
        // canvas.drawLine(x,y,(int)(x+radius*Math.cos(sangle/180.0f*Math.PI)),(int)(y+radius*Math.sin(sangle/180.0f*Math.PI)),paint);
        // canvas.drawLine(x,y,(int)(x+radius*Math.cos((sangle+swangle)/180.0f*Math.PI)),(int)(y+radius*Math.sin((sangle+swangle)/180.0f*Math.PI)),paint);
    }

    @Override
    public void onDraw(Canvas canvas) {

        //draw_game(canvas);

        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x =0;
        float y =0;
        x = e.getX();
        y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (game_pause==true)
                {
                    game_pause = false;
                    return true;
                }
                if (isGuiIngame== true) {

                        if (player_alive == false && (System.currentTimeMillis() - _timeshow_end > 2000)) {

                            if (check_rect((int) x, (int) y, root_pos[0]-85 , root_pos[1]+195, 70, 70) == true) {
                                player_alive = true;
                                snd_click.seekTo(0);
                                snd_click.start();
                                if (isGame==1)
                                game_restart();
                                else if (isGame==2) game2_restart();
                            } else if (check_rect((int) x, (int) y,root_pos[0]+20,  root_pos[1]+195, 70, 70) == true) {
                                player_alive = true;
                                if (isGame==1)
                                    game_restart();
                                else if (isGame==2) game2_restart();
                                snd_click.seekTo(0);
                                snd_click.start();
                                isGuiIngame = false;

                            }



                        } else {
                            snd_click.seekTo(0);
                            snd_click.start();
                            is_click_start = true;
                        }


                        break;
                    }
                    else
                    {


                       if(check_rect((int)x,(int)y, root_pos[0]-300,root_pos[1]+100,200,200)==true)
                       {
                           isGame=1;
                               game_restart();

                           snd_click.seekTo(0);
                           snd_click.start();
                           snd_end.seekTo(0);
                           snd_end.start();
                           HightScore = Read_Score(super.getContext());
                           isGuiIngame = true;

                       }
                        else if(check_rect((int)x,(int)y, root_pos[0]+200,root_pos[1]+100,200,200)==true) {
                           isGame=2;
                           game2_restart();
                           snd_click.seekTo(0);
                           snd_click.start();
                           snd_end.seekTo(0);
                           snd_end.start();
                           HightScore = Read_Score(super.getContext());
                           isGuiIngame = true;

                       }
                    }
               }

                return true;

    }
    private boolean check_rect(int inx,int iny,int rcx,int rcy,int rcw,int rch)
    {

        if (inx > rcx && inx < rcx+rcw && iny > rcy && iny< rcy+rch) return true;
        return false;

    }

    public void Write_Score(int score,Context context)  {
        File gfile  = new File(context.getFilesDir(),"giaynhap_game_save.dat");

          if (isGame ==2)
            gfile = new File(context.getFilesDir(),"giaynhap_game_save2.dat");

        try {
        FileOutputStream stream = new FileOutputStream(gfile);

            try {
                stream.write((score + "").getBytes());
                stream.close();
            } catch (IOException e) {

            }

        }
        catch (FileNotFoundException e) {

        }
    }
    public int Read_Score(Context context){

        File gfile= new File(context.getFilesDir(),"giaynhap_game_save.dat");
        if (isGame ==2)
            gfile = new File(context.getFilesDir(),"giaynhap_game_save2.dat");

        int length = (int) gfile.length();
        if (length<=0) return 0;
        byte[] bytes = new byte[length];
        try {
            FileInputStream in = new FileInputStream(gfile);
            try {
                in.read(bytes);
                in.close();
            } catch (IOException e) {

            }
        }
        catch (FileNotFoundException e) {

        }
        String contents = new String(bytes);
        if (contents!="")
        return Integer.parseInt(contents);
        else return 0;
    }
}