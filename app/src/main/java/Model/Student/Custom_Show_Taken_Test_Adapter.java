package Model.Student;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Test_BO;

/**
 * Created by Muhammad Taimoor on 5/29/2017.
 */

public class Custom_Show_Taken_Test_Adapter extends ArrayAdapter<Test_BO> {
    private Context context;
    private ArrayList<Test_BO> test_bo;

    public  Custom_Show_Taken_Test_Adapter(Context c, ArrayList<Test_BO> t){
        super(c, 0, t);
        StrictMode.ThreadPolicy p = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(p);
        context = c;
        test_bo = t;
    }

    private class ViewHolder {
        private TextView name;
        private Button btn;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Test_BO test = getItem(position);
        ViewHolder v = null;
        View view = convertView;
        try
        {
            if (convertView == null) {
                v = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_test_list_view, parent, false);
                v.name = (TextView) convertView.findViewById(R.id.testName);
                v.btn = (Button) convertView.findViewById(R.id.testButton);
                convertView.setTag(v);
            }
            else {
                v = (ViewHolder) convertView.getTag();
            }

            v.name.setText(""+test.getName());
            v.btn.setText("View Submission");
            return convertView;

        }catch (Exception e)
        {
            return null;
        }

    }
}
