

package com.materialstudies.reply.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.materialstudies.reply.R

/**
 * An static data store of [Account]s. This includes both [Account]s owned by the current user and
 * all [Account]s of the current user's contacts.
 */
object AccountStore {

    private val allUserAccounts = mutableListOf(
        Account(
            1L,
            0L,
            "Nimyears",
            "Nimyears",
            "nimyears@gmail.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_,
            true
        ),
        Account(
            2L,
            0L,
            "Nimyears",
            "Nimyears",
            "Nimyearsersonloveshiking@gmail.com",
            "Nimyearsersonloveshiking@work.com",
            R.drawable.ic_logo_
        ),
        Account(
            3L,
            0L,
            "Nimyears",
            "Nimyears",
            "Nimyearsersonc@google.com",
            "Nimyearsersonc@gmail.com",
            R.drawable.ic_logo_
        )
    )

    private val allUserContactAccounts = listOf(
        Account(
            4L,
            1L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            5L,
            2L,
            "Jienian",
            "jiang",
            "2129300193@qq.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            6L,
            3L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            7L,
            4L,
            "jienian",
            "Williams",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            8L,
            5L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            9L,
            6L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.avatar_express
        ),
        Account(
            10L,
            7L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            11L,
            8L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            12L,
            9L,
            "jienian",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        ),
        Account(
            13L,
            10L,
            "jiang",
            "jiang",
            "jienian@outlook.com",
            "jienian@outlook.com",
            R.drawable.ic_logo_
        )
    )

    private val _userAccounts: MutableLiveData<List<Account>> = MutableLiveData()
    val userAccounts: LiveData<List<Account>>
        get() = _userAccounts

    init {
        postUpdateUserAccountsList()
    }

    /**
     * Get the current user's default account.
     */
    fun getDefaultUserAccount() = allUserAccounts.first()

    /**
     * Get all [Account]s owned by the current user.
     */
    fun getAllUserAccounts() = allUserAccounts

    /**
     * Whether or not the given [Account.id] uid is an account owned by the current user.
     */
    fun isUserAccount(uid: Long): Boolean = allUserAccounts.any { it.uid == uid }

    fun setCurrentUserAccount(accountId: Long): Boolean {
        var updated = false
        allUserAccounts.forEachIndexed { index, account ->
            val shouldCheck = account.id == accountId
            if (account.isCurrentAccount != shouldCheck) {
                allUserAccounts[index] = account.copy(isCurrentAccount = shouldCheck)
                updated = true
            }
        }
        if (updated) postUpdateUserAccountsList()
        return updated
    }

    private fun postUpdateUserAccountsList() {
        val newList = allUserAccounts.toList()
        _userAccounts.value = newList
    }

    /**
     * Get the contact of the current user with the given [accountId].
     */
    fun getContactAccountById(accountId: Long): Account {
        return allUserContactAccounts.firstOrNull { it.id == accountId } ?: allUserContactAccounts.first()
    }
}