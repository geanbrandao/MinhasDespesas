package dev.geanbrandao.minhasdespesas.core.database.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDb(
    @PrimaryKey(autoGenerate = false)
    val categoryId: Long,
    val name: String,
    val icon: String,
    val canRemove: Boolean = false,
): Parcelable {
    override fun describeContents(): Int {
        return Parcelable.CONTENTS_FILE_DESCRIPTOR
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        //
    }
}
