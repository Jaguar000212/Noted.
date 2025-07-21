package com.jaguar.noted.objects

import org.json.JSONObject
import java.util.UUID

data class Note(
    private var id: String = "",
    private var title: String = "",
    private var description: String = "",
    private var tags: List<String> = emptyList(),
    private var dueDate: String? = "",
    private var dueTime: String? = "",
    private var isCompleted: Boolean = false
) {

    constructor(
        title: String,
        description: String,
        tags: List<String>,
        dueDate: String?,
        dueTime: String?,
        isCompleted: Boolean
    ) : this() {
        this.id = UUID.randomUUID().toString()
        this.title = title
        this.description = description
        this.tags = tags
        this.dueDate = dueDate
        this.dueTime = dueTime
        this.isCompleted = isCompleted
    }

    constructor(jsonObject: JSONObject) : this() {
        val jsonArray = jsonObject.optJSONArray("tags")

        this.id = jsonObject.optString("id")
        this.title = jsonObject.optString("title")
        this.description = jsonObject.optString("description")
        this.tags = (0 until (jsonArray?.length() ?: 0)).map { i ->
            jsonArray!!.optString(i, null)
        }
        this.dueDate = jsonObject.optString("date", null)
        this.dueTime = jsonObject.optString("time", null)
        this.isCompleted = jsonObject.optBoolean("isCompleted")
    }

    fun getID(): String = this.id
    fun getTitle(): String = this.title
    fun getDescription(): String = this.description
    fun getTags(): List<String> = this.tags
    fun getDueDate(): String? = this.dueDate
    fun getDueTime(): String? = this.dueTime
    fun getIsCompleted(): Boolean = this.isCompleted

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setTags(tags: List<String>) {
        this.tags = tags
    }

    fun setDueDate(dueDate: String?) {
        this.dueDate = dueDate
    }

    fun setDueTime(dueTime: String?) {
        this.dueTime = dueTime
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    override fun toString(): String {
        return """
            ID: $id
            Title: $title
            Description: $description
            Tags: $tags
            Due Date: $dueDate
            Due Time: $dueTime
            Is Completed: $isCompleted
            """.trimIndent()
    }

    fun toJSON(): JSONObject {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("id", id)
            jsonObject.put("title", title)
            jsonObject.put("description", description)
            jsonObject.put("tags", tags)
            jsonObject.put("date", dueDate)
            jsonObject.put("time", dueTime)
            jsonObject.put("isCompleted", isCompleted)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
