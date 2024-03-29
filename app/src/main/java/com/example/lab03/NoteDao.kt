package com.example.lab03

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM note_table WHERE id = :noteId")
    suspend fun deleteNote(noteId: Int)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = :noteId")
    fun getNote(noteId: Int) : Note
}