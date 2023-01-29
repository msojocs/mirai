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
import net.mamoe.mirai.internal.QQAndroidBot
import net.mamoe.mirai.internal.network.Packet
import net.mamoe.mirai.internal.network.QQAndroidClient
import net.mamoe.mirai.internal.network.protocol.data.proto.OidbCmd0xb77
import net.mamoe.mirai.internal.network.protocol.data.proto.OidbSso
import net.mamoe.mirai.internal.network.protocol.packet.OutgoingPacketFactory
import net.mamoe.mirai.internal.network.protocol.packet.buildOutgoingUniPacket
import net.mamoe.mirai.internal.utils.io.serialization.loadAs
import net.mamoe.mirai.internal.utils.io.serialization.toByteArray
import net.mamoe.mirai.internal.utils.io.serialization.writeProtoBuf
import kotlin.random.Random

internal object ArkPacket : OutgoingPacketFactory<ArkPacket.Response>("OidbSvc.0xb77_10") {
    override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): Response {
        with(readBytes().loadAs(OidbSso.OIDBSSOPkg.serializer())) {
            return Response(result == 0, result)
        }
    }

    class Response(val success: Boolean, val code: Int) : Packet {
        override fun toString(): String = "ArkResponse(success=$success,code=$code)"
    }

    fun friendInvoke(
        client: QQAndroidClient,
        nudgeTargetId: Long,
        messageReceiverUin: Long,
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
                    clientInfo = OidbCmd0xb77.ClientInfo(
                        platform = 1,
                        sdkVersion = "1.0.0",
                        androidPackageName = "tv.danmaku.bili",
                        androidSignature = "7194d531cbe7960a22007b9f6bdaa38b"
                    ),
                    extInfo = OidbCmd0xb77.ExtInfo(
                        msgSeq = Random(1).nextLong()
                    ),
                    recvUin = 1690127128,
                    miniAppMsgBody = OidbCmd0xb77.MiniAppMsgBody(
                        miniAppAppid = 1109937557,
                        miniAppPath = "pages",
                        webPageUrl = "url",
                        miniAppType = 1,
                        title = "title",
                        desc = "desc",
                        jsonStr = "{\"app\":\"com.tencent.qqpay.qqmp.groupmsg\",\"desc\":\"\",\"view\":\"groupPushView\",\"ver\":\"1.0.0.7\",\"prompt\":\"菜单\",\"appID\":\"\",\"sourceName\":\"\",\"actionData\":\"\",\"actionData_A\":\"\",\"sourceUrl\":\"\",\"meta\":{\"groupPushData\":{\"bannerImg\":\"https://img.l.ctliccl.top/viewpicture.php?id=48b503\",\"bannerLink\":\"http://qm.qq.com/cgi-bin/qm/qr?from=app&p=android&k=YCdcbs3lgpOOAkhdHIycNyM6u281Nx3s\",\"bannerTxt\":\"菜单\",\"cancel_url\":\"http://www.baidu.com\",\"fromIcon\":\"\",\"fromName\":\"name\",\"item1Img\":\"\",\"report_url\":\"\",\"summaryTxt\":\"娱乐系统（待开发）\\n\\n群管系统（待开发）\\n\\n银行系统（待开发）\\n\\n玩家系统（待开发）\\n\\n便民系统（待开发）\",\"time\":\"\"}},\"config\":{\"forward\":0,\"showSender\":1}}"
                    ),
//                    recvGuildId = 123
                ).toByteArray(OidbCmd0xb77.ReqBody.serializer())
            )
        )
    }

    fun troopInvoke(
        client: QQAndroidClient,
        messageReceiverGroupCode: Long,
        nudgeTargetId: Long,
    ) = buildOutgoingUniPacket(client) {
        writeProtoBuf(
            OidbSso.OIDBSSOPkg.serializer(),
            OidbSso.OIDBSSOPkg(
                command = 2935,
                serviceType = 1,
                result = 0,
                bodybuffer = OidbCmd0xb77.ReqBody(
                    appid = 123,

                ).toByteArray(OidbCmd0xb77.ReqBody.serializer())
            )
        )
    }

}