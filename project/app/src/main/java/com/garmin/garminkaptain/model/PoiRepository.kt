package com.garmin.garminkaptain.model

import com.garmin.garminkaptain.KaptainApplication
import com.garmin.garminkaptain.data.PointOfInterest
import kotlinx.coroutines.flow.Flow

object PoiRepository {

    fun getPoiList(application: KaptainApplication): Flow<List<PointOfInterest>> =
        application.poiDatabase.getPoiDao().getAllPoi()

    fun getPoi(application: KaptainApplication, id: Long): Flow<PointOfInterest> =
        application.poiDatabase.getPoiDao().getPoi(id)

    suspend fun getReviews(application: KaptainApplication, id: Long) =
        application.poiDatabase.getPoiDao().getPoiWithReviews(id).reviews
}