package com.plan9.naseemdev.naseem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import BussinessObjects.Show_Test_BO.Complete_Test_BO;
import BussinessObjects.Test_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.ListUtils;
import Model.Session;

public class TakeTest extends AppCompatActivity {

    private Test_BO test_bo;
    private int id;
    private String URL;
    private TextView attempt_time;
    private Session session;
    private ListView list_text_questions, list_paragraph_questions, list_boolean_questions;
    private ExpandableListView list_single_choice_questions, list_multi_choice_questions;
    private LinearLayout reload, load_test;
    private TextView label_text_questions, label_single_choice_questions, label_multi_choice_questions, label_paragraph_questions, label_boolean_questions;
    private View line_text_question, line_single_choice_questions, line_multi_choice_questions, line_paragraph_questions, line_boolean_questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        test_bo = (Test_BO) bundle.getSerializable("test");
        setTitle(test_bo.getName());

        session = new Session(getApplicationContext());
        reload = (LinearLayout)findViewById(R.id.reload_Test_Taken_Layout);
        reload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                hide();
                reload.setVisibility(View.GONE);
                load_test.setVisibility(View.VISIBLE);
                loadTest();
            }
        });
        load_test = (LinearLayout)findViewById(R.id.load_test);

        list_text_questions = (ListView)findViewById(R.id.list_text_questions);
        list_text_questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}
        });

        list_single_choice_questions = (ExpandableListView) findViewById(R.id.list_single_choice_questions);
        list_single_choice_questions.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                list_single_choice_questions.expandGroup(i);
                return true;
            }
        });

        list_multi_choice_questions = (ExpandableListView)findViewById(R.id.list_multi_choice_questions);
        list_multi_choice_questions.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                list_multi_choice_questions.expandGroup(i);
                return true;
            }
        });

        list_paragraph_questions = (ListView)findViewById(R.id.list_paragraph_questions);
        list_paragraph_questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}
        });

        list_boolean_questions = (ListView)findViewById(R.id.list_boolean_questions);
        list_boolean_questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}
        });

        label_text_questions = (TextView)findViewById(R.id.label_text_questions);
        line_text_question = (View)findViewById(R.id.line_text_question);

        label_single_choice_questions = (TextView)findViewById(R.id.label_single_choice_questions);
        line_single_choice_questions = (View)findViewById(R.id.line_single_choice_question);

        label_multi_choice_questions = (TextView)findViewById(R.id.label_multi_choice_questions);
        line_multi_choice_questions = (View)findViewById(R.id.line_multi_choice_question);

        label_paragraph_questions = (TextView)findViewById(R.id.label_paragraph_questions);
        line_paragraph_questions = (View)findViewById(R.id.line_paragraph_question);

        label_boolean_questions = (TextView)findViewById(R.id.label_boolean_questions);
        line_boolean_questions = (View)findViewById(R.id.line_boolean_question);

        hide();
        load_test.setVisibility(View.VISIBLE);
        reload.setVisibility(View.GONE);
        loadTest();
    }

    public void loadTest() {
        URL = Constants.URL_Start_Test + session.getAuthenticationToken();
        id = session.getUserId();
        if (session.getAuthenticationToken().equals("") || session.getAuthenticationToken().equals(null) || session.getAuthenticationToken().equals("null")) {
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent it = new Intent(this, SignUpSignIn.class);
            this.finish();
            startActivity(it);
        }
        else {
            if (isConnected()) {
                getTest();
            }
            else{
                hide();
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        attempt_time = new TextView(this);
        attempt_time.setTextColor(getResources().getColor(R.color.color_white));
        attempt_time.setPadding(5, 5, 5, 5);
        attempt_time.setTypeface(null, Typeface.BOLD);
        attempt_time.setTextSize(18);
        menu.add(0, 1, 1, "").setActionView(attempt_time).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        int time = Integer.parseInt(test_bo.getAttempt_time())*60*1000;
        new CountDownTimer(time, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                attempt_time.setText(""+String.format("%d : %d", TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                attempt_time.setText("");
            }
        }.start();
        return true;
    }

    private boolean isConnected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        else
            connected = false;
        return  connected;
    }


    public void getTest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            if(s.equals("-1")){
                                session.destroy_session();
                                Intent it = new Intent(getApplicationContext(), SignUpSignIn.class);
                                finish();
                                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                startActivity(it);
                                return;
                            }
                            if(s.equals("-2")){
                                hide();
                                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            JsonParsor js = new JsonParsor();
                            Complete_Test_BO test_object = js.parseStartTest(s.toString());
                            if(test_object != null){
                                Toast.makeText(getApplicationContext(), test_object.getText_questions().size() + " Text Questions Got", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), test_object.getSingleChoiceQuestions().size() + " Single Choice Questions Got", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), test_object.getMultipleChoiceQuestions().size() + " Multi Choice Questions Got", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), test_object.getParagraphQuestions().size() + " Paragraph Questions Got", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), test_object.getBooleanQuestions().size() + " Boolean Questions Got", Toast.LENGTH_LONG).show();
                                /*if(test_object.getText_questions().size() > 0 ){
                                    Show_Test_Custom_Text_Questions_Adapter adapter = new Show_Test_Custom_Text_Questions_Adapter(getApplicationContext(), test_object.getText_questions(), ShowTest.this);
                                    list_text_questions.setAdapter(adapter);
                                    ListUtils.setDynamicHeight(list_text_questions);
                                    list_text_questions.setVisibility(View.VISIBLE);
                                }
                                else
                                    list_text_questions.setVisibility(View.GONE);
                                if(test_object.getSingleChoiceQuestions().size() > 0){
                                    Show_Test_Custom_Single_Choice_Question_Adapter adapter = new Show_Test_Custom_Single_Choice_Question_Adapter(getApplicationContext(), test_object.getSingleChoiceQuestions(), ShowTest.this);
                                    list_single_choice_questions.setAdapter(adapter);
                                    int size = test_object.getSingleChoiceQuestions().size();
                                    for (int i=0; i<size; i++) {
                                        list_single_choice_questions.expandGroup(i);
                                    }
                                    list_single_choice_questions.setVisibility(View.VISIBLE);
                                    list_single_choice_questions.setGroupIndicator(null);
                                    ListUtils.setDynamicHeightE(list_single_choice_questions);
                                }
                                else
                                    list_single_choice_questions.setVisibility(View.GONE);
                                if(test_object.getMultipleChoiceQuestions().size() > 0){
                                    Show_Test_Custom_Multi_Choice_Question_Adapter adapter = new Show_Test_Custom_Multi_Choice_Question_Adapter(getApplicationContext(), test_object.getMultipleChoiceQuestions(), ShowTest.this);
                                    list_multi_choice_questions.setAdapter(adapter);
                                    int size = test_object.getMultipleChoiceQuestions().size();
                                    for(int i=0; i<size; i++){
                                        list_multi_choice_questions.expandGroup(i);
                                    }
                                    list_multi_choice_questions.setVisibility(View.VISIBLE);
                                    list_multi_choice_questions.setGroupIndicator(null);
                                    ListUtils.setDynamicHeightE(list_multi_choice_questions);
                                }
                                else
                                    list_multi_choice_questions.setVisibility(View.GONE);

                                if(test_object.getParagraphQuestions().size() > 0){
                                    Show_Test_Custom_Paragraph_Questions_Adapter adapter = new Show_Test_Custom_Paragraph_Questions_Adapter(getApplicationContext(), test_object.getParagraphQuestions(), ShowTest.this);
                                    list_paragraph_questions.setAdapter(adapter);
                                    list_paragraph_questions.setVisibility(View.VISIBLE);
                                    ListUtils.setDynamicHeight(list_paragraph_questions);
                                }
                                else
                                    list_paragraph_questions.setVisibility(View.GONE);

                                if(test_object.getBooleanQuestions().size() > 0){
                                    Show_Test_Custom_Boolean_Questions_Adapter adapter = new Show_Test_Custom_Boolean_Questions_Adapter(getApplicationContext(), test_object.getBooleanQuestions(), ShowTest.this);
                                    list_boolean_questions.setAdapter(adapter);
                                    list_boolean_questions.setVisibility(View.VISIBLE);
                                    ListUtils.setDynamicHeight(list_boolean_questions);
                                }
                                else
                                    list_boolean_questions.setVisibility(View.GONE);
                                */
                                show();
                            }
                            else{
                                hide();
                                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Test, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            load_test.setVisibility(View.GONE);
                        }catch (Exception e){
                            hide();
                            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error + e.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hide();
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(id));
                map.put(Constants.User_Key_Test_Id,String.valueOf(test_bo.getId()));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void hide(){
        reload.setVisibility(View.VISIBLE);
        load_test.setVisibility(View.GONE);

        list_text_questions.setVisibility(View.GONE);
        list_single_choice_questions.setVisibility(View.GONE);
        list_multi_choice_questions.setVisibility(View.GONE);
        list_paragraph_questions.setVisibility(View.GONE);
        list_boolean_questions.setVisibility(View.GONE);

        label_text_questions.setVisibility(View.GONE);
        label_single_choice_questions.setVisibility(View.GONE);
        label_multi_choice_questions.setVisibility(View.GONE);
        label_paragraph_questions.setVisibility(View.GONE);
        label_boolean_questions.setVisibility(View.GONE);
        line_text_question.setVisibility(View.GONE);
        line_single_choice_questions.setVisibility(View.GONE);
        line_multi_choice_questions.setVisibility(View.GONE);
        line_paragraph_questions.setVisibility(View.GONE);
        line_boolean_questions.setVisibility(View.GONE);
    }

    private void show(){
        label_text_questions.setVisibility(View.VISIBLE);
        label_single_choice_questions.setVisibility(View.VISIBLE);
        label_multi_choice_questions.setVisibility(View.VISIBLE);
        label_paragraph_questions.setVisibility(View.VISIBLE);
        label_boolean_questions.setVisibility(View.VISIBLE);
        line_text_question.setVisibility(View.VISIBLE);
        line_single_choice_questions.setVisibility(View.VISIBLE);
        line_multi_choice_questions.setVisibility(View.VISIBLE);
        line_paragraph_questions.setVisibility(View.VISIBLE);
        line_boolean_questions.setVisibility(View.VISIBLE);
    }
 }
