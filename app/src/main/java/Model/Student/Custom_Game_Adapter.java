package Model.Student;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Game_BO;

/**
 * Created by Muhammad Taimoor on 7/3/2017.
 */

public class Custom_Game_Adapter extends ArrayAdapter<Game_BO> {

    private Context context;
    private ArrayList<Game_BO> game_bo;

    public Custom_Game_Adapter(Context c, ArrayList<Game_BO> g){
        super(c, 0, g);
        StrictMode.ThreadPolicy p = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(p);
        context = c;
        game_bo = g;
    }

    private class ViewHolder {
        private TextView name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Game_BO game = getItem(position);
        ViewHolder v = null;
        View view = convertView;
        try
        {
            if (convertView == null) {
                v = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_student_game, parent, false);
                v.name = (TextView) convertView.findViewById(R.id.game_name);
                convertView.setTag(v);
            }
            else {
                v = (ViewHolder) convertView.getTag();
            }
            v.name.setText(""+game.getName());
            return convertView;
        }catch (Exception e)
        {
            return null;
        }

    }
}
