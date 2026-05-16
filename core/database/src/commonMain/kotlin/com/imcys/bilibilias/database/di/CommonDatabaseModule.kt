package com.imcys.bilibilias.database.di

import com.imcys.bilibilias.database.BILIBILIASDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

fun createDatabaseModule(
    databaseProvider: () -> BILIBILIASDatabase,
): Module = module {
    single<BILIBILIASDatabase> {
        databaseProvider()
    }

    factory {
        get<BILIBILIASDatabase>().biliUsersDao()
    }
    factory {
        get<BILIBILIASDatabase>().biliUserCookiesDao()
    }
    factory {
        get<BILIBILIASDatabase>().downloadTaskDao()
    }
}
