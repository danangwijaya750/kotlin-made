package com.dngwjy.madesub3.presentation.ui.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.dngwjy.madesub3.R
import com.dngwjy.madesub3.presentation.ui.detail.DetailActivity
import com.dngwjy.madesub3.services.StackWidgetService
import com.dngwjy.madesub3.util.logE

/**
 * Implementation of App Widget functionality.
 */
class StackWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        appWidgetIds.forEach {
            val intent = Intent(context, StackWidgetService::class.java)
            val views = RemoteViews(context.packageName, R.layout.stack_widget)
            views.setRemoteAdapter(R.id.favorite_stack_view, intent)
            views.setEmptyView(R.id.favorite_stack_view, R.id.empty_stack_view)

            val detailMovie = Intent(context, DetailActivity::class.java)
            val clickDetail = PendingIntent.getActivity(context, 0, detailMovie, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.favorite_stack_view, clickDetail)

            val updateIntent = Intent(context, StackWidget::class.java)
            updateIntent.action = StackWidget.WIDGET_UPDATE
            val pendingIntent = PendingIntent.getBroadcast(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.favorite_refresh_widget, pendingIntent)


            appWidgetManager.updateAppWidget(it, views)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        logE(intent?.action)

        if (intent?.action!!.equals(WIDGET_UPDATE, ignoreCase = true)) {
            updateWidget(context)
            logE("updated")
        }
    }

    private fun updateWidget(context: Context?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, StackWidget::class.java))
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.favorite_stack_view)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        const val WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"
        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {


        }
    }
}

