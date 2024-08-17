package com.my.version.feature.cover.main

import androidx.lifecycle.ViewModel
import com.my.version.core.domain.repository.CoverLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoverViewModel @Inject constructor(
    private val coverLocalRepository: CoverLocalRepository
): ViewModel() {

}