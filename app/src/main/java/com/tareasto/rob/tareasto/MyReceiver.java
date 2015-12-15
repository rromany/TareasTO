package com.tareasto.rob.tareasto;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//Definir Notification Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String nombre = intent.getStringExtra("nombre");
        String tiempo = intent.getStringExtra("fecha")+" " +intent.getIntExtra("horas",0)+":"+intent.getIntExtra("minutos",0);
//Definir URI de sonido
        //Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setContentTitle("Alarma: "+nombre)
                        .setContentText(tiempo)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true);

//Desplegar notificacion
        notificationManager.notify(0, mBuilder.build());
        Toast.makeText(context,"Alarma("+tiempo+"): "+nombre, Toast.LENGTH_LONG).show();
    }
}
