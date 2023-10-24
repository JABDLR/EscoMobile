package mx.ipn.escom.escomobile.app.modelo.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.config.AppConfig
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        /**
         * Método necesario para recibir notificaciones en segundo plano
         * Se debe invocar desde MainActivity, es decir, lo antes posible para registrar
         * el servicio
         * */
        fun createNotificationChannel( context: Context ) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "ESCOMobile"
                val descriptionText = "Prueba Firebase"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("ESCOMobile_APP", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager = context.
                    getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        /**
         * Devuelve el Token obtenido. Sedebe comprobar cierto periodo de tiempo debido a
         * que puede cambiar ya sea porque se desisntalo la app, el usuario borro los datos
         * de la app o porque ha caducado.
         * */
        fun getTokenfirebase( completionHandler: ( token: String ) -> Unit ) {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
                val token  = instanceIdResult.token
                MyLogger.debug( "TOKEN $token")
                completionHandler( token )
            }
        }

        /**
         * Suscribe a un usuario a un tópico/canal/tema para las notificaciones.
         *
         * Se hace a través de esta función y no directo para extender funcionalidad más fácil.
         */
        fun subscribeToTopic(topic: String) {
            MyLogger.info( "Suscrito a '${topic}'.")
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        MyLogger.debug( "NEW TOKEN $token")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if(remoteMessage.data.isNotEmpty()) {
            MyLogger.error( "DATA ${ remoteMessage.data }")
        }

        MyLogger.error( "MENSAJE $remoteMessage")
        makeNotification( remoteMessage )
    }

    private fun makeNotification( remoteMessage: RemoteMessage? ) {
        val notification = NotificationCompat.Builder( this, "" ).apply {
            setSmallIcon( R.mipmap.ic_app_icon)
            setContentTitle( remoteMessage?.notification?.title )
            setContentText( remoteMessage?.notification?.body )
            setStyle( NotificationCompat.BigTextStyle().bigText( remoteMessage?.notification?.body ) )
            setPriority( NotificationCompat.PRIORITY_DEFAULT )
            setAutoCancel( true )
        }

        /*
        with( NotificationManagerCompat.from( this ) ) {
            // notificationId is a unique int for each notification that you must define
            notify( 2, notification.build() )
        }*/
    }
}