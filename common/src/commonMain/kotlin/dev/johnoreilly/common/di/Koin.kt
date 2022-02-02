package dev.johnoreilly.common.di

import dev.johnoreilly.common.data.remote.FantasyPremierLeagueApi
import dev.johnoreilly.common.data.repository.FantasyPremierLeagueRepository
import dev.johnoreilly.common.data.repository.FixtureDb
import dev.johnoreilly.common.data.repository.PlayerDb
import dev.johnoreilly.common.data.repository.TeamDb
import io.ktor.client.*
import io.realm.Configuration
import io.realm.Realm
import io.realm.RealmConfiguration
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs = enableNetworkLogs) }

    single<Configuration> { RealmConfiguration.with(schema = setOf(PlayerDb::class, TeamDb::class, FixtureDb::class)) }
    single { Realm.open(get()) }

    single { FantasyPremierLeagueRepository() }
    single { FantasyPremierLeagueApi(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true
    // to avoid 'mutation attempt of frozen kotlin.collections.HashMap@' in K/N
    useAlternativeNames = false
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.NONE
        }
    }
}
