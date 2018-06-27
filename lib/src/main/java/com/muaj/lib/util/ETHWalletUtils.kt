package com.muaj.lib.util

import com.muaj.lib.entity.ETHWallet
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.wallet.DeterministicSeed
import org.web3j.crypto.ECKeyPair

/**
 * Created by muaj on 2018/6/25
 * 以太坊钱包创建工具类
 */
class ETHWalletUtils {
    /**
     * 随机
     */
    private val secureRandom = SecureRandomUtils.secureRandom()
    companion object {
        /**
         * 通用的以太坊基于bip44协议的助记词路径（imtoken jaxx Metamask myetherwallet）
         * */
        val ETH_JAXX_TYPE = "m/44'/60'/0'/0/0"
        val ETH_LEDGER_TYPE = "m/44'/60'/0'/0"
        val ETH_CUSTOM_TYPE = "m/44'/60'/1'/0/0"
    }

    fun generateMnemonic(){
        val pathArray = ETH_JAXX_TYPE.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val passphrase = ""
        val creationTimeSeconds = System.currentTimeMillis() / 1000

        val ds = DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds)
        return generateWalletByMnemonic(walletName, ds, pathArray, pwd)
    }

    /**
     * @param walletName 钱包名称
     * @param ds         助记词加密种子
     * @param pathArray  助记词标准
     * @param pwd        密码
     * @return
     */
    fun generateWalletByMnemonic(walletName: String, ds: DeterministicSeed,
                                 pathArray: Array<String>, pwd: String): ETHWallet? {
        //种子
        val seedBytes = ds.seedBytes
        //        System.out.println(Arrays.toString(seedBytes));
        //助记词
        val mnemonic = ds.mnemonicCode
        //        System.out.println(Arrays.toString(mnemonic.toArray()));
        if (seedBytes == null)
            return null
        var dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes)
        for (i in 1 until pathArray.size) {
            val childNumber: ChildNumber
            if (pathArray[i].endsWith("'")) {
                val number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length - 1))
                childNumber = ChildNumber(number, true)
            } else {
                val number = Integer.parseInt(pathArray[i])
                childNumber = ChildNumber(number, false)
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber)
        }
        val keyPair = ECKeyPair.create(dkKey.privKeyBytes)
        val ethWallet = generateWallet(walletName, pwd, keyPair)
        if (ethWallet != null) {
            ethWallet!!.setMnemonic(convertMnemonicList(mnemonic!!))
        }
        return ethWallet
    }

}