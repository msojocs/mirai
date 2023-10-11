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
import net.mamoe.mirai.internal.network.protocol.data.jce.*
import net.mamoe.mirai.internal.network.protocol.data.jce.FriendLike
import net.mamoe.mirai.internal.network.protocol.data.jce.FriendLikeResp
import net.mamoe.mirai.internal.network.protocol.data.jce.RequestPacket
import net.mamoe.mirai.internal.network.protocol.packet.OutgoingPacketFactory
import net.mamoe.mirai.internal.network.protocol.packet.buildOutgoingUniPacket
import net.mamoe.mirai.internal.utils.io.serialization.*
import net.mamoe.mirai.internal.utils.io.serialization.jceRequestSBuffer
import net.mamoe.mirai.internal.utils.io.serialization.writeJceStruct
import net.mamoe.mirai.utils.hexToBytes

internal object LikePacket : OutgoingPacketFactory<LikePacket.Response>("VisitorSvc.ReqFavorite") {
    override suspend fun ByteReadPacket.decode(bot: QQAndroidBot): Response {
        val res = this.readUniPacket(FriendLikeResp.serializer())
        // https://github.com/tsuzcx/qq_apk/blob/dfa4bbb676ea1d1dc583317281980df86420ecb4/com.tencent.mobileqq/classes.jar/com/tencent/mobileqq/app/NearbyCmdHelper.java#L608
        return Response(res.stHead.replyCode == 0, res.stHead.replyCode, res.stHead.strResult)

    }

    class Response(val success: Boolean, val code: Int, val msg: String) : Packet {
        override fun toString(): String = "LikeResponse(success=$success,code=$code,msg=$msg)"
    }

    fun invoke(
        client: QQAndroidClient,
        targetId: Long,
        count: Int = 1
    ) = buildOutgoingUniPacket(client) {

        val requestId = client.nextRequestPacketRequestId()
        writeJceStruct(
            RequestPacket.serializer(),
            RequestPacket(
                funcName = "ReqFavorite",
                servantName = "VisitorSvc",
                version = 3,
                sBuffer = jceRequestSBuffer(
                    "ReqFavorite",
                    FriendLike.serializer(),
                    FriendLike(
                        head = FriendLikeHead(
                            fromId = client.uin,
                            ver = 1,
                            seq = requestId,
                            reqType = 1,
                            trigger = 0,
                            vCookies = "0C180001060131160131".hexToBytes(),
                        ),
                        targetId = targetId,
                        opType = 0,
                        emSource = 65535,
                        iCount = count
                    )
                )
            )
        )
    }

}