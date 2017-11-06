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

import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;

import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Test_BO;
import Model.Multi_Choise_Option_Event;
import Model.Session;

/**
 * Created by DELL on 10/2/2017.
 */

public class Custom_Dialog_Add_Multi_Option extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button add ;
    private Test_BO tests;
    private EditText option;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    Multi_Choice_Options multi_choice_options;
    CheckBox is_right;
    MultipleChoiceQuestions multipleChoiceQuestions;

    public Custom_Dialog_Add_Multi_Option(Activity a  , MultipleChoiceQuestions multipleChoiceQuestions) {
        super(a);
        activity = a;
        this.multipleChoiceQuestions = multipleChoiceQuestions;
        //tests = u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_option);
        multi_choice_options = new Multi_Choice_Options();

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
                    multi_choice_options.setOption(option.getText().toString());
                    if(is_right.isChecked()) {
                        multipleChoiceQuestions.setAlready(true);
                    }
                    multi_choice_options.setCorrect(is_right.isChecked());

                    Multi_Choise_Option_Event scoE = new Multi_Choise_Option_Event();
                    scoE.setMco(multi_choice_options);
                    EventBus.getDefault().post(scoE);
                    this.dismiss();
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
