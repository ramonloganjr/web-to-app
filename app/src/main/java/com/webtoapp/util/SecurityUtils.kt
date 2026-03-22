package com.webtoapp.util

fun isInsecureRemoteHttpUrl(url: String): Boolean {
    return url.startsWith("http://") && 
        !url.startsWith("http://localhost") && 
        !url.startsWith("http://127.0.0.1") && 
        !url.startsWith("http://10.") && 
        !url.startsWith("http://192.168.")
}
