package ru.rager.credit.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single

interface BaseDao<Entity, ID> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(o: Entity): Single<ID>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(o: List<Entity>): Single<List<ID>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(o: Entity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(o: List<Entity>): Completable

    @Delete
    fun delete(o: Entity): Completable

}