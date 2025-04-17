package com.example.iamcalmapp.roomdb
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="description") val desc: String,
    @ColumnInfo(name="date") val date: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
): Parcelable