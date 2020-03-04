package com.juntadeandalucia.ced.commons_android

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.InputStream

class FileManager(private val context: Context) {

    companion object {
        private const val SHARED_FOLDER = "shared"  // This value must match to the one in presentation/.../res/xml/provider_paths.xml.
        private const val APPLICATION_PDF = "application/pdf"
        private const val IMAGEN_JPEG = "image/*"
    }

    fun saveInputStreamAsFileInSharedFolder(inputStream: InputStream, fileName: String): File {
        val file: File = createFileInSharedFolder(fileName)
        copyInputStreamToFile(inputStream, file)
        return file
    }

    private fun copyInputStreamToFile(inputStream: InputStream, file: File) {
        inputStream.use { input ->
            file.outputStream().use { fileOutputStream ->
                input.copyTo(fileOutputStream)
            }
        }
    }

    private fun createFileInSharedFolder(fileName: String): File {
        val folder = File(context.filesDir,
            SHARED_FOLDER
        )
        if (!folder.exists()) {
            folder.mkdirs()
        }

        return File(folder, fileName)
    }


    fun getPdfIntent(pdf: File): Intent? {
        val authority: String = context.packageName + ".fileprovider"
        val uri: Uri = FileProvider.getUriForFile(context, authority, pdf)
        val target: Intent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            setDataAndType(uri,
                APPLICATION_PDF
            )
        }
        return Intent.createChooser(target, context.getString(R.string.open_pdf))
    }

    fun getImagenIntent(image: File): Intent? {
        val authority: String = context.packageName + ".fileprovider"
        val uri: Uri = FileProvider.getUriForFile(context, authority, image)
        val target: Intent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            setDataAndType(uri,
                IMAGEN_JPEG
            )
        }
        return Intent.createChooser(target, context.getString(R.string.open_image))
    }

}
