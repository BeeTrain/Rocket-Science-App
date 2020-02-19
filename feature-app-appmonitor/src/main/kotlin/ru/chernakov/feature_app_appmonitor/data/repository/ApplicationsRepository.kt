package ru.chernakov.feature_app_appmonitor.data.repository

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import ru.chernakov.feature_app_appmonitor.data.model.ApplicationItem
import java.io.File
import java.security.MessageDigest
import java.util.*

class ApplicationsRepository(private val packageManager: PackageManager) {

    fun fetchApplications(): List<ApplicationItem> {
        val appsList = mutableListOf<ApplicationItem>()

        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        for (packageInfo in packages) {
            if (packageInfo.isAppNameNotEmpty() && packageInfo.isNonSystemApp()) {
                with(packageInfo) {
                    appsList.add(
                        ApplicationItem(
                            packageManager.getApplicationLabel(this.applicationInfo).toString(),
                            packageName,
                            versionName,
                            applicationInfo.sourceDir,
                            applicationInfo.dataDir,
                            isSystemPackage(),
                            getSHA(),
                            firstInstallTime,
                            lastUpdateTime,
                            fromPlayMarket(),
                            getFileSize(),
                            packageManager.getApplicationIcon(applicationInfo)
                        )
                    )
                }
            }
        }

        return appsList
    }

    fun getApplicationInfo(appPackage: String): ApplicationItem? {
        val app: ApplicationItem

        try {
            with(packageManager.getPackageInfo(appPackage, 0)) {
                app = ApplicationItem(
                    packageManager.getApplicationLabel(this.applicationInfo).toString(),
                    packageName,
                    versionName,
                    applicationInfo.sourceDir,
                    applicationInfo.dataDir,
                    isSystemPackage(),
                    getSHA(),
                    firstInstallTime,
                    lastUpdateTime,
                    fromPlayMarket(),
                    getFileSize(),
                    packageManager.getApplicationIcon(applicationInfo)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
            return null
        }

        return app
    }

    fun getOpenAppIntent(appPackage: String): Intent? {
        return packageManager.getLaunchIntentForPackage(appPackage)
    }

    fun prepareUninstallAppIntent(appPackage: String): Intent {
        return Intent(Intent.ACTION_UNINSTALL_PACKAGE).apply {
            data = Uri.parse(UNINSTALL_URI_PREFIX + appPackage)
            putExtra(Intent.EXTRA_RETURN_RESULT, true)
        }
    }

    fun getShareIntent(file: File): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        intent.type = SHARE_INTENT_TYPE
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION or
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }

    private fun PackageInfo.isAppNameNotEmpty(): Boolean {
        return packageManager.getApplicationLabel(this.applicationInfo).isNotEmpty() || this.packageName.isNotEmpty()
    }

    private fun PackageInfo.isNonSystemApp(): Boolean {
        return try {
            (applicationInfo.sourceDir.startsWith(APP_SOURCE_DIR) &&
                packageManager.getLaunchIntentForPackage(packageName) != null)
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun PackageInfo.isSystemPackage(): Boolean {
        return this.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun PackageInfo.getSHA(): String {
        var shaRes = ""
        val sha: List<String>
        val md: MessageDigest = MessageDigest.getInstance(MD_ALGORITHM)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val signingInfo =
                packageManager.getPackageArchiveInfo(
                    applicationInfo.sourceDir, PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo

            sha = if (signingInfo == null) {
                listOf()
            } else {
                if (signingInfo.hasMultipleSigners()) {
                    signingInfo.apkContentsSigners.map {
                        md.update(it.toByteArray())
                        md.digest().bytesToHex()
                    }
                } else {
                    signingInfo.signingCertificateHistory.map {
                        md.update(it.toByteArray())
                        md.digest().bytesToHex()
                    }
                }
            }
        } else {
            val signingInfo = packageManager.getPackageArchiveInfo(
                this.applicationInfo.sourceDir,
                PackageManager.GET_SIGNATURES
            ).signatures

            sha = signingInfo.map {
                md.update(it.toByteArray())
                md.digest().bytesToHex()
            }
        }
        for (String in sha) {
            shaRes += sha
        }

        return shaRes
    }

    private fun PackageInfo.fromPlayMarket(): Boolean {
        val installer = packageManager.getInstallerPackageName(packageName)

        return installer != null && VALID_INSTALLERS.contains(installer)
    }

    private fun PackageInfo.getFileSize(): Long {
        val file = File(this.applicationInfo.sourceDir)
        if (!file.exists())
            return 0
        if (!file.isDirectory)
            return file.length()
        val dirs = LinkedList<File>()
        dirs.add(file)
        var result: Long = 0
        while (!dirs.isEmpty()) {
            val dir = dirs.removeAt(0)
            if (!dir.exists())
                continue
            val listFiles = dir.listFiles()
            if (listFiles == null || listFiles.isEmpty())
                continue
            for (child in listFiles) {
                result += child.length()
                if (child.isDirectory)
                    dirs.add(child)
            }
        }
        return result
    }

    @Suppress("MagicNumber")
    private fun ByteArray.bytesToHex(): String {
        val hexChars = CharArray(this.size * 2)
        var v: Int
        for (j in this.indices) {
            v = this[j].toInt() and 0xFF
            hexChars[j * 2] = HEX_ARRAY[v.ushr(4)]
            hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
        }
        return String(hexChars)
    }

    companion object {
        private const val APP_SOURCE_DIR = "/data/app/"
        private const val SHARE_INTENT_TYPE = "application/vnd.android.package-archive"
        private const val MD_ALGORITHM = "SHA"
        private const val UNINSTALL_URI_PREFIX = "package:"

        private val VALID_INSTALLERS = ArrayList(listOf("com.android.vending", "com.google.android.feedback"))
        private val HEX_ARRAY =
            charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    }
}