package com.vfgodoy.android_system_bar

import com.vfgodoy.android_system_bar.extension.toMoneyFormat
import org.junit.Assert.*
import org.junit.Test

class ExtensionsUnitTest {
    @Test
    fun toMoneyFormatTests(){
        assertEquals("R$10,25", "10.25".toMoneyFormat())
        assertEquals("R$1,00", "1".toMoneyFormat())
        assertEquals("R$11,00", "11".toMoneyFormat())
        assertEquals("R$111,00", "111".toMoneyFormat())
        assertEquals("R$111,10", "111.10".toMoneyFormat())
        assertEquals("R$111,11", "111.11".toMoneyFormat())
        assertEquals("R$1,20", "1.2".toMoneyFormat())
        assertEquals("R$1,30", "1.30".toMoneyFormat())
        assertEquals("R$74,80", "74.8".toMoneyFormat())
        assertEquals("R$79,80", "79.80".toMoneyFormat())
        assertEquals("R$114,80", "114.801".toMoneyFormat())
        assertEquals("R$26,80", "26.80456".toMoneyFormat())
        assertEquals("R$123,80", "123.8011111111".toMoneyFormat())
        assertEquals("R$0,00", "0".toMoneyFormat())
    }
}