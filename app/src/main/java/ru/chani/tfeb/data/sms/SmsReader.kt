package ru.chani.tfeb.data.sms

import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import ru.chani.tfeb.domain.entity.CardEntity

class SmsReader {

    fun readSms(context: Context?): String {
        val projection = arrayOf(NUMBER_COLUMN, TEXT_COLUMN, TYPE_COLUMN)


        val cursor = context?.contentResolver?.query(
            Telephony.Sms.CONTENT_URI,
            projection, "address = ?", arrayOf(TFEB_SMS_NUMBER_FOR_CHECK_BALANCE),
            null
        )


        val numberColIdx = cursor?.getColumnIndex(NUMBER_COLUMN)
        val textColIdx = cursor?.getColumnIndex(TEXT_COLUMN)

        val isCursorContainsTfebNumber = checkCursorForContainSmsFromSelectedNumber(
            cursor,
            numberColIdx,
            TFEB_SMS_NUMBER_FOR_CHECK_BALANCE
        )

        cursor

        return if (isCursorContainsTfebNumber) {
            val smsContainsBalanceInfo = findSmsContainsBalanceInfo(cursor, textColIdx)
            if (smsContainsBalanceInfo != SMS_HAS_NOT_INFO_ABOUT_BALANCE) {
                val balance = parseBalanceFromSms(smsContainsBalanceInfo)
                balance
            } else {
                CardEntity.DEFAULT_CARD_BALANCE
            }
        } else {
            CardEntity.DEFAULT_CARD_BALANCE
        }
    }


    private fun checkCursorForContainSmsFromSelectedNumber(
        cursor: Cursor?,
        numberColumnIndex: Int?,
        selectedNumber: String
    ): Boolean {
        lateinit var number: String
        cursor?.let { cursor ->
            if (cursor.moveToFirst()) {
                var isChecked = false
                while (!isChecked) {
                    number = numberColumnIndex?.let { cursor.getString(it) }
                        ?: SMS_DB_HAS_NOT_CONTAINS_SMS_FROM_TFEB
                    if (number.contains(selectedNumber)) {
                        isChecked = true
                        return true
                    } else {
                        if (cursor.isLast) isChecked = true
                    }
                }
            }
        }
        return false
    }

    private fun findSmsContainsBalanceInfo(cursor: Cursor?, textColIdx: Int?): String {
        lateinit var tmpTextSMS: String
        lateinit var textSmsForReturn: String

        cursor?.let { cursor ->
            if (cursor.moveToFirst()) {
                var isChecked = false
                while (!isChecked) {
                    tmpTextSMS = textColIdx?.let { cursor.getString(it) } ?: SMS_HAS_NOT_INFO_ABOUT_BALANCE
                    if (tmpTextSMS.contains(KEY_WORD_INTO_SMS_CONTAINS_BALANCE_INFO)) {
                        isChecked = true
                        textSmsForReturn = tmpTextSMS
                    } else {
                        if (!cursor.isLast) {
                            cursor.moveToNext()
                        } else {
                            textSmsForReturn = SMS_HAS_NOT_INFO_ABOUT_BALANCE
                            isChecked = true
                        }
                    }
                }
                cursor.close()
            }
        }
        return textSmsForReturn
    }


    private fun parseBalanceFromSms(textSMS: String): String {
        //convert String to List of lines
        val lines = textSMS.lines()
        //get line contains balance
        val lineWithBalanceInfo = lines[INDEX_LINE_CONTAINS_BALANCE_INFO]
        val balanceWithTmtLabel =
            lineWithBalanceInfo
                .split(SEPARATOR_FOR_FEEL_BALANCE)[INDEX_BALANCE_AND_TMT_LABEL]
        val balance = balanceWithTmtLabel
            .split(SEPARATOR_FOR_BALANCE_AND_TMT_LABEL)[INDEX_BALANCE]
        return balance
    }


    companion object {

        private const val TFEB_SMS_NUMBER_FOR_CHECK_BALANCE = "+0717"
        private const val SMS_DB_HAS_NOT_CONTAINS_SMS_FROM_TFEB = "00000000"

        private const val KEY_WORD_INTO_SMS_CONTAINS_BALANCE_INFO = "Balance"

        private const val INDEX_LINE_CONTAINS_BALANCE_INFO = 1
        private const val INDEX_BALANCE_AND_TMT_LABEL = 1
        private const val INDEX_BALANCE = 0
        private const val SEPARATOR_FOR_FEEL_BALANCE = ":"
        private const val SEPARATOR_FOR_BALANCE_AND_TMT_LABEL = " "
        private const val SMS_HAS_NOT_INFO_ABOUT_BALANCE = "0"


        private const val NUMBER_COLUMN = Telephony.TextBasedSmsColumns.ADDRESS
        private const val TEXT_COLUMN = Telephony.TextBasedSmsColumns.BODY
        private const val TYPE_COLUMN = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent
    }
}