package com.webtoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.webtoapp.ui.data.converter.Converters
import com.webtoapp.data.dao.WebAppDao
import com.webtoapp.data.dao.AppCategoryDao
import com.webtoapp.data.dao.AppUsageStatsDao
import com.webtoapp.data.model.WebApp
import com.webtoapp.data.model.AppCategory

/**
 * Room数据库
 */
@Database(
    entities = [WebApp::class, AppCategory::class],
    version = 18,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun webAppDao(): WebAppDao
    abstract fun appCategoryDao(): AppCategoryDao
    abstract fun appUsageStatsDao(): AppUsageStatsDao

    companion object {
        private const val DATABASE_NAME = "webtoapp.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
        
        /**
         * 关闭数据库连接
         * 通常在 Application.onTerminate 或测试时调用
         */
        fun closeDatabase() {
            synchronized(this) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }
        
        /**
         * 通用迁移：添加新列（如果不存在）
         */
        private fun createAddColumnMigration(
            startVersion: Int,
            endVersion: Int,
            columnName: String,
            columnType: String = "TEXT",
            defaultValue: String = "NULL"
        ): Migration {
            return object : Migration(startVersion, endVersion) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    try {
                        db.execSQL("ALTER TABLE web_apps ADD COLUMN $columnName $columnType DEFAULT $defaultValue")
                    } catch (e: Exception) {
                        // 列可能已存在，忽略错误
                        android.util.Log.w("AppDatabase", "迁移 $startVersion->$endVersion 跳过: ${e.message}")
                    }
                }
            }
        }
        
        // 迁移定义 - autoStartConfig
        private val MIGRATION_11_12 = createAddColumnMigration(11, 12, "autoStartConfig")
        private val MIGRATION_10_12 = createAddColumnMigration(10, 12, "autoStartConfig")
        private val MIGRATION_9_12 = createAddColumnMigration(9, 12, "autoStartConfig")
        private val MIGRATION_8_12 = createAddColumnMigration(8, 12, "autoStartConfig")
        
        // 迁移定义 - forcedRunConfig (版本 12 -> 13)
        private val MIGRATION_12_13 = createAddColumnMigration(12, 13, "forcedRunConfig")
        
        // 迁移定义 - blackTechConfig, disguiseConfig (版本 13 -> 14)
        private val MIGRATION_13_14 = object : Migration(13, 14) {
            override fun migrate(db: SupportSQLiteDatabase) {
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN blackTechConfig TEXT DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 13->14 blackTechConfig 跳过: ${e.message}")
                }
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN disguiseConfig TEXT DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 13->14 disguiseConfig 跳过: ${e.message}")
                }
            }
        }
        
        // 迁移定义 - 添加 categoryId 和 app_categories 表 (版本 14 -> 15)
        private val MIGRATION_14_15 = object : Migration(14, 15) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create app_categories 表
                try {
                    db.execSQL("""
                        CREATE TABLE IF NOT EXISTS app_categories (
                            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            name TEXT NOT NULL,
                            icon TEXT NOT NULL DEFAULT '📁',
                            color TEXT NOT NULL DEFAULT '#6200EE',
                            sortOrder INTEGER NOT NULL DEFAULT 0,
                            createdAt INTEGER NOT NULL DEFAULT 0
                        )
                    """.trimIndent())
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 14->15 创建 app_categories 跳过: ${e.message}")
                }
                // 添加 categoryId 列
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN categoryId INTEGER DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 14->15 categoryId 跳过: ${e.message}")
                }
            }
        }
        private val MIGRATION_11_13 = object : Migration(11, 13) {
            override fun migrate(db: SupportSQLiteDatabase) {
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN autoStartConfig TEXT DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 11->13 autoStartConfig 跳过: ${e.message}")
                }
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN forcedRunConfig TEXT DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 11->13 forcedRunConfig 跳过: ${e.message}")
                }
            }
        }
        private val MIGRATION_10_13 = object : Migration(10, 13) {
            override fun migrate(db: SupportSQLiteDatabase) {
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN autoStartConfig TEXT DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 10->13 autoStartConfig 跳过: ${e.message}")
                }
                try {
                    db.execSQL("ALTER TABLE web_apps ADD COLUMN forcedRunConfig TEXT DEFAULT NULL")
                } catch (e: Exception) {
                    android.util.Log.w("AppDatabase", "迁移 10->13 forcedRunConfig 跳过: ${e.message}")
                }
            }
        }
        
        // 迁移定义 - 删除浏览器扩展字段 (版本 16 -> 17)
        // SQLite 不支持直接删除列，需要重建表
        private val MIGRATION_16_17 = object : Migration(16, 17) {
            override fun migrate(db: SupportSQLiteDatabase) {
                android.util.Log.i("AppDatabase", "迁移 16->17: 删除浏览器扩展字段")
                
                // 1. 创建新表（不包含 browserExtensionEnabled 和 browserExtensionIds）
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS web_apps_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        url TEXT NOT NULL,
                        iconPath TEXT,
                        packageName TEXT,
                        appType TEXT NOT NULL DEFAULT 'WEB',
                        mediaConfig TEXT,
                        galleryConfig TEXT,
                        htmlConfig TEXT,
                        activationEnabled INTEGER NOT NULL DEFAULT 0,
                        activationCodes TEXT NOT NULL DEFAULT '[]',
                        activationCodeList TEXT NOT NULL DEFAULT '[]',
                        activationRequireEveryTime INTEGER NOT NULL DEFAULT 0,
                        isActivated INTEGER NOT NULL DEFAULT 0,
                        adsEnabled INTEGER NOT NULL DEFAULT 0,
                        adConfig TEXT,
                        announcementEnabled INTEGER NOT NULL DEFAULT 0,
                        announcement TEXT,
                        adBlockEnabled INTEGER NOT NULL DEFAULT 0,
                        adBlockRules TEXT NOT NULL DEFAULT '[]',
                        webViewConfig TEXT NOT NULL,
                        splashEnabled INTEGER NOT NULL DEFAULT 0,
                        splashConfig TEXT,
                        bgmEnabled INTEGER NOT NULL DEFAULT 0,
                        bgmConfig TEXT,
                        apkExportConfig TEXT,
                        themeType TEXT NOT NULL DEFAULT 'AURORA',
                        translateEnabled INTEGER NOT NULL DEFAULT 0,
                        translateConfig TEXT,
                        extensionModuleIds TEXT NOT NULL DEFAULT '[]',
                        userscriptEnabled INTEGER NOT NULL DEFAULT 0,
                        userscriptIds TEXT NOT NULL DEFAULT '[]',
                        autoStartConfig TEXT,
                        forcedRunConfig TEXT,
                        blackTechConfig TEXT,
                        disguiseConfig TEXT,
                        categoryId INTEGER,
                        createdAt INTEGER NOT NULL DEFAULT 0,
                        updatedAt INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())
                
                // 2. 复制数据（排除删除的列）
                db.execSQL("""
                    INSERT INTO web_apps_new (
                        id, name, url, iconPath, packageName, appType,
                        mediaConfig, galleryConfig, htmlConfig,
                        activationEnabled, activationCodes, activationCodeList, activationRequireEveryTime, isActivated,
                        adsEnabled, adConfig,
                        announcementEnabled, announcement,
                        adBlockEnabled, adBlockRules,
                        webViewConfig,
                        splashEnabled, splashConfig,
                        bgmEnabled, bgmConfig,
                        apkExportConfig, themeType,
                        translateEnabled, translateConfig,
                        extensionModuleIds,
                        userscriptEnabled, userscriptIds,
                        autoStartConfig, forcedRunConfig,
                        blackTechConfig, disguiseConfig,
                        categoryId, createdAt, updatedAt
                    )
                    SELECT 
                        id, name, url, iconPath, packageName, appType,
                        mediaConfig, galleryConfig, htmlConfig,
                        activationEnabled, activationCodes, activationCodeList, activationRequireEveryTime, isActivated,
                        adsEnabled, adConfig,
                        announcementEnabled, announcement,
                        adBlockEnabled, adBlockRules,
                        webViewConfig,
                        splashEnabled, splashConfig,
                        bgmEnabled, bgmConfig,
                        apkExportConfig, themeType,
                        translateEnabled, translateConfig,
                        extensionModuleIds,
                        userscriptEnabled, userscriptIds,
                        autoStartConfig, forcedRunConfig,
                        blackTechConfig, disguiseConfig,
                        categoryId, createdAt, updatedAt
                    FROM web_apps
                """.trimIndent())
                
                // 3. 删除旧表
                db.execSQL("DROP TABLE web_apps")
                
                // 4. 重命名新表
                db.execSQL("ALTER TABLE web_apps_new RENAME TO web_apps")
                
                android.util.Log.i("AppDatabase", "迁移 16->17 完成")
            }
        }
        
        // 迁移定义 - 删除油猴脚本字段 (版本 17 -> 18)
        private val MIGRATION_17_18 = object : Migration(17, 18) {
            override fun migrate(db: SupportSQLiteDatabase) {
                android.util.Log.i("AppDatabase", "迁移 17->18: 删除油猴脚本字段")
                
                // 1. 创建新表（不包含 userscriptEnabled 和 userscriptIds）
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS web_apps_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        url TEXT NOT NULL,
                        iconPath TEXT,
                        packageName TEXT,
                        appType TEXT NOT NULL DEFAULT 'WEB',
                        mediaConfig TEXT,
                        galleryConfig TEXT,
                        htmlConfig TEXT,
                        activationEnabled INTEGER NOT NULL DEFAULT 0,
                        activationCodes TEXT NOT NULL DEFAULT '[]',
                        activationCodeList TEXT NOT NULL DEFAULT '[]',
                        activationRequireEveryTime INTEGER NOT NULL DEFAULT 0,
                        isActivated INTEGER NOT NULL DEFAULT 0,
                        adsEnabled INTEGER NOT NULL DEFAULT 0,
                        adConfig TEXT,
                        announcementEnabled INTEGER NOT NULL DEFAULT 0,
                        announcement TEXT,
                        adBlockEnabled INTEGER NOT NULL DEFAULT 0,
                        adBlockRules TEXT NOT NULL DEFAULT '[]',
                        webViewConfig TEXT NOT NULL,
                        splashEnabled INTEGER NOT NULL DEFAULT 0,
                        splashConfig TEXT,
                        bgmEnabled INTEGER NOT NULL DEFAULT 0,
                        bgmConfig TEXT,
                        apkExportConfig TEXT,
                        themeType TEXT NOT NULL DEFAULT 'AURORA',
                        translateEnabled INTEGER NOT NULL DEFAULT 0,
                        translateConfig TEXT,
                        extensionModuleIds TEXT NOT NULL DEFAULT '[]',
                        autoStartConfig TEXT,
                        forcedRunConfig TEXT,
                        blackTechConfig TEXT,
                        disguiseConfig TEXT,
                        categoryId INTEGER,
                        createdAt INTEGER NOT NULL DEFAULT 0,
                        updatedAt INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())
                
                // 2. 复制数据（排除删除的列）
                db.execSQL("""
                    INSERT INTO web_apps_new (
                        id, name, url, iconPath, packageName, appType,
                        mediaConfig, galleryConfig, htmlConfig,
                        activationEnabled, activationCodes, activationCodeList, activationRequireEveryTime, isActivated,
                        adsEnabled, adConfig,
                        announcementEnabled, announcement,
                        adBlockEnabled, adBlockRules,
                        webViewConfig,
                        splashEnabled, splashConfig,
                        bgmEnabled, bgmConfig,
                        apkExportConfig, themeType,
                        translateEnabled, translateConfig,
                        extensionModuleIds,
                        autoStartConfig, forcedRunConfig,
                        blackTechConfig, disguiseConfig,
                        categoryId, createdAt, updatedAt
                    )
                    SELECT 
                        id, name, url, iconPath, packageName, appType,
                        mediaConfig, galleryConfig, htmlConfig,
                        activationEnabled, activationCodes, activationCodeList, activationRequireEveryTime, isActivated,
                        adsEnabled, adConfig,
                        announcementEnabled, announcement,
                        adBlockEnabled, adBlockRules,
                        webViewConfig,
                        splashEnabled, splashConfig,
                        bgmEnabled, bgmConfig,
                        apkExportConfig, themeType,
                        translateEnabled, translateConfig,
                        extensionModuleIds,
                        autoStartConfig, forcedRunConfig,
                        blackTechConfig, disguiseConfig,
                        categoryId, createdAt, updatedAt
                    FROM web_apps
                """.trimIndent())
                
                // 3. 删除旧表
                db.execSQL("DROP TABLE web_apps")
                
                // 4. 重命名新表
                db.execSQL("ALTER TABLE web_apps_new RENAME TO web_apps")
                
                android.util.Log.i("AppDatabase", "迁移 17->18 完成")
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addMigrations(
                    MIGRATION_8_12,
                    MIGRATION_9_12,
                    MIGRATION_10_12,
                    MIGRATION_11_12,
                    MIGRATION_12_13,
                    MIGRATION_11_13,
                    MIGRATION_10_13,
                    MIGRATION_13_14,
                    MIGRATION_14_15,
                    MIGRATION_16_17,
                    MIGRATION_17_18
                )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
