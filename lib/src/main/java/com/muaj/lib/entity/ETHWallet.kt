package com.muaj.lib.entity

import com.muaj.lib.realmex.AutoIncrementPK
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by muaj on 2018/6/26
 * wallet entity
 */
@AutoIncrementPK
class ETHWallet : RealmObject() {
    @PrimaryKey
    var id: Long= 0

    var address: String? = null
    var name: String? = null
    var password: String? = null
    var keystorePath: String? = null
    var mnemonic: String? = null
    var isCurrent: Boolean = false
    var isBackup: Boolean = false


    override fun toString(): String {
        return "ETHWallet{" +
                "id=" + id +
                ", address=" + address+
                ", name=" + name +
                ", password=" + password +
                ", keystorePath=" + keystorePath +
                ", mnemonic=" + mnemonic +
                ", isCurrent=" + isCurrent +
                ", isBackup=" + isBackup +
                "}"
    }



}