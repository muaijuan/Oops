package com.muaj.lib.util

/**
 * Created by muaj on 2018/6/25
 * 以太坊钱包创建工具类
 */
class ETHWalletUtils {
    companion object {
        /**
         * 通用的以太坊基于bip44协议的助记词路径（imtoken jaxx Metamask myetherwallet）
         * */
        val ETH_JAXX_TYPE = "m/44'/60'/0'/0/0"
        val ETH_LEDGER_TYPE = "m/44'/60'/0'/0"
        val ETH_CUSTOM_TYPE = "m/44'/60'/1'/0/0"
    }

    fun generateMnemonic(){

    }

}