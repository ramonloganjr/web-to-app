package com.webtoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.webtoapp.ui.data.converter.Converters

/**
 * 应用类型
 */
enum class AppType {
    WEB,      // Web page应用（默认）
    IMAGE,    // Image展示应用（单图片，兼容旧版）
    VIDEO,    // Video播放应用（单视频，兼容旧版）
    HTML,     // LocalHTML应用（支持HTML+CSS+JS）
    GALLERY,  // Media画廊应用（多图片/视频，支持分类、排序、连续播放）
    FRONTEND, // 前端项目应用（Vue/React/Vite 等构建产物）
    NODEJS_APP, // Node.js 应用
    WORDPRESS,  // WordPress 应用
    PHP_APP,    // PHP 应用
    PYTHON_APP, // Python 应用
    GO_APP      // Go 应用
}

/**
 * WebApp实体类 - 存储用户创建的应用配置
 */
@Entity(tableName = "web_apps")
@TypeConverters(Converters::class)
data class WebApp(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // 基本信息
    val name: String,
    val url: String,                           // WEB类型为URL，IMAGE/VIDEO类型为媒体文件路径
    val iconPath: String? = null,
    val packageName: String? = null,
    val appType: AppType = AppType.WEB,        // App类型
    
    // Media应用配置（仅 IMAGE/VIDEO 类型，兼容旧版）
    val mediaConfig: MediaConfig? = null,
    
    // Media画廊配置（仅 GALLERY 类型，支持多媒体）
    val galleryConfig: GalleryConfig? = null,
    
    // HTML应用配置（仅 HTML 类型）
    val htmlConfig: HtmlConfig? = null,

    // Activation码配置
    val activationEnabled: Boolean = false,
    val activationCodes: List<String> = emptyList(),  // 旧格式（兼容性）
    val activationCodeList: List<com.webtoapp.core.activation.ActivationCode> = emptyList(),  // 新格式
    val activationRequireEveryTime: Boolean = false,  // Yes否每次启动都需要验证
    val isActivated: Boolean = false,

    // Ad配置
    val adsEnabled: Boolean = false,
    val adConfig: AdConfig? = null,

    // Announcement配置
    val announcementEnabled: Boolean = false,
    val announcement: Announcement? = null,

    // Ad拦截配置
    val adBlockEnabled: Boolean = false,
    val adBlockRules: List<String> = emptyList(),

    // WebView配置
    val webViewConfig: WebViewConfig = WebViewConfig(),

    // Start画面配置
    val splashEnabled: Boolean = false,
    val splashConfig: SplashConfig? = null,

    // Background music配置
    val bgmEnabled: Boolean = false,
    val bgmConfig: BgmConfig? = null,
    
    // APK 导出配置（仅打包APK时生效）
    val apkExportConfig: ApkExportConfig? = null,
    
    // Theme配置（用于导出的应用 UI 风格）
    val themeType: String = "AURORA",
    
    // Web page自动翻译配置
    val translateEnabled: Boolean = false,
    val translateConfig: TranslateConfig? = null,
    
    // 扩展模块配置
    val extensionModuleIds: List<String> = emptyList(),  // Enable的扩展模块ID列表
    
    // 自启动配置
    val autoStartConfig: AutoStartConfig? = null,
    
    // 强制运行配置
    val forcedRunConfig: com.webtoapp.core.forcedrun.ForcedRunConfig? = null,
    
    // 黑科技功能配置（独立模块）
    val blackTechConfig: com.webtoapp.core.blacktech.BlackTechConfig? = null,
    
    // App伪装配置（独立模块）
    val disguiseConfig: com.webtoapp.core.disguise.DisguiseConfig? = null,
    
    // 分类ID（关联 AppCategory）
    val categoryId: Long? = null,

    // 元数据
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * 广告配置
 */
data class AdConfig(
    val bannerEnabled: Boolean = false,
    val bannerId: String = "",
    val interstitialEnabled: Boolean = false,
    val interstitialId: String = "",
    val splashEnabled: Boolean = false,
    val splashId: String = "",
    val splashDuration: Int = 3 // 秒
)

/**
 * 公告模板类型
 */
enum class AnnouncementTemplateType {
    MINIMAL,        // 极简风格
    XIAOHONGSHU,    // 小红书风格
    GRADIENT,       // 渐变风格
    GLASSMORPHISM,  // 毛玻璃风格
    NEON,           // 霓虹风格
    CUTE,           // 可爱风格
    ELEGANT,        // 优雅风格
    FESTIVE,        // 节日风格
    DARK,           // 暗黑风格
    NATURE          // 自然风格
}

/**
 * 公告触发模式
 */
enum class AnnouncementTriggerMode {
    ON_LAUNCH,      // Start时触发
    ON_INTERVAL,    // 定时间隔触发
    ON_NO_NETWORK   // 无网络时触发
}

/**
 * 公告配置
 */
data class Announcement(
    val title: String = "",
    val content: String = "",
    val linkUrl: String? = null,
    val linkText: String? = null,
    val showOnce: Boolean = true,
    val enabled: Boolean = true,
    val version: Int = 1, // 用于判断是否显示过
    val template: AnnouncementTemplateType = AnnouncementTemplateType.XIAOHONGSHU, // Announcement模板
    val showEmoji: Boolean = true, // Yes否显示表情
    val animationEnabled: Boolean = true, // Yes否启用动画
    // 新增：需要勾选同意/已阅读才能关闭
    val requireConfirmation: Boolean = false,
    // 新增：允许用户勾选不再显示
    val allowNeverShow: Boolean = true,
    
    // ==================== 触发机制 ====================
    // Start时触发（默认开启，保持backward compatible）
    val triggerOnLaunch: Boolean = true,
    // 无网络时触发
    val triggerOnNoNetwork: Boolean = false,
    // 定时间隔触发（分钟，0=禁用）
    val triggerIntervalMinutes: Int = 0,
    // 定时触发是否在启动时也立即触发一次
    val triggerIntervalIncludeLaunch: Boolean = false
)

/**
 * 状态栏颜色模式
 */
enum class StatusBarColorMode {
    THEME,      // 跟随主题色（默认）
    TRANSPARENT,// 完全透明
    CUSTOM      // Custom颜色
}

/**
 * Status bar background类型
 */
enum class StatusBarBackgroundType {
    COLOR,  // 纯色背景（使用 statusBarColor）
    IMAGE   // Image背景
}


/**
 * 长按菜单样式
 */
enum class LongPressMenuStyle {
    DISABLED,       // Disable长按菜单
    SIMPLE,         // 简洁模式：仅保存图片、复制链接
    FULL,           // 完整模式：所有功能
    IOS,            // iOS 风格：类似 iPhone 的模糊背景菜单
    FLOATING,       // 悬浮气泡：在点击位置显示小气泡
    CONTEXT         // 右键菜单：类似桌面端右键菜单
}

/**
 * User-Agent 模式
 * 用于伪装浏览器身份，绕过网站对 WebView 的检测
 */
enum class UserAgentMode(
    val displayName: String,
    val description: String,
    val userAgentString: String?
) {
    DEFAULT(
        "System Default",
        "Use Android WebView default User-Agent",
        null
    ),
    CHROME_MOBILE(
        "Chrome Mobile",
        "Disguise as Chrome Android browser",
        "Mozilla/5.0 (Linux; Android 14; Pixel 8 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
    ),
    CHROME_DESKTOP(
        "Chrome Desktop",
        "Disguise as Chrome Windows browser",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    ),
    SAFARI_MOBILE(
        "Safari Mobile",
        "Disguise as Safari iOS browser",
        "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Mobile/15E148 Safari/604.1"
    ),
    SAFARI_DESKTOP(
        "Safari Desktop",
        "Disguise as Safari macOS browser",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_0) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Safari/605.1.15"
    ),
    FIREFOX_MOBILE(
        "Firefox Mobile",
        "Disguise as Firefox Android browser",
        "Mozilla/5.0 (Android 14; Mobile; rv:120.0) Gecko/120.0 Firefox/120.0"
    ),
    FIREFOX_DESKTOP(
        "Firefox Desktop",
        "Disguise as Firefox Windows browser",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:120.0) Gecko/20100101 Firefox/120.0"
    ),
    EDGE_MOBILE(
        "Edge Mobile",
        "Disguise as Edge Android browser",
        "Mozilla/5.0 (Linux; Android 14; Pixel 8 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36 EdgA/120.0.0.0"
    ),
    EDGE_DESKTOP(
        "Edge Desktop",
        "Disguise as Edge Windows browser",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0"
    ),
    CUSTOM(
        "Custom",
        "Use custom User-Agent string",
        null
    )
}

/**
 * WebView configuration
 */
data class WebViewConfig(
    val javaScriptEnabled: Boolean = true,
    val domStorageEnabled: Boolean = true,
    val allowFileAccess: Boolean = false,
    val allowContentAccess: Boolean = true,
    val cacheEnabled: Boolean = true,
    val userAgent: String? = null,
    val userAgentMode: UserAgentMode = UserAgentMode.DEFAULT, // User-Agent 模式
    val customUserAgent: String? = null, // Custom User-Agent（仅 CUSTOM 模式使用）
    val desktopMode: Boolean = false, // Keep forbackward compatible
    val zoomEnabled: Boolean = true,
    val swipeRefreshEnabled: Boolean = true,
    val fullscreenEnabled: Boolean = true,
    val downloadEnabled: Boolean = true,
    val openExternalLinks: Boolean = false, // External链接是否在浏览器打开
    val hideToolbar: Boolean = false, // Hide工具栏（全屏模式，无浏览器特征）
    val showStatusBarInFullscreen: Boolean = false, // Fullscreen模式下是否显示状态栏
    val landscapeMode: Boolean = false, // Landscape模式
    val injectScripts: List<UserScript> = emptyList(), // User自定义注入脚本
    val statusBarColorMode: StatusBarColorMode = StatusBarColorMode.THEME, // Status bar颜色模式
    val statusBarColor: String? = null, // Custom状态栏颜色（仅 CUSTOM 模式生效，如 "#FF5722"）
    val statusBarDarkIcons: Boolean? = null, // Status bar图标颜色：true=深色图标，false=浅色图标，null=自动
    // Status bar背景配置（新增）
    val statusBarBackgroundType: StatusBarBackgroundType = StatusBarBackgroundType.COLOR, // Background type
    val statusBarBackgroundImage: String? = null, // Cropped image path
    val statusBarBackgroundAlpha: Float = 1.0f, // Alpha 0.0-1.0
    val statusBarHeightDp: Int = 0, // Custom高度dp（0=系统默认）
    val longPressMenuEnabled: Boolean = true, // Yes否启用长按菜单
    val longPressMenuStyle: LongPressMenuStyle = LongPressMenuStyle.FULL, // Long press menu style
    val adBlockToggleEnabled: Boolean = false, // Allow用户在运行时切换广告拦截开关
    val popupBlockerEnabled: Boolean = true, // 启用弹窗拦截器（拦截 window.open 等弹窗广告）
    val popupBlockerToggleEnabled: Boolean = false, // Allow用户在运行时切换弹窗拦截开关
    
    // ============ 浏览器兼容性增强配置 ============
    val initialScale: Int = 0, // Initial scale (0-200, 0=自动)，解决 CSS zoom 不生效问题
    val newWindowBehavior: NewWindowBehavior = NewWindowBehavior.SAME_WINDOW, // window.open / target="_blank" 行为
    val enablePaymentSchemes: Boolean = true, // Enable支付宝、微信等支付 scheme 拦截
    val enableShareBridge: Boolean = true, // Enable navigator.share 桥接
    val enableZoomPolyfill: Boolean = true, // Enable CSS zoom polyfill（自动转换为 transform）
    
    // ============ 高级功能配置 ============
    val enableCrossOriginIsolation: Boolean = false // 启用跨域隔离（SharedArrayBuffer/FFmpeg.wasm 支持）
)

/**
 * User custom script (Tampermonkey style)
 */
data class UserScript(
    val name: String = "",           // Script名称
    val code: String = "",           // JavaScript 代码
    val enabled: Boolean = true,     // Yes否启用
    val runAt: ScriptRunTime = ScriptRunTime.DOCUMENT_END // 运行时机
)

/**
 * Script run timing
 */
enum class ScriptRunTime {
    DOCUMENT_START, // Page开始加载时（DOM 未就绪）
    DOCUMENT_END,   // DOM 就绪后（推荐）
    DOCUMENT_IDLE   // Page完全加载后
}

/**
 * New window open behavior（window.open / target="_blank"）
 */
enum class NewWindowBehavior {
    SAME_WINDOW,    // 在当前窗口打开（默认）
    EXTERNAL_BROWSER, // Open in external browser
    POPUP_WINDOW,   // 弹出新窗口（需要处理）
    BLOCK           // Block opening
}

/**
 * Splash screen configuration
 */
data class SplashConfig(
    val type: SplashType = SplashType.IMAGE,  // Class型：图片或视频
    val mediaPath: String? = null,             // Media文件路径
    val duration: Int = 3,                     // Image显示时长（秒，1-5秒）
    val clickToSkip: Boolean = true,           // Yes否允许点击跳过
    val orientation: SplashOrientation = SplashOrientation.PORTRAIT, // Show方向
    val fillScreen: Boolean = true,            // Yes否自动放大铺满屏幕
    val enableAudio: Boolean = false,          // Yes否启用视频音频
    
    // Video裁剪配置
    val videoStartMs: Long = 0,                // Video裁剪起始时间（毫秒）
    val videoEndMs: Long = 5000,               // Video裁剪结束时间（毫秒）
    val videoDurationMs: Long = 0              // 原视频总时长（毫秒）
)

/**
 * 启动画面类型
 */
enum class SplashType {
    IMAGE,  // Image
    VIDEO   // Video
}

/**
 * 启动画面显示方向
 */
enum class SplashOrientation {
    PORTRAIT,   // Portrait
    LANDSCAPE   // Landscape
}

/**
 * Media app configuration（图片/视频转APP）- 兼容旧版单媒体模式
 */
data class MediaConfig(
    val mediaPath: String,                         // Media文件路径
    val enableAudio: Boolean = true,               // Video是否启用音频
    val loop: Boolean = true,                      // Yes否循环播放（视频）
    val autoPlay: Boolean = true,                  // Yes否自动播放（视频）
    val fillScreen: Boolean = true,                // Yes否铺满屏幕
    val orientation: SplashOrientation = SplashOrientation.PORTRAIT, // Show方向
    val backgroundColor: String = "#000000"        // 背景颜色
)

// ==================== 媒体画廊配置（新版多媒体支持）====================

/**
 * Media gallery configuration - 支持多图片/视频、分类、排序、连续播放
 */
data class GalleryConfig(
    val items: List<GalleryItem> = emptyList(),                      // Media项列表
    val categories: List<GalleryCategory> = emptyList(),             // 分类列表
    val playMode: GalleryPlayMode = GalleryPlayMode.SEQUENTIAL,      // Play模式
    val imageInterval: Int = 3,                                      // Image播放间隔（秒，1-60）
    val loop: Boolean = true,                                        // Yes否循环播放
    val autoPlay: Boolean = false,                                   // 进入后是否自动播放
    val shuffleOnLoop: Boolean = false,                              // Loop时是否打乱顺序
    val defaultView: GalleryViewMode = GalleryViewMode.GRID,         // Default视图模式
    val gridColumns: Int = 3,                                        // 网格列数（2-5）
    val sortOrder: GallerySortOrder = GallerySortOrder.CUSTOM,       // Sort方式
    val backgroundColor: String = "#000000",                         // Play器背景颜色
    val showThumbnailBar: Boolean = true,                            // Play时显示底部缩略图栏
    val showMediaInfo: Boolean = true,                               // Show媒体信息（名称、索引等）
    val orientation: SplashOrientation = SplashOrientation.PORTRAIT, // 屏幕方向
    val enableAudio: Boolean = true,                                 // Video是否启用音频
    val videoAutoNext: Boolean = true,                               // Video播放完自动下一个
    val rememberPosition: Boolean = false                            // 记住上次播放位置
) {
    /**
     * Get media items by category
     */
    fun getItemsByCategory(categoryId: String?): List<GalleryItem> {
        return if (categoryId == null) {
            items
        } else {
            items.filter { it.categoryId == categoryId }
        }
    }
    
    /**
     * Get sorted media items
     */
    fun getSortedItems(categoryId: String? = null): List<GalleryItem> {
        val filtered = getItemsByCategory(categoryId)
        return when (sortOrder) {
            GallerySortOrder.CUSTOM -> filtered.sortedBy { it.sortIndex }
            GallerySortOrder.NAME_ASC -> filtered.sortedBy { it.name.lowercase() }
            GallerySortOrder.NAME_DESC -> filtered.sortedByDescending { it.name.lowercase() }
            GallerySortOrder.DATE_ASC -> filtered.sortedBy { it.createdAt }
            GallerySortOrder.DATE_DESC -> filtered.sortedByDescending { it.createdAt }
            GallerySortOrder.TYPE -> filtered.sortedBy { it.type.ordinal }
        }
    }
    
    /**
     * Statistics
     */
    val imageCount: Int get() = items.count { it.type == GalleryItemType.IMAGE }
    val videoCount: Int get() = items.count { it.type == GalleryItemType.VIDEO }
    val totalCount: Int get() = items.size
}

/**
 * Gallery media item
 */
data class GalleryItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val path: String,                                // Media文件路径
    val type: GalleryItemType,                       // Media类型
    val name: String = "",                           // Show名称
    val categoryId: String? = null,                  // 所属分类ID
    val duration: Long = 0,                          // Video时长（毫秒）
    val thumbnailPath: String? = null,               // 缩略图路径
    val sortIndex: Int = 0,                          // 手动排序索引
    val createdAt: Long = System.currentTimeMillis(),// 添加时间
    val width: Int = 0,                              // Media宽度
    val height: Int = 0,                             // Media高度
    val fileSize: Long = 0                           // File大小（字节）
) {
    /**
     * 格式化的时长显示（视频）
     */
    val formattedDuration: String
        get() {
            if (type != GalleryItemType.VIDEO || duration <= 0) return ""
            val seconds = (duration / 1000) % 60
            val minutes = (duration / 1000 / 60) % 60
            val hours = duration / 1000 / 60 / 60
            return if (hours > 0) {
                String.format("%d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format("%d:%02d", minutes, seconds)
            }
        }
    
    /**
     * 格式化的文件大小显示
     */
    val formattedFileSize: String
        get() {
            if (fileSize <= 0) return ""
            return when {
                fileSize < 1024 -> "$fileSize B"
                fileSize < 1024 * 1024 -> String.format("%.1f KB", fileSize / 1024.0)
                fileSize < 1024 * 1024 * 1024 -> String.format("%.1f MB", fileSize / 1024.0 / 1024.0)
                else -> String.format("%.2f GB", fileSize / 1024.0 / 1024.0 / 1024.0)
            }
        }
}

/**
 * 画廊分类
 */
data class GalleryCategory(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,                                // 分类名称
    val icon: String = "📁",                        // 分类图标（emoji）
    val color: String = "#6200EE",                   // 分类颜色
    val sortIndex: Int = 0                           // Sort索引
)

/**
 * 媒体项类型
 */
enum class GalleryItemType {
    IMAGE,  // Image
    VIDEO   // Video
}

/**
 * 画廊播放模式
 */
enum class GalleryPlayMode {
    SEQUENTIAL,   // Sequential播放
    SHUFFLE,      // Shuffle播放
    SINGLE_LOOP   // 单个循环
}

/**
 * 画廊视图模式
 */
enum class GalleryViewMode {
    GRID,         // 网格视图
    LIST,         // List视图
    TIMELINE      // Time线视图
}

/**
 * 画廊排序方式
 */
enum class GallerySortOrder {
    CUSTOM,       // Custom排序（手动拖拽）
    NAME_ASC,     // Name升序
    NAME_DESC,    // Name降序
    DATE_ASC,     // Date升序（最早在前）
    DATE_DESC,    // Date降序（最新在前）
    TYPE          // 按类型分组（图片在前/视频在前）
}

/**
 * HTML应用配置（本地HTML+CSS+JS转APP）
 */
data class HtmlConfig(
    val projectId: String = "",                    // 项目ID（用于定位文件目录）
    val projectDir: String? = null,                // 项目目录路径（用于遍历嵌入）
    val entryFile: String = "index.html",          // 入口HTML文件名
    val files: List<HtmlFile> = emptyList(),       // 所有文件列表（HTML/CSS/JS等）
    val enableJavaScript: Boolean = true,          // Yes否启用JavaScript
    val enableLocalStorage: Boolean = true,        // Yes否启用本地存储
    val allowFileAccess: Boolean = true,           // Yes否允许文件访问
    val backgroundColor: String = "#FFFFFF",       // 背景颜色
    val landscapeMode: Boolean = false             // Landscape模式
) {
    /**
     * 获取有效的入口文件名
     * 验证 entryFile 必须有文件名部分（不能只是 .html 或空字符串）
     */
    fun getValidEntryFile(): String {
        return entryFile.takeIf { 
            it.isNotBlank() && it.substringBeforeLast(".").isNotBlank() 
        } ?: "index.html"
    }
}

/**
 * HTML项目中的单个文件
 */
data class HtmlFile(
    val name: String,                              // File名（含相对路径，如 "css/style.css"）
    val path: String,                              // Local绝对路径
    val type: HtmlFileType = HtmlFileType.OTHER    // File类型
)

/**
 * HTML文件类型
 */
enum class HtmlFileType {
    HTML,   // HTML文件
    CSS,    // CSS样式文件
    JS,     // JavaScript文件
    IMAGE,  // Image资源
    FONT,   // 字体文件
    OTHER   // 其他文件
}

/**
 * 背景音乐播放模式
 */
enum class BgmPlayMode {
    LOOP,       // 单曲循环
    SEQUENTIAL, // Sequential播放
    SHUFFLE     // Shuffle播放
}

/**
 * 音乐标签 - 用于分类
 */
enum class BgmTag {
    PURE_MUSIC,
    POP,
    ROCK,
    CLASSICAL,
    JAZZ,
    ELECTRONIC,
    FOLK,
    CHINESE_STYLE,
    ANIME,
    GAME,
    MOVIE,
    HEALING,
    EXCITING,
    SAD,
    ROMANTIC,
    RELAXING,
    WORKOUT,
    SLEEP,
    STUDY,
    OTHER;
    
    val displayName: String get() = when (this) {
        PURE_MUSIC -> com.webtoapp.core.i18n.Strings.bgmTagPureMusic
        POP -> com.webtoapp.core.i18n.Strings.bgmTagPop
        ROCK -> com.webtoapp.core.i18n.Strings.bgmTagRock
        CLASSICAL -> com.webtoapp.core.i18n.Strings.bgmTagClassical
        JAZZ -> com.webtoapp.core.i18n.Strings.bgmTagJazz
        ELECTRONIC -> com.webtoapp.core.i18n.Strings.bgmTagElectronic
        FOLK -> com.webtoapp.core.i18n.Strings.bgmTagFolk
        CHINESE_STYLE -> com.webtoapp.core.i18n.Strings.bgmTagChineseStyle
        ANIME -> com.webtoapp.core.i18n.Strings.bgmTagAnime
        GAME -> com.webtoapp.core.i18n.Strings.bgmTagGame
        MOVIE -> com.webtoapp.core.i18n.Strings.bgmTagMovie
        HEALING -> com.webtoapp.core.i18n.Strings.bgmTagHealing
        EXCITING -> com.webtoapp.core.i18n.Strings.bgmTagExciting
        SAD -> com.webtoapp.core.i18n.Strings.bgmTagSad
        ROMANTIC -> com.webtoapp.core.i18n.Strings.bgmTagRomantic
        RELAXING -> com.webtoapp.core.i18n.Strings.bgmTagRelaxing
        WORKOUT -> com.webtoapp.core.i18n.Strings.bgmTagWorkout
        SLEEP -> com.webtoapp.core.i18n.Strings.bgmTagSleep
        STUDY -> com.webtoapp.core.i18n.Strings.bgmTagStudy
        OTHER -> com.webtoapp.core.i18n.Strings.bgmTagOther
    }
}

/**
 * LRC 字幕元素
 */
data class LrcLine(
    val startTime: Long,    // Start时间（毫秒）
    val endTime: Long,      // End时间（毫秒）
    val text: String,       // Lyrics文本
    val translation: String? = null  // 翻译（可选）
)

/**
 * LRC 字幕数据
 */
data class LrcData(
    val lines: List<LrcLine> = emptyList(),
    val title: String? = null,
    val artist: String? = null,
    val album: String? = null,
    val language: String? = null
)

/**
 * 字幕主题样式
 */
data class LrcTheme(
    val id: String,
    val name: String,
    val fontFamily: String = "default",
    val fontSize: Float = 18f,
    val textColor: String = "#FFFFFF",
    val highlightColor: String = "#FFD700",
    val backgroundColor: String = "#80000000",
    val strokeColor: String? = null,
    val strokeWidth: Float = 0f,
    val shadowEnabled: Boolean = true,
    val animationType: LrcAnimationType = LrcAnimationType.FADE,
    val position: LrcPosition = LrcPosition.BOTTOM,
    val showTranslation: Boolean = true
)

/**
 * 字幕动画类型
 */
enum class LrcAnimationType {
    NONE, FADE, SLIDE_UP, SLIDE_LEFT, SCALE, TYPEWRITER, KARAOKE;
    
    val displayName: String get() = when (this) {
        NONE -> com.webtoapp.core.i18n.Strings.lrcAnimNone
        FADE -> com.webtoapp.core.i18n.Strings.lrcAnimFade
        SLIDE_UP -> com.webtoapp.core.i18n.Strings.lrcAnimSlideUp
        SLIDE_LEFT -> com.webtoapp.core.i18n.Strings.lrcAnimSlideLeft
        SCALE -> com.webtoapp.core.i18n.Strings.lrcAnimScale
        TYPEWRITER -> com.webtoapp.core.i18n.Strings.lrcAnimTypewriter
        KARAOKE -> com.webtoapp.core.i18n.Strings.lrcAnimKaraoke
    }
}

/**
 * 字幕位置
 */
enum class LrcPosition {
    TOP, CENTER, BOTTOM;
    
    val displayName: String get() = when (this) {
        TOP -> com.webtoapp.core.i18n.Strings.lrcPosTop
        CENTER -> com.webtoapp.core.i18n.Strings.lrcPosCenter
        BOTTOM -> com.webtoapp.core.i18n.Strings.lrcPosBottom
    }
}

/**
 * 背景音乐项
 */
data class BgmItem(
    val id: String = java.util.UUID.randomUUID().toString(),  // 唯一ID
    val name: String,           // 音乐名称
    val path: String,           // 音乐文件路径
    val coverPath: String? = null, // 封面图片路径（可选）
    val isAsset: Boolean = false,  // Yes否为预置资源
    val tags: List<BgmTag> = emptyList(),  // 标签
    val sortOrder: Int = 0,     // Sort顺序
    val lrcData: LrcData? = null,  // LRC 字幕数据
    val lrcPath: String? = null,   // LRC 文件路径
    val duration: Long = 0      // 音乐时长（毫秒）
)

/**
 * 背景音乐配置
 */
data class BgmConfig(
    val playlist: List<BgmItem> = emptyList(),  // Play列表
    val playMode: BgmPlayMode = BgmPlayMode.LOOP, // Play模式
    val volume: Float = 0.5f,                    // Volume (0.0-1.0)
    val autoPlay: Boolean = true,                // Yes否自动播放
    val showLyrics: Boolean = true,              // Yes否显示歌词
    val lrcTheme: LrcTheme? = null               // 字幕主题
)

/**
 * APK 架构选择
 */
enum class ApkArchitecture(
    val abiFilters: List<String>
) {
    UNIVERSAL(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")),
    ARM64(listOf("arm64-v8a", "x86_64")),
    ARM32(listOf("armeabi-v7a", "x86"));
    
    val displayName: String get() = when (this) {
        UNIVERSAL -> com.webtoapp.core.i18n.Strings.archUniversal
        ARM64 -> com.webtoapp.core.i18n.Strings.archArm64
        ARM32 -> com.webtoapp.core.i18n.Strings.archArm32
    }
    
    val description: String get() = when (this) {
        UNIVERSAL -> com.webtoapp.core.i18n.Strings.archUniversalDesc
        ARM64 -> com.webtoapp.core.i18n.Strings.archArm64Desc
        ARM32 -> com.webtoapp.core.i18n.Strings.archArm32Desc
    }
    
    companion object {
        fun fromName(name: String): ApkArchitecture {
            return entries.find { it.name == name } ?: UNIVERSAL
        }
    }
}

/**
 * APK 导出配置（仅打包APK时生效）
 */
data class ApkExportConfig(
    val customPackageName: String? = null,       // Custom包名（如 com.example.myapp）
    val customVersionName: String? = null,       // Custom版本名（如 1.0.0）
    val customVersionCode: Int? = null,          // Custom版本号（如 1）
    val architecture: ApkArchitecture = ApkArchitecture.UNIVERSAL,  // APK架构
    val encryptionConfig: ApkEncryptionConfig = ApkEncryptionConfig(),  // Encryption配置
    val isolationConfig: com.webtoapp.core.isolation.IsolationConfig = com.webtoapp.core.isolation.IsolationConfig(),  // 独立环境/多开配置
    val backgroundRunEnabled: Boolean = false,   // Yes否启用后台运行
    val backgroundRunConfig: BackgroundRunExportConfig = BackgroundRunExportConfig()  // 后台运行配置
)

/**
 * 后台运行导出配置
 */
data class BackgroundRunExportConfig(
    val notificationTitle: String = "",          // 通知标题
    val notificationContent: String = "",        // 通知内容
    val showNotification: Boolean = true,        // Yes否显示通知
    val keepCpuAwake: Boolean = true             // Yes否保持CPU唤醒
)

/**
 * APK 加密配置
 */
data class ApkEncryptionConfig(
    val enabled: Boolean = false,                // Yes否启用加密
    val encryptConfig: Boolean = true,           // Encryption配置文件
    val encryptHtml: Boolean = true,             // Encryption HTML/CSS/JS
    val encryptMedia: Boolean = false,           // Encryption媒体文件（图片/视频）
    val encryptSplash: Boolean = false,          // Encryption启动画面
    val encryptBgm: Boolean = false,             // Encryption背景音乐
    val customPassword: String? = null,          // Custom密码（可选，增强安全性）
    val enableIntegrityCheck: Boolean = true,    // Enable完整性检查
    val enableAntiDebug: Boolean = true,         // Enable反调试保护
    val enableAntiTamper: Boolean = true,        // Enable防篡改保护
    val obfuscateStrings: Boolean = false,       // 混淆字符串（实验性）
    val encryptionLevel: EncryptionLevel = EncryptionLevel.STANDARD  // Encryption强度
) {
    /**
     * 加密强度级别
     */
    enum class EncryptionLevel(val iterations: Int) {
        FAST(5000),
        STANDARD(10000),
        HIGH(50000),
        PARANOID(100000);
        
        val description: String get() = when (this) {
            FAST -> com.webtoapp.core.i18n.Strings.encryptLevelFast
            STANDARD -> com.webtoapp.core.i18n.Strings.encryptLevelStandard
            HIGH -> com.webtoapp.core.i18n.Strings.encryptLevelHigh
            PARANOID -> com.webtoapp.core.i18n.Strings.encryptLevelParanoid
        }
    }
    
    companion object {
        /** 不加密 */
        val DISABLED = ApkEncryptionConfig(enabled = false)
        
        /** 基础加密（仅加密代码和配置） */
        val BASIC = ApkEncryptionConfig(
            enabled = true,
            encryptConfig = true,
            encryptHtml = true,
            encryptMedia = false,
            enableIntegrityCheck = true,
            enableAntiDebug = false,
            encryptionLevel = EncryptionLevel.STANDARD
        )
        
        /** 完全加密（加密所有资源） */
        val FULL = ApkEncryptionConfig(
            enabled = true,
            encryptConfig = true,
            encryptHtml = true,
            encryptMedia = true,
            encryptSplash = true,
            encryptBgm = true,
            enableIntegrityCheck = true,
            enableAntiDebug = true,
            enableAntiTamper = true,
            encryptionLevel = EncryptionLevel.HIGH
        )
        
        /** 最高安全级别 */
        val MAXIMUM = ApkEncryptionConfig(
            enabled = true,
            encryptConfig = true,
            encryptHtml = true,
            encryptMedia = true,
            encryptSplash = true,
            encryptBgm = true,
            enableIntegrityCheck = true,
            enableAntiDebug = true,
            enableAntiTamper = true,
            obfuscateStrings = true,
            encryptionLevel = EncryptionLevel.PARANOID
        )
    }
    
    /** 转换为内部加密配置 */
    fun toEncryptionConfig(): com.webtoapp.core.crypto.EncryptionConfig {
        return com.webtoapp.core.crypto.EncryptionConfig(
            enabled = enabled,
            encryptConfig = encryptConfig,
            encryptHtml = encryptHtml,
            encryptMedia = encryptMedia,
            encryptSplash = encryptSplash,
            encryptBgm = encryptBgm,
            customPassword = customPassword,
            enableIntegrityCheck = enableIntegrityCheck,
            enableAntiDebug = enableAntiDebug,
            enableAntiTamper = enableAntiTamper,
            enableRootDetection = false,
            enableEmulatorDetection = false,
            obfuscateStrings = obfuscateStrings,
            encryptionLevel = when (encryptionLevel) {
                EncryptionLevel.FAST -> com.webtoapp.core.crypto.EncryptionLevel.FAST
                EncryptionLevel.STANDARD -> com.webtoapp.core.crypto.EncryptionLevel.STANDARD
                EncryptionLevel.HIGH -> com.webtoapp.core.crypto.EncryptionLevel.HIGH
                EncryptionLevel.PARANOID -> com.webtoapp.core.crypto.EncryptionLevel.PARANOID
            },
            enableRuntimeProtection = enableIntegrityCheck || enableAntiDebug || enableAntiTamper,
            blockOnThreat = false
        )
    }
}

/**
 * 翻译目标语言
 */
enum class TranslateLanguage(val code: String, val displayName: String) {
    CHINESE("zh-CN", "中文"),
    ENGLISH("en", "英文"),
    JAPANESE("ja", "日文"),
    ARABIC("ar", "阿拉伯语")
}

/**
 * 网页自动翻译配置
 */
data class TranslateConfig(
    val targetLanguage: TranslateLanguage = TranslateLanguage.CHINESE,  // 目标翻译语言
    val showFloatingButton: Boolean = true  // Yes否显示翻译悬浮按钮
)

/**
 * WebApp 扩展函数 - 获取所有激活码（兼容新旧格式）
 */
fun WebApp.getAllActivationCodes(): List<com.webtoapp.core.activation.ActivationCode> {
    val codes = mutableListOf<com.webtoapp.core.activation.ActivationCode>()
    
    // 添加新格式激活码
    codes.addAll(activationCodeList)
    
    // 添加旧格式激活码（转换为新格式）
    activationCodes.forEach { codeStr ->
        // 尝试解析为新格式
        val code = com.webtoapp.core.activation.ActivationCode.fromJson(codeStr)
        if (code != null) {
            codes.add(code)
        } else {
            // 旧格式，转换为永久激活码
            codes.add(com.webtoapp.core.activation.ActivationCode.fromLegacyString(codeStr))
        }
    }
    
    return codes
}

/**
 * WebApp 扩展函数 - 获取激活码字符串列表（用于兼容旧代码）
 */
fun WebApp.getActivationCodeStrings(): List<String> {
    val strings = mutableListOf<String>()
    
    // 添加新格式激活码的 JSON 字符串
    activationCodeList.forEach { code ->
        strings.add(code.toJson())
    }
    
    // 添加旧格式激活码
    activationCodes.forEach { codeStr ->
        // If not JSON 格式，直接添加
        if (!codeStr.trimStart().startsWith("{")) {
            strings.add(codeStr)
        }
    }
    
    return strings
}

/**
 * 自启动配置
 */
data class AutoStartConfig(
    val bootStartEnabled: Boolean = false,      // 开机自启动
    val scheduledStartEnabled: Boolean = false, // 定时自启动
    val scheduledTime: String = "08:00",        // 定时启动时间（HH:mm 格式）
    val scheduledDays: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7), // Start日期（1-7 代表周一到周日）
    val scheduledRepeat: Boolean = true         // Yes否重复（每天/每周）
)
