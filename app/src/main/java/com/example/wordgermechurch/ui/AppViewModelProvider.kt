package com.example.wordgermechurch.ui

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wordgermechurch.VersetApplication
import com.example.wordgermechurch.ui.create.InsertViewmodel
import com.example.wordgermechurch.ui.home.HommeViewModel
import com.example.wordgermechurch.ui.listng.ListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VersetApplication
            HommeViewModel(application.container.itemsRepository)
        }
        // Initializer for ItemEntryViewModel
        initializer {
            InsertViewmodel(VersetApplication().container.itemsRepository)
        }
        initializer {
            ListViewModel(VersetApplication().container.itemsRepository)
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [VersetApplication].
 */
fun CreationExtras.VersetApplication(): VersetApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VersetApplication)
