package com.Ecosia.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.Ecosia.R;
import com.Ecosia.services.EcosiaAudioPlayerService;


public class EcosiaPlayerWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_ecosia_player);

        remoteViews.setOnClickPendingIntent(R.id.btnStart,
                buildStartActionPendingIntent(context));

        remoteViews.setOnClickPendingIntent(R.id.btnStop,
                buildStopActionPendingIntent(context));

        pushWidgetUpdate(context, remoteViews);
    }

    private PendingIntent buildStartActionPendingIntent(Context context) {
        Intent intent = new Intent(context, EcosiaAudioPlayerService.class);
        intent.setAction(EcosiaAudioPlayerService.ACTION_PLAY);
        return PendingIntent.getService(context, 0, intent,
                0);
    }

    private PendingIntent buildStopActionPendingIntent(Context context) {
        Intent intent = new Intent(context, EcosiaAudioPlayerService.class);
        intent.setAction(EcosiaAudioPlayerService.ACTION_STOP);
        return PendingIntent.getService(context, 0, intent,
                0);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context,
                EcosiaPlayerWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

}
