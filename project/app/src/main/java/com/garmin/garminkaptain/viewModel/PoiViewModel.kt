package com.garmin.garminkaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PoiViewModel(application: Application) : AndroidViewModel(application) {

    init {
        Log.d(TAG, "init called")
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getPoi(id: Long): LiveData<PointOfInterest?> = liveData {
        loadingLiveData.postValue(true)
        PoiRepository.getPoi(getApplication(), id)?.collect {
            emit(it)
            loadingLiveData.postValue(false)
        }
    }

    fun getPoiList(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData
    }

    fun getLoading(): LiveData<Boolean> = loadingLiveData

    fun loadPoiList() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            PoiRepository.getPoiList(getApplication())?.collect {
                poiListLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }

}