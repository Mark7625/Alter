package org.alter.game.communication

import net.rsprot.protocol.game.outgoing.social.FriendListLoaded
import net.rsprot.protocol.game.outgoing.social.UpdateFriendList
import net.rsprot.protocol.game.outgoing.social.UpdateFriendList.Companion.PROPERTY_REFERRED
import org.alter.game.model.entity.Player
import org.alter.game.playersaving.PlayerSerialization


class SocialManager(val client : Player) {

    val MAX_FRIENDS_COUNT: Int = 200
    val MAX_IGNORES_COUNT: Int = 100

    private val friends: List<String> = listOf("Test")
    private val ignores: List<String> = emptyList()

    fun initalize() {
        initFriendList()
    }

    private fun initFriendList() {
        if (friends.isEmpty()) {
            client.write(FriendListLoaded)
        } else {
            updateAllFriends(client)
        }
    }

    fun updateAllFriends(player: Player) {
        friends.forEach {
            addFriend(player,it,false)
        }
    }

    fun addFriend(player: Player, name : String, justAdded : Boolean) {
        if (justAdded) {
            if (name.equals(player.username, ignoreCase = true)) {
                player.writeMessage("You can't add yourself to your own friends list.")
                return
            }
            //TODO CHECK LOGIN NAME -> DISPLAY NAME
            //if (!PlayerSerialization.playerExists(player)) {
              //  player.sendMessage("Could not find player.")
              //  return
           // }
        }
        updateStatus(player,name,justAdded)
    }

    private fun updateStatus(player: Player, userName: String, justAdded: Boolean) {
        var online = player.world.players.any { userName.equals(it.username, true) }
        val friendUpdate = emptyList<UpdateFriendList.Friend>().toMutableList()
        if (online) {
            friendUpdate.add(UpdateFriendList.OnlineFriend(justAdded,userName,"MarkNew",304,client.privilege.id, PROPERTY_REFERRED,"","Old School 35",8,0))
        } else {
            friendUpdate.add(UpdateFriendList.OfflineFriend(justAdded, userName,"",client.privilege.id,PROPERTY_REFERRED,""))
        }
        player.write(UpdateFriendList(friendUpdate))
    }


}