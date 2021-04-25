package com.forever.test.jsoup;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author WJX
 * @date 2020/7/31 8:58
 */
public class SslUtils {


    public static void ignoreSsl() {
        trustHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
    }


    private static void trustHttpsCertificates() {

        TrustManager[] trustManagers = new TrustManager[] {new MyTrustManager()};

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, trustManagers, null);

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }


    static class MyTrustManager implements TrustManager, X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }


}
