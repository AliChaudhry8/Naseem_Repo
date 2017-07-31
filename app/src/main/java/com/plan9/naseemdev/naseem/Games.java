package com.plan9.naseemdev.naseem;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import BussinessObjects.Game_BO;
import Model.Student.Custom_Game_Adapter;

/**
 * Created by Muhammad Taimoor on 5/16/2017.
 */

public class Games extends Fragment{
    private ArrayList<Game_BO> game;
    private ListView games_list;
    public Games(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        game = new ArrayList<Game_BO>();
        game.add(new Game_BO("Dusk Drive", "<embed width=\"800\" height=\"512\" base=\"http://external.kongregate-games.com/gamez/0022/3733/live/\" src=\"http://external.kongregate-games.com/gamez/0022/3733/live/embeddable_223733.swf\" type=\"application/x-shockwave-flash\"></embed>"));
        game.add(new Game_BO("The Walls", "<embed width=\"550\" height=\"400\" base=\"http://external.kongregate-games.com/gamez/0000/6664/live/\" src=\"http://external.kongregate-games.com/gamez/0000/6664/live/embeddable_6664.swf\" type=\"application/x-shockwave-flash\"></embed>"));
        game.add(new Game_BO("Bullet Heaven", "<embed width=\"800\" height=\"600\" base=\"http://external.kongregate-games.com/gamez/0024/7191/live/\" src=\"http://external.kongregate-games.com/gamez/0024/7191/live/embeddable_247191.swf\" type=\"application/x-shockwave-flash\"></embed>"));
        game.add(new Game_BO("The Last Dinosaurs", "<embed width=\"800\" height=\"600\" base=\"http://external.kongregate-games.com/gamez/0021/6697/live/\" src=\"http://external.kongregate-games.com/gamez/0021/6697/live/embeddable_216697.swf\" type=\"application/x-shockwave-flash\"></embed>"));
        game.add(new Game_BO("Roller Ride", "<embed width=\"800\" height=\"512\" base=\"http://external.kongregate-games.com/gamez/0021/6804/live/\" src=\"http://external.kongregate-games.com/gamez/0021/6804/live/embeddable_216804.swf\" type=\"application/x-shockwave-flash\"></embed>"));

        games_list = (ListView)rootView.findViewById(R.id.games_list);
        Custom_Game_Adapter custom_game_adapter = new Custom_Game_Adapter(getActivity().getApplicationContext(), game);
        games_list.setAdapter(custom_game_adapter);
        games_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), game.get(i).getName(), Toast.LENGTH_LONG).show();
                if(isFlashAvailable(getActivity().getApplicationContext())) {
                    Game_BO game_bo = game.get(i);
                    Intent it = new Intent(getActivity(), ViewGame.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("game_bo", game_bo);
                    it.putExtras(bundle);
                    startActivity(it);
                }
            }
        });
        return rootView;
    }

    public static boolean isFlashAvailable(Context context) {
        String mVersion;
        try {
            mVersion = context.getPackageManager().getPackageInfo("com.adobe.flashplayer", 0).versionName;
            //Log.d("Flash", "Installed: " + mVersion);
            //Toast.makeText(context, "Adobe Found: " + mVersion, Toast.LENGTH_LONG).show();
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //Log.d("Flash", "Not installed");
            //Toast.makeText(context, "Adobe Not Installed", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
