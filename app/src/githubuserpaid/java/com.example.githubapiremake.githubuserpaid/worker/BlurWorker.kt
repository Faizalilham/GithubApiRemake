package com.example.githubapiremake.githubuserpaid.worker

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class BlurWorker(ctx: Context, prm : WorkerParameters) : Worker(ctx,prm) {

    override fun doWork(): Result {
        val appContext = applicationContext
        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        // For close
//        notification("blurring image", appContext)

        return try{
            if(TextUtils.isEmpty(resourceUri)){
                Log.d("Uri Status", "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = appContext.contentResolver
            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri)))

            val output = blurBitmap(picture, appContext)
            val outputUri = writeBitmapToFile(appContext, output)

            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            ListenableWorker.Result.success(outputData)

        }catch (throwable: Throwable){
            Log.d("Blur Status", "Error applying blur")
            throwable.printStackTrace()
            ListenableWorker.Result.failure()
        }
    }

}