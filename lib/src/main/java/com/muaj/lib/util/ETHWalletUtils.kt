package com.muaj.lib.util

import com.muaj.lib.base.App
import com.muaj.lib.entity.ETHWallet
import com.muaj.lib.extensions.createOrExistsDir
import com.muaj.lib.extensions.getExternalPrivateDirPath
import com.muaj.lib.extensions.logi
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.wallet.DeterministicSeed
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletFile
import org.web3j.protocol.ObjectMapperFactory
import java.io.File
import java.io.IOException

/**
 * Created by muaj on 2018/6/25
 * 以太坊钱包创建工具类
 */
class ETHWalletUtils {
    private val objectMapper = ObjectMapperFactory.getObjectMapper()
    // 钱包文件外置存储目录
    val Wallet_DIR: String
        get() = App.mContext.getExternalPrivateDirPath("oops_wallet")
    //walletTemp
    val Wallet_Tmp_DIR: String
        get() = App.mContext.getExternalPrivateDirPath("oops_wallet_temp")

    /**
     * 随机
     */
    private val secureRandom = SecureRandomUtils.secureRandom()
    private val TAG = "ETHWalletUtils"

    companion object {
        /**
         * 通用的以太坊基于bip44协议的助记词路径（imtoken jaxx Metamask myetherwallet）
         * */
        val ETH_JAXX_TYPE = "m/44'/60'/0'/0/0"
        val ETH_LEDGER_TYPE = "m/44'/60'/0'/0"
        val ETH_CUSTOM_TYPE = "m/44'/60'/1'/0/0"
    }

    fun generateMnemonic(walletName: String, pwd: String): ETHWallet? {
        val pathArray = ETH_JAXX_TYPE.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val passphrase = ""
        val creationTimeSeconds = System.currentTimeMillis() / 1000

        //step1:生成助记词mnemonic
        //step1-1:生成一个长度为 128~256 位 (bits) 的随机序列(熵)，规定熵的位数必须是 32 的整数倍
        //step1-2:对熵进行Sha256Hash哈希,校验和的长度为熵的长度/32 位,取熵哈希后的前 n 位作为校验和 (n= 熵长度/32)
        //step1-3:随机序列 + 校验和
        //step1-4:把step1-3得到的结果每 11 位切割
        //step1-5:step1-4得到的每 11 位字节匹配词库的一个词得到助记词串
        //step2:通过助记词和输入的密码（可为空字符串）生成二进制种子，BIP39 采用 PBKDF2 函数推算种子;
        //bitcoinj使用PBKDF2SHA512 函数推算种子，具体参数如下：1）助记词句子作为密码  2）"mnemonic" + passphrase 作为盐
        //3)2048 作为重复计算的次数 4)HMAC-SHA512 作为随机算法 5)512 位(64 字节)是期望得到的密钥长度
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
    private fun generateWalletByMnemonic(walletName: String, ds: DeterministicSeed,
                                         pathArray: Array<String>, pwd: String): ETHWallet? {
        //种子
        val seedBytes = ds.seedBytes
        //        System.out.println(Arrays.toString(seedBytes));
        //助记词
        val mnemonic = ds.mnemonicCode
        //        System.out.println(Arrays.toString(mnemonic.toArray()));
        if (seedBytes == null)
            return null
        //step3:从根种子生成主私钥和主链码
        var dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes)
        //step4:子私钥推导
        for (i in 1 until pathArray.size) {
            val childNumber: ChildNumber
            childNumber = if (pathArray[i].endsWith("'")) {
                val number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length - 1))
                ChildNumber(number, true)
            } else {
                val number = Integer.parseInt(pathArray[i])
                ChildNumber(number, false)
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber)
        }

        //step5：生成Secp256k1公钥/密钥对，并存储到ECKeyPair内
        val keyPair = ECKeyPair.create(dkKey.privKeyBytes)
        val ethWallet = generateWallet(walletName, pwd, keyPair)
        ethWallet?.mnemonic = convertMnemonicList(mnemonic!!)
        return ethWallet
    }

    private fun convertMnemonicList(mnemonics: List<String>): String {
        var mnemonicStr = ""
        for (mnemonic in mnemonics) {
            mnemonicStr = "$mnemonic "
        }
        return mnemonicStr.trim { it <= ' ' }
    }

    private fun generateWallet(walletName: String, pwd: String, ecKeyPair: ECKeyPair): ETHWallet? {
        val walletFile: WalletFile
        try {
            //step6：根据公钥/密钥对生成钱包
            walletFile = Wallet.create(pwd, ecKeyPair, 1024, 1) // WalletUtils. .generateNewWalletFile();
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        val publicKey = ecKeyPair.publicKey
        val s = publicKey.toString()
        logi("publicKey = $s", TAG)
        val destination = File("$Wallet_DIR/", "keystore_$walletName.json")

        //目录不存在则创建目录，创建不了则报错
        if (!createOrExistsDir(Wallet_DIR)) {
            println("创建目标文件所在目录失败！")
            return null
        }

        try {
            objectMapper.writeValue(destination, walletFile)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        val ethWallet = ETHWallet()
        ethWallet.name = walletName
        ethWallet.address = Keys.toChecksumAddress(walletFile.address)
        ethWallet.keystorePath = destination.absolutePath
        ethWallet.password = Md5Utils.md5(pwd)
        return ethWallet
    }




}