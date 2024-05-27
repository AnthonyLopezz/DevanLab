package com.devan.lab.ViewModel

import androidx.lifecycle.ViewModel
import com.devan.lab.Models.ArchiveModel

class ArchiveViewModel (val repository: ArchiveModel): ViewModel() {

    constructor(): this(ArchiveModel())

    fun loadDataArchive() = repository.archiveItems

}