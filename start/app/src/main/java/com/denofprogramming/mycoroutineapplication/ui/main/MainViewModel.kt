package com.denofprogramming.mycoroutineapplication.ui.main


import android.graphics.Bitmap
import androidx.lifecycle.*
import com.denofprogramming.mycoroutineapplication.network.MockNetworkService
import com.denofprogramming.mycoroutineapplication.repository.image.CoroutineImageRepository
import com.denofprogramming.mycoroutineapplication.repository.time.DefaultClock
import com.denofprogramming.mycoroutineapplication.shared.Resource
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import kotlinx.coroutines.*


/**
 *
 * In this demo, I want to cover the following topic;
 *  1) Use ViewModel coroutine support
 *
 *  At the end of completing the TO-DO's the application will compile and run, give it a try!
 *
 */
class MainViewModel : ViewModel() {


    private val _clock = DefaultClock.build()

    private val _imageRepository =
        CoroutineImageRepository.build(MockNetworkService.build())

    val image: LiveData<Resource<Bitmap>> get() = _image

    private val _image = MutableLiveData<Resource<Bitmap>>()

    val currentTimeTransformed = _clock.time.switchMap {
        val timeFormatted = MutableLiveData<String>()
        val time = _clock.timeStampToTime(it)
        logMessage("currentTimeTransformed time is $time")
        timeFormatted.value = time
        timeFormatted
    }

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    //TODO 1 - We no longer need to create a Job to tidy up the viewModel, it's handled by the ViewModel for us.
    private var _viewModelJob = Job()


    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [_viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    //TODO 2 - We no longer need a CoroutineScope, as we can use the one provide by the ViewModel.
    private val _uiScope = CoroutineScope(Dispatchers.Main + _viewModelJob)


    init {
        startClock()
    }

    fun onButtonClicked() {
        logMessage("Start onButtonClicked()")
        //TODO 3 - We need to modify this so we can use the launch builder function on this.viewModelScope
        _uiScope.launch {
            loadImage()
        }
    }

    fun onCancelClicked() {
        _imageRepository.cancel()
    }

    private suspend fun loadImage() {
        logMessage("Start loadImage()")
        val imageResource = try {
            _imageRepository.fetchImage(_imageRepository.nextImageId())
        } catch (e: Exception) {
            logMessage("loadImage() exception $e")
            Resource.error(e.localizedMessage ?: "No Message")
        }
        showImage(imageResource)
    }

    // This time I've made the showImage Main-Safe using the withContext
    private suspend fun showImage(imageResource: Resource<Bitmap>) {
        logMessage("showingImage...")
        withContext(Dispatchers.Main) {
            _image.value = imageResource
        }

    }

    private fun startClock() {
        logMessage("Start startClock()")
        _clock.start()
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    //TODO 4 - We no-longer need do manually do this as the ViewModel implements it for us.
    override fun onCleared() {
        super.onCleared()
        _viewModelJob.cancel()
       /*
        // or we could use...
       _uiScope.cancel()
       */
    }


}