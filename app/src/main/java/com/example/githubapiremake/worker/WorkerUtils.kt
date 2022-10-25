package com.example.githubapiremake.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.githubapiremake.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

//fun statusNotification(message: String, ctx: Context) {
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
//        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val channel = NotificationChannel(CHANNEL_ID, name, importance)
//        channel.description = description
//
//        val notificationManager =
//            ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
//
//        notificationManager?.createNotificationChannel(channel)
//    }
//
//
//    val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
//        .setSmallIcon(R.drawable.ic_launcher_foreground)
//        .setContentTitle(NOTIFICATION_TITLE)
//        .setContentText(message)
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//        .setVibrate(LongArray(0))
//
//    NotificationManagerCompat.from(ctx).notify(NOTIFICATION_ID, builder.build())
//}

fun blurBitmap(bitmap: Bitmap, applicationContext: Context): Bitmap {
    lateinit var rsContext: RenderScript
    try {

        val output = Bitmap.createBitmap(
            bitmap.width, bitmap.height, bitmap.config)

        rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
        val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
        val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
        val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
        theIntrinsic.apply {
            setRadius(10f)
            theIntrinsic.setInput(inAlloc)
            theIntrinsic.forEach(outAlloc)
        }
        outAlloc.copyTo(output)

        return output
    } finally {
        rsContext.finish()
    }
}

fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
    val name = IMAGE_BLURRED
    val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
    if (!outputDir.exists()) {
        outputDir.mkdirs()
    }
    val outputFile = File(outputDir, name)
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
    } finally {
        out?.let {
            try {
                it.close()
            } catch (ignore: IOException) {
                Log.d("FileOutput","${ignore.message}")
            }

        }
    }
    return Uri.fromFile(outputFile)
}