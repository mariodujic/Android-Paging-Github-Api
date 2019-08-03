package com.groundzero.github.data

import androidx.test.platform.app.InstrumentationRegistry
import com.groundzero.github.data.local.LocalInstance
import com.groundzero.github.data.local.LocalRepository
import com.groundzero.github.utils.LOCAL_USERNAME_KEY
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalRepositoryTest {

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val localInstance = LocalInstance(context)
    private val localRepository = LocalRepository(localInstance)

    @Test
    fun set_shared_preference_data_true() {
        localRepository.setData(LOCAL_USERNAME_KEY, USERNAME).apply()
        assertEquals(localRepository.getData(LOCAL_USERNAME_KEY), USERNAME)
    }

    companion object {
        const val USERNAME = "John"
    }
}