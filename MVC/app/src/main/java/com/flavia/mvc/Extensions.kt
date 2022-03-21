package com.flavia.mvc

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/*
    Created by: Flavia Figueroa
*/

fun Context?.parseJsonFromResource(fileName: String, json: (json: String) -> Unit){
    this?.let {
        try {
            json.invoke(it.readFromJsonFile(fileName))
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}

fun Context.readFromJsonFile(fileName: String) : String {
    return try {
        val inputStream: InputStream = this.assets.open(fileName)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var receiveString : String
        while (bufferedReader.readLine().let { receiveString = it ?: ""; it != null }) {
            stringBuilder.append(receiveString)
        }
        inputStream.close()
        stringBuilder.toString()
    } catch (ex: Exception) {
        ex.printStackTrace()
        String()
    }
}