

package com.materialstudies.reply.data

import com.materialstudies.reply.R

/**
 * [SearchSuggestion]的静态数据存储。
 */
object SearchSuggestionStore {

  val YESTERDAY_SUGGESTIONS = listOf(
    SearchSuggestion(
      R.drawable.ic_schedule,
      "家",
      "国家地址"
    ),
    SearchSuggestion(
      R.drawable.ic_home,
      "家",
      "国家地址"
    )
  )

  val THIS_WEEK_SUGGESTIONS = listOf(
    SearchSuggestion(
      R.drawable.ic_schedule,
      "家",
      "国家地址"
    ),
    SearchSuggestion(
      R.drawable.ic_schedule,
      "家",
      "国家地址"
    ),
    SearchSuggestion(
      R.drawable.ic_schedule,
      "家",
      "国家地址"
    )
  )
}
