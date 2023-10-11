/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

package net.mamoe.mirai.internal.network.protocol.packet.chat

import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json
import net.mamoe.mirai.internal.QQAndroidBot
import net.mamoe.mirai.internal.network.Packet
import net.mamoe.mirai.internal.network.QQAndroidClient
import net.mamoe.mirai.internal.network.protocol.data.proto.OidbCmd0xb77
import net.mamoe.mirai.internal.network.protocol.data.proto.OidbSso
import net.mamoe.mirai.internal.network.protocol.packet.OutgoingPacketFactory
import net.mamoe.mirai.internal.network.protocol.packet.buildOutgoingUniPacket
import net.mamoe.mirai.internal.utils.io.serialization.loadAs
import net.mamoe.mirai.internal.utils.io.serialization.readProtoBuf
import net.mamoe.mirai.internal.utils.io.serialization.toByteArray
import net.mamoe.mirai.internal.utils.io.serialization.writeProtoBuf
import net.mamoe.mirai.message.data.ArkAppMessage
import net.mamoe.mirai.message.data.MessageSourceKind
import kotlin.random.Random

internal object ArkPacket : OutgoingPacketFactory<ArkPacket.Response>("OidbSvc.0xb77_10") {
    override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): Response {
        return Response(readProtoBuf(OidbSso.OIDBSSOPkg.serializer()))
    }

    class Response(val pkg: OidbSso.OIDBSSOPkg) : Packet {
        override fun toString(): String = "ArkPacket.Response(success=${pkg.result == 0}, error=${pkg.errorMsg})"
    }

    operator fun invoke(
        client: QQAndroidClient,
        targetId: Long,
        data: ArkAppMessage,
        targetKind: MessageSourceKind,
    ) = buildOutgoingUniPacket(client) {
        // [mirai:app:{"app":"com.tencent.miniapp_01","view":"view_8C8E89B49BE609866298ADDFF2DBABA4","ver":"1.0.0.19","prompt":"[QQ小程序]知乎","meta":{"detail_1":{"appid":"1110081493","appType":0,"title":"知乎","desc":"","icon":"https:\/\/miniapp.gtimg.cn\/public\/appicon\/f29f9992ab3078e1e42101c2fa56ad87_200.jpg","preview":"https:\/\/pubminishare-30161.picsz.qpic.cn\/b4edf285-18d9-4d18-93c6-93b870d6ca92","url":"m.q.qq.com\/a\/s\/4e6ab1e2e8541aec526119caf24ad923","scene":0,"host":{"uin":1690127128,"nick":"msojocs"},"shareTemplateId":"8C8E89B49BE609866298ADDFF2DBABA4","shareTemplateData":{},"showLittleTail":"","gamePoints":"","gamePointsUrl":""}},"config":{"type":"normal","width":0,"height":0,"forward":1,"autoSize":0,"ctime":1674910059,"token":""}}]
        writeProtoBuf(
            OidbSso.OIDBSSOPkg.serializer(),
            OidbSso.OIDBSSOPkg(
                command = 2935,
                serviceType = 9,
                result = 0,
                bodybuffer = OidbCmd0xb77.ReqBody(
                    appid = 100951776,
                    appType = 1,
                    msgStyle = 10,
                    // 0:好友 1:群聊 3:频道
                    sendType =  when (targetKind) {
                        MessageSourceKind.FRIEND -> 0
                        MessageSourceKind.GROUP -> 1
                        else -> error("Internal error: Unsupported targetKind $targetKind")
                    },
                    clientInfo = OidbCmd0xb77.ClientInfo(
                        platform = 1,
                        sdkVersion = "1.0.0",
                        androidPackageName = "tv.danmaku.bili",
                        androidSignature = "7194d531cbe7960a22007b9f6bdaa38b"
                    ),
                    extInfo = OidbCmd0xb77.ExtInfo(
                        msgSeq = Random(1).nextLong()
                    ),
                    recvUin = targetId,
                    miniAppMsgBody = OidbCmd0xb77.MiniAppMsgBody(
                        miniAppAppid = 1109937557,
                        miniAppPath = "pages",
                        webPageUrl = "url",
                        miniAppType = 1,
                        title = "title",
                        desc = "desc",
                        jsonStr = Json.encodeToString(ArkAppMessage.serializer(), data)
                    ),
//                    recvGuildId = 123
                ).toByteArray(OidbCmd0xb77.ReqBody.serializer())
            )
        )
    }

}