package edu.plan9.naseemdev.naseem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import BussinessObjects.Show_Test_BO.Complete_Test_BO;
import BussinessObjects.Show_Test_BO.Image_Questions;
import BussinessObjects.Show_Test_BO.Marks;
import BussinessObjects.Test_BO;
import Model.Constants;
import Model.Student.Show_Test_Adapters.Show_Test_Custom_Boolean_Questions_Adapter;
import Model.Student.Show_Test_Adapters.Show_Test_Custom_Image_Questions_Adapter;
import Model.Student.Show_Test_Adapters.Show_Test_Custom_Multi_Choice_Question_Adapter;
import Model.Student.Show_Test_Adapters.Show_Test_Custom_Paragraph_Questions_Adapter;
import Model.Student.Show_Test_Adapters.Show_Test_Custom_Single_Choice_Question_Adapter;
import Model.Student.Show_Test_Adapters.Show_Test_Custom_Text_Questions_Adapter;
import Model.JsonParsor;
import Model.ListUtils;
import Model.Session;
import Model.Student.Start_Test_Adapters.Take_Test_Image_Questions_Adapter;

public class ShowTest extends AppCompatActivity {

    private Test_BO test_bo;
    private String URL;
    private int id;
    private Session session;
    private ListView list_text_questions, list_paragraph_questions, list_boolean_questions , list_image_questions;
    private ExpandableListView list_single_choice_questions, list_multi_choice_questions;
    private LinearLayout reload, load_test;
    private TextView label_text_questions, label_single_choice_questions, label_multi_choice_questions, label_paragraph_questions, label_boolean_questions, obtained_marks, total_marks;
    private View line_text_question, line_single_choice_questions, line_multi_choice_questions, line_paragraph_questions, line_boolean_questions;
    private int total;
    private double obtained;
    String student_id;
    Bundle bundle;
    TransferUtility transferUtility;
    AmazonS3Client s3Client;
    Complete_Test_BO test_object;
    private Set<String> set_text_questions, set_single_choice_questions, set_multi_choice_questions, set_paragraph_questions, set_boolean_questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test);
        Intent it = getIntent();
        bundle = it.getExtras();
        test_bo = (Test_BO) bundle.getSerializable("test");
        if(bundle.get("student_id") != null) {
            student_id = bundle.getString("student_id");
        }
        setTitle(test_bo.getName());


        set_text_questions = new HashSet<String>();
        set_single_choice_questions = new HashSet<String>();
        set_multi_choice_questions = new HashSet<String>();
        set_paragraph_questions = new HashSet<String>();
        set_boolean_questions = new HashSet<String>();

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

        list_image_questions = (ListView)findViewById(R.id.list_image_questions);
        list_image_questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
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

        obtained_marks = (TextView)findViewById(R.id.obtained_marks);
        obtained = 0;
        total_marks = (TextView)findViewById(R.id.total_marks);
        total = 0;

        hide();
        load_test.setVisibility(View.VISIBLE);
        reload.setVisibility(View.GONE);
        loadTest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:{
                this.finish();
                break;
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void loadTest() {
        if(bundle.get("student_id") != null) {
            URL = Constants.URL_TakenTest + session.getAuthenticationToken();
        }
        else{
            URL = Constants.URL_ShowTest + session.getAuthenticationToken();
        }
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
                getShowTest();
            }
            else{
                hide();
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public void getShowTest(){
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
                            test_object = js.parseShowTest(s.toString());
                            show();
                            if(test_object.getText_questions().size() == 0){
                                label_text_questions.setVisibility(View.GONE);
                                line_text_question.setVisibility(View.GONE);
                            }
                            if(test_object.getSingleChoiceQuestions().size() == 0){
                                label_single_choice_questions.setVisibility(View.GONE);
                                line_single_choice_questions.setVisibility(View.GONE);
                            }
                            if(test_object.getMultipleChoiceQuestions().size() == 0){
                                label_multi_choice_questions.setVisibility(View.GONE);
                                line_multi_choice_questions.setVisibility(View.GONE);
                            }
                            if(test_object.getParagraphQuestions().size() == 0){
                                label_paragraph_questions.setVisibility(View.GONE);
                                line_paragraph_questions.setVisibility(View.GONE);
                            }
                            if(test_object.getBooleanQuestions().size() == 0){
                                label_boolean_questions.setVisibility(View.GONE);
                                line_boolean_questions.setVisibility(View.GONE);
                            }

                            if(test_object != null){
                                if(test_object.getText_questions().size() > 0 ){
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

                              /*  if(test_object.getImagesQuestions().size() > 0){
                                    credentialsProvider();
                                    transferUtility = new TransferUtility(s3Client, getApplicationContext());
                                    File file;
                                    for(int i=0 ; i < test_object.getImagesQuestions().size() ; i++){
                                        file = new File(Environment.getExternalStorageDirectory().toString() + "/" + test_object.getImagesQuestions().get(i).getImageKey());
                                        test_object.getImagesQuestions().get(i).setImage(file);
                                        TransferObserver observer = transferUtility.download(
                                                "naseemedu/uploads",     /* The bucket to upload to */
                                  /*              test_object.getImagesQuestions().get(i).getImageKey(),    /* The key for the uploaded object */
                                    /*            test_object.getImagesQuestions().get(i).getImage()       /* The file where the data to upload exists */
                                      /*  );
                                        //test_object.getImagesQuestions().get(i).setImage(file);
                                        observer.setTransferListener(new TransferListener() {
                                            @Override
                                            public void onStateChanged(int id, TransferState state) {
                                                if(state.toString() == "COMPLETED"){
                                                    //Toast.makeText(getApplicationContext() , "Successed" , Toast.LENGTH_LONG).show();
                                                    //getShowTest();
                                                    //save_test(j);
                                                    setImageQuestions(test_object.getImagesQuestions());
                                                }
                                            }

                                            @Override
                                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                                            }

                                            @Override
                                            public void onError(int id, Exception ex) {

                                            }
                                        });
                                        //transferObserverListener(observer);
                                    }

                                    Show_Test_Custom_Image_Questions_Adapter adapter = new Show_Test_Custom_Image_Questions_Adapter(getApplicationContext(), test_object.getImagesQuestions(), ShowTest.this);
                                    list_image_questions.setAdapter(adapter);
                                    list_image_questions.setVisibility(View.VISIBLE);
                                    ListUtils.setDynamicHeight(list_image_questions);
                                }
                                else
                                    list_image_questions.setVisibility(View.GONE);*/

                                //show();
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
                map.put("testid",String.valueOf(test_bo.getId()));
                if(URL.equals(Constants.URL_TakenTest + session.getAuthenticationToken())) {
                    map.put("student_id", student_id);
                }
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    public void setTextQuestionsMarks(Marks marks){
        if(!set_text_questions.contains(""+marks.getId())){
            total = total + marks.getTotal();
            obtained = obtained + marks.getObtained();
            total_marks.setText("Total Marks: " + total);
            obtained_marks.setText("Obtained Marks: " + obtained);
            set_text_questions.add(""+marks.getId());
        }
    }

    public void setSingleChoiceQuestionSMarks(Marks marks){
        if(!set_single_choice_questions.contains(""+marks.getId())){
            total = total + marks.getTotal();
            obtained = obtained + marks.getObtained();
            total_marks.setText("Total Marks: " + total);
            obtained_marks.setText("Obtained Marks: " + obtained);
            set_single_choice_questions.add(""+marks.getId());
        }
    }

    public void setMultiChoiceQuestionSMarks(Marks marks){
        if(!set_multi_choice_questions.contains(""+marks.getId())){
            total = total + marks.getTotal();
            obtained = obtained + marks.getObtained();
            total_marks.setText("Total Marks: " + total);
            obtained_marks.setText("Obtained Marks: " + obtained);
            set_multi_choice_questions.add(""+marks.getId());
        }
    }

    public void setParagraphQuestionsMarks(Marks marks){
        if(!set_paragraph_questions.contains(""+marks.getId())){
            total = total + marks.getTotal();
            obtained = obtained + marks.getObtained();
            total_marks.setText("Total Marks: " + total);
            obtained_marks.setText("Obtained Marks: " + obtained);
            set_paragraph_questions.add(""+marks.getId());
        }
    }

    public void setBooleanQuestionsMarks(Marks marks){
        if(!set_boolean_questions.contains(""+marks.getId())){
            total = total + marks.getTotal();
            obtained = obtained + marks.getObtained();
            total_marks.setText("Total Marks: " + total);
            obtained_marks.setText("Obtained Marks: " + obtained);
            set_boolean_questions.add(""+marks.getId());
        }
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
        obtained_marks.setVisibility(View.GONE);
        total_marks.setVisibility(View.GONE);
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
        obtained_marks.setVisibility(View.VISIBLE);
        obtained_marks.setText("Obtained Marks: "+obtained);
        total_marks.setVisibility(View.VISIBLE);
        total_marks.setText("Total Marks: "+total);
    }

    public void credentialsProvider(){

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:687a499c-965c-4185-9b6a-8e72af76cf36", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        setAmazonS3Client(credentialsProvider);
    }

    public void setAmazonS3Client(CognitoCachingCredentialsProvider credentialsProvider){

        // Create an S3 client
        s3Client = new AmazonS3Client(credentialsProvider);

        // Set the region of your S3 bucket
        s3Client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1));

    }
    public void setImageQuestions(ArrayList<Image_Questions> images){
        Show_Test_Custom_Image_Questions_Adapter adapter = new Show_Test_Custom_Image_Questions_Adapter(getApplicationContext(), images, ShowTest.this);
        list_image_questions.setAdapter(adapter);
        list_image_questions.setVisibility(View.VISIBLE);
        ListUtils.setDynamicHeight(list_image_questions);
    }
}
