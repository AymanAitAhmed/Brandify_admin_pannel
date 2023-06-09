package com.example.pfeadminpannel.views.allUsers

import com.example.pfeadminpannel.module.localServer.User

sealed class ListSortingType(val stringType : String){
    object A_Z : ListSortingType("a_z")
    object Z_A : ListSortingType("z_a")
    object RANDOM : ListSortingType("random")
companion object{
    fun sortList(sortingType: String,listUsers : List<User>):List<User>{
        return when(sortingType){
            A_Z.stringType -> {
                listUsers.sortedBy { it.name }
            }

            RANDOM.stringType -> {
                listUsers.shuffled()
            }

            Z_A.stringType -> {
                listUsers.sortedByDescending { it.name }
            }

            else -> {
                listUsers
            }
        }
    }
}

}
