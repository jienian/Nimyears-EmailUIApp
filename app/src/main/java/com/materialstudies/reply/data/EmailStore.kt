
package com.materialstudies.reply.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.materialstudies.reply.R
import com.materialstudies.reply.ui.home.Mailbox

/**
 * A static data store of [Email]s.
 */
object EmailStore {

    private val allEmails = mutableListOf(
        Email(
            0L,
            AccountStore.getContactAccountById(9L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            """
                文本
            """.trimIndent(),
            isStarred = true
        ),
        Email(
            1L,
            AccountStore.getContactAccountById(6L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            """
               文本
            """.trimIndent()
        ),
        Email(
            2L,
            AccountStore.getContactAccountById(5L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            "文本...",
            listOf(
                EmailAttachment(R.drawable.paris_1, "Bridge in Paris"),
                EmailAttachment(R.drawable.paris_2, "Bridge in Paris at night"),
                EmailAttachment(R.drawable.paris_3, "City street in Paris"),
                EmailAttachment(R.drawable.paris_4, "Street with bike in Paris")
            ),
            true
        ),
        Email(
            3L,
            AccountStore.getContactAccountById(8L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            """
                文本...
            """.trimIndent(),
            mailbox = Mailbox.SENT
        ),
        Email(
            4L,
            AccountStore.getContactAccountById(11L),
            listOf(
                AccountStore.getDefaultUserAccount(),
                AccountStore.getContactAccountById(8L),
                AccountStore.getContactAccountById(5L)
            ),
            "题目",
            """
              文本...
            """.trimIndent(),
            isStarred = true
        ),
        Email(
            5L,
            AccountStore.getContactAccountById(13L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            "文本"
        ),
        Email(
            6L,
            AccountStore.getContactAccountById(10L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            "文本",
            mailbox = Mailbox.SENT
        ),
        Email(
            7L,
            AccountStore.getContactAccountById(9L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            "文本..."
        ),
        Email(
            8L,
            AccountStore.getContactAccountById(13L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            """
              文本...
          """.trimIndent(),
            mailbox = Mailbox.TRASH
        ),
        Email(
            9L,
            AccountStore.getContactAccountById(10L),
            listOf(AccountStore.getDefaultUserAccount()),
            "自定义题目",
            """
            文本...
          """.trimIndent(),
            mailbox = Mailbox.DRAFTS
        ),
        Email(
            10L,
            AccountStore.getContactAccountById(5L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            """
           文本....
          """.trimIndent(),
            mailbox = Mailbox.TRASH
        ),
        Email(
            10L,
            AccountStore.getContactAccountById(5L),
            listOf(AccountStore.getDefaultUserAccount()),
            "题目",
            """
                        文本...
          """.trimIndent(),
            mailbox = Mailbox.SPAM
        )
    )

    private val _emails: MutableLiveData<List<Email>> = MutableLiveData()

    private val inbox: LiveData<List<Email>>
        get() = Transformations.map(_emails) { emails ->
            emails.filter { it.mailbox == Mailbox.INBOX }
        }

    private val starred: LiveData<List<Email>>
        get() = Transformations.map(_emails) { emails ->
            emails.filter { it.isStarred }
        }

    private val sent: LiveData<List<Email>>
        get() = Transformations.map(_emails) { emails ->
            emails.filter { it.mailbox == Mailbox.SENT }
        }

    private val trash: LiveData<List<Email>>
        get() = Transformations.map(_emails) { emails ->
            emails.filter { it.mailbox == Mailbox.TRASH }
        }

    private val spam: LiveData<List<Email>>
        get() = Transformations.map(_emails) { emails ->
            emails.filter { it.mailbox == Mailbox.SPAM }
        }

    private val drafts: LiveData<List<Email>>
        get() = Transformations.map(_emails) { emails ->
            emails.filter { it.mailbox == Mailbox.DRAFTS }
        }

    init {
        _emails.value = allEmails
    }

    fun getEmails(mailbox: Mailbox): LiveData<List<Email>> {
        return when (mailbox) {
            Mailbox.INBOX -> inbox
            Mailbox.STARRED -> starred
            Mailbox.SENT -> sent
            Mailbox.TRASH -> trash
            Mailbox.SPAM -> spam
            Mailbox.DRAFTS -> drafts
        }
    }

    /**
     * Get an [Email] with the given [id].
     */
    fun get(id: Long): Email? {
        return allEmails.firstOrNull { it.id == id }
    }

    /**
     * Create a new, blank [Email].
     */
    fun create(): Email {
        return Email(
            System.nanoTime(), // Unique ID generation.
            AccountStore.getDefaultUserAccount()
        )
    }

    /**
     * Create a new [Email] that is a reply to the email with the given [replyToId].
     */
    fun createReplyTo(replyToId: Long): Email {
        val replyTo = get(replyToId) ?: return create()
        return Email(
            id = System.nanoTime(),
            sender = replyTo.recipients.firstOrNull() ?: AccountStore.getDefaultUserAccount(),
            recipients = listOf(replyTo.sender) + replyTo.recipients,
            subject = replyTo.subject,
            isStarred = replyTo.isStarred,
            isImportant = replyTo.isImportant
        )
    }

    /**
     * Delete the [Email] with the given [id].
     */
    fun delete(id: Long) {
        update(id) { mailbox = Mailbox.TRASH }
    }

    /**
     * Update the [Email] with the given [id] by applying all mutations from [with].
     */
    fun update(id: Long, with: Email.() -> Unit) {
        allEmails.find { it.id == id }?.let {
            it.with()
            _emails.value = allEmails
        }
    }

    /**
     * Get a list of [EmailFolder]s by which [Email]s can be categorized.
     */
    fun getAllFolders() = listOf(
        "Receipts",
        "Pine Elementary",
        "Taxes",
        "Vacation",
        "Mortgage",
        "Grocery coupons"
    )
}

