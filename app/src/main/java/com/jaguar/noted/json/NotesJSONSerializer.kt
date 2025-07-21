package com.jaguar.noted.json

import android.content.Context
import android.util.Log
import com.jaguar.noted.objects.Note
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Writer

class NotesJSONSerializer(private val filename: String, private val context: Context) {
    @Throws(IOException::class)
    fun saveNotes(notes: List<Note>) {
        val jsonArray = JSONArray()

        for (note in notes) {
            jsonArray.put(note.toJSON())
        }

        var writer: Writer? = null
        try {
            val out: OutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            writer = OutputStreamWriter(out)
            writer.write(jsonArray.toString())
        } finally {
            writer?.close()
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun loadNotes(): ArrayList<Note> {
        val notes = ArrayList<Note>()
        var reader: BufferedReader? = null
        try {
            val `in`: InputStream = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(`in`))
            val jsonString = StringBuilder()
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                jsonString.append(line)
            }

            val jArray = JSONTokener(jsonString.toString()).nextValue() as JSONArray
            for (i in 0..<jArray.length()) {
                notes.add(Note(jArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {
            Log.e("Note to self", "(Might be a first start) Error loading notes: ", e)
        } finally {
            reader?.close()
        }
        return notes
    }
}
