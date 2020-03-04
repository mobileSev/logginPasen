package com.juntadeandalucia.ced.data.di

import android.content.Context
import android.util.Base64
import com.juntadeandalucia.ced.data.R
import java.security.MessageDigest
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate


class SSLConfig(private val flavor: String, private val context: Context) {


    fun extractCertificate(): String? {
        val certificateId: Int = when (flavor) {
            "pre" -> R.raw.mytruststore
            "pro" -> R.raw.mytruststore
            else -> return null
        }

        return try {
            val certificate: X509Certificate = generateCertificate(certificateId)
            val publicKeyEncoded: ByteArray = certificate.publicKey.encoded
            val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val publicKeySha256 = messageDigest.digest(publicKeyEncoded)
            val publicKeyShaBase64: ByteArray = Base64.encode(publicKeySha256, Base64.DEFAULT)

            "sha256/" + String(publicKeyShaBase64)
        } catch (e: Exception) {
            throw IllegalStateException("Problema con los certificados digitales.")
        }
    }

    private fun generateCertificate(certificate: Int): X509Certificate {
        getInputStreamCertificate(certificate).use {
            return getcertificateFactory().generateCertificate(it) as X509Certificate
        }
    }

    private fun getInputStreamCertificate(certificate: Int) =
        context.resources.openRawResource(certificate)

    private fun getcertificateFactory() = CertificateFactory.getInstance("X.509")


}