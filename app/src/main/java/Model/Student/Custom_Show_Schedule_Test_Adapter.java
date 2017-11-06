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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import BussinessObjects.Test_BO;

/**
 * Created by Muhammad Taimoor on 5/29/2017.
 */

public class Custom_Show_Schedule_Test_Adapter extends ArrayAdapter<Test_BO> {
    private Context context;
    private ArrayList<Test_BO> test_bo;

    public  Custom_Show_Schedule_Test_Adapter(Context c, ArrayList<Test_BO> t){
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

            if(test.getStart_time().equals("") || test.getStart_time().equals("null") || test.getStart_time().equals(null))
                v.btn.setText("Start Test");
            else{
                // Getting Test Year, Month, Day, Hour and Minute
                String []startDateTime = test.getStart_time().split("T");
                String[] testdate = startDateTime[0].split("-");
                String test_year = testdate[0];
                int test_month = Integer.parseInt(testdate[1]);
                int test_day = Integer.parseInt(testdate[2]);
                String [] testTime = startDateTime[1].split(":");
                int testhour = Integer.parseInt(testTime[0]);
                int testminute = Integer.parseInt(testTime[1]);


                String t_Month, t_Day, t_Hour, t_Minute;
                if(test_month>0 && test_month<10)
                    t_Month = "0" + test_month;
                else
                    t_Month = "" + test_month;
                if(test_day>0 && test_day<10)
                    t_Day = "0" + test_day;
                else
                    t_Day = "" + test_day;
                if(testhour>0 && testhour<10)
                    t_Hour = "0" + testhour;
                else
                    t_Hour = "" + testhour;
                if(testminute>0 && testminute<10)
                    t_Minute = "0" + testminute;
                else
                    t_Minute = "" + testminute;

                String str = test_year+t_Month+t_Day+t_Hour+t_Minute;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                try {
                    Date d = sdf.parse(str);
                    if (new Date().after(d) || new Date().equals(d) ) {
                        v.btn.setText("Start Test");
                    }
                    else
                        v.btn.setText("Can't Start Test");
                } catch (ParseException ex) {}
            }
            return convertView;
        }catch (Exception e)
        {
            return null;
        }

    }
}
