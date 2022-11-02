package com.example.githubapiremake.githubuserpaid.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.githubapiremake.R
import com.example.githubapiremake.worker.BlurWorker
import com.example.githubapiremake.worker.KEY_IMAGE_URI

class BlurViewModel(application : Application):ViewModel() {


    private val workManager = WorkManager.getInstance(application)
    private var imageUri: Uri? = null

    fun setImageUri(uri: Uri?) {
        imageUri = uri
    }

   // One time request
    internal fun applyBlur() {
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForUri())
            .build()
        workManager.enqueue(blurRequest)
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }


//    private fun getImageUri(context: Context): Uri {
//        val resources = context.resources
//
//        return Uri.Builder()
//            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//            .authority(resources.getResourcePackageName(R.mipmap.ic_launcher))
//            .appendPath(resources.getResourceTypeName(R.mipmap.ic_launcher))
//            .appendPath(resources.getResourceEntryName(R.mipmap.ic_launcher))
//            .build()
//    }
}

class BlurViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
            BlurViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
