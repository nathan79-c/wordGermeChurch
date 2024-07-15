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
                Item(description = "Romains 8.11", content = "Et si l’Esprit de celui qui a ressuscité Jésus d’entre les morts habite en vous, celui qui a ressuscité le Christ-Jésus d’entre les morts donnera aussi la vie à vos corps mortels par son Esprit qui habite en vous."),
                Item(description = "Luc 11.9-10 ; 13", content = "9 Et moi, je vous dis : Demandez, et l'on vous donnera ; cherchez, et vous trouverez ; frappez, et l'on vous ouvrira. 10 Car quiconque demande reçoit, celui qui cherche trouve, et l'on ouvre à celui qui frappe. 13 Si donc, méchants comme vous l'êtes, vous savez donner de bonnes choses à vos enfants, à combien plus forte raison le Père céleste donnera-t-il le Saint-Esprit à ceux qui le lui demandent."),
                Item(description = "Romains 8.26", content = "De même aussi l’Esprit nous aide dans notre faiblesse, car nous ne savons pas ce qu’il nous convient de demander dans nos prières. Mais l’Esprit lui-même intercède par des soupirs inexprimables"),
                Item(description = "Psaume 119.11", content = "Je serre ta parole dans mon cœur, Afin de ne pas pécher contre toi."),
                Item(description = "Jérémie 23.29", content = "Ma Parole n’est-elle pas comme un feu, dit l’Eternel, comme un marteau qui brise le roc ?"),
                Item(description = "1 Jean 5.14-15", content = "Nous avons auprès de lui cette assurance, que si nous demandons quelque chose selon sa volonté, il nous écoute. Et si nous savons qu'il nous écoute, quelque chose que nous demandions, nous savons que nous possédons la chose que nous lui avons demandée."),
                Item(description = "Josué 1.8", content = "Que ce livre de la loi ne s’éloigne point de ta bouche ; médite-le jour et nuit, pour agir fidèlement selon tout ce qui y est écrit ; car c’est alors que tu auras du succès dans tes entreprises, c’est alors que tu réussiras."),
                Item(description = "Matthieu 8.17", content = "…Il a pris nos infirmités, et il s’est chargé de nos maladies."),
                Item(description = "Marc 11.23-24", content = "Je vous le dis en vérité, si quelqu’un dit à cette montagne : Ôte-toi de là et jette-toi dans la mer, et s’il ne doute point en son cœur, mais croit que ce qu’il dit arrive, il le verra s’accomplir. C’est pourquoi je vous dis : Tout ce que vous demanderez en priant, croyez que vous l’avez reçu, et vous le verrez s’accomplir."),
                Item(description = "Marc 16.17-18", content = "Voici les miracles qui accompagneront ceux qui auront cru : en mon nom, ils chasseront les démons ; ils parleront de nouvelles langues ; ils saisiront des serpents ; s’ils boivent quelque breuvage mortel, il ne leur fera point de mal ; ils imposeront les mains aux malades, et les malades, seront guéris."),
                Item(description = "1 Jean 5.19", content = "Nous savons que nous sommes de Dieu, et que le monde entier est sous la puissance du malin."),
                Item(description = "Jacques 4.7", content = "Soumettez-vous donc à Dieu ; résistez au diable, et il fuira loin de vous."),
                Item(description = "1 Pierre 5.8-9", content = "Soyez sobres, veillez. Votre adversaire, le diable, rôde comme un lion rugissant, cherchant qui il dévorera. Résistez-lui avec une foi ferme, sachant que les mêmes souffrances sont imposées à vos frères dans le monde."),
                Item(description = "Éphésiens 6.10", content = "Au reste, fortifiez-vous dans le Seigneur, et par sa force toute-puissante."),
                Item(description = "Luc 10.19", content = "Voici, je vous ai donné le pouvoir de marcher sur les serpents et les scorpions, et sur toute la puissance de l’ennemi ; et rien ne pourra vous nuire."),
                Item(description = "Apocalypse 12.11", content = "Ils l’ont vaincu à cause du sang de l’agneau et à cause de la parole de leur témoignage, et ils n’ont pas aimé leur vie jusqu’à craindre la mort."),
                Item(description = "Esaïe 8.10", content = "Formez des projets, et ils seront anéantis ; Donnez des ordres et ils seront sans effet : Car Dieu est avec nous."),
                Item(description = "Philippiens 2.9-11", content = "C’est pourquoi aussi Dieu l’a souverainement élevé, et lui a donné le nom qui est au-dessus de tout nom, afin qu’au nom de Jésus tout genou fléchisse dans les cieux, sur la terre et sous la terre, et que toute langue confesse que Jésus-Christ est Seigneur, à la gloire de Dieu le Père."),
                Item(description = "Philippiens 4.6-7", content = "Ne vous inquiétez de rien ; mais en toute chose faites connaître vos besoins à Dieu par des prières et des supplications, avec des actions de grâces. Et la paix de Dieu, qui surpasse toute intelligence, gardera vos cœurs et vos pensées en Jésus-Christ."),
                Item(description = "Psaumes 27.3", content = "Si une armée se campait contre moi, mon cœur n’aurait aucune crainte ; si une guerre s’élevait contre moi, je serais malgré cela plein de confiance."),
                Item(description = "Psaumes 33.9-11", content = "Car il dit, et la chose arrive ; Il ordonne, et elle existe. L’Éternel renverse les desseins des nations, il anéantit les projets des peuples ; Les desseins de l’Éternel subsistent à toujours, et les projets de son cœur, de génération en génération."),
                Item(description = "Psaumes 34.6", content = "Quand on tourne vers lui les regards, on est rayonnant de joie, Et le visage ne se couvre pas de honte."),
                Item(description = "Psaumes 34.8", content = "L’ange de l’Éternel campe autour de ceux qui le craignent, et il les arrache au danger."),
                Item(description = "Psaumes 34.18", content = "Quand les justes crient, l’Éternel entend, et il les délivre de toutes leurs détresses ;"),
                Item(description = "Psaumes 37.4-5", content = "Fais de l’Éternel tes délices, et il te donnera ce que ton cœur désire. Recommande ton sort à l’Éternel, mets en lui ta confiance, et il agira."),
                Item(description = "Psaumes 37.25", content = "J’ai été jeune, j’ai vieilli ; et je n’ai point vu le juste abandonné, ni sa postérité mendiant son pain."),
                Item(description = "Psaumes 60.14", content = "Avec Dieu, nous ferons des exploits ; il écrasera nos ennemis."),
                Item(description = "Psaumes 91.5-8", content = "Tu ne craindras ni les terreurs de la nuit, ni la flèche qui vole de jour, Ni la peste qui marche dans les ténèbres, ni la contagion qui frappe en plein midi. Que mille tombent à ton côté, et dix mille à ta droite, tu ne seras pas atteint ; De tes yeux seulement tu regarderas, et tu verras la rétribution des méchants."),
                Item(description = "Psaumes 91.10-12", content = "Aucun malheur ne t’arrivera, aucun fléau n’approchera de ta tente. Car il ordonnera à ses anges de te garder dans toutes tes voies ; Ils te porteront sur les mains, de peur que ton pied ne heurte contre une pierre."),
                Item(description = "Psaumes 103.2-3", content = "Mon âme, bénis l’Éternel, et n’oublie aucun de ses bienfaits ! C’est lui qui pardonne toutes tes iniquités, qui guérit toutes tes maladies ;"),
                Item(description = "2 Corinthiens 8.9", content = "Car vous connaissez la grâce de notre Seigneur Jésus-Christ, qui pour vous s’est fait pauvre, de riche qu’il était, afin que par sa pauvreté vous fussiez enrichis."),
                Item(description = "2 Corinthiens 10.3-5", content = "Si nous marchons dans la chair, nous ne combattons pas selon la chair. Car les armes avec lesquelles nous combattons ne sont pas charnelles ; mais elles sont puissantes, par la vertu de Dieu, pour renverser des forteresses. Nous renversons les raisonnements et toute hauteur qui s’élève contre la connaissance de Dieu, et nous amenons toute pensée captive à l’obéissance de Christ."),
                Item(description = "Galates 6.7", content = "Ne vous n’y trompez pas : on ne se moque pas de Dieu. Ce qu’un homme aura semé, il le moissonnera aussi."),
                Item(description = "Galates 6.2", content = "Portez les fardeaux les uns des autres, et vous accomplirez ainsi la loi de Christ."),
                Item(description = "Matthieu 5.13-14", content = "Vous êtes le sel de la terre. Mais si le sel perd sa saveur, avec quoi la lui rendra-t-on ? Il ne sert plus qu'à être jeté dehors, et foulé aux pieds par les hommes. Vous êtes la lumière du monde. Une ville située sur une montagne ne peut être cachée ;"),
                Item(description = "Matthieu 6.33", content = "Cherchez premièrement le royaume et la justice de Dieu; et toutes ces choses vous seront données par-dessus."),
                Item(description = "Matthieu 7.12", content = "Tout ce que vous voulez que les hommes fassent pour vous, faites-le de même pour eux, car c'est la loi et les prophètes."),
                Item(description = "Matthieu 7.21", content = "Ceux qui me disent : Seigneur, Seigneur ! n'entreront pas tous dans le royaume des cieux, mais celui-là seul qui fait la volonté de mon Père qui est dans les cieux."),
                Item(description = "Matthieu 11.28-30", content = "Venez à moi, vous tous qui êtes fatigués et chargés, et je vous donnerai du repos. Prenez mon joug sur vous et recevez mes instructions, car je suis doux et humble de cœur ; et vous trouverez du repos pour vos âmes. Car mon joug est doux, et mon fardeau léger."),
                Item(description = "Matthieu 21.22", content = "Tout ce que vous demanderez avec foi par la prière, vous le recevrez."),
                Item(description = "Matthieu 25.21", content = "Son maître lui dit : C'est bien, bon et fidèle serviteur ; tu as été fidèle en peu de chose, je te confierai beaucoup ; entre dans la joie de ton maître."),
                Item(description = "Matthieu 26.41", content = "Veillez et priez, afin que vous ne tombiez pas dans la tentation ; l'esprit est bien disposé, mais la chair est faible."),
                Item(description = "Matthieu 28.19-20", content = "Allez, faites de toutes les nations des disciples, les baptisant au nom du Père, du Fils et du Saint-Esprit, et enseignez-leur à observer tout ce que je vous ai prescrit. Et voici, je suis avec vous tous les jours, jusqu'à la fin du monde."),
                Item(description = "Luc 4.18-19", content = "L'Esprit du Seigneur est sur moi, Parce qu'il m'a oint pour annoncer une bonne nouvelle aux pauvres ; Il m'a envoyé pour guérir ceux qui ont le cœur brisé, Pour proclamer aux captifs la délivrance, Et aux aveugles le recouvrement de la vue, Pour renvoyer libres les opprimés, Pour publier une année de grâce du Seigneur."),
                Item(description = "Luc 6.38", content = "Donnez, et il vous sera donné : on versera dans votre sein une bonne mesure, serrée, secouée et qui déborde ; car on vous mesurera avec la mesure dont vous vous serez servis."),
                Item(description = "Luc 6.40", content = "Le disciple n'est pas plus que le maître ; mais tout disciple accompli sera comme son maître."),
                Item(description = "Colossiens 1.13", content = "qui nous a délivrés de la puissance des ténèbres et nous a transportés dans le royaume du Fils de son amour,"),
                Item(description = "Colossiens 2.10", content = "Vous avez tout pleinement en lui, qui est le chef de toute domination et de toute autorité."),
                Item(description = "Colossiens 3.3-4", content = "Car vous êtes morts, et votre vie est cachée avec Christ en Dieu. Quand Christ, votre vie, paraîtra, alors vous paraîtrez aussi avec lui dans la gloire."),
                Item(description = "Hébreux 11.1", content = "Or la foi est une ferme assurance des choses qu'on espère, une démonstration de celles qu'on ne voit pas."),
                Item(description = "Hébreux 11.6", content = "Or sans la foi il est impossible de lui être agréable ; car il faut que celui qui s'approche de Dieu croie que Dieu existe, et qu'il est le rémunérateur de ceux qui le cherchent."),
                Item(description = "Proverbes 28.13", content = "Celui qui cache ses transgressions ne prospère point, Mais celui qui les avoue et les délaisse obtient miséricorde."),
                Item(description = "Proverbes 11.25", content = "L'âme bienfaisante sera rassasiée, Et celui qui arrose sera lui-même arrosé.")
            )

            itemDao.insertAll(versets)
        }
    }
}

