package Model.Teacher.New_Test_Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;

import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import BussinessObjects.Test_BO;
import Model.Session;
import Model.Single_Choise_Option_Event;

/**
 * Created by DELL on 10/1/2017.
 */

public class Custom_Dialog_Add_Option extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button add ;
    private Test_BO tests;
    private EditText option;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    Single_Choice_Options single_choice_options;
    CheckBox is_right;
    SingleChoiceQuestions singleChoiceQuestions;

    public Custom_Dialog_Add_Option(Activity a  , SingleChoiceQuestions singleChoiceQuestions) {
        super(a);
        activity = a;
        this.singleChoiceQuestions = singleChoiceQuestions;
        //tests = u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_option);
        single_choice_options = new Single_Choice_Options();

        add = (Button) findViewById(R.id.dialog_add);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        option = (EditText)findViewById(R.id.single_question_name);

        is_right = (CheckBox)findViewById(R.id.is_right);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_add: {
                if(option.getText().toString().equals("") || option.getText().toString().equals(null)){
                    Toast.makeText(activity.getApplicationContext() , "!!!!! Enter the Option Text !!!!!" , Toast.LENGTH_LONG).show();
                }
                else{

                    single_choice_options.setOption(option.getText().toString());
                    if(is_right.isChecked()){
                        if(singleChoiceQuestions.isAlready()){
                            Toast.makeText(activity.getApplicationContext() , "You have Already Selected One Option true" , Toast.LENGTH_LONG).show();
                        }
                        else {
                            single_choice_options.setCorrect(is_right.isChecked());
                            singleChoiceQuestions.setAlready(true);
                            Single_Choise_Option_Event scoE = new Single_Choise_Option_Event();
                            scoE.setSco(single_choice_options);
                            EventBus.getDefault().post(scoE);
                            this.dismiss();
                        }
                    }
                    else{
                        single_choice_options.setCorrect(is_right.isChecked());
                        Single_Choise_Option_Event scoE = new Single_Choise_Option_Event();
                        scoE.setSco(single_choice_options);
                        EventBus.getDefault().post(scoE);
                        this.dismiss();
                    }
                }
               /* if(singleChoiceQuestions.getOptions().size() == 0){
                    Toast.makeText(activity.getApplicationContext() , "Please Add Minimum of One Option" , Toast.LENGTH_LONG).show();
                }
                else {
                    Text_Questions txt = new Text_Questions();

                    txt.setTitle(add_question.getText().toString());
                    if (add_marks.getText().toString().equals(null) || add_marks.getText().toString().equals("") || add_marks.getText().toString().equals("null")) {
                        txt.setMarks(0);
                    } else {
                        txt.setMarks(Integer.valueOf(add_marks.getText().toString()));
                    }
                    Text_Question_Event testQ = new Text_Question_Event();
                    testQ.setTestQ(txt);
                    EventBus.getDefault().post(testQ);
                    this.dismiss();*/
                //}
                break;
            }
            case R.id.dialog_add_option:{

                break;
            }
            case R.id.dialog_cancel: {
                dismiss();
                break;
            }
            default: {
                dismiss();
            }
        }
    }
}
