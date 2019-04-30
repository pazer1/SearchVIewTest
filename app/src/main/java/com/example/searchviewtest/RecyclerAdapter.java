package com.example.searchviewtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<String> mItemList;
    private ArrayList matches;
    private Context context;

    public RecyclerAdapter(List<String> mItemList, ArrayList matches) {
        this.mItemList = mItemList;
        this.matches = matches;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.listview_items,viewGroup,false);
        view.findViewById(R.id.iv_twitch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((IsInstalled)(context.getApplicationContext())).isTwitch()){
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("tv.twitch.android.app");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    String url = "market://details?id=" + "tv.twitch.android.app";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(i);
                }
            }
        });
        return RecyclerItemViewHolder.newInstance(view);
    }

    private String utcToLocalWithTime(String time){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd HH:mm");
        TimeZone tz = TimeZone.getDefault();
        String locTime;
        try {
            Date date = inputFormat.parse(time);
            long milliseconds = date.getTime();
            long now = System.currentTimeMillis();
            int offset = tz.getOffset(milliseconds);
            locTime= outputFormat.format(milliseconds+offset);
            return locTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        final RecyclerItemViewHolder holder = (RecyclerItemViewHolder)viewHolder;
        Match match = (Match) matches.get(i);
        String time = utcToLocalWithTime(match.getBegin_at());
        Match.OpponentsBean.OpponentBean team1;
        Match.OpponentsBean.OpponentBean team2;
        String team1Img;
        String team2Img;
        String team1Name = null;
        String team2Name = null;
        String leaguname = match.getLeague().getName();
        if(match.getOpponents().size()>0 ){
            team1 = match.getOpponents().get(0).getOpponent();
            team2 = match.getOpponents().get(1).getOpponent();
            team1Img = team1.getImage_url().toString();
            team2Img = team2.getImage_url().toString();
            team1Name = team1.getName();
            team2Name = team2.getName();

            if(team1Img==null || team2Img==null){
                team1Img = "https://cdn.pandascore.co/images/league/image/4139/2593d-EU_Masters_Trans-iloveimg-resized.png";
                team2Img = "https://cdn.pandascore.co/images/league/image/4139/2593d-EU_Masters_Trans-iloveimg-resized.png";
            }
        }else{
             team1Img = "https://cdn.pandascore.co/images/league/image/4139/2593d-EU_Masters_Trans-iloveimg-resized.png";
             team2Img = "https://cdn.pandascore.co/images/league/image/4139/2593d-EU_Masters_Trans-iloveimg-resized.png";
            if(team1Img==null || team2Img==null){
                team1Img = "https://cdn.pandascore.co/images/league/image/4139/2593d-EU_Masters_Trans-iloveimg-resized.png";
                team2Img = "https://cdn.pandascore.co/images/league/image/4139/2593d-EU_Masters_Trans-iloveimg-resized.png";
            }
        }
        holder.setmImageView(team1Img,team2Img);
        holder.setTeamName(team1Name,team2Name);
        holder.setmImageView(team1Img,team2Img);
        holder.setLeaguename(leaguname);
        holder.setNoti(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
                    String channelID ="ch_1";
                    String channelName="channel";
                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                    NotificationChannel channel = new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);

                    RemoteViews notificationLayout = new RemoteViews(context.getPackageName(),R.layout.notification_small);
                    Notification customNotification = new NotificationCompat.Builder(context,channelID)
                            .setSmallIcon(R.drawable.notification)
                            .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                            .setCustomContentView(notificationLayout)
                            .build();
                    notificationManager.notify(1,customNotification);
                }else{
                    RemoteViews notificationLayout = new RemoteViews(context.getPackageName(),R.layout.notification_small);
                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                    Notification customNotification = new NotificationCompat.Builder(context,null)
                            .setSmallIcon(R.drawable.notification)
                            .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                            .setCustomContentView(notificationLayout)
                            .build();
                    notificationManager.notify(1,customNotification);

                }

            }

        });
        holder.setItemText(time);
    }

    @Override
    public int getItemCount() {
        return mItemList==null?0:mItemList.size();
    }
}
