package com.belyakov.listofcats.data

import android.os.Environment
import com.belyakov.listofcats.domain.utils.DownloadImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

internal class DownloadImageProviderImpl : DownloadImageProvider {

    override suspend fun downloadImage(url: String) = withContext(Dispatchers.IO) {
        delay(5000)
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

            val buffer = ByteArray(1024)
            var bytesRead = input.read(buffer)
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead)
                bytesRead = input.read(buffer)
            }

            output.close()
            input.close()

            true
        } catch (e: Exception) {
            false
        }
    }
}