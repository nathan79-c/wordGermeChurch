package com.example.wordgermechurch.data

import android.content.Context

/**
* App container for Dependency injection.
*/
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(VersetDatabase.getDatabase(context).itemDao())
    }
}
