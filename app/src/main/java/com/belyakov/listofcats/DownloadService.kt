package com.belyakov.listofcats

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.belyakov.listofcats.domain.DownloadProgressCallback
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadService : Service(), DownloadProgressCallback{

    private companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "DownloadChannel"
        const val PROGRESS_MAX = 100
    }

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        createNotificationBuilder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("url")

        url?.let {
            startForeground(NOTIFICATION_ID, notificationBuilder.build())
            downloadImage(url)
        }

        return START_NOT_STICKY
    }

    private fun downloadImage(url: String) : Boolean {
        try {
            val imageUrl = URL(url)
            val connection = imageUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream

            val filename = "cat_${System.currentTimeMillis()}.jpg"
            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(directory, filename)
            val output = FileOutputStream(file)

            val fileLength = connection.contentLength
            var totalBytesRead = 0

            val buffer = ByteArray(1024)
            var bytesRead = input.read(buffer)
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead)
                totalBytesRead += bytesRead

                // Рассчитайте прогресс загрузки в процентах
                val progress = (totalBytesRead * 100 / fileLength).toInt()

                // Обновите LiveData с прогрессом загрузки
                onProgressUpdated(progress)
                bytesRead = input.read(buffer)
            }
            output.close()
            input.close()
           return true
        } catch (e: Exception) {
            return false
        }
        // Выполните загрузку изображения и обновление прогресса
        // Уведомления с прогрессом загрузки будут автоматически обновляться
        // Обновляйте уведомление с помощью notificationBuilder.setProgress() и notificationManager.notify()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Download Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationBuilder() {
        notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Загрузка изображения")
            .setContentText("Загрузка в процессе")
            .setSmallIcon(R.drawable.ic_download)
            .setOngoing(true)
            .setProgress(PROGRESS_MAX, 0, false)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onProgressUpdated(progress: Int) {

    }
}