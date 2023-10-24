package mx.ipn.escom.escomobile.app.auxiliares

import okhttp3.OkHttpClient
import java.lang.Exception
import javax.net.ssl.SSLContext
import mx.ipn.escom.escomobile.app.config.AppConfig
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


object ClienteCertificadoAutofirmado {
    fun obtenerCliente(inputStream: InputStream): OkHttpClient.Builder {
        var builder = OkHttpClient.Builder()
        try {
            // Load CAs from an InputStream
            val certificateFactory = CertificateFactory.getInstance("X.509")

            // Load self-signed certificate (*.crt file)
            val certificate = certificateFactory.generateCertificate(inputStream)
            inputStream.close()

            // Create a KeyStore containing our trusted CAs
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, AppConfig.apiKey.toCharArray())
            keyStore.setCertificateEntry("ca", certificate)

            // Create a TrustManager that trusts the CAs in our KeyStore.
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
            trustManagerFactory.init(keyStore)

            val trustManagers = trustManagerFactory.trustManagers
            val x509TrustManager = trustManagers[0] as X509TrustManager

            // Create an SSLSocketFactory that uses our TrustManager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(x509TrustManager), null)
            var sslSocketFactory = sslContext.socketFactory

            builder.sslSocketFactory(sslSocketFactory, x509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

        }
        catch (e: Exception) {

        }
        return builder
    }

    fun obtenerClienteNoSeguro(): OkHttpClient.Builder {
        val x509TrustManager = object: X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }

        val trustManagers = arrayOf<TrustManager>(x509TrustManager)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
        builder.hostnameVerifier { _, _ -> true }
        return builder
    }

}