package com.example.wordgermechurch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class VersetDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: VersetDatabase? = null

        fun getDatabase(context: Context): VersetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VersetDatabase::class.java,
                    "item_database"
                )
                    .addCallback(AppDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.itemDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(itemDao: ItemDao) {
            val versets = listOf(
                Item(description = "Ephésiens 6.18", content = "Faites-en tout temps par l’Esprit toutes sortes de prières et de supplications. Veillez à cela avec une entière persévérance, et priez pour tous les saints."),
                Item(description = "Psaume 32.8", content = "Je t’instruirai et te montrerai la voie que tu dois suivre ; Je te conseillerai, j’aurai le regard sur toi."),
                Item(description = "Jérémie 33.3", content = "Invoque-moi, et je te répondrai ; Je t'annoncerai de grandes choses, des choses cachées, Que tu ne connais pas."),
                Item(description = "Actes 1.8", content = "Mais vous recevrez une puissance, le Saint-Esprit survenant sur vous, et vous serez mes témoins à Jérusalem, dans toute la Judée, dans la Samarie, et jusqu’aux extrémités de la terre."),
                Item(description = "Romains 8.16", content = "L’Esprit lui-même rend témoignage à notre esprit que nous sommes enfants de Dieu."),
                Item(description = "Romains 8.11", content = "Et si l’Esprit de celui qui a ressuscité Jésus d’entre les morts habite en vous, celui qui a ressuscité le Christ-Jésus d’entre les morts donnera aussi la vie à vos corps mortels par son Esprit qui habite en vous.")
            )
            itemDao.insertAll(versets)
        }
    }
}

