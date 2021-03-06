package uk.co.beevorwhite.soiree.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import uk.co.beevorwhite.soiree.MainActivity;
import uk.co.beevorwhite.soiree.R;

import static uk.co.beevorwhite.soiree.model.Keys.ACTION_UPDATE_WIDGET;


public class widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent openAppIntent = new Intent(context, MainActivity.class);
        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_heading, newPendingIntent);

        // display empty view if widget has no data to display
        views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_text);

        Intent intent = new Intent(context, MyWidgetRemoteViewsFactory.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        if (action.equals(ACTION_UPDATE_WIDGET)) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName widget = new ComponentName(context.getApplicationContext(), widget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
            onUpdate(context, appWidgetManager, appWidgetIds);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

