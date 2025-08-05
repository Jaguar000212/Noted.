package com.jaguar.noted.objects

import org.json.JSONObject
import java.util.UUID

data class Note(
    private var id: String = "",
    private var title: String = "",
    private var description: String = "",
    private var tags: List<String> = emptyList(),
    private var dueTime: Long? = null,
    private var isCompleted: Boolean = false,
    private var subTasks: List<Note> = emptyList()
) {
    constructor(
        title: String,
        description: String,
        tags: List<String>,
        dueTime: Long?,
        isCompleted: Boolean,
        subTasks: List<Note>
    ) : this() {
        this.id = UUID.randomUUID().toString()
        this.title = title
        this.description = description
        this.tags = tags
        this.dueTime = dueTime
        this.isCompleted = isCompleted
        this.subTasks = subTasks
    }

    constructor(jsonObject: JSONObject) : this() {
        val jsonTags = jsonObject.optJSONArray("tags")
        val jsonSubTasks = jsonObject.optJSONArray("subTasks")

        this.id = jsonObject.optString("id")
        this.title = jsonObject.optString("title")
        this.description = jsonObject.optString("description")
        this.tags = (0 until (jsonTags?.length() ?: 0)).map { i ->
            jsonTags!!.optString(i, null)
        }
        this.dueTime = jsonObject.optLong("time", 0)
        this.isCompleted = jsonObject.optBoolean("isCompleted")
        this.subTasks = (0 until (jsonSubTasks?.length() ?: 0)).map { i ->
            Note(jsonSubTasks!!.getJSONObject(i))
        }
    }

    fun getID(): String = this.id
    fun getTitle(): String = this.title
    fun getDescription(): String = this.description
    fun getTags(): List<String> = this.tags
    fun getDueTime(): Long? = this.dueTime
    fun getIsCompleted(): Boolean = this.isCompleted
    fun getSubTasks(): List<Note> = this.subTasks

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setTags(tags: List<String>) {
        this.tags = tags
    }

    fun setDueTime(dueTime: Long?) {
        this.dueTime = dueTime
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun addSubTask(subTask: Note) {
        this.subTasks += subTask
    }

    fun removeSubTask(subTask: Note) {
        this.subTasks -= subTask
    }

    override fun toString(): String {
        return """
            ID: $id
            Title: $title
            Description: $description
            Tags: $tags
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
            jsonObject.put("time", dueTime)
            jsonObject.put("isCompleted", isCompleted)
            val jsonArray = org.json.JSONArray()
            subTasks.forEach { subTask -> jsonArray.put(subTask.toJSON()) }
            jsonObject.put("subTasks", jsonArray)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }
}
