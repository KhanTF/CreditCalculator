package ru.rager.credit.presentation.util

import android.text.Editable
import android.text.TextWatcher

class DecimalTextWatcher(
    private val mainSize: Int = 12,
    private val fractionalSize: Int = 2
) : TextWatcher {

    companion object {
        private const val CARRIAGE_SIZE = 3
        private const val WHITESPACE = " "
    }

    private var isDeleteToFormat = false
    private var toDeleteStartIndex = -1
    private var toDeleteEndIndex = -1

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (s == null) return
        if (!isDeleteToFormat && count == 1 && after == 0 && s[start].isWhitespace()) {
            toDeleteStartIndex = start - 1
            toDeleteEndIndex = start
        } else {
            toDeleteStartIndex = -1
            toDeleteEndIndex = -1
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s == null) return
        if (toDeleteStartIndex < 0 || toDeleteStartIndex >= s.length) {
            toDeleteStartIndex = -1
        }
        if (toDeleteEndIndex < 0 || toDeleteEndIndex >= s.length) {
            toDeleteEndIndex = -1
        }
        isDeleteToFormat = false
    }

    override fun afterTextChanged(s: Editable?) {
        if (s == null) {
            return
        }
        if (s.isEmpty()) {
            return
        }
        if (!preFormat(s)) {
            return
        }
        if (!formatMain(s)) {
            return
        }
        if (!formatFractional(s)) {
            return
        }
    }

    private fun preFormat(s: Editable): Boolean {
        if (toDeleteStartIndex >= 0 && toDeleteEndIndex >= 0) {
            isDeleteToFormat = true
            s.delete(toDeleteStartIndex, toDeleteEndIndex)
            return false
        }
        if (s.first().isPointer()) {
            isDeleteToFormat = true
            s.delete(0, 1)
            return false
        }
        if (s.first() == '0') {
            isDeleteToFormat = true
            s.delete(0, 1)
            return false
        }
        if (s.first().isWhitespace()) {
            isDeleteToFormat = true
            s.delete(0, 1)
            return false
        }
        if (s.last().isWhitespace()) {
            isDeleteToFormat = true
            s.delete(s.length - 1, s.length)
            return false
        }
        return true
    }

    private fun formatMain(s: Editable): Boolean {
        val mainEnd = getMainEnd(s)
        var counter = 0
        var carriageCounter = 0
        for (i in mainEnd - 1 downTo 0) {
            val symbol = s[i]
            if (symbol.isDigit()) {
                if (carriageCounter == CARRIAGE_SIZE) {
                    s.insert(i + 1, WHITESPACE)
                    return false
                } else {
                    counter++
                    carriageCounter++
                }
            } else if (symbol.isWhitespace()) {
                if (carriageCounter == CARRIAGE_SIZE) {
                    carriageCounter = 0
                } else {
                    isDeleteToFormat = true
                    s.delete(i, i + 1)
                    return false
                }
            } else {
                isDeleteToFormat = true
                s.delete(i, i + 1)
                return false
            }
        }
        if (counter > mainSize && mainEnd > 0) {
            isDeleteToFormat = true
            s.delete(mainEnd - 1, mainEnd)
            return false
        }
        return true
    }

    private fun formatFractional(s: Editable): Boolean {
        val fractionalStart = getMainEnd(s) + 1
        var counter = 0
        for (i in fractionalStart until s.length) {
            val symbol = s[i]
            if (symbol.isDigit()) {
                counter++
            } else {
                isDeleteToFormat = true
                s.delete(i, i + 1)
                return false
            }
        }
        if (counter > fractionalSize && s.length > 0) {
            isDeleteToFormat = true
            s.delete(s.length - 1, s.length)
            return false
        }
        return true
    }

    private fun getMainEnd(s: Editable): Int {
        val pointerIndex = s.indexOfFirst { it.isPointer() }
        return if (pointerIndex == -1) {
            s.length
        } else {
            pointerIndex
        }
    }

    private fun Char.isPointer() = this == '.'

}