/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.message.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNames
import net.mamoe.mirai.message.code.CodableMessage
import net.mamoe.mirai.utils.MiraiExperimentalApi
import net.mamoe.mirai.utils.currentTimeSeconds
import net.mamoe.mirai.utils.safeCast

@Serializable
@SerialName(ArkAppMessage.SERIAL_NAME)
public open class ArkAppMessage(
    public open var app: String = "",
    public open var desc: String = "",
    public open var view: String = "",
    public open var ver: String = "",
    public open var prompt: String = "",
    public open var meta: ArkAppMeta = ArkAppMeta(),
    public open var config: ArkAppConfig = ArkAppConfig(),
    @JsonNames("appID")
    public open var appId: String = "",
    public open var sourceAd: String = "",
    public open var sourceName: String = "",
    public open var actionData: String = "",
    public open var actionData_A: String = "",
    public open var sourceUrl: String = "",
    public open var extraApps: ArrayList<ArkAppMessage>? = ArrayList(),
    public open var text: String = "",
    public open var extra: String = ""
) : MessageContent, ConstrainSingle, CodableMessage {

    override val key: MessageKey<*> get() = MusicShare

    @MiraiExperimentalApi
    override fun appendMiraiCodeTo(builder: StringBuilder) {
        builder.append("[mirai:arkappmessage:")
            .append(Json.encodeToString(this))
            .append(']')
    }

    override fun toString(): String {
//        TODO("Not yet implemented")
        return "ArkAppMessage";
    }

    override fun contentToString(): String {
        return "[卡片]"
    }
    /**
     * 注意, baseKey [MessageContent] 不稳定. 未来可能会有变更.
     */
    public companion object Key :
        AbstractPolymorphicMessageKey<MessageContent, ArkAppMessage>
            (MessageContent, { it.safeCast() }) {

        /**
         * @since 2.3
         */
        public const val SERIAL_NAME: String = "ArkAppMessage"
    }
}

@Serializable
public data class ArkAppConfig(
    public val ctime: Long = currentTimeSeconds(),
    public val forward: Int = 0,
    public val showSender: Int = 0,
    public val token: String? = ""
) {
    override fun toString(): String {
        return super.toString()
    }
}
@Serializable
public open class ArkAppMeta(
    public open var singleImg: ArkAppSingleImg? = null,
    public open var groupPushData: ArkAppGroupPushData? = null,
    public open var notification: ArkAppNotification? = null,
) {

    override fun toString(): String {
        return super.toString()
    }
}
/*

      "sourceMsgId" : "0",
      "title" : "清墨的橘个人博客",
      "desc" : "为了纪念，为了祭奠",
      "preview" : "",
      "tag" : "点我进入网站",
      "musicUrl" : "",
      "jumpUrl" : "https://www.abcio.cn",
      "appid" : 3353779836,
      "app_type" : 1,
      "action" : "",
      "source_url" : "",
      "source_icon" : "",
      "android_pkg_name" : ""
 */
@Serializable
public data class ArkAppMusic(
    public val sourceMsgId: String = "",
    public val title: String = "",
    public val desc: String = "",
    public val preview: String = "",
    public val tag: String = "",
    public val musicUrl: String = "",
    public val jumpUrl: String = "",
    public val appid: Long,
    public val app_type: Int,
    public val action: String = "",
    public val source_url: String = "",
    public val source_icon: String = "",
    public val android_pkg_name: String = "",
) {
    override fun toString(): String {
        return super.toString()
    }
}
@Serializable
public data class ArkAppSingleImg(
    public val mainImage: String = "",
    public val mainUrl: String = ""
) {
    override fun toString(): String {
        return super.toString()
    }
}
@Serializable
public data class ArkAppGroupPushData(
    public val time: String = "",
    public val cancel_url : String = "",
    public val fromIcon : String = "",
    public val report_url : String = "",
    public val bannerLink : String = "",
    public val fromName : String = "",
    public val summaryTxt : String = "",
    public val bannerImg : String = "",
    public val bannerTxt : String = "",
    public val item1Img : String = "",
) {
    override fun toString(): String {
        return super.toString()
    }
}

/**
 *
"animationType" : "0",
"arkBgUrl" : "\/qzone\/space_item\/material\/QzoneGift\/org\/12\/19676\/ke.png",
"arkGuestBgUrl" : "\/qzone\/space_item\/material\/QzoneGift\/org\/13\/204973\/ke.png",
"boxZipUrl" : "",
"desc" : "",
"giftMsg" : "（司司专属礼物）",
"giftName" : "时间:2020年6月28日",
"giftParticleUrl" : "\/aoi\/sola\/20200211152108_OwLVpEObQT.png",
"giftPrice" : "6660",
"giftZipUrl" : "\/qzone\/qzact\/act\/external\/shijun\/mghdh.zip",
"isFree" : "0",
"lottieUrl" : "",
"msgId" : "6794593814080377150",
"openIconUrl" : "\/aoi\/sola\/20200211152101_nl6z8Et70n.png",
"orderNum" : "",
"sender" : "1",
"toUin" : "2012683191",
"unopenIconUrl" : "\/aoi\/sola\/20190524114722_moYXoHozlK.png"
 */
@Serializable
public data class ArkAppGiftData(
    public val animationType: String = "",
    public val arkBgUrl: String = "",
    public val arkGuestBgUrl: String = "",
    public val boxZipUrl: String = "",
    public val desc: String = "",
    public val giftMsg: String = "",
    public val giftName: String = "",
    public val giftParticleUrl: String = "",
    public val giftPrice: String = "",
    public val giftZipUrl: String = "",
    public val isFree: String = "",
    public val lottieUrl: String = "",
    public val msgId: String = "",
    public val openIconUrl: String = "",
    public val orderNum: String = "",
    public val sender: String = "",
    public val toUin: String = "",
    public val unopenIconUrl: String = ""
) {
    override fun toString(): String {
        return super.toString()
    }
}
/*

    "notification" : {
      "appInfo" : {
        "appName" : "清墨的橘",
        "appType" : 4,
        "appid" : 1109659848,
        "iconUrl" : "https://yun..cn/tx.png"
      },
      "button" : [
        {
          "action" : "https://.cn",
          "name" : "清墨的橘官方博客"
        },
        {
          "action" : "https://.cn",
          "name" : "进入小程序查看详情"
        }
      ],
      "data" : [
        {
          "title" : "小破站",
          "value" : "www.abcio.cn"
        },
        {
          "title" : "滴滴滴",
          "value" : "在线寻友链"
        },
        {
          "title" : "小要求",
          "value" : "已备案博客优先"
        }
      ],
      "emphasis_keyword" : "",
      "title" : "淫荡的一天就这样过了"
    }
 */
@Serializable
public open class ArkAppNotification(
    public open var appInfo: AppInfo? = AppInfo(),
    public open var button: List<Button>? = ArrayList(),
    public open var data: List<Data>? = ArrayList(),
    public open var emphasis_keyword: String? = "",
    public open var title: String? = "",
) {
    @Serializable
    public open class AppInfo(
        public open var appName: String = "",
        public open var appType: Int = 4,
        public open var appid: Long = 0,
        public open var iconUrl: String = ""
    ){}
    @Serializable
    public class Button (
        public val action: String = "",
        public val name: String = ""
    ){}
    @Serializable
    public class Data (
        public val title: String = "",
        public val value: String = ""
    ){}
    override fun toString(): String {
        return super.toString()
    }
}